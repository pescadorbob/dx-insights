package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.domain.BuildScan;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;
import com.github.pescadorbob.dxinsights.scan.start.ForStoringScans;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "BuildScanState",
        storages = {@Storage("build-scans.xml")})
public class IntellijPersistentStateBuildScanRepository implements ForStoringScans, PersistentStateComponent<BuildScanState> {

    private BuildScanState state;

    public IntellijPersistentStateBuildScanRepository() {
        state = new BuildScanState();
    }


    @Override
    public BuildScan getBuild(BuildId buildId) {
        return state.getBuildScan(buildId);
    }

    @Override
    public void save(BuildScan buildScan) {
        state.addBuildScan(buildScan);
    }

    @Override
    public @Nullable BuildScanState getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull BuildScanState buildScanState) {
        this.state = buildScanState;
    }
}
