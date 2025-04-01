package com.dx.insights.core.usecases.browse;

import com.dx.insights.core.domain.DailyStats;

import java.time.LocalDate;
import java.util.Map;

public interface ForBrowsingStats {
    void updateUi(Map<LocalDate, DailyStats> dailyStats);
}
