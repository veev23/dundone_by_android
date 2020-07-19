package com.makers.dundone.data.character;

import com.makers.dundone.data.BaseDundoneResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResCharSearch extends BaseDundoneResponse {
    @SerializedName("result")
    @Expose
    private ArrayList<CharInfoData> result;
    public ArrayList<CharInfoData> getResult() {
        return result;
    }

    public ResCharSearch(boolean isSuccess, int code, String message, ArrayList<CharInfoData> result) {
        super(isSuccess, code, message);
        this.result = result;
    }
}
