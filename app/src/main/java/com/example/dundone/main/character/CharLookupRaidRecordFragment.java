package com.example.dundone.main.character;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dundone.R;
import com.example.dundone.Singleton;
import com.example.dundone.common_class.CustomRecyclerDecoration;
import com.example.dundone.data.character.CharacterOtherData;
import com.example.dundone.data.character.ResRaidClearCounts;
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

public class CharLookupRaidRecordFragment extends Fragment {
    private Context mContext;

    @BindView(R.id.recyclerview)
    RecyclerView rvRaidSumList;
    private PairAdapter<String, Integer> mResultAdapter;
    private ArrayList<Pair<String, Integer>> mNameAndValueList;

    @BindView(R.id.fiend_remain)
    TextView tvFiendRemain;
    @BindView(R.id.prey_remain)
    TextView tvPreyRemain;
    @BindView(R.id.fiend_today_clear)
    TextView tvFiendToday;
    @BindView(R.id.prey_today_clear)
    TextView tvPreyToday;

    @BindView(R.id.char_menu)
    View vCharMenu;
    private TextView tvCharName;
    private TextView tvTitle;
    private ImageView ivCharImg;
    private CharacterOtherData charData;

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
            charData = (CharacterOtherData) bundle.getSerializable(getString(R.string.char_data));
            tvCharName.setText(charData.getCharData().getCharName());
            tvTitle.setText("레이드 기록 조회");
            String url = "https://img-api.neople.co.kr/df/servers/" + charData.getServerData().getServerId()
                    + "/characters/" + charData.getCharData().getCharId() + "?zoom=3";
            Glide.with(mContext).load(url).into(ivCharImg);

            if(charData.getOthers().isFiendTodayClear())
                tvFiendToday.setVisibility(View.VISIBLE);
            if(charData.getOthers().isPreyTodayClear())
                tvPreyToday.setVisibility(View.VISIBLE);
            tvFiendRemain.setText(charData.getOthers().getFiendRemain() + " / 2");
            tvPreyRemain.setText(charData.getOthers().getPreyRemain() + " / 2");

        } else {
            Toast.makeText(mContext, "화면 전환중에 무언가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void reqGetRaidRecords(){
        if(charData == null) return;
        String charId = charData.getCharData().getCharId();
        String serverId = charData.getServerData().getServerId();
        Call<ResRaidClearCounts> call= Singleton.dundoneService.getRaidClearCounts(serverId, charId);
        call.enqueue(new Callback<ResRaidClearCounts>() {
            @Override
            public void onResponse(Call<ResRaidClearCounts> call, Response<ResRaidClearCounts> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == ResponseCode.SUCCESS) {
                        for(ResRaidClearCounts.RaidData i : response.body().getResult()) {
                            mNameAndValueList.add(new Pair<>(i.getRaidName(), i.getCnt()));
                        }
                        mResultAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "errorcode " + response.body().getCode() + " : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "errorcode " + response.code() + " : " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResRaidClearCounts> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initRecyclerView(){
        mNameAndValueList = new ArrayList<>();
        rvRaidSumList.setLayoutManager(new LinearLayoutManager(mContext));
        rvRaidSumList.addItemDecoration(new CustomRecyclerDecoration(10));
        mResultAdapter = new PairAdapter<>(mContext,mNameAndValueList);
        rvRaidSumList.setAdapter(mResultAdapter);
    }
    private void init(){
        initCharStatus();
        initRecyclerView();
        reqGetRaidRecords();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_char_lookup_raid_record, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
}
