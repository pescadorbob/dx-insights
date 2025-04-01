package com.dx.insights.usecases.start;

import java.time.LocalDate;
import java.util.Map;

public interface ForStoringStats {
    DailyStats getDailyStats(LocalDate date);

    void save(DailyStats dailyStats);

    Map<LocalDate,DailyStats> list();
}
