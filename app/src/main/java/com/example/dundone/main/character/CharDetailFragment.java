package com.example.dundone.main.character;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dundone.R;
import com.example.dundone.data.character.CharacterOtherData;
import com.example.dundone.data.character.ResCharBaseDataFromDNF;
import com.example.dundone.main.MainActivity;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dundone.Singleton.dnfService;

public class CharDetailFragment extends Fragment {

    private Context mContext;

    @BindView(R.id.char_menu)
    View vCharMenu;
    private TextView tvCharName;
    private TextView tvTitle;
    private ImageView ivCharImg;
    @BindView(R.id.level_in_char_detail)
    TextView tvLevel;
    @BindView(R.id.guildname_in_char_detail)
    TextView tvGuildName;
    private CharacterOtherData charData;
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
            charData = (CharacterOtherData) bundle.getSerializable(getString(R.string.char_data));
            tvCharName.setText(charData.getCharData().getCharName());
            tvTitle.setText(charData.getServerData().getServerName());
            String url = "https://img-api.neople.co.kr/df/servers/" + charData.getServerData().getServerId()
                    + "/characters/" + charData.getCharData().getCharId() + "?zoom=3";
            Glide.with(mContext).load(url).into(ivCharImg);

            Call<ResCharBaseDataFromDNF> resCharBaseDataFromDNFCall =
                    dnfService.getCharBaseData(charData.getServerData().getServerId(),
                            charData.getCharData().getCharId(),
                            getString(R.string.apikey));
            resCharBaseDataFromDNFCall.enqueue(new Callback<ResCharBaseDataFromDNF>() {
                @Override
                public void onResponse(Call<ResCharBaseDataFromDNF> call, Response<ResCharBaseDataFromDNF> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            tvLevel.setText(String.valueOf(response.body().getLevel()));
                            tvGuildName.setText(String.valueOf(response.body().getGuildName()));
                        }
                    } else {
                        Toast.makeText(mContext, "neople errorcode : " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResCharBaseDataFromDNF> call, Throwable t) {
                    Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(mContext, "화면 전환중에 무언가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    private void callAddFragment(Fragment fragment, String calleeName){
        Bundle bundle = new Bundle(1);
        bundle.putSerializable(getString(R.string.char_data), charData);
        fragment.setArguments(bundle);
        ((MainActivity)getActivity()).addFragment(fragment, calleeName);
    }
    @OnClick(R.id.lookup_epic_button)
    void toCharLookupEpic(){
        CharLookupEpicFragment fragment = new CharLookupEpicFragment();
        callAddFragment(fragment, getString(R.string.char_lookup_epic_fragment));
    }
    @OnClick(R.id.lookup_upgrade_button)
    void toCharLookupUpgrade(){
        CharLookupUpgradeFragment fragment = new CharLookupUpgradeFragment();
        callAddFragment(fragment, getString(R.string.char_lookup_upgrade_fragment));
    }
    @OnClick(R.id.lookup_raid_record)
    void toCharLookupUpRaidRecord(){
        CharLookupRaidRecordFragment fragment = new CharLookupRaidRecordFragment();
        callAddFragment(fragment, getString(R.string.char_lookup_raid_record_fragment));
    }

    private void init(){
        initCharStatus();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_char_detail, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
}
