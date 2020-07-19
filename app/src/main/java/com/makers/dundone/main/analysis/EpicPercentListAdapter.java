package com.makers.dundone.main.analysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makers.dundone.R;
import com.makers.dundone.data.etc.ResDungeonList;
import com.makers.dundone.data.item.EpicCountData;
import com.makers.dundone.data.item.ResGetEpicDropList;
import com.makers.dundone.main.ResponseCode;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.dundone.Singleton.dundoneService;

public class EpicPercentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener mListener = null;
    private Context mContext;
    private ArrayList<EpicCountData> mItemList;
    private int sum;
    private String dungeonName;
    private boolean isUpdating = false;
    //서버의 데이터를 다 불러온 경우
    private boolean isNoUpdate = false;
    private int nowPage = 1;
    private static String mItemName;
    private static final int limit = 10;

    public static void initSearchItemName(){
        mItemName = null;
    }

    public interface OnItemClickListener {
        void onItemCilckListener(View v, int p);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public static String getItemSearchName(){
        return mItemName;
    }
    private boolean loadingStart() {
        if(isNoUpdate) return false;
        isUpdating = true;
        mItemList.add(null);
        notifyItemInserted(mItemList.size() - 1);
        return true;
    }

    private void loadingEnd() {
        isUpdating = false;
        mItemList.remove(mItemList.size() - 1);
        notifyItemRemoved(mItemList.size());
    }

    public void itemAllClear(){
        mItemList.clear();
        isNoUpdate =false;
    }

    public void reqGetDropEpics(final String itemName) {
        if (isUpdating) return;
        if(!loadingStart()) return;
        if (itemName != null && !itemName.equals(mItemName)) {
            mItemName = itemName;
            nowPage = 1;
        }
        Call<ResGetEpicDropList> call = dundoneService.getEpicDropList(nowPage, limit, dungeonName, mItemName);
        call.enqueue(new Callback<ResGetEpicDropList>() {
            @Override
            public void onResponse(Call<ResGetEpicDropList> call, Response<ResGetEpicDropList> response) {
                loadingEnd();
                if (response.isSuccessful()) {
                    if (response.body().getCode() == ResponseCode.SUCCESS) {
                        ArrayList<EpicCountData> data = response.body().getItemList();
                        mItemList.addAll(data);
                        notifyItemRangeInserted(mItemList.size() - data.size(), mItemList.size());
                        if(data.size() < limit){
                            isNoUpdate = true;
                        }
                    } else {
                        Toast.makeText(mContext, "errorcode " + response.body().getCode() + " : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "errorcode " + response.code() + " : " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResGetEpicDropList> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
                loadingEnd();
            }
        });
        nowPage++;
    }

    private final static int VIEW_TYPE_LOADING = 1;
    private final static int VIEW_TYPE_ITEM = 0;

    @Override
    public int getItemViewType(int position) { //null값인 경우 로딩타입
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public EpicPercentListAdapter(Context context, ResDungeonList.DungeonEpic dungeonepic) {
        mContext = context;
        mItemList = new ArrayList<>();
        this.sum = dungeonepic.getCnt();
        dungeonName = dungeonepic.getDungeonName();
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

    class ProgressViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar pbBar;

        ProgressViewHolder(@NonNull View v) {
            super(v);
            pbBar = v.findViewById(R.id.progressbar);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.item_count_info, parent, false);
            return new ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = inflater.inflate(R.layout.item_progressbar, parent, false);
            return new ProgressViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
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
        } else if (viewHolder instanceof ProgressViewHolder) {
            ProgressViewHolder holder = (ProgressViewHolder) viewHolder;
            holder.pbBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

}

