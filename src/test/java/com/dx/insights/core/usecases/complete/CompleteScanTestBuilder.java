package com.dx.insights.core.usecases.complete;

import com.dx.insights.core.defense.guards.ForGeneratingBuildIds;
import com.dx.insights.core.usecases.start.ForNotifyingUI;
import com.dx.insights.core.usecases.start.ForStoringScans;
import com.dx.insights.core.usecases.start.ForStoringStats;

import java.time.Clock;
import java.time.ZonedDateTime;

public class CompleteScanTestBuilder {

    private Clock clock;
    private ForStoringScans scanRepository;
    private ForGeneratingBuildIds buildIdGenerator;
    private ForNotifyingUI forNotifyingUI;
    private ForStoringStats forStoringStats;

    public static CompleteScanTestBuilder aCompleteScan(){
        return new CompleteScanTestBuilder();
    }

    public static Clock clockAtTime(String time){
        var parsedDateTime = atTime(time);
        return Clock.fixed(parsedDateTime.toInstant(),parsedDateTime.getZone());

    }

    public static ZonedDateTime atTime(String time){
        var isoTime = String.format("2025-03-29T%s-07:00",time);
        return ZonedDateTime.parse(isoTime);
    }

    public CompleteScanTestBuilder withClock(Clock testClock) {
        this.clock = testClock;
        return this;
    }

    public CompleteScan build() {
        return new CompleteScan(clock,scanRepository,buildIdGenerator, forNotifyingUI,forStoringStats);
    }

    public CompleteScanTestBuilder withScanRepository(ForStoringScans scanRepository) {
        this.scanRepository = scanRepository;
        return this;
    }

    public CompleteScanTestBuilder withScanUI(ForNotifyingUI forNotifyingUI) {
        this.forNotifyingUI = forNotifyingUI;
        return this;
    }

    public CompleteScanTestBuilder withBuildIdGenerator(ForGeneratingBuildIds buildIdGenerator) {
        this.buildIdGenerator = buildIdGenerator;
        return this;
    }


    public CompleteScanTestBuilder withStatsRepository(ForStoringStats forStoringStats) {
        this.forStoringStats = forStoringStats;
        return this;
    }

}
