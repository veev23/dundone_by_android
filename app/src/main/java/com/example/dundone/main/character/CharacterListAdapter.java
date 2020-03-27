package com.example.dundone.main.character;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dundone.R;
import com.example.dundone.data.character.CharacterOtherData;

import java.util.ArrayList;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder> {
    private ArrayList<CharacterOtherData> mItemList;
    private Context mContext;

    private int nowSize;
    private static final int minSize = 7;
    public boolean addSize(){
        if(mItemList.size() > nowSize) {
            nowSize++;
            return true;
        }
        return false;
    }
    public boolean isSoMiniSize(){
        return nowSize < minSize;
    }
    public void remove(int pos){
        mItemList.remove(pos);
        if(nowSize > mItemList.size()) nowSize--;
        notifyItemRemoved(pos);
    }

    //OnItemClick
    private OnItemClickListener mListener = null;

    public interface OnItemClickListener {
        void onItemCilck(View v, int p);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }


    //OnItemLongClick
    private OnItemLongClickListener mLongListener = null;

    public interface OnItemLongClickListener {
        boolean onItemLongCilck(View v, int p);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.mLongListener = listener;
    }



    public CharacterListAdapter(ArrayList<CharacterOtherData> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
        nowSize = Math.min(minSize,mItemList.size());
    }


    public class CharacterListViewHolder extends RecyclerView.ViewHolder{

        TextView tvCharName;
        TextView tvCharServer;
        TextView tvPreyRemain;
        TextView tvFiendRemain;
        TextView tvPreyTodayClear;
        TextView tvFiendTodayClear;
        TextView tvEpics;
        ImageView ivCharImg;


        public CharacterListViewHolder (@NonNull View itemView) {
            super(itemView);
            View vCharInfo=itemView.findViewById(R.id.char_info_in_raid_state);

            tvCharName = vCharInfo.findViewById(R.id.tv_name);
            tvCharServer = vCharInfo.findViewById(R.id.tv_detail_info);
            tvPreyRemain = itemView.findViewById(R.id.prey_remain_in_raid_state);
            tvFiendRemain = itemView.findViewById(R.id.fiend_remain_in_raid_state);
            tvPreyTodayClear = itemView.findViewById(R.id.prey_today_clear_in_raid_state);
            tvFiendTodayClear = itemView.findViewById(R.id.fiend_today_clear_in_raid_state);
            tvEpics = itemView.findViewById(R.id.epic_count);
            ivCharImg = vCharInfo.findViewById(R.id.iv_descript_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener !=null){
                            mListener.onItemCilck(v, pos);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mLongListener !=null){
                            return mLongListener.onItemLongCilck(v, pos);
                        }
                    }
                    return false;
                }
            });
        }
    }

    @NonNull
    @Override
    public CharacterListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_char_raid_state, viewGroup, false) ;
        CharacterListViewHolder vh = new CharacterListViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterListViewHolder viewHolder, int i) {
        CharacterOtherData item = mItemList.get(i);
        viewHolder.tvCharName.setText(item.getCharData().getCharName());
        viewHolder.tvCharServer.setText(item.getServerData().getServerName());
        //아직 불러오지 않은 상태
        if(item.getOthers().isNotYetLoaded()){
            viewHolder.tvEpics.setText("불러오는 중..");
        }
        else {
            viewHolder.tvPreyRemain.setText(String.valueOf(item.getOthers().getPreyRemain()) + " / 2");
            viewHolder.tvFiendRemain.setText(String.valueOf(item.getOthers().getFiendRemain()) + " / 2");
            viewHolder.tvEpics.setText(String.valueOf(item.getOthers().getEpicWeek()));
            viewHolder.tvPreyTodayClear.setVisibility(item.getOthers().isPreyTodayClear() ? View.VISIBLE : View.INVISIBLE);
            viewHolder.tvFiendTodayClear.setVisibility(item.getOthers().isFiendTodayClear() ? View.VISIBLE : View.INVISIBLE);
        }
        String url = "https://img-api.neople.co.kr/df/servers/"+item.getServerData().getServerId()+
                "/characters/"+item.getCharData().getCharId()+"?zoom=3";
        Glide.with(mContext).load(url).into(viewHolder.ivCharImg);
    }

    @Override
    public int getItemCount() {
        return nowSize;
    }
}
