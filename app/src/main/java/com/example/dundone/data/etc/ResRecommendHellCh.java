package com.example.dundone.data.etc;

import com.example.dundone.data.BaseDundoneResponse;
import com.google.gson.annotations.SerializedName;

public class ResRecommendHellCh extends BaseDundoneResponse {
    @SerializedName("result")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public ResRecommendHellCh(boolean isSuccess, int code, String message, Channel channel) {
        super(isSuccess, code, message);
        this.channel = channel;
    }
}
