package com.example.dundone.auction.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dundone.auction.viewholder.ItemListViewHolder;
import com.example.dundone.data.item.ItemData;
import com.example.dundone.R;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListViewHolder> {
    private ArrayList<ItemData> itemList;
    private Context context;

    public ItemListAdapter(ArrayList<ItemData> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_item_data, viewGroup, false) ;
        ItemListViewHolder vh = new ItemListViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder viewHolder, int i) {
        ItemData item = itemList.get(i);
        viewHolder.tvItemName.setText(item.getItemName());
        String url = "https://img-api.neople.co.kr/df/items/" + item.getItemId();
        Glide.with(context).load(url).into(viewHolder.ivItemImage);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
