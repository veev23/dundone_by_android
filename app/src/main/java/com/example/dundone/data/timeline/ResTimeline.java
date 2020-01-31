package com.example.dundone.data.timeline;

import com.example.dundone.data.character.ResCharBaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResTimeline extends ResCharBaseData{
    public ResTimeline(String characterId, String characterName, int level, String jobId, String jobGrowId, String jobName, String jobGrowName, String adventureName, String guildId, String guildName, TimelineResult timelineResult) {
        super(characterId, characterName, level, jobId, jobGrowId, jobName, jobGrowName, adventureName, guildId, guildName);
        this.timelineResult = timelineResult;
    }

    @SerializedName("timelineResult")
    @Expose
    public TimelineResult timelineResult;

}