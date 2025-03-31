package com.github.pescadorbob.dxinsights.browse;

import com.github.pescadorbob.dxinsights.complete.CompleteScanResult;
import com.github.pescadorbob.dxinsights.domain.BuildScan;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;
import com.github.pescadorbob.dxinsights.scan.start.ForStoringScans;
import com.github.pescadorbob.dxinsights.start.DailyStats;
import com.github.pescadorbob.dxinsights.start.ForStoringStats;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;

import static com.github.pescadorbob.dxinsights.start.BuildScanStatus.SUCCESSFUL;

public class BrowseStats {
    private final ForStoringStats statsRepository;
    private final ForBrowsingStats browsingUI;

    public BrowseStats( ForBrowsingStats browsingUI, ForStoringStats forStoringStats) {
        this.browsingUI = browsingUI;
        this.statsRepository = forStoringStats;
    }

    public BrowseStatsResult execute(BrowseStatsRequest request) {

        var stats = statsRepository.list();
        var result = new BrowseStatsResult();
        browsingUI.updateUi(stats);
        return result;
    }


}
