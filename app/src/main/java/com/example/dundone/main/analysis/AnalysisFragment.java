package com.example.dundone.main.analysis;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dundone.R;
import com.example.dundone.main.MainActivity;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnalysisFragment extends Fragment {
    private Context mContext;
    @OnClick(R.id.epic_analysys)
    void toAnalysysEpicFragment(){
        ((MainActivity)getActivity()).addFragment(new AnalysysEpicFragment(), getString(R.string.analysis_epic_fragment));
    }

    @OnClick(R.id.rainforce_analysys)
    void toAnalysysRainforceFragment(){
        ((MainActivity)getActivity()).addFragment(new AnalysysReinforceFragment(), getString(R.string.analysis_reinforce_fragment));
    }
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
