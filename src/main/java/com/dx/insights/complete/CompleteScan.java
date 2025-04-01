package com.dx.insights.complete;

import com.dx.insights.domain.BuildScan;
import com.dx.insights.domain.guards.ForGeneratingBuildIds;
import com.dx.insights.scan.start.BuildId;
import com.dx.insights.scan.start.ForNotifyingUI;
import com.dx.insights.scan.start.ForStoringScans;
import com.dx.insights.start.DailyStats;
import com.dx.insights.start.ForStoringStats;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;

import static com.dx.insights.start.BuildScanStatus.SUCCESSFUL;

public class CompleteScan {
    private final Clock clock;
    private final ForStoringScans scanRepository;
    private final ForStoringStats statsRepository;
    private final ForGeneratingBuildIds buildIdGenerator;
    private final ForNotifyingUI forNotifyingUI;

    public CompleteScan(Clock clock, ForStoringScans scanRepository, ForGeneratingBuildIds buildIdGenerator, ForNotifyingUI forNotifyingUI,ForStoringStats forStoringStats) {
        this.clock = clock;
        this.scanRepository = scanRepository;
        this.buildIdGenerator = buildIdGenerator;
        this.forNotifyingUI = forNotifyingUI;
        this.statsRepository = forStoringStats;
    }

    public CompleteScanResult execute(CompleteScanRequest request) {
        var buildScan = scanRepository.getBuild(new BuildId(request.getId()));
        updateExecutionsStats(buildScan);
        buildScan.completeScan(SUCCESSFUL,clock.instant());
        scanRepository.save(buildScan);
        forNotifyingUI.notifyUpdated(buildScan);

        return new CompleteScanResult(buildScan.getBuildId().id());
    }
    private void updateExecutionsStats(BuildScan buildScan) {
        var today = LocalDate.ofInstant(clock.instant(),clock.getZone());
        DailyStats dailyStats;
        if(statsRepository.getDailyStats(today) == null){
            dailyStats = new DailyStats();
        } else {
            dailyStats = statsRepository.getDailyStats(today);
        }
        dailyStats.setSuccessfulTests(dailyStats.getSuccessfulTests()+1);

        dailyStats.setTotalDuration(Duration.between(buildScan.getStartDateTime().toInstant(),clock.instant()).toMillis());
        dailyStats.setDate(today);
        statsRepository.save(dailyStats);
    }

}
