package com.example.dundone.main.character;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dundone.R;
import com.example.dundone.Singleton;
import com.example.dundone.common_class.CustomRecyclerDecoration;
import com.example.dundone.data.character.CharBaseData;
import com.example.dundone.data.character.CharacterData;
import com.example.dundone.data.character.RaidData;
import com.example.dundone.data.character.ResCharSearch;
import com.example.dundone.data.server.ResServerList;
import com.example.dundone.data.server.ServerData;
import com.example.dundone.main.MainActivity;
import com.example.dundone.main.NeopleAPI;
import com.example.dundone.main.ResponseCode;
import com.example.dundone.onBackPressListener;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dundone.Singleton.DpToPixel;
import static com.example.dundone.Singleton.gson;
import static com.example.dundone.main.character.CharListFragment.CHAR_LIST;
import static com.example.dundone.main.character.CharListFragment.PREF_CHAR_TYPE;

public class CharacterAddFragment extends Fragment implements onBackPressListener {

    private boolean isSearched = false;

    @Override
    public void onBackPress() {
        if (isSearched) {
            updateSearchViewBefore();
        } else {
            ((MainActivity) getActivity()).backFragment();
        }
    }

    ArrayList<CharacterData> charDataAddedList;

    private Context mContext;
    private ArrayList<ServerData> servers = new ArrayList<>();
    private TextView[] tvServer;
    private int selectedServer = 0;

    @BindView(R.id.et_char_search)
    EditText etCharSearch;

    @BindView(R.id.server_list)
    GridLayout glServerView;

    @BindView(R.id.char_search_list_in_char_add)
    RecyclerView rvCharResult;
    private BaseInfoAdapter<CharBaseData> searchAdapter;
    private ArrayList<CharBaseData> charSearchList = new ArrayList<>();

    @BindView(R.id.cl_in_char_add)
    ConstraintLayout container;

    @BindView(R.id.openapi_in_char_add)
    View ivToDevSite;

    private ConstraintSet mAfterConstSet = new ConstraintSet();
    private ConstraintSet mBeforeConstSet = new ConstraintSet();

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private void updateSearchViewBefore() {
        isSearched = false;
        TransitionManager.beginDelayedTransition(container, new AutoTransition().setDuration(400));
        mBeforeConstSet.applyTo(container);
    }

    private void updateSearchViewAfter() {
        isSearched = true;
        ivToDevSite.setVisibility(View.GONE);
        charSearchList.clear();
        searchAdapter.notifyDataSetChanged();
        TransitionManager.beginDelayedTransition(container, new AutoTransition().setDuration(400));
        mAfterConstSet.applyTo(container);
    }

    private void hideKeyBoard() {
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(etCharSearch.getWindowToken(), 0);
    }

    @OnClick(R.id.search_button_in_char_add)
    void reqCharSearch() {

        Toast.makeText(mContext, "\"" + etCharSearch.getText() + "\" 검색", Toast.LENGTH_LONG).show();
        hideKeyBoard();
        etCharSearch.clearFocus();
        updateSearchViewAfter();
        charSearchList.add(new CharBaseData("[에픽]", "6e610499113920b694fae97231bf1fff", new ServerData("bakal", "바칼")));
        charSearchList.add(new CharBaseData("여캐", "c269d0beddd7b2ae69be74a127fc0292", new ServerData("bakal", "바칼")));
        charSearchList.add(new CharBaseData("plnder", "07955eb5e783b7f18a7c0b6bb80c0b98", new ServerData("bakal", "바칼")));

        //TODO:charSearchList 초기화 후 response.body()에서 갱신
        searchAdapter.notifyDataSetChanged();
/*----------------------
        String serverId = servers.get(selectedServer).getServerId();
        String charName = etCharSearch.getText().toString();
        Call<ResCharSearch> resCharSearchCall = Singleton.dundoneService.getCharSearchRes(serverId, charName);
        resCharSearchCall.enqueue(new Callback<ResCharSearch>() {
            @Override
            public void onResponse(Call<ResCharSearch> call, Response<ResCharSearch> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == ResponseCode.SUCCESS) {
                        updateSearchViewAfter();
                        charSearchList.add(new CharBaseData("[에픽]", "6e610499113920b694fae97231bf1fff", new ServerData("bakal", "바칼")));
                        charSearchList.add(new CharBaseData("여캐", "c269d0beddd7b2ae69be74a127fc0292", new ServerData("bakal", "바칼")));
                        charSearchList.add(new CharBaseData("plnder", "07955eb5e783b7f18a7c0b6bb80c0b98", new ServerData("bakal", "바칼")));
                        charSearchList.add(new CharBaseData("plnder", "07955eb5e783b7f18a7c0b6bb80c0b98", new ServerData("bakal", "바칼")));
                        charSearchList.add(new CharBaseData("plnder", "07955eb5e783b7f18a7c0b6bb80c0b98", new ServerData("bakal", "바칼")));

                        //TODO:charSearchList 초기화 후 response.body()에서 갱신
                        searchAdapter.notifyDataSetChanged();
                        if (charSearchList.isEmpty()) {
                            Toast.makeText(mContext, "검색 결과가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(mContext, "errorcode " + response.body().getCode() + " : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "errorcode " + response.code() + " : " + response.message(), Toast.LENGTH_LONG).show();
                }
                ivToDevSite.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ResCharSearch> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        */

    }

