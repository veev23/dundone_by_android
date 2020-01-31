package com.example.dundone.data.timeline;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timeline {

    @SerializedName("code")
    @Expose
    public int code;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("searchRange")
    @Expose
    public String date;
    @SerializedName("timelineData")
    @Expose
    public TimelineData timelineData;

}