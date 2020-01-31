package com.example.dundone.data.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResServerList {
    @SerializedName("rows")
    @Expose
    ArrayList<ServerData> rows;

    public ResServerList(ArrayList<ServerData> rows) {
        this.rows = rows;
    }

    public ArrayList<ServerData> getRows() {
        return rows;
    }
}
