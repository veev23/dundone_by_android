package com.example.dundone.data.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResItemList {
    @SerializedName("rows")
    @Expose
    private ArrayList<ItemData> rows = null;

    public ArrayList<ItemData> getRows() {
        return rows;
    }

    public ResItemList(ArrayList<ItemData> rows) {
        this.rows = rows;
    }
}
