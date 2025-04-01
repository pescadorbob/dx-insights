package com.dx.insights.usecases.start;

import com.dx.insights.domain.BuildScan;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FakeScanRepository implements ForStoringScans {
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
