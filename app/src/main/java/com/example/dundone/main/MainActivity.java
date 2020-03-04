package com.example.dundone.main;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dundone.FragmentChange;
import com.example.dundone.R;
import com.example.dundone.main.auction.AuctionFragment;
import com.example.dundone.main.character.CharListFragment;
import com.example.dundone.main.character.CharacterAddFragment;
import com.example.dundone.main.entities.TabItem;
import com.example.dundone.main.hell.HellRecommendFragment;
import com.example.dundone.main.home.HomeFragment;
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

    private final Context context = this;
    @BindView(R.id.tl_main_tab)
    TabLayout tlMainTab;
    private final int MAIN_TAB_ITEM_SIZE = 4;
    private TabItem tiMainTabItem[] = new TabItem[MAIN_TAB_ITEM_SIZE];

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private void tabSetting() {
        tiMainTabItem[0] = new TabItem(R.drawable.home_off, R.drawable.home_on, "홈");
        tiMainTabItem[1] = new TabItem(R.drawable.character_off2, R.drawable.character_on, "캐릭터");
        tiMainTabItem[2] = new TabItem(R.drawable.market_off, R.drawable.market_on, "경매장");
        tiMainTabItem[3] = new TabItem(R.drawable.hcr_off, R.drawable.hcr_on, "헬추첨");
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
                        replaceFragment(new HomeFragment(), getString(R.string.char_home_fragment));
                        break;
                    case 1:
                        replaceFragment(new CharListFragment(), getString(R.string.char_list_fragment));
                        break;
                    case 2:
                        replaceFragment(new AuctionFragment(), getString(R.string.char_auction_fragment));
                        break;
                    case 3:
                        replaceFragment(new HellRecommendFragment(), getString(R.string.hell_recommend_fragment));
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
    void charAddOnClick(){
        addFragment(new CharacterAddFragment(), getString(R.string.char_add_fragment));
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
    public void backFragment(){
        if(fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
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
