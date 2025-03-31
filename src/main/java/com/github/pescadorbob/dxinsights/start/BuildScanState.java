package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.domain.BuildScan;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class BuildScanState {
    private final Map<BuildId, BuildScan> scans;
    private final Map<LocalDate, DailyStats> stats;

    public BuildScanState() {
        scans = new HashMap<>();
        stats = new HashMap<>();
    }


    public void addBuildScan(BuildScan buildScan) {
        scans.put(buildScan.getBuildId(), buildScan);
    }

    public BuildScan getBuildScan(BuildId buildId) {
        return scans.get(buildId);
    }

    public DailyStats getStats(LocalDate date) {
        return stats.get(date);
    }

    public void saveStat(LocalDate today, DailyStats dailyStats) {
        stats.put(today,dailyStats);
    }

    public Map<LocalDate, DailyStats> allStats() {
        return stats;
    }
}
