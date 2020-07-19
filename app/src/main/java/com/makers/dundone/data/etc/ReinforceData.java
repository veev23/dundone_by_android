package com.makers.dundone.data.etc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReinforceData {
    @SerializedName("upgrade")
    @Expose
    private int level;
    @SerializedName("success")
    @Expose
    private int success;
    @SerializedName("fail")
    @Expose
    private int fail;
    public int getLevel() {
        return level;
    }

    public int getSuccess() {
        return success;
    }

    public int getFail() {
        return fail;
    }

    public ReinforceData(int level, int successCount, int failCount) {
        this.level = level;
        this.success = successCount;
        this.fail = failCount;
    }

}
