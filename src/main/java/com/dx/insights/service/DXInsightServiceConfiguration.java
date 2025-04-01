package com.dx.insights.service;

import com.dx.insights.core.usecases.browse.BrowseStats;
import com.dx.insights.core.usecases.complete.CompleteScan;
import com.dx.insights.core.domain.guards.ForGeneratingBuildIds;
import com.dx.insights.core.usecases.start.ForStoringScans;
import com.dx.insights.core.usecases.start.StartScan;
import com.dx.insights.core.usecases.start.ForStoringStats;
import com.dx.insights.core.usecases.start.IntellijPersistentStateBuildScanRepository;


import java.time.Clock;

public class DXInsightServiceConfiguration {
    private final IntellijPersistentStateBuildScanRepository intellijPersistentStateBuildScanRepository;
    private final DXInsightService dxInsightService;

    public DXInsightServiceConfiguration(DXInsightService dxInsightService, IntellijPersistentStateBuildScanRepository repository) {
        this.dxInsightService = dxInsightService;
        intellijPersistentStateBuildScanRepository = repository;
    }
    public Clock getClock() {
        return Clock.systemDefaultZone();
    }

    public ForStoringScans getScanRepository() {
        return intellijPersistentStateBuildScanRepository;
    }

    public ForGeneratingBuildIds getBuildIdGenerator() {
        return new UUIDBuildIdGenerator();
    }

    public ForStoringStats getStatsRepository() {
        return intellijPersistentStateBuildScanRepository;
    }

    public StartScan getStartScan() {
        return new StartScan(getClock(),getScanRepository(),getBuildIdGenerator(),dxInsightService,getStatsRepository());
    }

    public CompleteScan getCompleteScan() {
        return new CompleteScan(getClock(),getScanRepository(),getBuildIdGenerator(),dxInsightService,getStatsRepository());
    }

    public BrowseStats getBrowseStats() {
        return new BrowseStats(dxInsightService,getStatsRepository());
    }


}
