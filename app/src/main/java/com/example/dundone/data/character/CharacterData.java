package com.example.dundone.data.character;

import com.example.dundone.data.server.ServerData;
public class CharacterData extends CharBaseData {
    public CharacterData(String charName, String charId, ServerData server, RaidData others) {
        super(charName, charId, server);
        this.others = others;
    }
    public CharacterData(CharBaseData cbd, RaidData others){
        super(cbd);
        this.others =others;
    }
    public RaidData getOthers() {
        return others;
    }
    private RaidData others;
}
