package com.example.dundone.data.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemData implements Serializable {
    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemRarity")
    @Expose
    private String itemRarity;
    @SerializedName("itemType")
    @Expose
    private String itemType;
    @SerializedName("itemTypeDetail")
    @Expose
    private String itemTypeDetail;
    @SerializedName("itemAvailableLevel")
    @Expose
    private int itemAvailableLevel;

    public ItemData(String itemId, String itemName, String itemRarity, String itemType, String itemTypeDetail, int itemAvailableLevel) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemRarity = itemRarity;
        this.itemType = itemType;
        this.itemTypeDetail = itemTypeDetail;
        this.itemAvailableLevel = itemAvailableLevel;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemRarity() {
        return itemRarity;
    }

    public void setItemRarity(String itemRarity) {
        this.itemRarity = itemRarity;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemTypeDetail() {
        return itemTypeDetail;
    }

    public void setItemTypeDetail(String itemTypeDetail) {
        this.itemTypeDetail = itemTypeDetail;
    }

    public int getItemAvailableLevel() {
        return itemAvailableLevel;
    }

    public void setItemAvailableLevel(int itemAvailableLevel) {
        this.itemAvailableLevel = itemAvailableLevel;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
