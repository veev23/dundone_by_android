package com.example.dundone.data.etc;

import com.google.gson.annotations.SerializedName;

public class Channel {
    @SerializedName("dungeonName")
    private String dungeonName;
    @SerializedName("channelNo")
    private int channelNo;

    public String getDungeonName() {
        return dungeonName;
    }

    public int getChannelNo() {
        return channelNo;
    }

    public Channel(String dungeonName, int channelNo) {
        this.dungeonName = dungeonName;
        this.channelNo = channelNo;
    }
}
