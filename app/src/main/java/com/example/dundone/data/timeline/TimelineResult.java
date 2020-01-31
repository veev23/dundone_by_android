package com.example.dundone.data.timeline;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimelineResult {

    @SerializedName("searchRange")
    @Expose
    public SearchRange searchRange;
    @SerializedName("next")
    @Expose
    public String next;
    @SerializedName("rows")
    @Expose
    public ArrayList<Timeline> timelineList = null;

}
