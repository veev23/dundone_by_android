package com.example.dundone.data.timeline;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchRange {

    @SerializedName("start")
    @Expose
    public String start;
    @SerializedName("end")
    @Expose
    public String end;

}