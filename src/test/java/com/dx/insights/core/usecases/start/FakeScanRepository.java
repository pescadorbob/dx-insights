package com.dx.insights.core.usecases.start;

import com.dx.insights.core.domain.BuildId;
import com.dx.insights.core.domain.BuildScan;

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
