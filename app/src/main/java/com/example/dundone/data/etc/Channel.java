package com.example.dundone.data.etc;

import com.google.gson.annotations.SerializedName;

public class Channel {
    @SerializedName("dungeonName")
    private String dungeonName;
    @SerializedName("channelNo")
    private int channelNo;
    @SerializedName("channelName")
    private String channelName;

    public String getDungeonName() {
        return dungeonName;
    }

    public int getChannelNo() {
        return channelNo;
    }

    public String getChannelName() {
        return channelName;
    }

    public Channel(String dungeonName, int channelNo, String channelName) {
        this.dungeonName = dungeonName;
        this.channelNo = channelNo;
        this.channelName = channelName;
    }
}
