package com.example.dundone.main.analysis;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dundone.R;
import com.example.dundone.data.etc.ResDungeonList;
import com.example.dundone.data.item.EpicCountData;
import com.example.dundone.main.MainActivity;
import com.example.dundone.main.ResponseCode;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dundone.Singleton.DpToPixel;
import static com.example.dundone.Singleton.dundoneService;

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
    private ArrayList<ArrayList<EpicCountData>> mDataList;
    private EpicsRVinRVAdapter mAdapter;

    private int mSelectedDungeon = 0;
    private TextView[] tvDungeons;
    private ArrayList<ResDungeonList.DungeonEpic> mDungeonEpics;

    @BindView(R.id.scrollView)
    HorizontalScrollView svScrollView;

    @BindView(R.id.et_search)
    EditText etSearch;
    private void hideKeyBoard() {
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
    }
    private void initPressSearch() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });
    }
    @OnClick(R.id.search_button)
    void search(){
        hideKeyBoard();
        etSearch.clearFocus();
        etSearch.setText("");

        String searchName = etSearch.getText().toString();
        mAdapter.search(mSelectedDungeon,searchName);
    }

    private void dungeonTabOnClick(final int pos) {
        svScrollView.scrollTo(tvDungeons[pos].getLeft(),0);
        vpEpicViewPager.setCurrentItem(pos);
        tvDungeons[mSelectedDungeon].setTextColor(ContextCompat.getColor(mContext, R.color.colorLittleRed));
        tvDungeons[mSelectedDungeon].setBackgroundResource(R.drawable.radius_empty_little_red_rect);
        tvDungeons[pos].setTextColor(ContextCompat.getColor(mContext, R.color.colorButtonBackgorund));
        tvDungeons[pos].setBackgroundResource(R.drawable.radius_little_red_rect);
        mSelectedDungeon = pos;
    }
    private void initLayout() {
        tvDungeons = new TextView[mDungeonEpics.size()];
        for(int pos = 0; pos< mDungeonEpics.size(); pos++) {
            tvDungeons[pos] = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_select_button, llDungeonLayout, false);
            tvDungeons[pos].setText(mDungeonEpics.get(pos).getDungeonName());
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
        mAdapter = new EpicsRVinRVAdapter(mContext, mDungeonEpics);
        vpEpicViewPager.setAdapter(mAdapter);

        vpEpicViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                dungeonTabOnClick(position);
                mAdapter.initUpdate(position);
            }
        });
        mAdapter.initUpdate(0);
    }

    private void reqGetDungeons(){
        Call<ResDungeonList> call = dundoneService.getDungeonEpics();
        call.enqueue(new Callback<ResDungeonList>() {
            @Override
            public void onResponse(Call<ResDungeonList> call, Response<ResDungeonList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == ResponseCode.SUCCESS) {
                        mDungeonEpics = response.body().getDungeonEpicList();
                        if(mDungeonEpics == null) {
                            Toast.makeText(mContext, getString(R.string.contact_to_dev), Toast.LENGTH_LONG).show();
                        }
                        else {
                            initLayout();
                        }
                    } else {
                        Toast.makeText(mContext, "errorcode " + response.body().getCode() + " : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "errorcode " + response.code() + " : " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResDungeonList> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void init(){
        initPressSearch();
        reqGetDungeons();
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
