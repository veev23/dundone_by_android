package com.example.dundone.data.item;

import com.example.dundone.data.BaseDundoneResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResGetEpicDropList extends BaseDundoneResponse {
    @SerializedName("result")
    private ArrayList<EpicCountData> itemList;

    public ArrayList<EpicCountData> getItemList() {
        return itemList;
    }

    public ResGetEpicDropList(boolean isSuccess, int code, String message, ArrayList<EpicCountData> itemList) {
        super(isSuccess, code, message);
        this.itemList = itemList;
    }
}
