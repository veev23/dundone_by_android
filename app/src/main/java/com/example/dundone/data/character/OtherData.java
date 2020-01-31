package com.example.dundone.data.character;

import java.util.Date;

public class OtherData {
    public OtherData(int preyRemain, boolean preyTodayClear, int fiendRemain, boolean fiendTodayClear, Date lastCheckTime) {
        this.preyRemain = preyRemain;
        this.preyTodayClear = preyTodayClear;
        this.fiendRemain = fiendRemain;
        this.fiendTodayClear = fiendTodayClear;
        this.lastCheckTime = lastCheckTime;
    }

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

    private int preyRemain;
    private boolean preyTodayClear;
    private int fiendRemain;
    private boolean fiendTodayClear;
    private Date lastCheckTime;
}
