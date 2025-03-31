package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.complete.CompleteScanResultTestBuilder;

import java.time.LocalDate;

public class DailyStatsTestBuilder {
    private static final String DEFAULT_DATE = "2025-03-29";
    private int testExecutions;
    private int successfulTests;
    private int failedTests;
    private long totalDuration;
    private LocalDate date;

    public DailyStatsTestBuilder(){
        withTestExecutions(0);
        withSuccessfulTests(0);
        withFailedTests(0);
        withTotalDuration(0);
        withDate(DEFAULT_DATE);
    }
    public static DailyStatsTestBuilder aDailyStats(){
        return new DailyStatsTestBuilder();
    }

    public DailyStats build(){
        return new DailyStats(testExecutions,successfulTests,failedTests,totalDuration,date);
    }

    public DailyStatsTestBuilder withDate(String date){
        this.date = LocalDate.parse(date);
        return this;
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
