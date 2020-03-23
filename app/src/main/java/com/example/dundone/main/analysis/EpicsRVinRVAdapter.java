package com.example.dundone.main.analysis;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.dundone.common_class.CustomRecyclerDecoration;
import com.example.dundone.data.item.EpicCountData;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EpicsRVinRVAdapter extends RecyclerView.Adapter<EpicsRVinRVAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ArrayList<EpicCountData>> mList;

    public EpicsRVinRVAdapter(Context mContext, ArrayList<ArrayList<EpicCountData>> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rvList;
        private EpicPercentListAdapter adapter;
        ViewHolder(@NonNull View v){
            super(v);
            rvList = (RecyclerView)v;
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView v = new RecyclerView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        v.setLayoutParams(params);
        v.addItemDecoration(new CustomRecyclerDecoration(10));
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        if(holder.adapter == null) {
            holder.rvList.setLayoutManager(new LinearLayoutManager(mContext));
            holder.adapter = new EpicPercentListAdapter(mContext, mList.get(pos));
            holder.rvList.setAdapter(holder.adapter);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
