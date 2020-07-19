package com.makers.dundone.data.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResGetItemIds implements Serializable {
    @SerializedName("rows")
    ArrayList<ItemData> items;

    public ArrayList<ItemData> getItems() {
        return items;
    }

    public ResGetItemIds(ArrayList<ItemData> items) {
        this.items = items;
    }
}
