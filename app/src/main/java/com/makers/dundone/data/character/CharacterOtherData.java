package com.makers.dundone.data.character;

import java.io.Serializable;

public class CharacterOtherData extends CharInfoData implements Serializable {
    public CharacterOtherData(CharInfoData cbd, RaidRemainData others){
        super(cbd);
        this.others =others;
    }
    public RaidRemainData getOthers() {
        if(others == null) others = new RaidRemainData();
        return others;
    }

    public void setOthers(RaidRemainData others) {
        this.others = others;
    }

    private transient RaidRemainData others;
}
