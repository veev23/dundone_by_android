package com.example.dundone.data.character;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResCharBaseData {
    public ResCharBaseData(String characterId, String characterName, int level, String jobId, String jobGrowId, String jobName, String jobGrowName, String adventureName, String guildId, String guildName) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.level = level;
        this.jobId = jobId;
        this.jobGrowId = jobGrowId;
        this.jobName = jobName;
        this.jobGrowName = jobGrowName;
        this.adventureName = adventureName;
        this.guildId = guildId;
        this.guildName = guildName;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobGrowId() {
        return jobGrowId;
    }

    public void setJobGrowId(String jobGrowId) {
        this.jobGrowId = jobGrowId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGrowName() {
        return jobGrowName;
    }

    public void setJobGrowName(String jobGrowName) {
        this.jobGrowName = jobGrowName;
    }

    public String getAdventureName() {
        return adventureName;
    }

    public void setAdventureName(String adventureName) {
        this.adventureName = adventureName;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    @SerializedName("characterId")
    @Expose
    public String characterId;
    @SerializedName("characterName")
    @Expose
    public String characterName;
    @SerializedName("level")
    @Expose
    public int level;
    @SerializedName("jobId")
    @Expose
    public String jobId;
    @SerializedName("jobGrowId")
    @Expose
    public String jobGrowId;
    @SerializedName("jobName")
    @Expose
    public String jobName;
    @SerializedName("jobGrowName")
    @Expose
    public String jobGrowName;
    @SerializedName("adventureName")
    @Expose
    public String adventureName;
    @SerializedName("guildId")
    @Expose
    public String guildId;
    @SerializedName("guildName")
    @Expose
    public String guildName;
}
