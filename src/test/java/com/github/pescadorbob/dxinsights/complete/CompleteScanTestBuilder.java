package com.github.pescadorbob.dxinsights.complete;

import com.github.pescadorbob.dxinsights.domain.guards.ForGeneratingBuildIds;
import com.github.pescadorbob.dxinsights.scan.start.ForNotifyingUI;
import com.github.pescadorbob.dxinsights.scan.start.ForStoringScans;
import com.github.pescadorbob.dxinsights.start.ForStoringStats;

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
