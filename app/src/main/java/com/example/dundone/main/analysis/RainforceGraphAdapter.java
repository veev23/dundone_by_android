package com.example.dundone.main.analysis;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.dundone.R;
import com.example.dundone.data.etc.RainforceData;
import com.example.dundone.main.character.UpgradeRateFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class RainforceGraphAdapter extends RecyclerView.Adapter<RainforceGraphAdapter.ViewHolder> {

        private FragmentManager fmManager;
    private Context mContext;
    private ArrayList<RainforceData> mList;

    public RainforceGraphAdapter(Context mContext, ArrayList<RainforceData> mList, FragmentManager fm) {
        this.mContext = mContext;
        this.mList = mList;
        this.fmManager = fm;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvLevel;
        private TextView tvPercent;
        private FrameLayout flGrapeFragment;
        private boolean isVisible;
        ViewHolder(@NonNull View v){
            super(v);
            isVisible = false;
            tvLevel = v.findViewById(R.id.rainforce_level);
            tvPercent = v.findViewById(R.id.rainforce_percent);
            flGrapeFragment = v.findViewById(R.id.fragment_graph);
            v.findViewById(R.id.rainforce_mini).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isVisible){
                        isVisible = false;
                        flGrapeFragment.setVisibility(View.VISIBLE);
                        int pos = getAdapterPosition();
                        FragmentTransaction transaction = fmManager.beginTransaction();
                        UpgradeRateFragment fragment = new UpgradeRateFragment();
                        Bundle bundle=new Bundle(2);
                        bundle.putInt("successCount", mList.get(pos).getSuccessCount());
                        bundle.putInt("failCount", mList.get(pos).getFailCount());
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.fragment_graph, fragment);
                    }
                    else{
                        isVisible = true;
                        flGrapeFragment.setVisibility(View.GONE);
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_graph, parent, false) ;
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        RainforceData item = mList.get(pos);
        holder.tvLevel.setText(String.valueOf(item.getLevel()));
        int sum = item.getSuccessCount() + item.getFailCount();
        int successRate = (int)((double)item.getSuccessCount() * 100 / (double)sum);
        holder.tvPercent.setText(String.valueOf(successRate));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
