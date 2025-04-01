package com.github.pescadorbob.dxinsights.service;

import com.github.pescadorbob.dxinsights.browse.BrowseStats;
import com.github.pescadorbob.dxinsights.browse.ForBrowsingStats;
import com.github.pescadorbob.dxinsights.complete.CompleteScan;
import com.github.pescadorbob.dxinsights.domain.guards.ForGeneratingBuildIds;
import com.github.pescadorbob.dxinsights.scan.start.ForStoringScans;
import com.github.pescadorbob.dxinsights.scan.start.StartScan;
import com.github.pescadorbob.dxinsights.start.ForStoringStats;
import com.github.pescadorbob.dxinsights.start.IntellijPersistentStateBuildScanRepository;


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
