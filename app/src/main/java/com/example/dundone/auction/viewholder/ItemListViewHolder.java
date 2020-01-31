package com.example.dundone.auction.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dundone.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListViewHolder extends RecyclerView.ViewHolder {
    public TextView tvItemName;
    public ImageView ivItemImage;
    public ItemListViewHolder(@NonNull View itemView) {
        super(itemView);
        tvItemName = itemView.findViewById(R.id.item_item_name);
        ivItemImage = itemView.findViewById(R.id.item_item_img);
    }
}