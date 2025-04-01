package com.dx.insights.start;

import com.dx.insights.domain.guards.ForGeneratingBuildIds;
import com.dx.insights.scan.start.ForStoringScans;
import com.dx.insights.scan.start.ForNotifyingUI;
import com.dx.insights.scan.start.StartScan;

import java.time.Clock;
import java.time.ZonedDateTime;

public class StartScanTestBuilder {
    private Clock clock;
    private ForStoringScans scanRepository;
    private ForGeneratingBuildIds buildIdGenerator;
    private ForNotifyingUI forNotifyingUI;
    private ForStoringStats forStoringStats;

    public static  StartScanTestBuilder aStartScan() {
        return new StartScanTestBuilder();
    }
    public static Clock clockAtTime(String time){
        var parsedDateTime = atTime(time);
        return Clock.fixed(parsedDateTime.toInstant(),parsedDateTime.getZone());

    }

    public static ZonedDateTime atTime(String time){
        var isoTime = String.format("2025-03-29T%s-07:00",time);
        return ZonedDateTime.parse(isoTime);
    }

    public StartScanTestBuilder withClock(Clock testClock) {
        this.clock = testClock;
        return this;
    }

    public StartScan build() {
        return new StartScan(clock,scanRepository,buildIdGenerator, forNotifyingUI,forStoringStats);
    }

    public StartScanTestBuilder withScanRepository(ForStoringScans scanRepository) {
        this.scanRepository = scanRepository;
        return this;
    }

    public StartScanTestBuilder withScanUI(ForNotifyingUI forNotifyingUI) {
        this.forNotifyingUI = forNotifyingUI;
        return this;
    }

    public StartScanTestBuilder withBuildIdGenerator(ForGeneratingBuildIds buildIdGenerator) {
        this.buildIdGenerator = buildIdGenerator;
        return this;
    }


    public StartScanTestBuilder withStatsRepository(ForStoringStats forStoringStats) {
        this.forStoringStats = forStoringStats;
        return this;
    }
}
