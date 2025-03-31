package com.github.pescadorbob.dxinsights.complete;

import com.github.pescadorbob.dxinsights.browse.BrowseStatsRequest;

public class BrowseStatsRequestTestBuilder {

    public static BrowseStatsRequestTestBuilder aBrowseStatsRequest(){
        return new BrowseStatsRequestTestBuilder();
    }

    public BrowseStatsRequest build(){
        return new BrowseStatsRequest();
    }

}
