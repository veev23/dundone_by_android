package com.example.dundone.main.hell;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.example.dundone.R;
import com.example.dundone.main.MainActivity;
import com.example.dundone.main.NeopleAPI;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.dundone.main.hell.HellRecommendFragment.many;

public class RecommendResultFragment extends Fragment {
    private Context mContext;

    @BindView(R.id.recommend_background)
    ImageView ivBackground;
    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.iv_neople_openapi)
    View ivToDevSite;
    @BindView(R.id.scratchView_sky)
    ScratchView svScratchView_sky;
    @BindView(R.id.scratchView_little_red)
    ScratchView svScratchView_little_red;
    @BindView(R.id.frameLayout)
    View vHideAnswer;
    @BindView(R.id.answer_channel)
    TextView tvAnswerChannel;
    @BindView(R.id.answer_num)
    TextView tvAnswerNum;

    @OnClick(R.id.back_button)
    void back(){
        ((MainActivity)getActivity()).backFragment();
    }
    private void initLayout(){
        Bundle bundle = getArguments();
        if(bundle == null) {
            Toast.makeText(mContext, "화면 전환에서 문제가 생겼습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        int type = bundle.getInt("type");
        if(type == many){
            ivBackground.setImageResource(R.drawable.recommend_background1);
            tvTitle.setText("많이 먹게 해주세요!!");
            svScratchView_little_red.setVisibility(View.GONE);
            vHideAnswer.setBackground(mContext.getDrawable(R.drawable.border_sky));
        }
        else{
            ivBackground.setImageResource(R.drawable.recommend_background2);
            tvTitle.setText("신화 먹게 해주세요!!");
            svScratchView_sky.setVisibility(View.GONE);
            vHideAnswer.setBackground(mContext.getDrawable(R.drawable.border_little_red));
        }
    }
    private void init(){
        initLayout();

        ivToDevSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NeopleAPI neopleAPI = new NeopleAPI(mContext);
                neopleAPI.toNeopleDeveloperSite();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recommend_result, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
}
