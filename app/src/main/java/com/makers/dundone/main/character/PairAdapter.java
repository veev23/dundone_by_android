package com.makers.dundone.main.character;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makers.dundone.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PairAdapter<K,V> extends RecyclerView.Adapter<PairAdapter<K,V>.ViewHolder> {
    private Context mContext;
    private ArrayList<Pair<K, V>> list;

    public PairAdapter(Context mContext, ArrayList<Pair<K, V>> list) {
        this.mContext = mContext;
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvInfo;
        ViewHolder(@NonNull View v){
            super(v);
            tvName = v.findViewById(R.id.name);
            tvInfo = v.findViewById(R.id.info);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_grey_text, parent, false) ;
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        Pair<K,V> item = list.get(pos);
        holder.tvName.setText(String.valueOf(item.first));
        holder.tvInfo.setText(mContext.getString(R.string.count, (Integer)item.second));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
