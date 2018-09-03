package com.example.nour.backgrounds.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.nour.backgrounds.Common.Common;
import com.example.nour.backgrounds.Interface.ItemClickListnener;
import com.example.nour.backgrounds.Listbackground;
import com.example.nour.backgrounds.Model.BackgroudItem;
import com.example.nour.backgrounds.R;
import com.example.nour.backgrounds.ViewBackground;
import com.example.nour.backgrounds.ViewHolder.BackViewHolder;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference categoryBackground;
    FirebaseRecyclerOptions<BackgroudItem>options;
    FirebaseRecyclerAdapter<BackgroudItem,BackViewHolder>adapter;
    private static TrendingFragment Instance=null;

    public TrendingFragment() {
database=FirebaseDatabase.getInstance();
categoryBackground=database.getReference(Common.STR_BACKGROUND);
        Query query=categoryBackground.orderByChild("viewCount")
                .limitToLast(10);
        options= new  FirebaseRecyclerOptions.Builder<BackgroudItem>()
                .setQuery(query,BackgroudItem.class)
                .build();
        adapter= new FirebaseRecyclerAdapter<BackgroudItem, BackViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final BackViewHolder holder, int position, @NonNull final BackgroudItem model) {
                Picasso.with(getActivity())
                        .load(model.getImageUrl())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.background, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(getActivity())
                                        .load(model.getImageUrl())
                                        .error(R.drawable.ic_terrain_black_24dp)
                                        .into(holder.background, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {
                                                Log.e("Error Edemt","couldn't fetch image");
                                            }
                                        });
                            }
                        });
                holder.setItemClickListnener(new ItemClickListnener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(getActivity(),ViewBackground.class);
                        Common.select_background=model;
                        Common.select_background_key=adapter.getRef(position).getKey();
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public BackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemview = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_background_item,parent,false);
                int height = parent.getMeasuredHeight()/2;
                itemview.setMinimumHeight(height);
                return new BackViewHolder(itemview);            }
        };
    }

    public static TrendingFragment getInstance (){
        if (Instance==null)
            Instance=new TrendingFragment();
        return Instance;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_daily_popular, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_trending);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadTrendList();
        return view;
    }

    private void loadTrendList() {
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        if (adapter !=null)
        adapter.stopListening();
        super.onStop();
    }

    @Override
    public void onStart() {
        if (adapter!=null)
            adapter.startListening();
        super.onStart();
    }
}