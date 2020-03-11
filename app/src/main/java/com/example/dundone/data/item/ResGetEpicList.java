package com.example.dundone.data.item;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResGetEpicList{
    @SerializedName("result")
    private ArrayList<EpicData> itemList;
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public ResGetEpicList(ArrayList<EpicData> itemList, boolean isSuccess, int code, String message) {
        this.itemList = itemList;
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public ArrayList<EpicData> getItemList() {
        return itemList;
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
