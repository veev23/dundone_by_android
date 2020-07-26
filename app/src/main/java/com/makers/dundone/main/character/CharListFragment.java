package com.makers.dundone.main.character;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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

import com.makers.dundone.AddToAdapterInterface;
import com.makers.dundone.R;
import com.makers.dundone.common_class.CustomRecyclerDecoration;
import com.makers.dundone.data.BaseDundoneResponse;
import com.makers.dundone.data.character.CharInfoData;
import com.makers.dundone.data.character.CharacterOtherData;
import com.makers.dundone.data.character.RaidRemainData;
import com.makers.dundone.data.character.ResCharStatus;
import com.makers.dundone.main.MainActivity;
import com.makers.dundone.main.ResponseCode;
import com.makers.dundone.onMainButtonClickListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;

import static com.makers.dundone.Singleton.DpToPixel;
import static com.makers.dundone.Singleton.dundoneService;
import static com.makers.dundone.Singleton.gson;

public class CharListFragment extends Fragment
        implements AddToAdapterInterface<CharInfoData>, onMainButtonClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private HashSet<String> havedCharIds = new HashSet<>();

    @Override
    public void onMainButtonClick() {
        CharacterAddFragment f = new CharacterAddFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("havedCharIds", havedCharIds);
        f.setArguments(bundle);
        ((MainActivity) getActivity()).addFragment(f, getString(R.string.char_add_fragment));
    }

    //sharedpreference string
    public static final String PREF_CHAR_TYPE = "PREF_CHAR_TYPE";
    public static final String CHAR_LIST = "CHAR_LIST";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;
    private ArrayList<CharacterOtherData> characterOtherDataList;
    private CharacterListAdapter characterListAdapter;

    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout srlRefresh;

    @BindView(R.id.recylerview_char_list)
    RecyclerView rvCharListView;

    @BindView(R.id.ad_view)
    AdView mAdView;

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

    private void prefCharDataSave(ArrayList<CharacterOtherData> arr) {
        String json = gson.toJson(arr);
        if (json.equals("null")) return;
        editor.putString(CHAR_LIST, json);
        editor.commit();
    }

    private void prefCharDataSearch() {
        String json = pref.getString(CHAR_LIST, "[]");
        if (json.equals("null")) {
            characterOtherDataList = new ArrayList<>();
        }
        try {
            characterOtherDataList = gson.fromJson(json, new TypeToken<ArrayList<CharacterOtherData>>() {
            }.getType());
        }catch (Exception e){
            characterOtherDataList = new ArrayList<>();
            Toast.makeText(mContext, R.string.contact_to_dev, Toast.LENGTH_SHORT).show();
        }
        for (CharacterOtherData ch : characterOtherDataList) {
            havedCharIds.add(ch.getCharData().getCharId());
        }
    }

    @Override
    public void onStop() {
        prefCharDataSave(characterOtherDataList);
        super.onStop();
    }

    private void init() {
        adViewInit();

        pref = mContext.getSharedPreferences(PREF_CHAR_TYPE, Context.MODE_PRIVATE);
        editor = pref.edit();
        prefCharDataSearch();
        srlRefresh.setOnRefreshListener(this);

        initRecyclerView();
    }

    private void toCharacterDetailFragment(CharacterOtherData charData) {
        Bundle bundle = new Bundle(1);
        bundle.putSerializable(getString(R.string.char_data), charData);
        CharDetailFragment cdf = new CharDetailFragment();
        cdf.setArguments(bundle);
        ((MainActivity) getActivity()).addFragment(cdf, getString(R.string.char_detail_fragment));
    }

    private void reqGetStatus(CharacterOtherData charData, final int i) {
        Call<ResCharStatus> charStatusCall =
                dundoneService.getCharStatus(charData.getServerData().getServerId(), charData.getCharData().getCharId());
        charStatusCall.enqueue(new Callback<ResCharStatus>() {
            @Override
            public void onResponse(Call<ResCharStatus> call, Response<ResCharStatus> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getCode() == ResponseCode.SUCCESS) {
                            RaidRemainData rdData = response.body().getOthers();
                            rdData.initParsing();
                            charData.setOthers(rdData);
                            characterListAdapter.notifyItemChanged(i);
                        } else {
                            Toast.makeText(mContext, "errorcode " + response.body().getCode() + " : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(mContext, "errorcode " + response.code() + " : " + response.message(), Toast.LENGTH_LONG).show();
                    }
                    refreshCount++;
                    if (refreshCount == characterOtherDataList.size())
                        srlRefresh.setRefreshing(false);
                } catch (Exception e) {
                    Toast.makeText(mContext, "reqGetStatus : " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResCharStatus> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
                refreshCount++;
                if (refreshCount == characterOtherDataList.size()) srlRefresh.setRefreshing(false);
            }
        });

    }

    private void removeConfirmDialog(int pos) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.create();
        dialog.setTitle(characterOtherDataList.get(pos).getCharData().getCharName() + "(을)를 삭제합니다.")
                .setNegativeButton("아니오", (dialog12, which) -> {
                })
                .setPositiveButton("네", (dialog1, which) -> {
                    havedCharIds.remove(characterOtherDataList.get(pos).getCharData().getCharId());
                    characterListAdapter.remove(pos);
                }).show();
    }

    private int refreshCount;

    private void UpdateStatusList() {
        refreshCount = 0;
        for (int i = 0; i < characterOtherDataList.size(); i++) {
            characterOtherDataList.get(i).getOthers().setNotYetLoaded();
            reqGetStatus(characterOtherDataList.get(i), i);
        }
    }

    private void initRecyclerView() {
        rvCharListView.setLayoutManager(new LinearLayoutManager(mContext));
        rvCharListView.addItemDecoration(new CustomRecyclerDecoration(DpToPixel(mContext, 20)));

        UpdateStatusList();

        characterListAdapter = new CharacterListAdapter(characterOtherDataList, mContext);
        rvCharListView.setAdapter(characterListAdapter);
        characterListAdapter.setOnItemClickListener((View v, int p) -> {
            toCharacterDetailFragment(characterOtherDataList.get(p));
        });
        characterListAdapter.setOnItemLongClickListener((v, p) -> {
            removeConfirmDialog(p);
            return true;
        });
        rvCharListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (rvCharListView.canScrollVertically(-1)) {
                    if (characterListAdapter.addSize()) {
                        final int pos = characterListAdapter.getItemCount() - 1;
                        reqGetStatus(characterOtherDataList.get(pos), pos);
                    }
                }
            }
        });
    }

    public void reqCharStatusUpdate(CharacterOtherData data, final int pos) {
        String serverId = data.getServerData().getServerId();
        String charId = data.getCharData().getCharId();
        Call<BaseDundoneResponse> call = dundoneService.Update(serverId, charId);
        call.enqueue(new Callback<BaseDundoneResponse>() {
            @Override
            public void onResponse(Call<BaseDundoneResponse> call, Response<BaseDundoneResponse> response) {
                try {
                    reqGetStatus(data, pos);
                } catch (Exception e) {
                    Toast.makeText(mContext, "reqCharStatusUpdate : " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BaseDundoneResponse> call, Throwable t) {
                //Toast.makeText(mContext, "charStateUpdate fail : " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void add(CharInfoData data) {
        characterOtherDataList.add(new CharacterOtherData(data, new RaidRemainData("0/0", "0/0","0/0", "-1/-1")));
        if (characterListAdapter.isSoMiniSize()) {
            characterListAdapter.addSize();
        }
        characterListAdapter.notifyItemInserted(characterOtherDataList.size());
        havedCharIds.add(data.getCharData().getCharId());
        final int pos = characterOtherDataList.size() - 1;

        reqCharStatusUpdate(characterOtherDataList.get(pos), pos);
    }

    @Override
    public void onRefresh() {
        if (characterOtherDataList.isEmpty()) {
            srlRefresh.setRefreshing(false);
            return;
        }
        UpdateStatusList();
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
