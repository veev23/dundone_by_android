package com.example.dundone.data.etc;

import com.google.gson.annotations.SerializedName;

public class ResRecommendHellCh {
    @SerializedName("result")
    private Channel channel;
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public Channel getChannel() {
        return channel;
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

    public ResRecommendHellCh(Channel channel, boolean isSuccess, int code, String message) {
        this.channel = channel;
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
