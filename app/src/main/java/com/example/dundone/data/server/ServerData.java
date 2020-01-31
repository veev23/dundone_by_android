package com.example.dundone.data.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerData {
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
