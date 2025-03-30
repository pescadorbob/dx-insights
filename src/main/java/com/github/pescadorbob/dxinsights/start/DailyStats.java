package com.github.pescadorbob.dxinsights.start;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyStats {
    int testExecutions = 0;
    int successfulTests = 0;
    int failedTests = 0;
    long totalDuration = 0;

}
