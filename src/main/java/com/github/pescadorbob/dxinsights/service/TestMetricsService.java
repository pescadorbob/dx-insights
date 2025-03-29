package com.github.pescadorbob.dxinsights.service;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;

@Service(Service.Level.PROJECT)
public final class TestMetricsService {
    private final Project project;

    public TestMetricsService(Project project){
        this.project = project;
    }
}
