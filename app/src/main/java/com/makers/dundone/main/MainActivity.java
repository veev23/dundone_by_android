package com.makers.dundone.main;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makers.dundone.FragmentChange;
import com.makers.dundone.R;
import com.makers.dundone.main.analysis.AnalysisFragment;
import com.makers.dundone.main.character.CharListFragment;
import com.makers.dundone.main.entities.TabItem;
import com.makers.dundone.main.hell.HellRecommendFragment;
import com.makers.dundone.main.home.HomeFragment;
import com.makers.dundone.onBackPressListener;
import com.makers.dundone.onMainButtonClickListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
implements FragmentChange {

    private final Context mContext = this;
    @BindView(R.id.tl_main_tab)
    TabLayout tlMainTab;
    @BindView(R.id.main_button)
    ImageView ivMainButton;

    private final int MAIN_TAB_ITEM_SIZE = 4;
    private TabItem tiMainTabItem[] = new TabItem[MAIN_TAB_ITEM_SIZE];

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Fragment[] fragments = new Fragment[MAIN_TAB_ITEM_SIZE];

    private void tabSetting() {
        tiMainTabItem[0] = new TabItem(R.drawable.home_off, R.drawable.home_on, "홈");
        tiMainTabItem[1] = new TabItem(R.drawable.character_off2, R.drawable.character_on, "캐릭터");
        tiMainTabItem[2] = new TabItem(R.drawable.analysis_off, R.drawable.analysis_on, "통계");
        tiMainTabItem[3] = new TabItem(R.drawable.hcr_off, R.drawable.hcr_on, "헬추첨");
        fragments[0] = new HomeFragment();
        fragments[1] = new CharListFragment();
        fragments[2] = new AnalysisFragment();
        fragments[3] = new HellRecommendFragment();
        final boolean clicked[] = {true, false, false, false};
        for (int i = 0; i < MAIN_TAB_ITEM_SIZE; i++) {
            View v = createMenuTabView(tiMainTabItem[i], clicked[i]);
            tlMainTab.addTab(tlMainTab.newTab().setCustomView(v));
        }

        tlMainTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                transaction = fragmentManager.beginTransaction();
                switch (pos) {
                    case 0:
                        ivMainButton.setImageDrawable(getDrawable(R.drawable.button_search));
                        replaceFragment(fragments[pos], getString(R.string.char_home_fragment));
                        break;
                    case 1:
                        ivMainButton.setImageDrawable(getDrawable(R.drawable.button_plus));
                        replaceFragment(fragments[pos], getString(R.string.char_list_fragment));
                        break;
                    case 2:
                        ivMainButton.setImageDrawable(getDrawable(R.drawable.button_search));
                        replaceFragment(fragments[pos], getString(R.string.analysis_fragment));
                        break;
                    case 3:
                        ivMainButton.setImageDrawable(getDrawable(R.drawable.button_search));
                        replaceFragment(fragments[pos], getString(R.string.hell_recommend_fragment));
                        break;
                    default:
                        return;
                }
                View t = tab.getCustomView();
                if (t != null)
                    changeMenuTabView(t, pos, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                View t = tab.getCustomView();
                if (t != null)
                    changeMenuTabView(t, pos, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @OnClick(R.id.main_button)
    void mainButtonOnClick(){
        FragmentManager.BackStackEntry backStackEntry =
                fragmentManager.getBackStackEntryAt(0);
        Fragment fragment = fragmentManager.findFragmentByTag(backStackEntry.getName());

        if(fragment instanceof onMainButtonClickListener){
            ((onMainButtonClickListener) fragment).onMainButtonClick();
        }
        else{
            Toast.makeText(mContext, "준비중입니다.", Toast.LENGTH_SHORT).show();
            //addFragment(new AuctionFragment(), getString(R.string.auction_fragment));
        }
    }
    private void changeMenuTabView(View tab, int tabIndex, boolean clicked) {
        TextView tabText = tab.findViewById(R.id.tab_text);
        ImageView tabImage = tab.findViewById(R.id.tab_image);
        if (clicked) {
            tabText.setTextColor(getColor(R.color.colorButtonBackgorund));
            tabImage.setImageResource(tiMainTabItem[tabIndex].getClickedImage());
        } else {
            tabText.setTextColor(getColor(R.color.colorGrey));
            tabImage.setImageResource(tiMainTabItem[tabIndex].getUnClickedImage());
        }
    }

    private View createMenuTabView(TabItem menuTabItem, boolean clicked) {
        View tab = LayoutInflater.from(this).inflate(R.layout.item_main_tab, tlMainTab, false);
        TextView tabText = tab.findViewById(R.id.tab_text);
        ImageView tabImage = tab.findViewById(R.id.tab_image);
        tabText.setText(menuTabItem.getText());
        if (clicked) {
            tabText.setTextColor(getColor(R.color.colorButtonBackgorund));
            tabImage.setImageResource(menuTabItem.getClickedImage());
        } else {
            tabText.setTextColor(getColor(R.color.colorGrey));
            tabImage.setImageResource(menuTabItem.getUnClickedImage());
        }
        return tab;
    }

    private void init() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        fragmentManager = getSupportFragmentManager();
        replaceFragment(new HomeFragment(), getString(R.string.char_home_fragment));
        tabSetting();
    }
    @Override
    public void replaceFragment(@NonNull Fragment fragment, String calleeName){
        if(fragmentManager.findFragmentByTag(calleeName) != null) return;
        for(int i=0; i<fragmentManager.getBackStackEntryCount(); i++){
            fragmentManager.popBackStack();
        }
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_main, fragment,calleeName);
        transaction.addToBackStack(calleeName);
        transaction.commit();
        fragmentManager.executePendingTransactions();
   }
    @Override
    public void addFragment(@NonNull Fragment fragment, String calleeName){
        if(fragmentManager.findFragmentByTag(calleeName) != null) return;
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.from_right_to_left, R.anim.fragment_fade_exit);
        transaction.add(R.id.fragment_main, fragment,calleeName);
        transaction.addToBackStack(calleeName);
        transaction.commit();
        fragmentManager.executePendingTransactions();
     }
    @Override
    public boolean backFragment(){
        if(fragmentManager.getBackStackEntryCount() > 1) {
           fragmentManager.popBackStack();
           return true;
        }
        return false;
    }

    private long backKeyPressedTime=0;
    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() > 1) {
            FragmentManager.BackStackEntry backStackEntry =
                    fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1);
            Fragment fragment = fragmentManager.findFragmentByTag(backStackEntry.getName());
            if(fragment instanceof onBackPressListener){
                ((onBackPressListener) fragment).onBackPress();
            }
            else{
                fragmentManager.popBackStack();
            }
        }
        else {
            if(System.currentTimeMillis()>backKeyPressedTime+2000){
                backKeyPressedTime = System.currentTimeMillis();
                Toast.makeText(this, getString(R.string.APP_TERMINATE_BACK_BUTTON), Toast.LENGTH_SHORT).show();
            }
            else {
                finish();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }
}
