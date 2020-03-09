package com.example.dundone.main.character;

import android.content.Context;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dundone.R;
import com.example.dundone.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dundone.Singleton.DpToPixel;

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

    private int successCount=0;
    private int failCount=0;

    private void initLayout() {
        Bundle bundle = getArguments();
        if(bundle == null){
            return;
        }
        else {
            successCount = bundle.getInt("successCount");
            failCount = bundle.getInt("failCount");
        }

        int sum = successCount + failCount;
        int successRate = (sum == 0)?0:100 * successCount / sum;
        int failRate = (sum==0)?0:100-successRate;

        ConstraintSet targetSet = new ConstraintSet();
        targetSet.clone(clLayout);
        int maxHeight = mSuccessBar.getHeight()-DpToPixel(mContext, 10);
        int height_success = (int) ((double) maxHeight * (double)successRate / 100.0)+DpToPixel(mContext, 10);
        int height_fail = (int) ((double) maxHeight * (double)failRate / 100.0)+DpToPixel(mContext, 10);
        Log.d("height is ",  maxHeight + ", "+height_success + " , " + height_fail);
        TransitionManager.beginDelayedTransition(clLayout, new AutoTransition().setDuration(1000));

        targetSet.constrainHeight(R.id.success_bar, height_success);
        targetSet.constrainHeight(R.id.fail_bar, height_fail);
        targetSet.clear(R.id.success_bar, ConstraintSet.TOP);
        targetSet.clear(R.id.fail_bar, ConstraintSet.TOP);

        mSuccessCount.setText(getString(R.string.count, successCount));
        mFailCount.setText(getString(R.string.count, failCount));
        mSuccessRate.setText(getString(R.string.percents,successRate));
        mFailRate.setText(getString(R.string.percents, failRate));

        targetSet.applyTo(clLayout);

    }

    private void init() {
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
                initLayout();
            }
        });
    }
}
