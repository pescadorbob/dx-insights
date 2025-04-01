package com.dx.insights.core.usecases.start;

import com.dx.insights.core.domain.BuildId;
import com.dx.insights.core.domain.BuildScan;
import com.dx.insights.core.domain.BuildScanState;
import com.dx.insights.core.domain.DailyStats;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.Map;

@State(name = "BuildScanState",
        storages = {@Storage("build-scans.xml")})
public class IntellijPersistentStateBuildScanRepository implements ForStoringScans, PersistentStateComponent<BuildScanState>, ForStoringStats {

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

    @Override
    public DailyStats getDailyStats(LocalDate date) {
        return state.getStats(date);
    }

    @Override
    public void save(DailyStats dailyStats) {
        state.saveStat(dailyStats.getDate(),dailyStats);
    }

    @Override
    public Map<LocalDate, DailyStats> list() {
        return state.allStats();
    }
}
