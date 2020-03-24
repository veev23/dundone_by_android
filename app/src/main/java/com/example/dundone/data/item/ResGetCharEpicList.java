package com.example.dundone.data.item;

import com.example.dundone.data.BaseDundoneResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResGetCharEpicList extends BaseDundoneResponse {
    @SerializedName("result")
    private ArrayList<EpicData> itemList;

    public ResGetCharEpicList(boolean isSuccess, int code, String message, ArrayList<EpicData> itemList) {
        super(isSuccess, code, message);
        this.itemList = itemList;
    }

    public ArrayList<EpicData> getItemList() {
        return itemList;
    }

}
