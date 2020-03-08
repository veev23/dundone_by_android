package com.example.dundone.data.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpicData {
    @SerializedName("itemName")
    @Expose
    private String name;
    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("date")
    @Expose
    private String date;

    public EpicData(String name, String itemId) {
        this.name = name;
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public String getItemId() {
        return itemId;
    }
}
