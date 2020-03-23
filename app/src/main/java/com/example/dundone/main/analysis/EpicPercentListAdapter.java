package com.example.dundone.main.analysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dundone.R;
import com.example.dundone.data.item.EpicCountData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EpicPercentListAdapter extends RecyclerView.Adapter<EpicPercentListAdapter.ViewHolder> {

    private OnItemClickListener mListener = null;
    private Context mContext;
    private ArrayList<EpicCountData> mItemList;
    private int sum = 0;

    public interface OnItemClickListener {
        void onItemCilckListener(View v, int p);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public EpicPercentListAdapter(Context context, ArrayList<EpicCountData> list){
        mContext = context;
        mItemList=list;
        for(EpicCountData i : list){
            sum += i.getCnt();
        }
    }
    public void add(EpicCountData data){
        sum += data.getCnt();
        mItemList.add(data);
        notifyItemChanged(mItemList.size()-1);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDetail;
        TextView tvPercent;
        ImageView ivImage;

        ViewHolder(@NonNull View v) {
            super(v);
            View vCharInfo=v.findViewById(R.id.base_info);
            tvName = vCharInfo.findViewById(R.id.tv_name);
            tvDetail = vCharInfo.findViewById(R.id.tv_detail_info);
            tvPercent = vCharInfo.findViewById(R.id.tv_percent);
            ivImage = vCharInfo.findViewById(R.id.iv_descript_img);

            v.setOnClickListener(new View.OnClickListener() {
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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_count_info, parent, false) ;
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EpicCountData item = mItemList.get(position);
            holder.tvName.setText(item.getName());
        holder.tvDetail.setText(mContext.getString(R.string.drop_count, item.getCnt()));
        double percent = 100.0*(double)item.getCnt()/(double)sum;
        holder.tvPercent.setText(mContext.getString(R.string.percents_realnum, percent));
            String url = "https://img-api.neople.co.kr/df/items/"+item.getItemId();
            Glide.with(mContext)
                    .load(url)
                    .centerCrop()
                    .into(holder.ivImage);
        }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}

