package com.dx.insights.usecases.browse;

public class BrowseStatsResultTestBuilder {

    public BrowseStatsResultTestBuilder() {
    }

    public static BrowseStatsResultTestBuilder aBrowseStatsResult() {
        return new BrowseStatsResultTestBuilder();
    }

    public BrowseStatsResult build() {
        return new BrowseStatsResult();
    }
}
