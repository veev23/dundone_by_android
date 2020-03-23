package com.example.dundone.main.analysis;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dundone.R;
import com.example.dundone.main.MainActivity;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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

    private int mSelectedDungeon = 0;
    private TextView[] tvDungeons;

    private void dungeonTabOnClick(final int i) {
        tvDungeons[mSelectedDungeon].setTextColor(ContextCompat.getColor(mContext, R.color.colorLittleRed));
        tvDungeons[mSelectedDungeon].setBackgroundResource(R.drawable.radius_empty_little_red_rect);
        tvDungeons[i].setTextColor(ContextCompat.getColor(mContext, R.color.colorButtonBackgorund));
        tvDungeons[i].setBackgroundResource(R.drawable.radius_little_red_rect);
        mSelectedDungeon = i;
    }

    private TabLayout.Tab makeTab(TabLayout tl, String text){
        TabLayout.Tab tab = tl.newTab();
        tab.setText(text);
        tab.setCustomView(R.layout.item_select_button);
        return tab;
    }
    private void initLayout() {
        tvDungeons = new TextView[5];
        for(int i=0; i<5;i++) {
            tvDungeons[i] = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_select_button, llDungeonLayout, false);
            tvDungeons[i].setText(i + "");
            LinearLayout.LayoutParams param =
                    new LinearLayout.LayoutParams(DpToPixel(mContext, 180), DpToPixel(mContext, 36));
            param.leftMargin = DpToPixel(mContext, 8);
            param.rightMargin = DpToPixel(mContext, 8);
            param.bottomMargin = DpToPixel(mContext, 16);
            param.topMargin = DpToPixel(mContext, 16);
            tvDungeons[i].setLayoutParams(param);
            final int idx = i;
            tvDungeons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dungeonTabOnClick(idx);
                }
            });
            llDungeonLayout.addView(tvDungeons[i]);
        }
        dungeonTabOnClick(mSelectedDungeon);
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
