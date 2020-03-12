package com.example.dundone.data.character;

import com.example.dundone.data.server.ServerData;
public class CharacterOtherData extends CharInfoData {
    public CharacterOtherData(CharInfoData cbd, RaidData others){
        super(cbd);
        this.others =others;
    }
    public RaidData getOthers() {
        return others;
    }

    public void setOthers(RaidData others) {
        this.others = others;
    }

    private RaidData others;
}
