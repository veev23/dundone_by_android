package com.example.dundone.main.analysis;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dundone.R;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public class AnalysisFragment extends Fragment {
    private Context mContext;
    private void init(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_analysis, container, false);
        mContext = getContext();
        ButterKnife.bind(this, v);
        init();
        return v;
    }
}
