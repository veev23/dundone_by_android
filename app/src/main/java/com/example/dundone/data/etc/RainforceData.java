package com.example.dundone.data.etc;

public class RainforceData {
    private int level;
    private int successCount;
    private int failCount;

    public int getLevel() {
        return level;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public RainforceData(int level, int successCount, int failCount) {
        this.level = level;
        this.successCount = successCount;
        this.failCount = failCount;
    }
}
