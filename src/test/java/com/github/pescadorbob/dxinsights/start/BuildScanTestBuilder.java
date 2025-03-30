package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.scan.start.BuildId;

import java.time.Duration;
import java.time.ZonedDateTime;

import static com.github.pescadorbob.dxinsights.start.StartScanTestBuilder.atTime;

public class BuildScanTestBuilder {
    private BuildId buildId;
    private ZonedDateTime startTime;
    private Duration duration;
    private BuildScanStatus buildScanStatus;

    public static BuildScanTestBuilder aBuildScan(){
        return new BuildScanTestBuilder();
    }

    public BuildScanTestBuilder withBuildId(String buildId) {
        this.buildId = new BuildId(buildId);
        return this;
    }

    public BuildScanTestBuilder withStartTime(String startTime){
        this.startTime = atTime(startTime);
        return this;
    }

    public BuildScan build(){
        return new BuildScan(buildId,startTime,duration,buildScanStatus);
    }

    public BuildScanTestBuilder withNoDuration() {
        this.duration = null;
        return this;
    }

    public BuildScanTestBuilder withBuildScanStatus(BuildScanStatus buildScanStatus) {
        this.buildScanStatus = buildScanStatus;
        return this;
    }
}
