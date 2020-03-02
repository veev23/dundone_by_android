package com.example.dundone.main.character;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.dundone.R;
import com.example.dundone.data.character.CharacterData;
import com.example.dundone.main.MainActivity;

import java.util.ArrayList;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder> {
    private ArrayList<CharacterData> itemList;
    private Context context;

    private OnItemClickListener mListener = null;

    public interface OnItemClickListener {
        void onItemCilckListener(View v, int p);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public CharacterListAdapter(ArrayList<CharacterData> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    public class CharacterListViewHolder extends RecyclerView.ViewHolder {

        TextView tvCharName;
        TextView tvCharServer;
        TextView tvPreyRemain;
        TextView tvFiendRemain;
        TextView tvPreyTodayClear;
        TextView tvFiendTodayClear;
        ImageView ivCharImg;

        public CharacterListViewHolder(@NonNull View itemView) {
            super(itemView);
            View vCharInfo=itemView.findViewById(R.id.char_info_in_raid_state);

            tvCharName = vCharInfo.findViewById(R.id.tv_char_name);
            tvCharServer = vCharInfo.findViewById(R.id.tv_char_server);
            tvPreyRemain = itemView.findViewById(R.id.prey_remain_in_raid_state);
            tvFiendRemain = itemView.findViewById(R.id.fiend_remain_in_raid_state);
            tvPreyTodayClear = itemView.findViewById(R.id.prey_today_clear_in_raid_state);
            tvFiendTodayClear = itemView.findViewById(R.id.fiend_today_clear_in_raid_state);
            ivCharImg = vCharInfo.findViewById(R.id.iv_char_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener !=null){
                            mListener.onItemCilckListener(v, pos);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public CharacterListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_char_raid_state, viewGroup, false) ;
        CharacterListViewHolder vh = new CharacterListViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterListViewHolder viewHolder, int i) {
        CharacterData item = itemList.get(i);
        viewHolder.tvCharName.setText(item.getCharName());
        viewHolder.tvCharServer.setText(item.getServerData().getServerName());
        viewHolder.tvPreyRemain.setText(String.valueOf(item.getOthers().getPreyRemain()));
        viewHolder.tvFiendRemain.setText(String.valueOf(item.getOthers().getFiendRemain()));
        viewHolder.tvPreyTodayClear.setVisibility(item.getOthers().isPreyTodayClear()?View.VISIBLE:View.INVISIBLE);
        viewHolder.tvFiendTodayClear.setVisibility(item.getOthers().isFiendTodayClear()?View.VISIBLE:View.INVISIBLE);


        String url = "https://img-api.neople.co.kr/df/servers/"+item.getServerData().getServerId()+
                "/characters/"+item.getCharId()+"?zoom=3";
        Glide.with(context).load(url).into(viewHolder.ivCharImg);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
