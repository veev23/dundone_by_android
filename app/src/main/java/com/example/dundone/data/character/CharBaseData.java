package com.example.dundone.data.character;

import com.example.dundone.data.server.ServerData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CharBaseData implements Serializable {
    @SerializedName("charName")
    private String charName;
    @SerializedName("charId")
    private String charId;
    @SerializedName("server")
    private ServerData serverData;

    public CharBaseData(String charName, String charId, ServerData serverData) {
        this.charName = charName;
        this.charId = charId;
        this.serverData = serverData;
    }
    public CharBaseData(CharBaseData cbd){
        this.charName = cbd.getCharName();
        this.charId = cbd.getCharId();
        this.serverData = cbd.getServerData();
    }
    public String getCharName() {
        return charName;
    }

    public String getCharId() {
        return charId;
    }

    public ServerData getServerData() {
        return serverData;
    }
}
