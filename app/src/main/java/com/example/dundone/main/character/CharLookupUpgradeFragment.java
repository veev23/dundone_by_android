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
import com.example.dundone.data.character.CharInfoData;
import com.example.dundone.data.etc.ReinforceData;
import com.example.dundone.data.etc.ResUpgradeCounts;
import com.example.dundone.main.MainActivity;
import com.example.dundone.main.ResponseCode;
import com.example.dundone.main.analysis.UpgradeRVinRVAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharLookupUpgradeFragment extends Fragment {

    private Context mContext;
    final private String[] tabTexts = {"강화", "증폭", "재련"};

    @BindView(R.id.char_menu)
    View vCharMenu;
    private TextView tvCharName;
    private TextView tvTitle;
    private ImageView ivCharImg;

    private ArrayList<Pair<String, ArrayList<ReinforceData>>> mTabList = new ArrayList<>();
    private ArrayList<ReinforceData>[] mReinforceList = new ArrayList[3];
    @BindView(R.id.viewpager)
    ViewPager2 vpList;
    private UpgradeRVinRVAdapter mViewpagerAdapter;
    @BindView(R.id.tablayout)
    TabLayout tlTabLayout;

    private final int mUpgradeCase = 3;

    private CharInfoData charData;

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
            charData = (CharInfoData) bundle.getSerializable(getString(R.string.char_data));
            tvCharName.setText(charData.getCharData().getCharName());
            tvTitle.setText("강화 횟수 조회");
            String url = "https://img-api.neople.co.kr/df/servers/" + charData.getServerData().getServerId()
                    + "/characters/" + charData.getCharData().getCharId() + "?zoom=3";
            Glide.with(mContext).load(url).into(ivCharImg);
        } else {
            Toast.makeText(mContext, "화면 전환중에 무언가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }


    private void reqGetUpgradeCounts(final int idx, final Call<ResUpgradeCounts> call, final ArrayList<ReinforceData> list){
        call.enqueue(new Callback<ResUpgradeCounts>() {
            @Override
            public void onResponse(Call<ResUpgradeCounts> call, Response<ResUpgradeCounts> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == ResponseCode.SUCCESS) {
                        list.addAll(response.body().getmUpgradeCounts());
                        mViewpagerAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(mContext, "errorcode " + response.body().getCode() + " : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "errorcode " + response.code() + " : " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResUpgradeCounts> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initViewPager(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        mViewpagerAdapter= new UpgradeRVinRVAdapter(mContext, mTabList, fm);
        vpList.setAdapter(mViewpagerAdapter);

        new TabLayoutMediator(tlTabLayout, vpList, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int pos) {
                tab.setText(tabTexts[pos]);
            }
        }).attach();
    }
    private void initRequest(){
        String charId = charData.getCharData().getCharId();
        String serverId = charData.getServerData().getServerId();
        Call<ResUpgradeCounts>[] call = new Call[tabTexts.length];
        call[0] = Singleton.dundoneService.getUpgradeCountsReinforce(serverId, charId, 13, 20);
        call[1] = Singleton.dundoneService.getUpgradeCountsAmplify(serverId, charId, 12, 20);
        call[2] = Singleton.dundoneService.getUpgradeCountsRefine(serverId, charId, 7, 8);
        for(int i=0; i<tabTexts.length; i++) {
            mReinforceList[i] = new ArrayList<>();
            Pair<String, ArrayList<ReinforceData>> dat = new Pair<>(tabTexts[i], mReinforceList[i]);
            mTabList.add(dat);
            reqGetUpgradeCounts(i, call[i], mTabList.get(i).second);
        }
    }
    private void init() {
        initCharStatus();
        initRequest();
        initViewPager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_char_lookup_upgrade, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
}
