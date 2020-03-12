package com.example.dundone.main.analysis;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dundone.R;
import com.example.dundone.data.etc.ReinforceData;
import com.example.dundone.main.MainActivity;
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

public class AnalysysReinforceFragment extends Fragment {

    private ArrayList<Pair<String, ArrayList<ReinforceData>>> mTabList = new ArrayList<>();
    private ArrayList<ReinforceData>[] mReinforceList = new ArrayList[3];
    @BindView(R.id.viewpager)
    ViewPager2 vpList;
    private RecyclerViewContainAdapter mViewpagerAdapter;
    @BindView(R.id.tablayout)
    TabLayout tlTabLayout;

    private final int mUpgradeCase = 3;

    @OnClick(R.id.back_button)
    void back(){
        ((MainActivity)getActivity()).backFragment();
    }
    private Context mContext;
    private void initViewPager(){
        //DEBUG : TestCode
        mReinforceList[0] = new ArrayList<>();
        mReinforceList[1] = new ArrayList<>();
        mReinforceList[2] = new ArrayList<>();
        mReinforceList[0].add(new ReinforceData(12, 5, 10));
        mReinforceList[0].add(new ReinforceData(13, 4, 10));
        mReinforceList[0].add(new ReinforceData(14, 3, 10));
        Pair<String, ArrayList<ReinforceData>> dat = new Pair<>("강화", mReinforceList[0]);
        mTabList.add(dat);
        mReinforceList[1].add(new ReinforceData(6, 2, 10));
        mReinforceList[1].add(new ReinforceData(7, 1, 10));
         dat= new Pair<>("재련", mReinforceList[1]);
        mTabList.add(dat);
        mReinforceList[2].add(new ReinforceData(12, 2, 10));
         dat = new Pair<>("증폭", mReinforceList[2]);
        mTabList.add(dat);
        //-----------------------------------

        FragmentManager fm = getActivity().getSupportFragmentManager();
        mViewpagerAdapter= new RecyclerViewContainAdapter(mContext, mTabList, fm);
        vpList.setAdapter(mViewpagerAdapter);

        new TabLayoutMediator(tlTabLayout, vpList, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            }
        }).attach();
        String[] tabTexts = {"강화", "재련", "증폭"};
        for(int i=0; i<mUpgradeCase; i++){
            TabLayout.Tab tab = tlTabLayout.getTabAt(i);
            if(tab ==null) break;
            tab.setText(tabTexts[i]);
        }
    }
    private void init(){
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
