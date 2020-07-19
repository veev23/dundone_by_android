package com.makers.dundone.data.character;

import com.makers.dundone.data.BaseDundoneResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResRaidClearCounts extends BaseDundoneResponse {
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

    public ResRaidClearCounts(boolean isSuccess, int code, String message, ArrayList<RaidData> result) {
        super(isSuccess, code, message);
        this.result = result;
    }

    public ArrayList<RaidData> getResult() {
        return result;
    }

    @SerializedName("result")
    @Expose
    private ArrayList<RaidData> result;
}
