package com.example.dundone.character.adapter;
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
import com.example.dundone.character.viewholder.CharacterListViewHolder;
import com.example.dundone.data.character.CharacterData;

import java.util.ArrayList;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListViewHolder> {
    private ArrayList<CharacterData> itemList;
    private Context context;

    public CharacterListAdapter(ArrayList<CharacterData> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public CharacterListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_char_raid_state, viewGroup, false) ;
        CharacterListViewHolder vh = new CharacterListViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterListViewHolder viewHolder, int i) {
        CharacterData item = itemList.get(i);
        String charName = item.getServer().getServerName()+"-"+item.getCharName();
        viewHolder.tvCharName.setText(charName);
        viewHolder.tvPreyRemain.setText(item.getOthers().getPreyRemain());
        viewHolder.tvFiendRemain.setText(item.getOthers().getFiendRemain());
        viewHolder.tvPreyTodayClear.setVisibility(item.getOthers().isPreyTodayClear()?View.VISIBLE:View.INVISIBLE);
        viewHolder.tvFiendTodayClear.setVisibility(item.getOthers().isFiendTodayClear()?View.VISIBLE:View.INVISIBLE);

        String url = "https://img-api.neople.co.kr/df/servers/"+item.getServer().getServerId()+
                "/characters/"+item.getCharId()+"?zoom=1";
        Glide.with(context).load(url).into(viewHolder.ivCharImg);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
