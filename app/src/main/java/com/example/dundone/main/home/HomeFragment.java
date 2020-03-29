package com.example.dundone.main.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator2;
import me.relex.circleindicator.CircleIndicator3;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dundone.R;
import com.example.dundone.main.NeopleAPI;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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

    @BindViews({R.id.notice0, R.id.notice1,R.id.notice2,R.id.notice3,R.id.notice4})
    TextView[] tvNotices;
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

    public void toURL(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    //event image, event url
    private Pair<ArrayList<String>, ArrayList<String>> mEventPair = new Pair<>(new ArrayList<>(), new ArrayList<>());
    private void initViewPager() {
        eventAdapter = new ImageViewAdapter(mEventPair.first, mContext);
        eventAdapter.setOnItemClickListener(new ImageViewAdapter.OnItemClickListener() {
            @Override
            public void onItemCilckListener(View v, int p) {
                toURL(mEventPair.second.get(p));
            }
        });
        vpEventPager.setAdapter(eventAdapter);
        ciEventIndicator.setViewPager(vpEventPager);
    }

    private void crawling(){
        String base = "http://df.nexon.com";
        String eventPath = "/df/news/event";
        String noticePath = "/df/news/notice";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document soup = Jsoup.connect(base+eventPath).get();
                    Elements events = soup.select("#container > div.contnet > div.event_con > div > ul > li");
                    for(Element event : events){
                        if(event.text().equals("")) continue;
                        String eventPath= event.select("a").attr("href");
                        mEventPair.second.add(base+eventPath);
                        String eventImageUrl = event.select("img").attr("src");
                        mEventPair.first.add("http:"+eventImageUrl);
                    }

                    soup = Jsoup.connect(base+noticePath).get();
                    Elements notices =soup.select("#container > div.contnet > table > tbody > tr");
                    notices.remove(0);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            initViewPager();

                            for(int i = 0;i<5; i++){
                                String text = notices.get(i).select("a").text();
                                tvNotices[i].setText(text);
                                String fullUrl =base+notices.get(i).select("a").attr("href");
                                tvNotices[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        toURL(fullUrl);
                                    }
                                });
                            }
                        }
                    });
                }
                catch (IOException e){

                }
            }
        }).start();

    }

    private void init() {
        adViewInit();
        crawling();
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
