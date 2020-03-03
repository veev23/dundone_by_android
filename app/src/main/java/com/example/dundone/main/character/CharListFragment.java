package com.example.dundone.main.character;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dundone.AddToAdapterInterface;
import com.example.dundone.R;
import com.example.dundone.common_class.CustomRecyclerDecoration;
import com.example.dundone.data.character.CharBaseData;
import com.example.dundone.data.character.CharacterData;
import com.example.dundone.data.character.RaidData;
import com.example.dundone.data.server.ServerData;
import com.example.dundone.main.MainActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class CharListFragment extends Fragment implements AddToAdapterInterface<CharBaseData> {

    private Context mContext;
    private ArrayList<CharacterData> characterDataList;
    private CharacterListAdapter characterListAdapter;
    @BindView(R.id.recylerview_char_list)
    RecyclerView rvCharListView;

    @BindView(R.id.ad_view_in_char_list)
    AdView mAdView;
    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void adViewInit(){
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    private void init(){
        adViewInit();
        bindRecyclerView();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_char_list, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
    private void toCharacterDetailFragment(CharBaseData charBaseData){
        Bundle bundle = new Bundle(1);
        bundle.putSerializable("char_info", charBaseData);
        CharDetailFragment cdf = new CharDetailFragment();
        cdf.setArguments(bundle);
        ((MainActivity)getActivity()).addFragment(cdf, getString(R.string.char_detail_fragment));
    }
    private void bindRecyclerView(){
        rvCharListView.setLayoutManager(new LinearLayoutManager(mContext));
        rvCharListView.setHasFixedSize(true);
        rvCharListView.addItemDecoration(new CustomRecyclerDecoration(10));
        characterDataList = new ArrayList<>();

        //test code
        characterDataList.add(new CharacterData("plnder","07955eb5e783b7f18a7c0b6bb80c0b98", new ServerData("bakal","바칼"), new RaidData(1,true,1,true)));
         //---------

        characterListAdapter = new CharacterListAdapter(characterDataList, mContext);
        rvCharListView.setAdapter(characterListAdapter);
        characterListAdapter.setOnItemClickListener(new CharacterListAdapter.OnItemClickListener() {
            @Override
            public void onItemCilckListener(View v, int p) {
                toCharacterDetailFragment(characterDataList.get(p));
            }
        });
    }
    @Override
    public void add(CharBaseData data){
        characterDataList.add(new CharacterData(data, new RaidData(1,true,1,true)));
        characterListAdapter.notifyDataSetChanged();
    }
}
