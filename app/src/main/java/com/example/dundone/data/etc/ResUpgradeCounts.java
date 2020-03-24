package com.example.dundone.data.etc;

import com.example.dundone.data.BaseDundoneResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResUpgradeCounts  extends BaseDundoneResponse {
    public ArrayList<ReinforceData> getmUpgradeCounts() {
        return mUpgradeCounts;
    }

    public ResUpgradeCounts(boolean isSuccess, int code, String message, ArrayList<ReinforceData> mUpgradeCounts) {
        super(isSuccess, code, message);
        this.mUpgradeCounts = mUpgradeCounts;
    }

    @SerializedName("result")
    @Expose
    private ArrayList<ReinforceData> mUpgradeCounts;
}
