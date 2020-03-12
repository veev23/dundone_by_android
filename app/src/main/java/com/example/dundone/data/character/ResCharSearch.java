package com.example.dundone.data.character;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResCharSearch {
    @SerializedName("result")
    @Expose
    private ArrayList<CharInfoData> result;
    @SerializedName("isSuccess")
    @Expose
    private boolean isSuccess;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("message")
    @Expose
    private String message;

    public ResCharSearch(ArrayList<CharInfoData> result, boolean isSuccess, int code, String message) {
        this.result = result;
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public ArrayList<CharInfoData> getResult() {
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
}
