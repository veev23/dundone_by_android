package com.example.dundone.main.character;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dundone.R;
import com.example.dundone.common_class.CustomRecyclerDecoration;
import com.example.dundone.data.character.CharInfoData;
import com.example.dundone.data.item.EpicData;
import com.example.dundone.data.item.ResGetEpicList;
import com.example.dundone.main.MainActivity;
import com.example.dundone.main.ResponseCode;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dundone.Singleton.dundoneService;

public class CharLookupEpic extends Fragment {

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
    @OnClick(R.id.back_button)
    void back() {
        ((MainActivity) getActivity()).backFragment();
    }

    private void initCharStatus() {
        tvCharName = vCharMenu.findViewById(R.id.tv_name);
        tvTitle = vCharMenu.findViewById(R.id.tv_title);
        ivCharImg = vCharMenu.findViewById(R.id.iv_descript_img);

        Bundle bundle = getArguments();
        if (bundle != null) {
            CharInfoData charData = (CharInfoData) bundle.getSerializable(getString(R.string.char_data));
            tvCharName.setText(charData.getCharData().getCharName());
            tvTitle.setText("에픽 조회");
            String url = "https://img-api.neople.co.kr/df/servers/" + charData.getServerData().getServerId()
                    + "/characters/" + charData.getCharData().getCharId() + "?zoom=3";
            Glide.with(mContext).load(url).into(ivCharImg);
            charId = charData.getCharData().getCharId();
        } else {
            Toast.makeText(mContext, "화면 전환중에 무언가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInverse = false;

    @OnClick(R.id.recent)
    void listToRecent() {
        if (isInverse) {
            isInverse = false;
            inverseList();
        }
    }

    @OnClick(R.id.old)
    void listToOlder() {
        if (!isInverse) {
            isInverse = true;
            inverseList();
        }
    }

    private void inverseList() {
        ArrayList<EpicData> tmp = mEpicList;
        baseInfoAdapter.notifyDataSetChanged();
        Toast.makeText(mContext, "클릭", Toast.LENGTH_SHORT).show();
    }

    private void reqGetEpicList(boolean recently) {
        tvNoResult.setText("Loading..");
        tvNoResult.setVisibility(View.VISIBLE);
        Call<ResGetEpicList> epicListCall = dundoneService.getEpicList(charId);
        epicListCall.enqueue(new Callback<ResGetEpicList>() {
            @Override
            public void onResponse(Call<ResGetEpicList> call, Response<ResGetEpicList> response) {
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
            public void onFailure(Call<ResGetEpicList> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initRecyclerView() {
        rvEpicViews.setLayoutManager(new LinearLayoutManager(mContext));
        rvEpicViews.addItemDecoration(new CustomRecyclerDecoration(10));
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
