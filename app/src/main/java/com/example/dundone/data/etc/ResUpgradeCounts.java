package com.example.dundone.data.etc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResUpgradeCounts {
    public ArrayList<ReinforceData> getmUpgradeCounts() {
        return mUpgradeCounts;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ResUpgradeCounts(ArrayList<ReinforceData> mUpgradeCounts, boolean isSuccess, int code, String message) {
        this.mUpgradeCounts = mUpgradeCounts;
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    @SerializedName("result")
    @Expose
    private ArrayList<ReinforceData> mUpgradeCounts;
    @SerializedName("isSuccess")
    @Expose
    private boolean isSuccess;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("message")
    @Expose
    private String message;
}
