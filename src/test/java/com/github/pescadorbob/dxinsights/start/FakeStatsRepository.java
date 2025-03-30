package com.github.pescadorbob.dxinsights.start;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FakeStatsRepository implements ForStoringStats {
    private final Map<LocalDate,DailyStats> stats;
    public FakeStatsRepository(){
        stats = new HashMap<>();
    }

    @Override
    public DailyStats getDailyStats(LocalDate date) {
        return stats.get(date);
    }

    @Override
    public void save(LocalDate today, DailyStats dailyStats) {
        stats.put(today,dailyStats);
    }
}
