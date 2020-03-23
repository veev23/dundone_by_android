package com.example.dundone.main.analysis;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dundone.R;
import com.example.dundone.Singleton;
import com.example.dundone.data.etc.ResUpgradeCounts;
import com.example.dundone.data.etc.ReinforceData;
import com.example.dundone.main.MainActivity;
import com.example.dundone.main.ResponseCode;
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

public class AnalysysReinforceFragment extends Fragment {

    final private String[] tabTexts = {"강화", "증폭", "재련"};
    private ArrayList<Pair<String, ArrayList<ReinforceData>>> mTabList = new ArrayList<>();
    private ArrayList<ReinforceData>[] mReinforceList = new ArrayList[3];
    @BindView(R.id.viewpager)
    ViewPager2 vpList;
    private UpgradeRVinRVAdapter mViewpagerAdapter;
    @BindView(R.id.tablayout)
    TabLayout tlTabLayout;

    private final int mUpgradeCase = 3;

    @OnClick(R.id.back_button)
    void back(){
        ((MainActivity)getActivity()).backFragment();
    }
    private Context mContext;

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
        Call<ResUpgradeCounts>[] call = new Call[tabTexts.length];
        call[0] = Singleton.dundoneService.getUpgradeCountsReinforce(null, null);
        call[1] = Singleton.dundoneService.getUpgradeCountsAmplify(null, null);
        call[2] = Singleton.dundoneService.getUpgradeCountsRefine(null, null);
        for(int i=0; i<tabTexts.length; i++) {
            mReinforceList[i] = new ArrayList<>();
            Pair<String, ArrayList<ReinforceData>> dat = new Pair<>(tabTexts[i], mReinforceList[i]);
            mTabList.add(dat);
            reqGetUpgradeCounts(i, call[i], mTabList.get(i).second);
        }
    }
    private void init(){
        initRequest();
        initViewPager();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_analysis_reinforce, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
}
