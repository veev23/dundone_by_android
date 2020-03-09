package com.example.dundone.main.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator2;
import me.relex.circleindicator.CircleIndicator3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dundone.R;
import com.example.dundone.main.NeopleAPI;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Context mContext;
    @BindView(R.id.openapi_in_home)
    View ivNeopleDevSite;

    @BindView(R.id.ad_view_in_home)
    AdView mAdView;

    @BindView(R.id.event_viewpager_indicator)
    CircleIndicator3 ciEventIndicator;
    @BindView(R.id.event_viewpager)
    ViewPager2 vpEventPager;
    private ImageViewAdapter eventAdapter;
    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void adViewInit() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void initViewPager() {
        ArrayList<String> srcList = new ArrayList<>();
        srcList.add("https://img-api.neople.co.kr/df/servers/bakal/characters/c269d0beddd7b2ae69be74a127fc0292?zoom=1");
        srcList.add("https://img-api.neople.co.kr/df/servers/bakal/characters/c269d0beddd7b2ae69be74a127fc0292?zoom=1");
        srcList.add("https://img-api.neople.co.kr/df/servers/bakal/characters/c269d0beddd7b2ae69be74a127fc0292?zoom=1");
        eventAdapter = new ImageViewAdapter(srcList, mContext);
        vpEventPager.setAdapter(eventAdapter);
        ciEventIndicator.setViewPager(vpEventPager);
    }

    ;

    private void init() {
        adViewInit();
        initViewPager();
        ivNeopleDevSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NeopleAPI neopleAPI = new NeopleAPI(mContext);
                neopleAPI.toNeopleDeveloperSite();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();

        return v;
    }
}
