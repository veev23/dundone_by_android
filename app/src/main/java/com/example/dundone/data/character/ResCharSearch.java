package com.example.dundone.data.character;

import java.util.ArrayList;

public class ResCharSearch {
    private ArrayList<CharacterData> result;
    private boolean isSuccess;
    private int code;
    private String message;

    public ResCharSearch(ArrayList<CharacterData> result, boolean isSuccess, int code, String message) {
        this.result = result;
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public ArrayList<CharacterData> getResult() {
        return result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
