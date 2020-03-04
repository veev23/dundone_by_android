package com.example.dundone.main.hell;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dundone.R;
import com.example.dundone.main.MainActivity;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HellRecommendFragment extends Fragment {
    private Context mContext;

    public static final int many=0;
    public static final int wish=1;
    private void toRecommendResult(int type){
        Toast.makeText(mContext, "머임???", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle(1);
        bundle.putInt("type", type);
        RecommendResultFragment rrf = new RecommendResultFragment();
        rrf.setArguments(bundle);
        ((MainActivity)getActivity()).addFragment(rrf, getString(R.string.recommend_result_fragment));
    }
    private void init(){
    }
    @OnClick(R.id.many_epic_recommend)
    void toManyFragment(){
        toRecommendResult(many);
    }
    @OnClick({R.id.wish_epic_recommend})
    void toWishFragment(){
        toRecommendResult(wish);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hell_recommend, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
}
