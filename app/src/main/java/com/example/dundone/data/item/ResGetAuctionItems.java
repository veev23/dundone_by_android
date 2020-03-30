package com.example.dundone.data.item;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResGetAuctionItems {
    @SerializedName("rows")
    ArrayList<AuctionItem> list;

    public ArrayList<AuctionItem> getList() {
        return list;
    }

    public ResGetAuctionItems(ArrayList<AuctionItem> list) {
        this.list = list;
    }
}
