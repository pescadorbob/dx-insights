package com.github.pescadorbob.dxinsights.service;

import com.github.pescadorbob.dxinsights.domain.BuildScan;
import com.github.pescadorbob.dxinsights.scan.start.ForNotifyingUI;
import com.github.pescadorbob.dxinsights.scan.start.StartScan;
import com.github.pescadorbob.dxinsights.start.IntellijPersistentStateBuildScanRepository;
import com.github.pescadorbob.dxinsights.toolwindow.TestMetricsChangedListener;
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
import com.intellij.util.messages.MessageBusConnection;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service(Service.Level.PROJECT)
@State(
        name = "TestMetricsService",
        storages = {@Storage("text-execution-metrics.xml")}
)
public final class DXInsightService implements PersistentStateComponent<DXInsightService.State>, ForNotifyingUI {
    private static final Logger LOG = Logger.getInstance(DXInsightService.class);
    private final Project project;
    @Getter
    private State state;
    private final Map<String, Long> testStartTimes = new HashMap<>();
    private final StartScan startScan;

    public DXInsightService(Project project) {
        this.project = project;
        this.state = new State();
        IntellijPersistentStateBuildScanRepository stateRepository = project.getService(IntellijPersistentStateBuildScanRepository.class);
        var config = new DXInsightServiceConfiguration(this, stateRepository);
        this.startScan = config.getStartScan();
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
    }

    public void initialize() {
        // Same code that was in runActivity
        MessageBusConnection messageBus = project.getMessageBus().connect();
        messageBus.subscribe(ExecutionManager.EXECUTION_TOPIC, new TestExecutionListener());
        LOG.info("Test Iteration Tracker initialized for project: " + project.getName());
    }

    @Override
    public void notifyUpdated(BuildScan buildScan) {
        publishMetricsUpdated();
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


        @Override
        public void processStarted(@NotNull String executorId, @NotNull ExecutionEnvironment env, @NotNull ProcessHandler handler) {
            if (isTestRun(env)) {
                String runId = env.getExecutionId() + ":" + env.getExecutor().getId();
                testStartTimes.put(runId, System.currentTimeMillis());

                LOG.info("Test execution started: " + getTestName(env));

                // Record test start in local stats
                String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
                if (!state.dailyStats.containsKey(today)) {
                    state.dailyStats.put(today, new DailyStats());
                }
                state.dailyStats.get(today).testExecutions++;
                publishMetricsUpdated();
            }
        }

        @Override
        public void processTerminated(@NotNull String executorId, @NotNull ExecutionEnvironment env, @NotNull ProcessHandler handler, int exitCode) {
            String runId = env.getExecutionId() + ":" + env.getExecutor().getId();
            Long startTime = testStartTimes.remove(runId);

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
                    publishMetricsUpdated();
                }

            }
        }

        private boolean isTestRun(ExecutionEnvironment env) {
            RunProfile profile = env.getRunProfile();

            String profileName = profile.toString().toLowerCase();
            return profileName.contains("junit") ||
                    profileName.contains("testng") ||
                    profileName.contains("test") ||
                    profileName.contains("integration");
        }

        private String getTestName(ExecutionEnvironment env) {
            RunProfile profile = env.getRunProfile();

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
    private void publishMetricsUpdated() {
        project.getMessageBus().syncPublisher(TestMetricsChangedListener.TEST_METRICS_CHANGED_TOPIC).testMetricsChanged();
    }
}
