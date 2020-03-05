package com.example.dundone.main.character;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dundone.R;
import com.example.dundone.data.character.CharBaseData;
import com.example.dundone.data.item.EpicData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseInfoAdapter<E> extends RecyclerView.Adapter<BaseInfoAdapter<E>.BaseInfoViewHolder> {

    private OnItemClickListener mListener = null;
    private Context mContext;
    private ArrayList<E> mItemList;

    public interface OnItemClickListener {
        void onItemCilckListener(View v, int p);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    BaseInfoAdapter(Context context, ArrayList<E> list){
        mContext = context;
        mItemList=list;
    }
    class BaseInfoViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvServer;
        ImageView ivImage;

        BaseInfoViewHolder(@NonNull View v) {
            super(v);
            View vCharInfo=v.findViewById(R.id.base_info);
            tvName = vCharInfo.findViewById(R.id.tv_name);
            tvServer = vCharInfo.findViewById(R.id.tv_detail_info);
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
    public BaseInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_base_info, parent, false) ;
        BaseInfoViewHolder vh = new BaseInfoViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseInfoViewHolder holder, int position) {
        E itemE = mItemList.get(position);
        if(itemE instanceof CharBaseData) {
            CharBaseData item = (CharBaseData)itemE;
            holder.tvName.setText(item.getCharName());
            holder.tvServer.setText(item.getServerData().getServerName());
            String url = "https://img-api.neople.co.kr/df/servers/" + item.getServerData().getServerId() + "/characters/" + item.getCharId() + "?zoom=3";
            Glide.with(mContext).load(url).into(holder.ivImage);
        }
        else if(itemE instanceof EpicData){
            EpicData item = (EpicData)itemE;
            holder.tvName.setText(item.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
