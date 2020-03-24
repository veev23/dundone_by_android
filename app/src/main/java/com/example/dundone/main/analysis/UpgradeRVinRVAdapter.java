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
    private ArrayList<RainforceGraphAdapter> mAdapterList;
    private FragmentManager fm;


    public UpgradeRVinRVAdapter(Context mContext, ArrayList<Pair<String, ArrayList<ReinforceData>>> mList, FragmentManager fm) {
        this.mContext = mContext;
        this.mList = mList;
        this.fm = fm;
        mAdapterList = new ArrayList<>();
        for (int pos = 0; pos < mList.size(); pos++) {
            mAdapterList.add(new RainforceGraphAdapter(mContext, mList.get(pos), fm));
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvList;
        private RainforceGraphAdapter adapter;

        ViewHolder(@NonNull View v) {
            super(v);
            rvList = (RecyclerView) v;
            rvList.setLayoutManager(new LinearLayoutManager(mContext));
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
        mAdapterList.get(pos).setOnItemActiveListener(new RainforceGraphAdapter.OnItemActiveListener() {
            @Override
            public void onItemActiveListener(View v, int p) {
                holder.rvList.smoothScrollToPosition(pos);
            }
        });
        holder.rvList.setAdapter(mAdapterList.get(pos));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
