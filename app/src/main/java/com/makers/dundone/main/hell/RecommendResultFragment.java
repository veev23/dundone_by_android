package com.makers.dundone.main.hell;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.makers.dundone.R;
import com.makers.dundone.data.etc.Channel;
import com.makers.dundone.data.etc.ResRecommendHellCh;
import com.makers.dundone.main.MainActivity;
import com.makers.dundone.main.NeopleAPI;
import com.makers.dundone.main.ResponseCode;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.dundone.Singleton.dundoneService;
import static com.makers.dundone.main.hell.HellRecommendFragment.many;

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
    TextView tvAnswerChannelName;
    @BindView(R.id.answer_num)
    TextView tvAnswerChNum;

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
            reqGetRecommendChannel(null);
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
            initInput();
        }
    }

    private void initInput(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("원하시는 에픽 이름을 적어주세요!");

        final EditText input = new EditText(mContext);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String itemName = input.getText().toString();
                Toast.makeText(mContext, "\""+itemName+"\"(으)로 빌어볼게요!!",Toast.LENGTH_SHORT).show();
                reqGetRecommendChannel(itemName);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(mContext, "뭔지는 모르겠지만..",Toast.LENGTH_SHORT).show();
                reqGetRecommendChannel(null);
            }
        });
        builder.show();
    }
    private void reqGetRecommendChannel(String itemName){
        Call<ResRecommendHellCh> call = dundoneService.getHellChannel(itemName);
        call.enqueue(new Callback<ResRecommendHellCh>() {
            @Override
            public void onResponse(Call<ResRecommendHellCh> call, Response<ResRecommendHellCh> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == ResponseCode.SUCCESS) {
                       Channel data = response.body().getChannel();
                       tvAnswerChannelName.setText(data.getChannelName());
                       tvAnswerChNum.setText(getString(R.string.channel_recommend, data.getChannelNo()));
                    } else {
                        Toast.makeText(mContext, "errorcode " + response.body().getCode() + " : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "errorcode " + response.code() + " : " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResRecommendHellCh> call, Throwable t) {
                Toast.makeText(mContext, "Request Fail : " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
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
