package com.example.dundone.data.character;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CharData implements Serializable {
    public String getCharName() {
        return charName;
    }

    public String getCharId() {
        return charId;
    }

    public CharData(String charName, String charId) {
        this.charName = charName;
        this.charId = charId;
    }

    @SerializedName("characterName")
    private String charName;
    @SerializedName("characterId")
    private String charId;
}
