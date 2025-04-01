package com.dx.insights.complete;

import com.dx.insights.usecases.browse.BrowseStatsRequest;

public class BrowseStatsRequestTestBuilder {

    public static BrowseStatsRequestTestBuilder aBrowseStatsRequest(){
        return new BrowseStatsRequestTestBuilder();
    }

    public BrowseStatsRequest build(){
        return new BrowseStatsRequest();
    }

}
