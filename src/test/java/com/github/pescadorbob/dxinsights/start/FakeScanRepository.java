package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.domain.BuildScan;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;
import com.github.pescadorbob.dxinsights.scan.start.ForStoringScans;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class FakeScanRepository implements ForStoringScans {
    public Map<BuildId, BuildScan> buildScans;

    public FakeScanRepository() {
        buildScans = new HashMap<>();
    }

    @Override
    public BuildScan getBuild(BuildId id) {
        var buildScan = buildScans.get(id);
        Objects.requireNonNull(buildScan, "Build scan not found");

        return buildScan.toBuilder().build();
    }

    @Override
    public void save(BuildScan buildScan) {
        buildScans.put(buildScan.getBuildId(), buildScan);
    }
}
