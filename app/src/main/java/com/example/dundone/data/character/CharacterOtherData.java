package com.example.dundone.data.character;

import android.os.Parcelable;

import java.io.Serializable;

public class CharacterOtherData extends CharInfoData implements Serializable {
    public CharacterOtherData(CharInfoData cbd, RaidRemainData others){
        super(cbd);
        this.others =others;
    }
    public RaidRemainData getOthers() {
        return others;
    }

    public void setOthers(RaidRemainData others) {
        this.others = others;
    }

    private RaidRemainData others;
}
