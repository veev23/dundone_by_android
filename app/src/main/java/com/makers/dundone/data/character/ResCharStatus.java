package com.makers.dundone.data.character;

import com.makers.dundone.data.BaseDundoneResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResCharStatus extends BaseDundoneResponse {

    public RaidRemainData getOthers() {
        return result;
    }

    @SerializedName("result")
    @Expose
    private RaidRemainData result;

    public ResCharStatus(boolean isSuccess, int code, String message, RaidRemainData result) {
        super(isSuccess, code, message);
        this.result = result;
    }
}
