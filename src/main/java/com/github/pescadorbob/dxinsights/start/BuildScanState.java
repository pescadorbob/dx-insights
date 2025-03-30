package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.domain.BuildScan;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;

import java.util.HashMap;
import java.util.Map;

public class BuildScanState {
    public Map<BuildId, BuildScan> scans = new HashMap<>();

    public void addBuildScan(BuildScan buildScan) {
        scans.put(buildScan.getBuildId(), buildScan);
    }

    public BuildScan getBuildScan(BuildId buildId) {
        return scans.get(buildId);
    }
}
