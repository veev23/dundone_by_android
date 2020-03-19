package com.example.dundone.data.character;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResRaidClearCounts {
    public class RaidData{
        @SerializedName("raidName")
        @Expose
        private String raidName;
        @SerializedName("cnt")
        @Expose
        private int cnt;

        public String getRaidName() {
            return raidName;
        }

        public int getCnt() {
            return cnt;
        }

        public RaidData(String raidName, int cnt) {
            this.raidName = raidName;
            this.cnt = cnt;
        }
    }

    public ResRaidClearCounts(ArrayList<RaidData> result, boolean isSuccess, int code, String message) {
        this.result = result;
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public ArrayList<RaidData> getResult() {
        return result;
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

    @SerializedName("result")
    @Expose
    private ArrayList<RaidData> result;
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
