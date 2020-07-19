package com.makers.dundone.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseDundoneResponse implements Serializable {

    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public BaseDundoneResponse(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
