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

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterSearchResultAdapter extends RecyclerView.Adapter<CharacterSearchResultAdapter.CharacterBaseViewHolder> {

    private OnItemClickListener mListener = null;
    private Context mContext;
    private ArrayList<CharBaseData> mItemList;

    public interface OnItemClickListener {
        void onItemCilckListener(View v, int p);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    CharacterSearchResultAdapter(Context context, ArrayList<CharBaseData> list){
        mContext = context;
        mItemList=list;
    }
    class CharacterBaseViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvServer;
        ImageView ivCharImage;

        CharacterBaseViewHolder(@NonNull View v) {
            super(v);
            View vCharInfo=v.findViewById(R.id.char_info_in_char_info);
            tvName = vCharInfo.findViewById(R.id.tv_char_name);
            tvServer = vCharInfo.findViewById(R.id.tv_char_server);
            ivCharImage = vCharInfo.findViewById(R.id.iv_char_img);
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
    public CharacterBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_char_info, parent, false) ;
        CharacterBaseViewHolder vh = new CharacterBaseViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterBaseViewHolder holder, int position) {
        CharBaseData item = mItemList.get(position);
        holder.tvName.setText(item.getCharName());
        holder.tvServer.setText(item.getServerData().getServerName());
        String url = "https://img-api.neople.co.kr/df/servers/"+item.getServerData().getServerId()+"/characters/"+ item.getCharId() + "?zoom=3";

        Glide.with(mContext).load(url).into(holder.ivCharImage);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
