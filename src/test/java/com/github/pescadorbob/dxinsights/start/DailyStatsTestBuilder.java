package com.github.pescadorbob.dxinsights.start;

public class DailyStatsTestBuilder {
    private int testExecutions;
    private int successfulTests;
    private int failedTests;
    private long totalDuration;

    public DailyStatsTestBuilder(){
        withTestExecutions(0);
        withSuccessfulTests(0);
        withFailedTests(0);
        withTotalDuration(0);
    }
    public static DailyStatsTestBuilder aDailyStats(){
        return new DailyStatsTestBuilder();
    }

    public DailyStats build(){
        return new DailyStats(testExecutions,successfulTests,failedTests,totalDuration);
    }

    public DailyStatsTestBuilder withTestExecutions(int testExecutions) {
        this.testExecutions = testExecutions;
        return this;
    }
    public DailyStatsTestBuilder withSuccessfulTests(int successfulTests){
        this.successfulTests = successfulTests;
        return this;
    }
    public DailyStatsTestBuilder withFailedTests(int failedTests){
        this.failedTests = failedTests;
        return this;
    }
    public DailyStatsTestBuilder withTotalDuration(int totalDuration){
        this.totalDuration = totalDuration;
        return this;
    }
}
