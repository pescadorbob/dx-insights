package com.github.pescadorbob.dxinsights.scan.start;

import com.github.pescadorbob.dxinsights.domain.guards.BuildIdGenerator;
import com.github.pescadorbob.dxinsights.start.BuildScan;
import com.github.pescadorbob.dxinsights.start.BuildScanStatus;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class StartScan {
    private final Clock clock;
    private final ScanRepository scanRepository;
    private final BuildIdGenerator buildIdGenerator;
    private final ForNotifyingUI forNotifyingUI;
    public StartScan(Clock clock, ScanRepository scanRepository, BuildIdGenerator buildIdGenerator, ForNotifyingUI forNotifyingUI) {
        this.clock = clock;
        this.scanRepository = scanRepository;
        this.buildIdGenerator = buildIdGenerator;
        this.forNotifyingUI = forNotifyingUI;
    }

    public StartScanResult execute(){
        var buildScan = BuildScan.builder()
                .buildScanStatus(BuildScanStatus.IN_PROGRESS)
                .startDateTime(getZonedDateTimeFromClock(clock))
                .buildId(buildIdGenerator.next())
                .build();

        scanRepository.save(buildScan);

        forNotifyingUI.notifyUpdated(buildScan);

        return new StartScanResult(buildScan.getBuildId().id());
    }
    public static ZonedDateTime getZonedDateTimeFromClock(Clock clock) {
        Instant instant = clock.instant();
        ZoneId zone = clock.getZone();
        return ZonedDateTime.ofInstant(instant, zone);
    }
}
