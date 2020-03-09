package com.example.dundone.main.character;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dundone.AddToAdapterInterface;
import com.example.dundone.R;
import com.example.dundone.common_class.CustomRecyclerDecoration;
import com.example.dundone.data.character.CharBaseData;
import com.example.dundone.data.character.CharacterData;
import com.example.dundone.data.character.RaidData;
import com.example.dundone.data.character.ResCharStatus;
import com.example.dundone.data.server.ServerData;
import com.example.dundone.main.MainActivity;
import com.example.dundone.main.ResponseCode;
import com.example.dundone.onMainButtonClickListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;

import static com.example.dundone.Singleton.dundoneService;
import static com.example.dundone.Singleton.gson;

public class CharListFragment extends Fragment
        implements AddToAdapterInterface<CharBaseData>, onMainButtonClickListener {

    private HashSet<CharBaseData> havedCharIds = new HashSet<>();
    @Override
    public void onMainButtonClick(){
        CharacterAddFragment f = new CharacterAddFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("havedCharIds",havedCharIds);
        f.setArguments(bundle);
        ((MainActivity)getActivity()).addFragment(f, getString(R.string.char_add_fragment));
    }
    //sharedpreference string
    public static final String PREF_CHAR_TYPE ="PREF_CHAR_TYPE";
    public static final String CHAR_LIST = "CHAR_LIST";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;
    private ArrayList<CharacterData> characterDataList;
    private CharacterListAdapter characterListAdapter;


    @BindView(R.id.recylerview_char_list)
    RecyclerView rvCharListView;

    @BindView(R.id.ad_view)
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

    private void prefCharDataSave(ArrayList<CharacterData> arr){
        String json = gson.toJson(arr);
        if(json.equals("null")) return;
        editor.putString(CHAR_LIST,json);
        editor.commit();
    }
    private void prefCharDataSearch(){
        String json = pref.getString(CHAR_LIST, "[]");
        if(json.equals("null")){
            characterDataList = new ArrayList<>();
        }
        characterDataList = gson.fromJson(json, new TypeToken<ArrayList<CharacterData>>(){}.getType());
        for(CharacterData ch : characterDataList){
            havedCharIds.add(new CharBaseData(ch.getCharName(), ch.getCharId(), ch.getServerData()));
        }
    }

    @Override
    public void onStop() {
        prefCharDataSave(characterDataList);
        super.onStop();
    }

    private void init(){
        adViewInit();

        pref = mContext.getSharedPreferences(PREF_CHAR_TYPE, Context.MODE_PRIVATE);
        editor = pref.edit();
        prefCharDataSearch();

        bindRecyclerView();
    }
    private void toCharacterDetailFragment(CharBaseData charBaseData){
        Bundle bundle = new Bundle(1);
        bundle.putSerializable("CharBaseData", charBaseData);
        CharDetailFragment cdf = new CharDetailFragment();
        cdf.setArguments(bundle);
        ((MainActivity)getActivity()).addFragment(cdf, getString(R.string.char_detail_fragment));
    }
    private void reqGetStatus(CharacterData charData, int i){
        Call<ResCharStatus> charStatusCall =
                dundoneService.getCharStatus(charData.getServerData().getServerId(), charData.getCharId());
        charStatusCall.enqueue(new Callback<ResCharStatus>() {
            @Override
            public void onResponse(Call<ResCharStatus> call, Response<ResCharStatus> response) {
                if (response.isSuccessful()) {
                    if(response.body().getCode() == ResponseCode.SUCCESS) {
                        RaidData rdData = response.body().getOthers();
                        rdData.initParsing();
                        charData.setOthers(rdData);
                        characterListAdapter.notifyItemChanged(i);
                    }
                    else{
                        Toast.makeText(mContext, "errorcode " + response.body().getCode() + " : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "errorcode " + response.code() + " : " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResCharStatus> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
    private void reqGetStatus(CharBaseData charData){
        Call<ResCharStatus> charStatusCall =
                dundoneService.getCharStatus(charData.getServerData().getServerId(), charData.getCharId());
        charStatusCall.enqueue(new Callback<ResCharStatus>() {
            @Override
            public void onResponse(Call<ResCharStatus> call, Response<ResCharStatus> response) {
                if (response.isSuccessful()) {
                    if(response.body().getCode() == ResponseCode.SUCCESS) {
                        RaidData rdData = response.body().getOthers();
                        rdData.initParsing();
                        //TODO : 중복된 이름인지 확인하는 작업 필요
                        characterDataList.add(new CharacterData(charData, rdData));
                        characterListAdapter.notifyItemInserted(characterDataList.size());
                        Toast.makeText(mContext, charData.getCharName(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(mContext, "errorcode " + response.body().getCode() + " : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "errorcode " + response.code() + " : " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResCharStatus> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
    private void bindRecyclerView(){
        rvCharListView.setLayoutManager(new LinearLayoutManager(mContext));
        rvCharListView.addItemDecoration(new CustomRecyclerDecoration(10));
        for(int i =0; i<characterDataList.size(); i++) {
            reqGetStatus(characterDataList.get(i), i);
        }

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
        reqGetStatus(data);
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
}
