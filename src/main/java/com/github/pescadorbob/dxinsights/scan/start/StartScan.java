package com.github.pescadorbob.dxinsights.scan.start;

import com.github.pescadorbob.dxinsights.domain.BuildScan;
import com.github.pescadorbob.dxinsights.domain.guards.ForGeneratingBuildIds;
import com.github.pescadorbob.dxinsights.start.BuildScanStatus;
import com.github.pescadorbob.dxinsights.start.DailyStats;
import com.github.pescadorbob.dxinsights.start.ForStoringStats;

import java.time.*;

public class StartScan {
    private final Clock clock;
    private final ForStoringScans scanRepository;
    private final ForStoringStats statsRepository;
    private final ForGeneratingBuildIds buildIdGenerator;
    private final ForNotifyingUI forNotifyingUI;

    public StartScan(Clock clock, ForStoringScans scanRepository, ForGeneratingBuildIds buildIdGenerator, ForNotifyingUI forNotifyingUI,ForStoringStats forStoringStats) {
        this.clock = clock;
        this.scanRepository = scanRepository;
        this.buildIdGenerator = buildIdGenerator;
        this.forNotifyingUI = forNotifyingUI;
        this.statsRepository = forStoringStats;
    }

    public StartScanResult execute(){
        var buildScan = BuildScan.builder()
                .buildScanStatus(BuildScanStatus.IN_PROGRESS)
                .startDateTime(getZonedDateTimeFromClock(clock))
                .buildId(buildIdGenerator.next())
                .build();

        scanRepository.save(buildScan);

        updateExecutionsStats();

        forNotifyingUI.notifyUpdated(buildScan);

        return new StartScanResult(buildScan.getBuildId().id());
    }

    private void updateExecutionsStats() {
        var today = LocalDate.ofInstant(clock.instant(),clock.getZone());
        if(statsRepository.getDailyStats(today) == null){
            statsRepository.save(today,new DailyStats());
        }
        var dailyStats = statsRepository.getDailyStats(today);
        dailyStats.setTestExecutions(dailyStats.getTestExecutions()+1);

    }

    public static ZonedDateTime getZonedDateTimeFromClock(Clock clock) {
        Instant instant = clock.instant();
        ZoneId zone = clock.getZone();
        return ZonedDateTime.ofInstant(instant, zone);
    }
}
