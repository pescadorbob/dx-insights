package com.dx.insights.browse;

import com.dx.insights.start.ForStoringStats;

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
