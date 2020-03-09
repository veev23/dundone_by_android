package com.example.dundone.main.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.ViewHolder> {

    private OnItemClickListener mListener = null;

    public interface OnItemClickListener {
        void onItemCilckListener(View v, int p);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public ImageViewAdapter(ArrayList<String> srcList, Context mContext) {
        this.srcList = srcList;
        this.mContext = mContext;
    }

    private ArrayList<String> srcList;
    private Context mContext;
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
             ivView = (ImageView)itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos !=RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemCilckListener(v,pos);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView v = new ImageView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        v.setLayoutParams(params);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        Glide.with(mContext).load(srcList.get(pos)).into(holder.ivView);
    }

    @Override
    public int getItemCount() {
        return srcList.size();
    }
}
