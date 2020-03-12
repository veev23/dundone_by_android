package com.example.dundone.data.character;

import com.example.dundone.data.server.ServerData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CharInfoData implements Serializable {
    @SerializedName("char")
    private CharData charData;
    @SerializedName("serverData")
    private ServerData serverData;

    public CharData getCharData() {
        return charData;
    }

    public ServerData getServerData() {
        return serverData;
    }

    public CharInfoData(CharInfoData cid){
        this.charData = cid.charData;
        this.serverData = cid.serverData;
    }
    public CharInfoData(CharData charData, ServerData serverData) {
        this.charData = charData;
        this.serverData = serverData;
    }
}
