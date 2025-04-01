package com.dx.insights.browse;

import com.dx.insights.start.DailyStats;

import java.time.LocalDate;
import java.util.Map;

public interface ForBrowsingStats {
    void updateUi(Map<LocalDate, DailyStats> dailyStats);
}
