package com.makers.dundone.data.item;

import com.google.gson.annotations.SerializedName;

public class AuctionItem extends ItemData {
    @SerializedName("regDate")
    private String regDate;
    @SerializedName("refine")
    private int refine;
    @SerializedName("reinforce")
    private int reinforce;
    @SerializedName("count")
    private int count;
    @SerializedName("unitprice")
    private int unitprice;
    @SerializedName("averageprice")
    private int averageprice;

    public String getRegDate() {
        return regDate;
    }

    public int getRefine() {
        return refine;
    }

    public int getReinforce() {
        return reinforce;
    }

    public int getCount() {
        return count;
    }

    public int getUnitprice() {
        return unitprice;
    }

    public int getAverageprice() {
        return averageprice;
    }

    public AuctionItem(String itemId, String itemName, String itemRarity, String itemType, String itemTypeDetail, int itemAvailableLevel, String regDate, int refine, int reinforce, int count, int unitprice, int averageprice) {
        super(itemId, itemName, itemRarity, itemType, itemTypeDetail, itemAvailableLevel);
        this.regDate = regDate;
        this.refine = refine;
        this.reinforce = reinforce;
        this.count = count;
        this.unitprice = unitprice;
        this.averageprice = averageprice;
    }
}
