package com.makers.dundone.data.character;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


//epicWeek
public class RaidRemainData implements Serializable {
    public static final int PREY_MAX = 2;
    public static final int FIEND_MAX = 2;
    public static final int SIROCO_MAX = 2;
    public int getPreyRemain() {
        return preyRemain;
    }

    public boolean isPreyTodayClear() {
        return preyTodayClear;
    }

    public int getFiendRemain() {
        return fiendRemain;
    }

    public boolean isFiendTodayClear() {
        return fiendTodayClear;
    }

    public int getSirocoRemain() {
        return sirocoRemain;
    }

    public boolean isSirocoTodayClear() {
        return sirocoTodayClear;
    }

    public int getEpicWeek() {
        return epicWeek;
    }
    public void setNotYetLoaded(){
        epics = "-1/-1";
    }
    public boolean isNotYetLoaded(){
        return epics.equals("-1/-1");
    }

    public void initParsing(){
        if(freye!=null) {
            String[] freyes = freye.split("/");
            if (freyes[0].equals("1")) preyTodayClear = true;
            else preyTodayClear = false;
            preyRemain = PREY_MAX - Integer.parseInt(freyes[1]);
        }
        if(fienWar!=null) {
            String[] fiendWars = fienWar.split("/");
            if (fiendWars[0].equals("1")) fiendTodayClear = true;
            else fiendTodayClear = false;
            fiendRemain = FIEND_MAX - Integer.parseInt(fiendWars[1]);
        }
        if(siroco!=null) {
            String[] sirocos = siroco.split("/");
            if (sirocos[0].equals("1")) sirocoTodayClear = true;
            else sirocoTodayClear = false;
            sirocoRemain = SIROCO_MAX - Integer.parseInt(sirocos[1]);
        }
        if(epics!=null) {
            String[] epicCount = epics.split("/");
            epicWeek = Integer.parseInt(epicCount[1]);
        }
    }

    public RaidRemainData(String freye, String fienWar, String siroco, String epics) {
        this.freye = freye;
        this.fienWar = fienWar;
        this.siroco = siroco;
        this.epics = epics;
    }
    public RaidRemainData(){
        this.freye = "0/0";
        this.fienWar = "0/0";
        this.siroco = "0/0";
        this.epics = "-1/-1";
    }

    @SerializedName("freye")
    @Expose
    private String freye;
    @SerializedName("fienWar")
    @Expose
    private String fienWar;
    @SerializedName("siroco")
    @Expose
    private String siroco;
    @SerializedName("epics")
    @Expose
    private String epics;
    private int preyRemain;
    private boolean preyTodayClear;
    private int fiendRemain;
    private boolean fiendTodayClear;
    private int sirocoRemain;
    private boolean sirocoTodayClear;

    private int epicWeek;
}
