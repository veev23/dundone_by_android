package com.makers.dundone.data.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpicCountData {
    @SerializedName("itemName")
    @Expose
    private String name;
    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("cnt")
    @Expose
    private int cnt;

    public String getName() {
        return name;
    }

    public String getItemId() {
        return itemId;
    }

    public int getCnt() {
        return cnt;
    }

    public EpicCountData(String name, String itemId, int cnt) {
        this.name = name;
        this.itemId = itemId;
        this.cnt = cnt;
    }
}
