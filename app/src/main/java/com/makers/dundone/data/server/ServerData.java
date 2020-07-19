package com.makers.dundone.data.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServerData implements Serializable {
    @SerializedName("serverId")
    @Expose
    private String serverId;
    @SerializedName("serverName")
    @Expose
    private String serverName;

    public ServerData(String serverId, String serverName) {
        this.serverId = serverId;
        this.serverName = serverName;
    }

    public String getServerId() {
        return serverId;
    }
    public String getServerName() {
        return serverName;
    }

}
