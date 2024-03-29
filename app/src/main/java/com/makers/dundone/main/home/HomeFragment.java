package com.makers.dundone.main.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator3;

import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makers.dundone.R;
import com.makers.dundone.Singleton;
import com.makers.dundone.main.NeopleAPI;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static android.view.View.GONE;
import static com.makers.dundone.Singleton.runOnUiThread;

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

    @BindViews({R.id.notice0, R.id.notice1, R.id.notice2, R.id.notice3, R.id.notice4})
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

    public void toURL(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    //event image, event url
    private Pair<ArrayList<String>, ArrayList<String>> mEventPair = new Pair<>(new ArrayList<>(), new ArrayList<>());

    private void initViewPager() {
        eventAdapter = new ImageViewAdapter(mEventPair.first, mContext);
        eventAdapter.setOnItemClickListener((v, p) -> toURL(mEventPair.second.get(p)));
        vpEventPager.setAdapter(eventAdapter);
        ciEventIndicator.setViewPager(vpEventPager);
    }

    private void crawling() {
        new CrawlingThread().start();
    }

    private void init() {
        adViewInit();
        crawling();
        ivNeopleDevSite.setOnClickListener(v -> {
            NeopleAPI neopleAPI = new NeopleAPI(mContext);
            neopleAPI.toNeopleDeveloperSite();
        });
    }

    @OnClick(R.id.noticeViewAll)
    void onClickToNoticeViewAll() {
        toURL("http://df.nexon.com/df/news/event");
    }

    @OnClick(R.id.eventViewAll)
    void onClickToEventViewAll() {
        toURL("http://df.nexon.com/df/news/notice");
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

    class CrawlingThread extends Thread {
        private String base = "http://df.nexon.com";
        private String eventPath = "/df/news/event";
        private String noticePath = "/df/news/notice";

        @Override
        public void run() {
            if (tvNotices == null || tvNotices[0] == null) return;
            final int len = tvNotices.length;
            try {
                Document soup = Jsoup.connect(base + eventPath).get();
                Elements events = soup.select("#container > div.contnet > div.event_con > div > ul > li");
                for (Element event : events) {
                    if (event.text().equals("")) continue;
                    String eventPath = event.select("a").attr("href");
                    mEventPair.second.add(base + eventPath);
                    String eventImageUrl = event.select("img").attr("src");
                    mEventPair.first.add("http:" + eventImageUrl);
                }

                soup = Jsoup.connect(base + noticePath).get();
                Elements notices = soup.select("#container > div.contnet > table > tbody > tr");
                notices.remove(0);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initViewPager();
                        try {
                            int i;
                            for (i = 0; i < Math.min(len, notices.size()); i++) {
                                String text = notices.get(i).select("a").text();
                                tvNotices[i].setText(text);

                                String fullUrl = base + notices.get(i).select("a").attr("href");
                                tvNotices[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        toURL(fullUrl);
                                    }
                                });
                            }
                            for (; i < len; i++) {
                                tvNotices[i].setVisibility(GONE);
                            }
                        } catch (Exception e) {
                            tvNotices[0].setText(getString(R.string.contact_to_dev));
                            for (int i = 1; i < len; i++) {
                                if (tvNotices[i] != null)
                                    tvNotices[i].setVisibility(GONE);
                            }
                        }
                    }
                });
            } catch (IOException e) {
                runOnUiThread(() -> {
                    tvNotices[0].setText("정보를 얻어오지 못했습니다.");
                    for (int i = 1; i < len; i++) {
                        if (tvNotices[i] != null)
                            tvNotices[i].setVisibility(GONE);
                    }
                });
            }
        }
    }
}
