package com.example.nour.backgrounds.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nour.backgrounds.Adapter.MyRecentAdapter;
import com.example.nour.backgrounds.Database.DataSource.IRecentsDataSource;
import com.example.nour.backgrounds.Database.DataSource.RecentRepository;
import com.example.nour.backgrounds.Database.LocalDatabase.LocalDatabase;
import com.example.nour.backgrounds.Database.LocalDatabase.RecentDataSource;
import com.example.nour.backgrounds.Database.Recents;
import com.example.nour.backgrounds.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class RecentsFragment extends Fragment {
@SuppressLint("StaticFieldLeak")
private static RecentsFragment Instance=null;
RecyclerView recyclerView;
List<Recents>recentsList;
MyRecentAdapter adapter;
Context context=null;
CompositeDisposable compositeDisposable;
RecentRepository recentRepository;
    @SuppressLint("ValidFragment")
    public RecentsFragment(Context context) {
        // Required empty public constructor
        this.context= context;
        compositeDisposable= new CompositeDisposable();
        LocalDatabase database = (LocalDatabase) LocalDatabase.getInstance(context);
        recentRepository= (RecentRepository) RecentRepository.getIntance(RecentDataSource.getInstance( database.recentsDa()));
    }

    public static RecentsFragment getInstance (Context context) {
        if (Instance == null)
            Instance = new RecentsFragment(context);
        return Instance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_recents, container, false);
      recyclerView=view.findViewById(R.id.recycler_recent);
      recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recentsList=new ArrayList<>();
        adapter=new MyRecentAdapter(context,recentsList);
        recyclerView.setAdapter(adapter);
        loadRecents();
      return view;
    }

    private void loadRecents() {
        Disposable disposable = recentRepository.getAllRecents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Recents>>() {
                    @Override
                    public void accept(List<Recents> recents) throws Exception {
                        onGetAllRecentsSuccess(recents);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ERROR",throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void onGetAllRecentsSuccess(List<Recents> recents) {
        recentsList.clear();
        recentsList.addAll(recents);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
