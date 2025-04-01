package com.dx.insights.core.usecases.browse;

import com.dx.insights.core.usecases.start.ForStoringStats;

public class BrowseStatsTestBuilder {

    private ForStoringStats forStoringStats;
    private ForBrowsingStats statsBrowser;

    public static BrowseStatsTestBuilder aBrowseStats(){
        return new BrowseStatsTestBuilder();
    }


    public BrowseStatsTestBuilder withStatsBrowser(ForBrowsingStats statsBrowser) {
        this.statsBrowser = statsBrowser;
        return this;
    }

    public BrowseStatsTestBuilder withStatsRepository(ForStoringStats forStoringStats) {
        this.forStoringStats = forStoringStats;
        return this;
    }

    public BrowseStats build() {
        return new BrowseStats( statsBrowser,forStoringStats);
    }

}
