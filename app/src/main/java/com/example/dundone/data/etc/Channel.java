package com.example.dundone.data.etc;

import com.google.gson.annotations.SerializedName;

public class Channel {
    @SerializedName("dungeonName")
    private String dungeonName;
    @SerializedName("channelNo")
    private int channelNo;
    @SerializedName("channelName")
    private int channelName;

    public String getDungeonName() {
        return dungeonName;
    }

    public int getChannelNo() {
        return channelNo;
    }

    public int getChannelName() {
        return channelName;
    }

    public Channel(String dungeonName, int channelNo, int channelName) {
        this.dungeonName = dungeonName;
        this.channelNo = channelNo;
        this.channelName = channelName;
    }
}
