package com.example.dundone.main.analysis;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dundone.R;
import com.example.dundone.data.item.EpicCountData;
import com.example.dundone.data.item.EpicData;
import com.example.dundone.main.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.dundone.Singleton.DpToPixel;

public class AnalysisEpicsFragment extends Fragment {
    private Context mContext;
    @OnClick(R.id.back_button)
    void back(){
        ((MainActivity)getActivity()).backFragment();
    }

    @BindView(R.id.dungeonlist)
    LinearLayout llDungeonLayout;
    @BindView(R.id.viewpager)
    ViewPager2 vpEpicViewPager;
    private ArrayList<ArrayList<EpicCountData>> mTabDataList = new ArrayList<>();
    private ArrayList<EpicCountData>[] mDataList;
    private EpicsRVinRVAdapter mAdapter;

    private int mSelectedDungeon = 0;
    private TextView[] tvDungeons;

    private void dungeonTabOnClick(final int pos) {
        vpEpicViewPager.setCurrentItem(pos);
        tvDungeons[mSelectedDungeon].setTextColor(ContextCompat.getColor(mContext, R.color.colorLittleRed));
        tvDungeons[mSelectedDungeon].setBackgroundResource(R.drawable.radius_empty_little_red_rect);
        tvDungeons[pos].setTextColor(ContextCompat.getColor(mContext, R.color.colorButtonBackgorund));
        tvDungeons[pos].setBackgroundResource(R.drawable.radius_little_red_rect);
        mSelectedDungeon = pos;
    }
    private void initLayout() {
        tvDungeons = new TextView[5];
        for(int pos=0; pos<5;pos++) {
            tvDungeons[pos] = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_select_button, llDungeonLayout, false);
            tvDungeons[pos].setText(pos + "");
            LinearLayout.LayoutParams param =
                    new LinearLayout.LayoutParams(DpToPixel(mContext, 180), DpToPixel(mContext, 36));
            param.leftMargin = DpToPixel(mContext, 8);
            param.rightMargin = DpToPixel(mContext, 8);
            param.bottomMargin = DpToPixel(mContext, 16);
            param.topMargin = DpToPixel(mContext, 16);
            tvDungeons[pos].setLayoutParams(param);
            final int idx = pos;
            tvDungeons[pos].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dungeonTabOnClick(idx);
                }
            });
            llDungeonLayout.addView(tvDungeons[pos]);
        }
        dungeonTabOnClick(mSelectedDungeon);
        initViewPager();
    }
    private void initViewPager(){
        int dungeonSize = 2;
        mDataList= new ArrayList[dungeonSize];
        mDataList[0] = new ArrayList<>();
        mDataList[1] = new ArrayList<>();
        for(int i=0; i<10; i++) {
            mDataList[0].add(new EpicCountData("다크플레임리퍼", "e48cb4d290e09622cb4434637b8b80b6", i));
            mDataList[1].add(new EpicCountData("asd", "e48cb4d290e09622cb4434637b8b80b6", 1));
        }
        mTabDataList.addAll(Arrays.asList(mDataList).subList(0, dungeonSize));

        mAdapter = new EpicsRVinRVAdapter(mContext, mTabDataList);
        vpEpicViewPager.setAdapter(mAdapter);

        vpEpicViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                dungeonTabOnClick(position);
            }
        });
    }
    private void init(){
        initLayout();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_analysis_epics, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
}
