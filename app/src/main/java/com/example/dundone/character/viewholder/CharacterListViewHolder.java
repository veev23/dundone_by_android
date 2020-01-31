package com.example.dundone.character.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dundone.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterListViewHolder extends RecyclerView.ViewHolder {
    public TextView tvCharName;
    public TextView tvPreyRemain;
    public TextView tvFiendRemain;
    public TextView tvPreyTodayClear;
    public TextView tvFiendTodayClear;
    public ImageView ivCharImg;
    public CharacterListViewHolder(@NonNull View itemView) {
        super(itemView);
        tvCharName = itemView.findViewById(R.id.char_name_in_raid_state);
        tvPreyRemain = itemView.findViewById(R.id.prey_remain_in_raid_state);
        tvFiendRemain = itemView.findViewById(R.id.fiend_remain_in_raid_state);
        tvPreyTodayClear = itemView.findViewById(R.id.prey_today_clear_in_raid_state);
        tvFiendTodayClear = itemView.findViewById(R.id.fiend_today_clear_in_raid_state);
        ivCharImg = itemView.findViewById(R.id.char_img_in_raid_state);
    }
}
