package com.github.pescadorbob.dxinsights.start;

import java.time.LocalDate;
import java.util.Map;

public interface ForStoringStats {
    DailyStats getDailyStats(LocalDate date);

    void save(DailyStats dailyStats);

    Map<LocalDate,DailyStats> list();
}
