package com.github.pescadorbob.dxinsights.domain;


import com.github.pescadorbob.dxinsights.scan.start.BuildId;
import com.github.pescadorbob.dxinsights.start.BuildScanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@EqualsAndHashCode
public class BuildScan {
    private final BuildId buildId;
    private final ZonedDateTime startDateTime;
    private Duration duration;
    private BuildScanStatus buildScanStatus;


    public void completeScan(BuildScanStatus buildScanStatus, Instant instant) {
        this.buildScanStatus = buildScanStatus;
        this.duration = Duration.between(startDateTime.toInstant(), instant);
    }
}
