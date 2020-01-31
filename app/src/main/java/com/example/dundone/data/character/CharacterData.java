package com.example.dundone.data.character;

import com.example.dundone.data.server.ServerData;

public class CharacterData {
    public CharacterData(String charName, String charId, ServerData server, OtherData others) {
        this.charName = charName;
        this.charId = charId;
        this.server = server;
        this.others = others;
    }

    public String getCharName() {
        return charName;
    }

    public String getCharId() {
        return charId;
    }

    public ServerData getServer() {
        return server;
    }

    public OtherData getOthers() {
        return others;
    }

    private String charName;
    private String charId;
    private ServerData server;
    private OtherData others;
}
