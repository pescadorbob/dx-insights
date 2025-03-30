package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.domain.BuildScan;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildScanStateTestBuilder {
    private Map<BuildId, BuildScan> scans = new HashMap<>();
    private Map<String, DailyStats> dailyStats = new HashMap<>();

    public BuildScanStateTestBuilder(){

    }
    public static BuildScanStateTestBuilder aBuildScanState(){
        return new BuildScanStateTestBuilder();
    }
    public BuildScanStateTestBuilder withBuildScans(List<BuildScan> buildScans){
        buildScans.forEach(buildScan -> scans.put(buildScan.getBuildId(),buildScan));
        return this;
    }
    public BuildScanStateTestBuilder withDailyStats(String date,List<DailyStats> dailyStats){
        dailyStats.forEach(dailyStat -> this.dailyStats.put(date,dailyStat));
        return this;
    }
    public BuildScanState build(){
        var state = new BuildScanState();
        state.setScans(scans);
        state.setStats(dailyStats);
        return state;
    }
}
