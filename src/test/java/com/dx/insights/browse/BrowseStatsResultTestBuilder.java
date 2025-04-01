package com.dx.insights.browse;

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
