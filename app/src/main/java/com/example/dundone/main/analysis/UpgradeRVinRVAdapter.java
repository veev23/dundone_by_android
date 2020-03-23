package com.example.dundone.main.analysis;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import com.example.dundone.common_class.CustomRecyclerDecoration;
import com.example.dundone.data.etc.ReinforceData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UpgradeRVinRVAdapter extends RecyclerView.Adapter<UpgradeRVinRVAdapter.ViewHolder> {
    private Context mContext;
    //Pair<Tab name, Rainforce list>
    private ArrayList<Pair<String, ArrayList<ReinforceData>>> mList;
    private FragmentManager fm;

    public UpgradeRVinRVAdapter(Context mContext, ArrayList<Pair<String, ArrayList<ReinforceData>>> mList, FragmentManager fm) {
        this.mContext = mContext;
        this.mList = mList;
        this.fm = fm;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rvList;
        private RainforceGraphAdapter adapter;
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
            holder.adapter = new RainforceGraphAdapter(mContext, mList.get(pos), fm, holder.rvList);
            holder.rvList.setAdapter(holder.adapter);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
