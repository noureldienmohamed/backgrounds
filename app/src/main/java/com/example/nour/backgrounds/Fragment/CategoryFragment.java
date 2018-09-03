package com.example.nour.backgrounds.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AndroidException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.nour.backgrounds.Common.Common;
import com.example.nour.backgrounds.HomeActivity;
import com.example.nour.backgrounds.Interface.ItemClickListnener;
import com.example.nour.backgrounds.Listbackground;
import com.example.nour.backgrounds.MainActivity;
import com.example.nour.backgrounds.Model.CategoryItem;
import com.example.nour.backgrounds.R;
import com.example.nour.backgrounds.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class CategoryFragment extends Fragment {
FirebaseRecyclerOptions<CategoryItem> options;
RecyclerView recyclerView;
FirebaseDatabase database;
DatabaseReference CategoryBackground;
FirebaseRecyclerAdapter<CategoryItem,CategoryViewHolder>adapter;


private static CategoryFragment Instance=null;


    public CategoryFragment() {
        database=FirebaseDatabase.getInstance();
        CategoryBackground=database.getReference(Common.STR_CATEGORY_BACKGROUND);
        options=new FirebaseRecyclerOptions.Builder<CategoryItem>().setQuery(CategoryBackground,CategoryItem.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<CategoryItem, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position, @NonNull final CategoryItem model) {
                Picasso.with(getActivity())
                        .load(model.getImageLinke())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.background_image, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(getActivity())
                                        .load(model.getImageLinke())
                                        .error(R.drawable.ic_terrain_black_24dp)
                                        .into(holder.background_image, new Callback() {
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
                holder.category_name.setText(model.getName());
                holder.setItemClickListnener(new ItemClickListnener() {
                    @Override
                    public void onClick(View view, int position) {
                 Common.CAREGORY_ID_SELECTED=adapter.getRef(position).getKey();
                 Common.Category_selscted=model.getName();
                        Intent intent = new Intent (getActivity(), Listbackground.class);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_category_item,parent,false);
                return new CategoryViewHolder(itemView);
            }
        };
    }
public static CategoryFragment getInstance (){
        if (Instance==null)
            Instance=new CategoryFragment();
        return Instance;

}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_category, container, false);
    recyclerView=view.findViewById(R.id.recycler_category);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
    recyclerView.setLayoutManager(gridLayoutManager);
    setCategory();
    return view;
    }

    private void setCategory() {
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter!=null)
        {
            adapter.startListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null)
        {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter!=null)
        {
            adapter.stopListening();
        }
    }


}