    private void pressSearchSetting() {
        etCharSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    reqCharSearch();
                    return true;
                }
                return false;
            }
        });
    }

    private void serverTabOnClick(final int i) {
        tvServer[selectedServer].setTextColor(ContextCompat.getColor(mContext, R.color.colorLittleRed));
        tvServer[selectedServer].setBackgroundResource(R.drawable.radius_empty_little_red_rect);
        tvServer[i].setTextColor(ContextCompat.getColor(mContext, R.color.colorButtonBackgorund));
        tvServer[i].setBackgroundResource(R.drawable.radius_little_red_rect);
        selectedServer = i;
    }

    private void reqGetServerList(LayoutInflater inflater) {
        Call<ResServerList> resServerListCall = Singleton.dnfService.getServerList(getString(R.string.apikey));
        resServerListCall.enqueue(new Callback<ResServerList>() {
            @Override
            public void onResponse(Call<ResServerList> call, @NonNull Response<ResServerList> response) {
                if (response.isSuccessful()) {
                    gridLayoutSetting(inflater, response.body().getRows());
                } else {
                    gridLayoutSetting(inflater, null);
                    Toast.makeText(mContext, "neople errorcode : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResServerList> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void gridLayoutSetting(LayoutInflater inflater, ArrayList<ServerData> serverRes) {
        servers.add(new ServerData("all", "전체"));
        if (serverRes != null) {
            servers.addAll(serverRes);
        }
        tvServer = new TextView[servers.size()];

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenwidth = metrics.widthPixels;
        int cellWidth = 82;
        //양 옆 마진 제외
        int column = (screenwidth - DpToPixel(mContext, 32)) / DpToPixel(mContext, cellWidth);
        int row = servers.size() / column;
        glServerView.setColumnCount(column);
        glServerView.setRowCount(row + 1);
        for (int i = 0, c = 0, r = 0; i < servers.size(); i++, c++) {
            if (c == column) {
                r++;
                //if last row
                if (r == row && (servers.size() - i) % 2 == column % 2) {
                    c = (column - 1) / 2 - ((servers.size() - i) / 2);
                } else {
                    c = 0;
                }
            }
            tvServer[i] = (TextView) inflater.inflate(R.layout.item_server_button, null, false);
            tvServer[i].setText(servers.get(i).getServerName());
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.width = DpToPixel(mContext, 72);
            param.height = DpToPixel(mContext, 30);
            param.topMargin = DpToPixel(mContext, 6);
            param.bottomMargin = DpToPixel(mContext, 6);
            param.columnSpec = GridLayout.spec(c, 1.0f);
            param.rowSpec = GridLayout.spec(r);
            param.setGravity(Gravity.CENTER);
            tvServer[i].setLayoutParams(param);
            final int idx = i;
            tvServer[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serverTabOnClick(idx);
                }
            });
            glServerView.addView(tvServer[i]);
        }
        serverTabOnClick(0);
    }

    private void recyclerViewSetting() {
        searchAdapter = new BaseInfoAdapter<>(mContext, charSearchList);
        rvCharResult.setLayoutManager(new LinearLayoutManager(mContext));
        rvCharResult.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener(new BaseInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemCilckListener(View v, int p) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                String tag = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 2).getName();
                Fragment fragment = fm.findFragmentByTag(tag);
                Toast.makeText(mContext, charSearchList.get(p).getCharName() + " 추가!", Toast.LENGTH_SHORT).show();
                //TODO: 다른 화면에서 온 경우는 막아야함. + 다른 화면에서는 못오게 막아야함.
                if (fragment == null | !(fragment instanceof CharListFragment)) return;
                CharListFragment clf = (CharListFragment) fragment;
                clf.add(charSearchList.get(p));
            }
        });
        CustomRecyclerDecoration customDivider = new CustomRecyclerDecoration(20);
        rvCharResult.addItemDecoration(customDivider);
    }

    private void init(LayoutInflater inflater) {
        reqGetServerList(inflater);
        pressSearchSetting();
        recyclerViewSetting();
        ivToDevSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NeopleAPI neopleAPI = new NeopleAPI(mContext);
                neopleAPI.toNeopleDeveloperSite();
            }
        });

        mAfterConstSet.clone(mContext, R.layout.constrain_layout_list_up_in_char_add);
        mBeforeConstSet.clone(container);

        pref = mContext.getSharedPreferences(PREF_CHAR_TYPE, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_char_add, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init(inflater);
        return v;
    }
}
