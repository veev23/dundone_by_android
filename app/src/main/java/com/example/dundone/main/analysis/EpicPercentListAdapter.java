package com.example.dundone.main.analysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dundone.R;
import com.example.dundone.data.etc.ResDungeonList;
import com.example.dundone.data.item.EpicCountData;
import com.example.dundone.data.item.ResGetEpicDropList;
import com.example.dundone.main.ResponseCode;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dundone.Singleton.dundoneService;

public class EpicPercentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener mListener = null;
    private Context mContext;
    private ArrayList<EpicCountData> mItemList;
    private int sum;
    private String dungeonName;
    private boolean isUpdating = false;
    private int nowPage = 1;
    private static final int limit = 20;

    public interface OnItemClickListener {
        void onItemCilckListener(View v, int p);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void reqGetDropEpics(String itemName) {
        if (isUpdating) return;
        isUpdating = true;
        if (itemName != null) {
            nowPage = 1;
            mItemList.clear();
        }
        Call<ResGetEpicDropList> call = dundoneService.getEpicDropList(nowPage, limit, dungeonName, itemName);
        call.enqueue(new Callback<ResGetEpicDropList>() {
            @Override
            public void onResponse(Call<ResGetEpicDropList> call, Response<ResGetEpicDropList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == ResponseCode.SUCCESS) {
                        ArrayList<EpicCountData> data = response.body().getItemList();
                        mItemList.addAll(data);
                        notifyItemRangeInserted(mItemList.size() - data.size(), mItemList.size());
                    } else {
                        Toast.makeText(mContext, "errorcode " + response.body().getCode() + " : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "errorcode " + response.code() + " : " + response.message(), Toast.LENGTH_LONG).show();
                }
                isUpdating = false;
            }

            @Override
            public void onFailure(Call<ResGetEpicDropList> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
                isUpdating = false;
            }
        });
        nowPage++;
    }

    public EpicPercentListAdapter(Context context, ResDungeonList.DungeonEpic dungeonepic) {
        mContext = context;
        mItemList = new ArrayList<>();
        this.sum = dungeonepic.getCnt();
        dungeonName = dungeonepic.getDungeonName();
        reqGetDropEpics(null);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDetail;
        TextView tvPercent;
        ImageView ivImage;

        ViewHolder(@NonNull View v) {
            super(v);
            View vCharInfo = v.findViewById(R.id.base_info);
            tvName = vCharInfo.findViewById(R.id.tv_name);
            tvDetail = vCharInfo.findViewById(R.id.tv_detail_info);
            tvPercent = vCharInfo.findViewById(R.id.tv_percent);
            ivImage = vCharInfo.findViewById(R.id.iv_descript_img);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemCilckListener(v, pos);
                        }
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_count_info, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        EpicCountData item = mItemList.get(position);
        holder.tvName.setText(item.getName());
        holder.tvDetail.setText(mContext.getString(R.string.drop_count, item.getCnt()));
        double percent = 100.0 * (double) item.getCnt() / (double) sum;
        holder.tvPercent.setText(mContext.getString(R.string.percents_realnum, percent));
        String url = "https://img-api.neople.co.kr/df/items/" + item.getItemId();
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

}

