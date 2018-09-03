package com.example.nour.backgrounds.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nour.backgrounds.Interface.ItemClickListnener;
import com.example.nour.backgrounds.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView category_name;
    public ImageView background_image;
    ItemClickListnener itemClickListnener;

    public void setItemClickListnener(ItemClickListnener itemClickListnener) {
        this.itemClickListnener = itemClickListnener;
    }

    public CategoryViewHolder(View itemView) {
        super(itemView);
        background_image=itemView.findViewById(R.id.image);
        category_name=itemView.findViewById(R.id.name);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
    itemClickListnener.onClick(v,getAdapterPosition());
    }
}
