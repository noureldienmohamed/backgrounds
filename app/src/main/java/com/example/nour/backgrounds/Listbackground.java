package com.example.nour.backgrounds;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nour.backgrounds.Common.Common;
import com.example.nour.backgrounds.Interface.ItemClickListnener;
import com.example.nour.backgrounds.Model.BackgroudItem;
import com.example.nour.backgrounds.ViewHolder.BackViewHolder;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class Listbackground extends AppCompatActivity {
Query query;
FirebaseRecyclerOptions<BackgroudItem> options;
FirebaseRecyclerAdapter<BackgroudItem,BackViewHolder> adpter;
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbackground);

        Toolbar toolbar = findViewById(R.id.toolbarr);
        toolbar.setTitle(Common.Category_selscted);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView=findViewById(R.id.recycler_list_Back);
        //recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        loadBackgroundList();
    }

    private void loadBackgroundList() {
        query=FirebaseDatabase.getInstance().getReference(Common.STR_BACKGROUND)
         .orderByChild("CategoryId").equalTo( Common.CAREGORY_ID_SELECTED);
        options=new FirebaseRecyclerOptions.Builder<BackgroudItem>()
                .setQuery(query,BackgroudItem.class)
                .build();
        adpter= new FirebaseRecyclerAdapter<BackgroudItem, BackViewHolder> (options ) {
            @NonNull
            @Override
            protected void onBindViewHolder(@NonNull final BackViewHolder holder, int position, @NonNull final BackgroudItem model) {
                Picasso.with(getBaseContext())
                        .load(model.getImageUrl())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.background, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                            Picasso.with(getBaseContext())
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
                        Intent intent = new Intent(Listbackground.this,ViewBackground.class);
                        Common.select_background=model;
                        Common.select_background_key=adpter.getRef(position).getKey();
                        startActivity(intent);
                    }
                });
            }
            @Override
            public BackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemview = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_background_item,parent,false);
                int height = parent.getMeasuredHeight()/2;
                itemview.setMinimumHeight(height);
                return new BackViewHolder(itemview);
            }


        };
        adpter.startListening();
        recyclerView.setAdapter(adpter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adpter!=null)
        {
            adpter.startListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adpter!=null)
        {
            adpter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adpter!=null)
        {
            adpter.stopListening();
        }

        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
        finish();
        return super.onOptionsItemSelected(item);
    }
}
