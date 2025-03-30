package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.domain.guards.BuildIdGenerator;
import com.github.pescadorbob.dxinsights.scan.start.ScanRepository;
import com.github.pescadorbob.dxinsights.scan.start.ForNotifyingUI;
import com.github.pescadorbob.dxinsights.scan.start.StartScan;

import java.time.Clock;
import java.time.ZonedDateTime;

public class StartScanTestBuilder {
    private Clock clock;
    private ScanRepository scanRepository;
    private BuildIdGenerator buildIdGenerator;
    private ForNotifyingUI forNotifyingUI;

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
        return new StartScan(clock,scanRepository,buildIdGenerator, forNotifyingUI);
    }

    public StartScanTestBuilder withScanRepository(ScanRepository scanRepository) {
        this.scanRepository = scanRepository;
        return this;
    }

    public StartScanTestBuilder withScanUI(ForNotifyingUI forNotifyingUI) {
        this.forNotifyingUI = forNotifyingUI;
        return this;
    }

    public StartScanTestBuilder withBuildIdGenerator(BuildIdGenerator buildIdGenerator) {
        this.buildIdGenerator = buildIdGenerator;
        return this;
    }


}
