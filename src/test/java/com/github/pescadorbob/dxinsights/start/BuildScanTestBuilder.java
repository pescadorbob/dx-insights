package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.domain.BuildScan;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;

import java.time.Duration;
import java.time.ZonedDateTime;

import static com.github.pescadorbob.dxinsights.start.StartScanTestBuilder.atTime;

public class BuildScanTestBuilder {
    private static final String DEFAULT_BUILD_ID = "1";
    public static final String DEFAULT_START_TIME = "10:00:00.000";
    private static final BuildScanStatus DEFAULT_BUILD_SCAN_STATUS = BuildScanStatus.IN_PROGRESS;
    private BuildId buildId;
    private ZonedDateTime startTime;
    private Duration duration;
    private BuildScanStatus buildScanStatus;

    public BuildScanTestBuilder(){
        withBuildId(DEFAULT_BUILD_ID);
        withStartTime(DEFAULT_START_TIME);
        withBuildScanStatus(DEFAULT_BUILD_SCAN_STATUS);
    }
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

    public BuildScanTestBuilder withDuration(String duration) {
        this.duration = Duration.parse(duration);
        return this;
    }
}
