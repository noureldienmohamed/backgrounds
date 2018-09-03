package com.example.nour.backgrounds.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.nour.backgrounds.Interface.ItemClickListnener;
import com.example.nour.backgrounds.R;

public class BackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public ImageView background;
    private ItemClickListnener itemClickListnener;
    public void setItemClickListnener(ItemClickListnener itemClickListnener) {
        this.itemClickListnener = itemClickListnener;
    }

    public BackViewHolder(View itemView) {
        super(itemView);
        background = itemView.findViewById(R.id.imageb);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListnener.onClick(v,getAdapterPosition());

    }
}
