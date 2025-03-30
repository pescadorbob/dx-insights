package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.domain.BuildScan;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class BuildScanState {
    Map<BuildId, BuildScan> scans;
    Map<String, DailyStats> stats;
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
}
