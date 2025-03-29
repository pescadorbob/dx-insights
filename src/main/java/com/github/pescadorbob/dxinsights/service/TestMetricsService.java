package com.github.pescadorbob.dxinsights.service;

import com.intellij.execution.ExecutionListener;
import com.intellij.execution.ExecutionManager;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.util.messages.MessageBusConnection;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetAddress;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service(Service.Level.PROJECT)
@State(
        name = "TestMetricsService",
        storages = {@Storage("text-execution-metrics.xml")}
)
public final class TestMetricsService implements PersistentStateComponent<TestMetricsService.State> {
    private static final Logger LOG = Logger.getInstance(TestMetricsService.class);
    private final Project project;
    private State state;
    private final Map<String, Long> testStartTimes = new HashMap<>();
    private final Map<String, ExecutionEnvironment> activeRuns = new HashMap<>();
    private MessageBusConnection messageBus;

    public TestMetricsService(Project project) {
        this.project = project;
        this.state = new State();
    }

    public State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
    }

    public void initialize() {
        // Same code that was in runActivity
        messageBus = project.getMessageBus().connect();
        messageBus.subscribe(ExecutionManager.EXECUTION_TOPIC, new TestExecutionListener(project));
        LOG.info("Test Iteration Tracker initialized for project: " + project.getName());
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
    private class TestExecutionListener implements ExecutionListener {
        private final Project project;

        public TestExecutionListener(Project project) {
            this.project = project;
        }

        @Override
        public void processStarted(@NotNull String executorId, @NotNull ExecutionEnvironment env, @NotNull ProcessHandler handler) {
            if (isTestRun(env)) {
                String runId = env.getExecutionId() + ":" + env.getExecutor().getId();
                testStartTimes.put(runId, System.currentTimeMillis());
                activeRuns.put(runId, env);

                LOG.info("Test execution started: " + getTestName(env));

                // Record test start in local stats
                String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
                if (!state.dailyStats.containsKey(today)) {
                    state.dailyStats.put(today, new DailyStats());
                }
                state.dailyStats.get(today).testExecutions++;
            }
        }

        @Override
        public void processTerminated(@NotNull String executorId, @NotNull ExecutionEnvironment env, @NotNull ProcessHandler handler, int exitCode) {
            String runId = env.getExecutionId() + ":" + env.getExecutor().getId();
            Long startTime = testStartTimes.remove(runId);
            activeRuns.remove(runId);

            if (startTime != null) {
                long duration = System.currentTimeMillis() - startTime;
                String testName = getTestName(env);

                LOG.info("Test execution finished: " + testName + " in " + duration + "ms");

                // Record in local stats
                String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
                DailyStats stats = state.dailyStats.get(today);
                if (stats != null) {
                    stats.totalDuration += duration;
                    stats.successfulTests += (exitCode == 0 ? 1 : 0);
                    stats.failedTests += (exitCode != 0 ? 1 : 0);
                }

            }
        }

        private boolean isTestRun(ExecutionEnvironment env) {
            RunProfile profile = env.getRunProfile();
            if (profile == null) return false;

            String profileName = profile.toString().toLowerCase();
            return profileName.contains("junit") ||
                    profileName.contains("testng") ||
                    profileName.contains("test") ||
                    profileName.contains("integration");
        }

        private String getTestName(ExecutionEnvironment env) {
            RunProfile profile = env.getRunProfile();
            if (profile == null) return "unknown";

            return profile.getName();
        }

        private String getHostname() {
            try {
                return InetAddress.getLocalHost().getHostName();
            } catch (Exception e) {
                return "unknown-host";
            }
        }
    }
}
