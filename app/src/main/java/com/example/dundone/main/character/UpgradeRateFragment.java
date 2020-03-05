package com.example.dundone.main.character;

import android.content.Context;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dundone.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UpgradeRateFragment extends Fragment {

    private Context mContext;

    @BindView(R.id.success_bar)
    View mSuccessBar;
    @BindView(R.id.fail_bar)
    View mFailBar;
    @BindView(R.id.success_count)
    TextView mSuccessCount;
    @BindView(R.id.fail_count)
    TextView mFailCount;
    @BindView(R.id.success_rate)
    TextView mSuccessRate;
    @BindView(R.id.fail_rate)
    TextView mFailRate;
    @BindView(R.id.constraintlayout)
    ConstraintLayout clLayout;


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
                ConstraintSet targetSet = new ConstraintSet();
                targetSet.clone(clLayout);
                int maxHeight = mSuccessBar.getHeight();
                int height_success = (int)((double)maxHeight * 0.5)+1;
                int height_fail = maxHeight-height_success;
                TransitionManager.beginDelayedTransition(clLayout,new AutoTransition().setDuration(1000));

                targetSet.constrainHeight(R.id.success_bar, height_success);
                targetSet.constrainHeight(R.id.fail_bar, height_fail);
                targetSet.clear(R.id.success_bar, ConstraintSet.TOP);
                targetSet.clear(R.id.fail_bar, ConstraintSet.TOP);

                mSuccessCount.setText("50회");
                mFailCount.setText("50회");
                mSuccessRate.setText("50%");
                mFailRate.setText("50%");

                targetSet.applyTo(clLayout);
            }
        });
    }
}
