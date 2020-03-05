package com.example.dundone.main.character;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.example.dundone.R;
import com.example.dundone.main.NeopleAPI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UpgradeRateFragment extends Fragment {

    private Context mContext;

    @BindView(R.id.success)
    View mSuccessBar;



    private void initLayout(){
        Bundle bundle = getArguments();


    }
    private void init(){
        initLayout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upgrade_rate, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.post(new Runnable() {
            @Override
            public void run() {
                mSuccessBar.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int height = mSuccessBar.getHeight();
                height = (int)((double)height * 0.5)+1;
                ConstraintLayout.LayoutParams params =
                        (ConstraintLayout.LayoutParams)mSuccessBar.getLayoutParams();
                params.height = height;
                mSuccessBar.setLayoutParams(params);
            }
        });
    }
}
