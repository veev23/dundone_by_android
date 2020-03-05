package com.example.dundone.main.character;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dundone.R;
import com.example.dundone.data.character.CharBaseData;
import com.example.dundone.main.MainActivity;
import com.example.dundone.main.NeopleAPI;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CharLookupUpgradeFragment extends Fragment {

    private Context mContext;

    @BindView(R.id.iv_neople_openapi)
    View ivToDevStie;

    @BindView(R.id.char_menu)
    View vCharMenu;
    private TextView tvCharName;
    private TextView tvTitle;
    private ImageView ivCharImg;

    @BindView(R.id.tablayout)
    TabLayout tlTabLayout;
    @BindView(R.id.viewpager)
    ViewPager2 vpRateViewPager;
    private FragmentStateAdapter mPagerAdapter;
    private final int mUpgradeCase = 3;

    @OnClick(R.id.back_button)
    void back(){
        ((MainActivity)getActivity()).backFragment();
    }
    private void initCharStatus(){
        tvCharName = vCharMenu.findViewById(R.id.tv_name);
        tvTitle = vCharMenu.findViewById(R.id.tv_title);
        ivCharImg = vCharMenu.findViewById(R.id.iv_descript_img);

        Bundle bundle = getArguments();
        if(bundle !=null) {
            CharBaseData charData = (CharBaseData) bundle.getSerializable("CharBaseData");
            tvCharName.setText(charData.getCharName());
            tvTitle.setText("강화 횟수 조회");
            String url = "https://img-api.neople.co.kr/df/servers/" + charData.getServerData().getServerId()
                    + "/characters/" + charData.getCharId() + "?zoom=3";
            Glide.with(mContext).load(url).into(ivCharImg);
        }
        else{
            Toast.makeText(mContext, "화면 전환중에 무언가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViewPager(){
        mPagerAdapter =new FragmentStateAdapter(getActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Bundle bundle =new Bundle(0);
                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                        default:
                            return null;
                }
                UpgradeRateFragment fragment = new UpgradeRateFragment();
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getItemCount() {
                return mUpgradeCase;
            }
        };
        vpRateViewPager.setAdapter(mPagerAdapter);
        new TabLayoutMediator(tlTabLayout, vpRateViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            }
        }).attach();
        String[] tabTexts = {"강화", "재련", "증폭"};
        for(int i=0; i<mUpgradeCase; i++){
            TabLayout.Tab tab = tlTabLayout.getTabAt(i);
            if(tab ==null) break;
            tab.setText(tabTexts[i]);
        }
    }
    private void init(){
        initCharStatus();
        initViewPager();


        ivToDevStie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NeopleAPI neopleAPI =new NeopleAPI(mContext);
                neopleAPI.toNeopleDeveloperSite();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_char_lookup_upgrade, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
}
