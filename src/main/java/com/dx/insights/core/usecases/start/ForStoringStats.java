package com.dx.insights.core.usecases.start;

import com.dx.insights.core.domain.DailyStats;

import java.time.LocalDate;
import java.util.Map;

public interface ForStoringStats {
    DailyStats getDailyStats(LocalDate date);

    void save(DailyStats dailyStats);

    Map<LocalDate,DailyStats> list();
}
