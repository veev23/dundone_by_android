package com.example.dundone.main.analysis;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.dundone.common_class.CustomRecyclerDecoration;
import com.example.dundone.data.etc.ResDungeonList;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EpicsRVinRVAdapter extends RecyclerView.Adapter<EpicsRVinRVAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ResDungeonList.DungeonEpic> mDungeonList;
    private ArrayList<EpicPercentListAdapter> mAdapterList;

    public EpicsRVinRVAdapter(Context mContext, ArrayList<ResDungeonList.DungeonEpic> dungeonEpics) {
        this.mContext = mContext;
        this.mDungeonList = dungeonEpics;
        mAdapterList = new ArrayList<>();
        for (int pos = 0; pos < mDungeonList.size(); pos++)
            mAdapterList.add(new EpicPercentListAdapter(mContext, mDungeonList.get(pos)));
    }

    public void search(final int selectedPos, final String itemName) {
        if(itemName.equals("") && EpicPercentListAdapter.getItemSearchName().equals("")) return;
        for(EpicPercentListAdapter i : mAdapterList){
            i.itemAllClear();
            i.notifyDataSetChanged();
        }
        mAdapterList.get(selectedPos).reqGetDropEpics(itemName);
    }
    public void initUpdate(final int selectedPos){
        mAdapterList.get(selectedPos).reqGetDropEpics(
                EpicPercentListAdapter.getItemSearchName()
        );
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvList;

        ViewHolder(@NonNull View v) {
            super(v);
            rvList = (RecyclerView) v;
            rvList.setLayoutManager(new LinearLayoutManager(mContext));
            rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (rvList.canScrollVertically(-1)) {
                        int pos = getAdapterPosition();
                        if (pos == RecyclerView.NO_POSITION) return;
                        EpicPercentListAdapter adapter = mAdapterList.get(pos);
                        adapter.reqGetDropEpics(null);
                    }
                }
            });
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
        EpicPercentListAdapter adapter = mAdapterList.get(pos);
        holder.rvList.setAdapter(adapter);
    }
    @Override
    public int getItemCount() {
        return mDungeonList == null ? 0 : mDungeonList.size();
    }
}
