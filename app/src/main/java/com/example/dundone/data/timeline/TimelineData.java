package com.example.dundone.data.timeline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimelineData {

    @SerializedName("itemId")
    @Expose
    public String itemId;
    @SerializedName("itemName")
    @Expose
    public String itemName;
    @SerializedName("itemRarity")
    @Expose
    public String itemRarity;
    @SerializedName("channelName")
    @Expose
    public String channelName;
    @SerializedName("channelNo")
    @Expose
    public int channelNo;
    @SerializedName("dungeonName")
    @Expose
    public String dungeonName;
    @SerializedName("guide")
    @Expose
    public boolean guide;
    @SerializedName("raidName")
    @Expose
    public String raidName;

}