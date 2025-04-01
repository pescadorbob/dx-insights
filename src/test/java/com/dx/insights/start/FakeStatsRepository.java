package com.dx.insights.start;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.dx.insights.start.DailyStatsTestBuilder.aDailyStats;

public class FakeStatsRepository implements ForStoringStats {
    private final Map<LocalDate,DailyStats> stats;
    public FakeStatsRepository(){
        stats = new HashMap<>();
    }

    @Override
    public DailyStats getDailyStats(LocalDate date) {
        var dailyStats = stats.get(date);
        if(dailyStats == null){
            return aDailyStats().build();
        }
        stats.put(date,dailyStats);
        return dailyStats;
    }

    @Override
    public void save( DailyStats dailyStats) {
        stats.put(dailyStats.getDate(),dailyStats);
    }

    @Override
    public Map<LocalDate, DailyStats> list() {
        return stats;
    }
}
