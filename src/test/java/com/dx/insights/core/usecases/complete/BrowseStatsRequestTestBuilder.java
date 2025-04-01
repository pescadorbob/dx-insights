package com.dx.insights.core.usecases.complete;

import com.dx.insights.core.usecases.browse.BrowseStatsRequest;

public class BrowseStatsRequestTestBuilder {

    public static BrowseStatsRequestTestBuilder aBrowseStatsRequest(){
        return new BrowseStatsRequestTestBuilder();
    }

    public BrowseStatsRequest build(){
        return new BrowseStatsRequest();
    }

}
