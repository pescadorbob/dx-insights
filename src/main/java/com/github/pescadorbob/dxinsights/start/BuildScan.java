package com.github.pescadorbob.dxinsights.start;


import com.github.pescadorbob.dxinsights.scan.start.BuildId;
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
