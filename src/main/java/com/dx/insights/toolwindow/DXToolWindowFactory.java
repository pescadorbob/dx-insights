package com.dx.insights.toolwindow;

import com.dx.insights.browse.BrowseStats;
import com.dx.insights.browse.ForBrowsingStats;
import com.dx.insights.service.DXInsightService;
import com.dx.insights.service.DXInsightServiceConfiguration;
import com.dx.insights.start.DailyStats;
import com.dx.insights.start.IntellijPersistentStateBuildScanRepository;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.awt.BorderLayout.*;

public class DXToolWindowFactory implements ToolWindowFactory, ForBrowsingStats {

    NewDXToolWindow newDxToolWindow;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        IntellijPersistentStateBuildScanRepository stateRepository = project.getService(IntellijPersistentStateBuildScanRepository.class);
        DXInsightService dxInsightService = project.getService(DXInsightService.class);
        var config = new DXInsightServiceConfiguration(dxInsightService, stateRepository);
        BrowseStats browseStats = new BrowseStats(this,config.getStatsRepository());
        var dxToolWindow = new DXToolWindow(project, toolWindow);
        newDxToolWindow = new NewDXToolWindow(this, project, toolWindow);
        subscribeToMetricsUpdates(project);
        var parentToolWindows = new ParentToolWindows(dxToolWindow, newDxToolWindow);
        var content = ContentFactory.getInstance().createContent(parentToolWindows.getContent(), null, false);
        toolWindow.getContentManager().addContent(content);
    }
    private void subscribeToMetricsUpdates(Project project) {
        MessageBusConnection messageBus;
        messageBus = project.getMessageBus().connect();
        messageBus.subscribe(TestMetricsChangedListener.TEST_METRICS_CHANGED_TOPIC, new TestMetricsChangedListener() {
            @Override
            public void testMetricsChanged() {
                newDxToolWindow.updateStats();
            }
        });
    }


    @Override
    public void updateUi(Map<LocalDate, DailyStats> dailyStats) {
        newDxToolWindow.updateStats();
    }

    class ParentToolWindows {
        private final DXToolWindow dxToolWindow;
        private final NewDXToolWindow newDxToolWindow;
        private JBPanel<JBPanel<?>> mainPanel;

        public ParentToolWindows(DXToolWindow dxToolWindow, NewDXToolWindow newDxToolWindow) {
            this.dxToolWindow = dxToolWindow;
            this.newDxToolWindow = newDxToolWindow;
        }

        public @Nullable JComponent getContent() {

            this.mainPanel = new JBPanel<>();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(dxToolWindow.getContent(), BorderLayout.WEST);
            mainPanel.add(newDxToolWindow.getContent(), BorderLayout.EAST);
            return mainPanel;
        }

    }

    class DXToolWindow {
        private final Project project;
        private final DXInsightService service;
        private JBPanel<JBPanel<?>> mainPanel;


        JTable existingMetricsTable;


        DXToolWindow(Project project, ToolWindow toolWindow) {
            this.project = project;
            service = toolWindow.getProject().getService(DXInsightService.class);
            subscribeToMetricsUpdates();
        }

        private void subscribeToMetricsUpdates() {
            MessageBusConnection messageBus;
            messageBus = project.getMessageBus().connect();
            messageBus.subscribe(TestMetricsChangedListener.TEST_METRICS_CHANGED_TOPIC, new TestMetricsChangedListener() {
                @Override
                public void testMetricsChanged() {
                    updateStats();
                }
            });
        }

        private void updateStats() {
            DXInsightService service = project.getService(DXInsightService.class);
            DXInsightService.State state = service.getState();

            var model = generateMetricsTableModel(Objects.requireNonNull(state));

            existingMetricsTable.setModel(model);
            mainPanel.revalidate();
            mainPanel.repaint();
        }

        public @Nullable JComponent getContent() {
            // Create stats table

            DXInsightService.State state = service.getState();

            var model = generateMetricsTableModel(Objects.requireNonNull(state));


            // Calculate weekly stats
            int weeklyExecutions = 0;
            int weeklySuccessful = 0;
            int weeklyFailed = 0;
            long weeklyDuration = 0;

            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

            for (int i = 0; i < 7; i++) {
                LocalDate date = today.minusDays(i);
                String dateStr = date.format(formatter);

                DXInsightService.DailyStats stats = state.dailyStats.get(dateStr);
                if (stats != null) {
                    weeklyExecutions += stats.testExecutions;
                    weeklySuccessful += stats.successfulTests;
                    weeklyFailed += stats.failedTests;
                    weeklyDuration += stats.totalDuration;
                }
            }

            this.mainPanel = new JBPanel<>();
            mainPanel.setLayout(new BorderLayout());

            // Weekly summary at top
            JPanel summaryPanel = weeklySummary(weeklyExecutions, weeklySuccessful, weeklyFailed, weeklyDuration);

            mainPanel.add(summaryPanel, NORTH);

            // Table in center
            existingMetricsTable = new JTable(model);

            tableInCenter(mainPanel, existingMetricsTable);


            // Note at bottom
            noteAtBottom(mainPanel);

            return mainPanel;
        }

        private void noteAtBottom(JBPanel<JBPanel<?>> targetPanel) {
            JLabel statsLabel = new JLabel("Note: Stats are tracked per IDE session and persist across restarts.");
            targetPanel.add(statsLabel, BorderLayout.SOUTH);
        }

        private void tableInCenter(JBPanel<JBPanel<?>> targetPanel, JTable metricsTable) {

            metricsTable.setFillsViewportHeight(true);
            JScrollPane scrollPane = new JScrollPane(metricsTable);
            targetPanel.add(scrollPane, BorderLayout.CENTER);
        }

        private static @NotNull JPanel weeklySummary(int weeklyExecutions, int weeklySuccessful, int weeklyFailed, long weeklyDuration) {
            JPanel summaryPanel = new JPanel(new GridLayout(1, 3));
            summaryPanel.add(new JLabel("Last 7 days: " + weeklyExecutions + " test executions"));
            summaryPanel.add(new JLabel("Success rate: " +
                    (weeklyExecutions > 0 ? (weeklySuccessful * 100 / weeklyExecutions) + "%" : "N/A")));
            summaryPanel.add(new JLabel("Failure rate: " +
                    (weeklyExecutions > 0 ? (weeklyFailed * 100 / weeklyExecutions) + "%" : "N/A")));
            summaryPanel.add(new JLabel("Avg duration: " +
                    (weeklyExecutions > 0 ? (weeklyDuration / weeklyExecutions) + " ms" : "N/A")));
            return summaryPanel;
        }

        private TableModel generateMetricsTableModel(DXInsightService.State state) {
            // Sort dates in reverse order (newest first)
            List<String> dates = new ArrayList<>(state.dailyStats.keySet());
            dates.sort((a, b) -> b.compareTo(a));
            String[] columnNames = {"Date", "Test Executions", "Successful", "Failed", "Avg Duration (ms)"};
            var model = new DefaultTableModel(columnNames, 0);
            for (String date : dates) {
                DXInsightService.DailyStats stats = state.dailyStats.get(date);
                if (stats != null) {
                    long avgDuration = stats.testExecutions > 0 ? stats.totalDuration / stats.testExecutions : 0;
                    model.addRow(new Object[]{
                            formatDate(date),
                            stats.testExecutions,
                            stats.successfulTests,
                            stats.failedTests,
                            avgDuration
                    });
                }
            }
            return model;
        }
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }

    String formatDate(String isoDate) {
        try {
            LocalDate date = LocalDate.parse(isoDate);
            return date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        } catch (Exception e) {
            return isoDate;
        }
    }

}
