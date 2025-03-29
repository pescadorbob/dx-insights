package com.github.pescadorbob.dxinsights.startup;

import com.github.pescadorbob.dxinsights.service.TestMetricsService;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestMetricsStartupActivity implements ProjectActivity {
    private static final Logger LOG = Logger.getInstance(TestMetricsStartupActivity.class);

    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        LOG.info("Initializing test metrics service for project: " + project.getName() + " ...");
        var service = project.getService(TestMetricsService.class);
        service.initialize();
        return null;
    }
}
