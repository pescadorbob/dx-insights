package com.github.pescadorbob.dxinsights.start;

import java.time.LocalDate;

public interface ForStoringStats {
    DailyStats getDailyStats(LocalDate date);

    void save(DailyStats dailyStats);
}
