package com.github.pescadorbob.dxinsights.browse;

import com.github.pescadorbob.dxinsights.start.DailyStats;

import java.time.LocalDate;
import java.util.Map;

public interface ForBrowsingStats {
    void updateUi(Map<LocalDate, DailyStats> dailyStats);
}
