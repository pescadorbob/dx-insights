package com.github.pescadorbob.dxinsights.service;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;

import java.util.HashMap;
import java.util.Map;

@Service(Service.Level.PROJECT)
public final class TestMetricsService {
    private final Project project;
    private final State state;

    public TestMetricsService(Project project){
        this.project = project;
        this.state = new State();
    }

    public State getState() {
        return state;
    }

    public static class State {
        public Map<String, DailyStats> dailyStats = new HashMap<>();
    }

    public static class DailyStats {
        public int testExecutions = 0;
        public int successfulTests = 0;
        public int failedTests = 0;
        public long totalDuration = 0;
    }
}
