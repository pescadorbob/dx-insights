package com.github.pescadorbob.dxinsights.domain;


import com.github.pescadorbob.dxinsights.scan.start.BuildId;
import com.github.pescadorbob.dxinsights.start.BuildScanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.ZonedDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class BuildScan {
    private final BuildId buildId;
    private final ZonedDateTime startDateTime;
    private final Duration duration;
    private final BuildScanStatus buildScanStatus;

}
