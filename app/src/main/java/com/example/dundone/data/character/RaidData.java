package com.example.dundone.data.character;

public class RaidData {
    public RaidData(int preyRemain, boolean preyTodayClear, int fiendRemain, boolean fiendTodayClear) {
        this.preyRemain = preyRemain;
        this.preyTodayClear = preyTodayClear;
        this.fiendRemain = fiendRemain;
        this.fiendTodayClear = fiendTodayClear;
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
}
