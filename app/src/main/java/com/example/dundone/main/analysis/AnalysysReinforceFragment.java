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

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnalysysReinforceFragment extends Fragment {

    private ArrayList<Pair<String, ArrayList<ReinforceData>>> mTabList = new ArrayList<>();
    private ArrayList<ReinforceData> mReinforceList = new ArrayList<>();
    @BindView(R.id.viewpager)
    ViewPager2 vpList;
    private RecyclerViewContainAdapter mViewpagerAdapter;


    @OnClick(R.id.back_button)
    void back(){
        ((MainActivity)getActivity()).backFragment();
    }
    private Context mContext;
    private void initViewPager(){
        mReinforceList.add(new ReinforceData(12, 5, 10));
        Pair<String, ArrayList<ReinforceData>> dat = new Pair<>("강화", mReinforceList);
        mTabList.add(dat);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        mViewpagerAdapter= new RecyclerViewContainAdapter(mContext, mTabList, fm);
        vpList.setAdapter(mViewpagerAdapter);
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
