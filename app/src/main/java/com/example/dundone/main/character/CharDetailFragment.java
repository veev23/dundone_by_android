package com.example.dundone.main.character;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dundone.R;
import com.example.dundone.data.character.CharBaseData;
import com.example.dundone.main.MainActivity;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CharDetailFragment extends Fragment {

    private Context mContext;

    @BindView(R.id.name_in_char_detail)
    TextView tvCharName;
    @BindView(R.id.server_in_char_detail)
    TextView tvCharServer;
    @BindView(R.id.img_in_char_detail)
    ImageView ivCharImg;

    @OnClick(R.id.back_button_in_char_detail)
    void back(){
        ((MainActivity)getActivity()).backFragment();
    }
    private void initLayout(){

    }
    private void initCharStatus(){
        Bundle bundle = getArguments();
        CharBaseData charData = (CharBaseData)bundle.getSerializable("char_info");
        tvCharName.setText(charData.getCharName());
        tvCharServer.setText(charData.getServerData().getServerName());
        String url = "https://img-api.neople.co.kr/df/servers/"+charData.getServerData().getServerId()
                +"/characters/"+charData.getCharId()+"?zoom=3";
        Glide.with(mContext).load(url).into(ivCharImg);
    }
    private void init(){
        initCharStatus();
        initLayout();
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
