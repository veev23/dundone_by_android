package com.example.dundone.main.analysis;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dundone.R;
import com.example.dundone.data.etc.RainforceData;
import com.example.dundone.main.MainActivity;
import com.example.dundone.main.home.ImageViewAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnalysysRainforceFragment extends Fragment {

    private ArrayList<Pair<String, ArrayList<RainforceData>>> mTabList;
    @BindView(R.id.viewpager)
    ViewPager2 vpList;
    private RecyclerViewContainAdapter mViewpagerAdapter;


    @OnClick(R.id.back_button)
    void back(){
        ((MainActivity)getActivity()).backFragment();
    }
    private Context mContext;
    private void initViewPager(){
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
        View v = inflater.inflate(R.layout.fragment_analysis_rainforce, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
}
