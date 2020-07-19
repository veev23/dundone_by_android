package com.makers.dundone.main.character;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makers.dundone.R;
import com.makers.dundone.common_class.CustomRecyclerDecoration;
import com.makers.dundone.data.character.CharInfoData;
import com.makers.dundone.data.item.EpicData;
import com.makers.dundone.data.item.ResGetCharEpicList;
import com.makers.dundone.main.MainActivity;
import com.makers.dundone.main.ResponseCode;

import java.util.ArrayList;
import java.util.Collections;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.dundone.Singleton.DpToPixel;
import static com.makers.dundone.Singleton.dundoneService;

public class CharLookupEpicFragment extends Fragment {

    private Context mContext;

    @BindView(R.id.recyclerview)
    RecyclerView rvEpicViews;
    private ArrayList<EpicData> mEpicList = new ArrayList<>();
    private BaseInfoAdapter<EpicData> baseInfoAdapter;

    @BindView(R.id.noResult)
    TextView tvNoResult;


    @BindView(R.id.char_menu)
    View vCharMenu;
    private TextView tvCharName;
    private TextView tvTitle;
    private ImageView ivCharImg;
    private String charId;
    private String serverId;
    @OnClick(R.id.back_button)
    void back() {
        ((MainActivity) getActivity()).backFragment();
    }

    @BindView(R.id.recent)
    TextView tvRecent;
    @BindView(R.id.old)
    TextView tvOld;

    private void initCharStatus() {
        tvCharName = vCharMenu.findViewById(R.id.tv_name);
        tvTitle = vCharMenu.findViewById(R.id.tv_title);
        ivCharImg = vCharMenu.findViewById(R.id.iv_descript_img);

        Bundle bundle = getArguments();
        if (bundle != null) {
            CharInfoData charData = (CharInfoData) bundle.getSerializable(getString(R.string.char_data));
            tvCharName.setText(charData.getCharData().getCharName());
            tvTitle.setText("이번 달 에픽 조회");
            String url = "https://img-api.neople.co.kr/df/servers/" + charData.getServerData().getServerId()
                    + "/characters/" + charData.getCharData().getCharId() + "?zoom=3";
            Glide.with(mContext).load(url).into(ivCharImg);
            charId = charData.getCharData().getCharId();
            serverId = charData.getServerData().getServerId();
        } else {
            Toast.makeText(mContext, "화면 전환중에 무언가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInverse = false;
    void selectedOrder(TextView selected, TextView unselected){
        selected.setTextColor(mContext.getColor(R.color.colorButtonBackgorund));
        selected.setBackground(mContext.getDrawable(R.drawable.radius_little_red_rect));
        unselected.setTextColor(mContext.getColor(R.color.colorLittleRed));
        unselected.setBackground(mContext.getDrawable(R.drawable.radius_empty_little_red_rect));
    }

    @OnClick(R.id.recent)
    void listToRecent() {
        if (isInverse) {
            isInverse = false;
            inverseList();
            selectedOrder(tvRecent, tvOld);
        }
    }

    @OnClick(R.id.old)
    void listToOlder() {
        if (!isInverse) {
            isInverse = true;
            inverseList();
            selectedOrder(tvOld,tvRecent);
        }
    }

    private void inverseList() {
        Collections.reverse(mEpicList);
        baseInfoAdapter.notifyDataSetChanged();
    }

    private void reqGetEpicList(boolean recently) {
        tvNoResult.setText("Loading..");
        tvNoResult.setVisibility(View.VISIBLE);
        Call<ResGetCharEpicList> epicListCall = dundoneService.getEpicList(serverId,charId);
        epicListCall.enqueue(new Callback<ResGetCharEpicList>() {
            @Override
            public void onResponse(Call<ResGetCharEpicList> call, Response<ResGetCharEpicList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == ResponseCode.SUCCESS) {
                        mEpicList.addAll(response.body().getItemList());
                        if (mEpicList.isEmpty()) {
                            tvNoResult.setText("검색 결과가 없습니다.");
                        } else {
                            tvNoResult.setVisibility(View.GONE);
                            baseInfoAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(mContext, "errorcode " + response.body().getCode() + " : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "errorcode " + response.code() + " : " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResGetCharEpicList> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initRecyclerView() {
        rvEpicViews.setLayoutManager(new LinearLayoutManager(mContext));
        rvEpicViews.addItemDecoration(new CustomRecyclerDecoration(DpToPixel(mContext, 20)));
        baseInfoAdapter = new BaseInfoAdapter<>(mContext, mEpicList, R.layout.item_base_info);
        rvEpicViews.setAdapter(baseInfoAdapter);
    }

    private void init() {
        initCharStatus();
        initRecyclerView();
        reqGetEpicList(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_char_lookup_epic, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
}
