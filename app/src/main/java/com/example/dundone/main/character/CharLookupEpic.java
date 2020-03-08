package com.example.dundone.main.character;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dundone.R;
import com.example.dundone.common_class.CustomRecyclerDecoration;
import com.example.dundone.data.character.CharBaseData;
import com.example.dundone.data.item.EpicData;
import com.example.dundone.main.MainActivity;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CharLookupEpic extends Fragment {

    private Context mContext;

    @BindView(R.id.char_menu)
    View vCharMenu;
    private TextView tvCharName;
    private TextView tvTitle;
    private ImageView ivCharImg;
    @BindView(R.id.recyclerview)
    RecyclerView rvEpicViews;
    private ArrayList<EpicData> mEpicList = new ArrayList<>();
    private BaseInfoAdapter<EpicData> baseInfoAdapter;

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
            tvTitle.setText("에픽 조회");
            String url = "https://img-api.neople.co.kr/df/servers/" + charData.getServerData().getServerId()
                    + "/characters/" + charData.getCharId() + "?zoom=3";
            Glide.with(mContext).load(url).into(ivCharImg);
        }
        else{
            Toast.makeText(mContext, "화면 전환중에 무언가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick({R.id.recent, R.id.old})
    void test(){
        Toast.makeText(mContext, "클릭", Toast.LENGTH_SHORT).show();
    }
    private void initRecyclerView(){
        mEpicList.add(new EpicData("1", "2"));
        mEpicList.add(new EpicData("2", "2"));
        mEpicList.add(new EpicData("3", "2"));
        mEpicList.add(new EpicData("4", "2"));
        mEpicList.add(new EpicData("5", "2"));
        mEpicList.add(new EpicData("6", "2"));
        mEpicList.add(new EpicData("7", "2"));
        mEpicList.add(new EpicData("8", "2"));
        mEpicList.add(new EpicData("9", "2"));
        mEpicList.add(new EpicData("10", "2"));
        mEpicList.add(new EpicData("11", "2"));
        mEpicList.add(new EpicData("1", "2"));
        mEpicList.add(new EpicData("2", "2"));
        mEpicList.add(new EpicData("3", "2"));
        mEpicList.add(new EpicData("4", "2"));
        mEpicList.add(new EpicData("5", "2"));
        mEpicList.add(new EpicData("6", "2"));
        mEpicList.add(new EpicData("7", "2"));
        mEpicList.add(new EpicData("8", "2"));
        mEpicList.add(new EpicData("9", "2"));
        mEpicList.add(new EpicData("10", "2"));
        mEpicList.add(new EpicData("11", "2"));
        mEpicList.add(new EpicData("1", "2"));
        mEpicList.add(new EpicData("2", "2"));
        mEpicList.add(new EpicData("3", "2"));
        mEpicList.add(new EpicData("4", "2"));
        mEpicList.add(new EpicData("5", "2"));
        mEpicList.add(new EpicData("6", "2"));
        mEpicList.add(new EpicData("7", "2"));
        mEpicList.add(new EpicData("8", "2"));
        mEpicList.add(new EpicData("9", "2"));
        mEpicList.add(new EpicData("10", "2"));
        mEpicList.add(new EpicData("11", "2"));
        mEpicList.add(new EpicData("1", "2"));
        mEpicList.add(new EpicData("2", "2"));
        mEpicList.add(new EpicData("3", "2"));
        mEpicList.add(new EpicData("4", "2"));
        mEpicList.add(new EpicData("5", "2"));
        mEpicList.add(new EpicData("6", "2"));
        mEpicList.add(new EpicData("7", "2"));
        mEpicList.add(new EpicData("8", "2"));
        mEpicList.add(new EpicData("9", "2"));
        mEpicList.add(new EpicData("10", "2"));
        mEpicList.add(new EpicData("11", "2"));
        mEpicList.add(new EpicData("1", "2"));
        mEpicList.add(new EpicData("2", "2"));
        mEpicList.add(new EpicData("3", "2"));
        mEpicList.add(new EpicData("4", "2"));
        mEpicList.add(new EpicData("5", "2"));
        mEpicList.add(new EpicData("6", "2"));
        mEpicList.add(new EpicData("7", "2"));
        mEpicList.add(new EpicData("8", "2"));
        mEpicList.add(new EpicData("9", "2"));
        mEpicList.add(new EpicData("10", "2"));
        mEpicList.add(new EpicData("11", "2"));
        mEpicList.add(new EpicData("1", "2"));
        mEpicList.add(new EpicData("2", "2"));
        mEpicList.add(new EpicData("3", "2"));
        mEpicList.add(new EpicData("4", "2"));
        mEpicList.add(new EpicData("5", "2"));
        mEpicList.add(new EpicData("6", "2"));
        mEpicList.add(new EpicData("7", "2"));
        mEpicList.add(new EpicData("8", "2"));
        mEpicList.add(new EpicData("9", "2"));
        mEpicList.add(new EpicData("10", "2"));
        mEpicList.add(new EpicData("11", "2"));
        rvEpicViews.setLayoutManager(new LinearLayoutManager(mContext));
        rvEpicViews.addItemDecoration(new CustomRecyclerDecoration(10));

        baseInfoAdapter=new BaseInfoAdapter<>(mContext, mEpicList);
        rvEpicViews.setAdapter(baseInfoAdapter);
    }
    private void init(){
        initCharStatus();
        initRecyclerView();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_char_lookup_epic, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
}
