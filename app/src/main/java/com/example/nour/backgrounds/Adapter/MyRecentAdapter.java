package com.example.nour.backgrounds.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nour.backgrounds.Common.Common;
import com.example.nour.backgrounds.Database.Recents;
import com.example.nour.backgrounds.Interface.ItemClickListnener;
import com.example.nour.backgrounds.Listbackground;
import com.example.nour.backgrounds.Model.BackgroudItem;
import com.example.nour.backgrounds.R;
import com.example.nour.backgrounds.ViewBackground;
import com.example.nour.backgrounds.ViewHolder.BackViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecentAdapter extends RecyclerView.Adapter<BackViewHolder> {
    private Context context;
    private List<Recents>recents;

    public MyRecentAdapter(Context context, List<Recents> recents) {
        this.context = context;
        this.recents = recents;
    }

    @NonNull
    @Override
    public BackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_background_item,parent,false);
        int height = parent.getMeasuredHeight()/2;
        itemview.setMinimumHeight(height);
        return new BackViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final BackViewHolder holder, final int position) {
        Picasso.with(context)
                .load(recents.get(position).getImageLink())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.background, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(recents.get(position).getImageLink())
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
                Intent intent = new Intent(context,ViewBackground.class);
                BackgroudItem backgroudItem = new BackgroudItem();
                backgroudItem.setCategoryId(recents.get(position).getCategoryId());
                backgroudItem.setImageUrl(recents.get(position).getImageLink());
                Common.select_background=backgroudItem;
                Common.select_background_key=recents.get(position).getKey();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
