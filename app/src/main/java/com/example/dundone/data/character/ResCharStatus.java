package com.example.dundone.data.character;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResCharStatus {
    public ResCharStatus(RaidData result, boolean isSuccess, int code, String message) {
        this.result = result;
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public RaidData getOthers() {
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
    private RaidData result;
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
