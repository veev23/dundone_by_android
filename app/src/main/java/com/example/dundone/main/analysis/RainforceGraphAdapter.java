package com.example.dundone.main.analysis;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
    private RecyclerView rvBinded;
    private ArrayList<Integer> mViewIds = new ArrayList<>();
    private int activeIdx = -1;
    private FrameLayout activeFL = null;

    public RainforceGraphAdapter(Context mContext, ArrayList<ReinforceData> mList, FragmentManager fm, String type, RecyclerView bindedRV) {
        this.mContext = mContext;
        this.mList = mList;
        this.fmManager = fm;
        this.type = type;
        this.rvBinded = bindedRV;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLevel;
        private TextView tvPercent;
        private FrameLayout flGrapeFragment;

        private void showGraph(ReinforceData reinforceData) {
            FragmentTransaction transaction = fmManager.beginTransaction();
            UpgradeRateFragment fragment = new UpgradeRateFragment();
            Bundle bundle = new Bundle(2);
            bundle.putInt("successCount", reinforceData.getSuccess());
            bundle.putInt("failCount", reinforceData.getFail());
            fragment.setArguments(bundle);
            transaction.replace(flGrapeFragment.getId(), fragment);
            transaction.commit();
        }

        ViewHolder(@NonNull View v) {
            super(v);
            tvLevel = v.findViewById(R.id.rainforce_level);
            tvPercent = v.findViewById(R.id.rainforce_percent);
            flGrapeFragment = v.findViewById(R.id.fragment_graph);

            int newId = View.generateViewId();
            flGrapeFragment.setId(newId);// Set container id
            v.findViewById(R.id.rainforce_mini).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos == RecyclerView.NO_POSITION) return;
                    if(activeFL !=null) {
                        activeFL.setVisibility(View.GONE);
                        // Delete old fragment
                        /*Fragment oldFragment = fmManager.findFragmentById(activeFL.getId());
                        if (oldFragment != null) {
                            fmManager.beginTransaction().remove(oldFragment).commit();
                        }
                        */
                    }
                    if (activeIdx != pos) {
                        flGrapeFragment.setVisibility(View.VISIBLE);
                        activeIdx = pos;
                        activeFL = flGrapeFragment;
                        showGraph(mList.get(pos));
                        rvBinded.smoothScrollToPosition(pos);
                    } else {
                        activeIdx = -1;
                        activeFL = null;
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

        View view = inflater.inflate(R.layout.item_graph, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        if (activeIdx == pos) {
            holder.flGrapeFragment.setVisibility(View.VISIBLE);
        } else {
            holder.flGrapeFragment.setVisibility(View.GONE);
        }
        ReinforceData item = mList.get(pos);
        holder.tvLevel.setText(item.getLevel() + type);
        int sum = item.getSuccess() + item.getFail();
        int successRate = (int) ((double) item.getSuccess() * 100 / (double) sum);
        holder.tvPercent.setText(mContext.getString(R.string.percents, successRate));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
