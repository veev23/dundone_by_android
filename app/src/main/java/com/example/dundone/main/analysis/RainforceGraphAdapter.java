package com.example.dundone.main.analysis;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.dundone.R;
import com.example.dundone.data.etc.ReinforceData;
import com.example.dundone.main.character.UpgradeRateFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class RainforceGraphAdapter extends RecyclerView.Adapter<RainforceGraphAdapter.ViewHolder> {

        private FragmentManager fmManager;
    private Context mContext;
    private ArrayList<ReinforceData> mList;
    private String type;

    public RainforceGraphAdapter(Context mContext, ArrayList<ReinforceData> mList, FragmentManager fm, String type) {
        this.mContext = mContext;
        this.mList = mList;
        this.fmManager = fm;
        this.type = type;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvLevel;
        private TextView tvPercent;
        private FrameLayout flGrapeFragment;
        private boolean isVisible;
        private boolean isFragmentCreated;
        ViewHolder(@NonNull View v){
            super(v);
            isVisible = false;
            isFragmentCreated = false;
            tvLevel = v.findViewById(R.id.rainforce_level);
            tvPercent = v.findViewById(R.id.rainforce_percent);
            flGrapeFragment = v.findViewById(R.id.fragment_graph);
            v.findViewById(R.id.rainforce_mini).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isVisible) {
                        isVisible = true;
                        flGrapeFragment.setVisibility(View.VISIBLE);
                    }
                    else{
                        isVisible = false;
                        flGrapeFragment.setVisibility(View.GONE);
                    }
                    if(!isFragmentCreated){
                        isFragmentCreated=true;
                        // Delete old fragment
                        int containerId = flGrapeFragment.getId();// Get container id
                        Fragment oldFragment = fmManager.findFragmentById(containerId);
                        if(oldFragment != null) {
                            fmManager.beginTransaction().remove(oldFragment).commit();
                        }
                        int newContainerId = View.generateViewId();// My method
                        flGrapeFragment.setId(newContainerId);// Set container id

                        int pos = getAdapterPosition();
                        FragmentTransaction transaction = fmManager.beginTransaction();
                        UpgradeRateFragment fragment = new UpgradeRateFragment();
                        Bundle bundle=new Bundle(2);
                        bundle.putInt("successCount", mList.get(pos).getSuccessCount());
                        bundle.putInt("failCount", mList.get(pos).getFailCount());
                        fragment.setArguments(bundle);
                        transaction.replace(flGrapeFragment.getId(), fragment);
                        transaction.commit();
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
        ReinforceData item = mList.get(pos);
        holder.tvLevel.setText(item.getLevel()+type);
        int sum = item.getSuccessCount() + item.getFailCount();
        int successRate = (int)((double)item.getSuccessCount() * 100 / (double)sum);
        holder.tvPercent.setText(mContext.getString(R.string.percents, successRate));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
