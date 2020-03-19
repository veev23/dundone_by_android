package com.example.dundone.data.character;

public class CharacterOtherData extends CharInfoData {
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
