package com.example.dundone.data.etc;

public class ReinforceData {
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

    public ReinforceData(int level, int successCount, int failCount) {
        this.level = level;
        this.successCount = successCount;
        this.failCount = failCount;
    }
}
