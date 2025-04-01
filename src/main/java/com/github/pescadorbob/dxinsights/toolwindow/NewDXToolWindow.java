package com.github.pescadorbob.dxinsights.toolwindow;

import com.github.pescadorbob.dxinsights.service.DXInsightService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBPanel;
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
import java.util.Objects;

import static java.awt.BorderLayout.NORTH;

class NewDXToolWindow {
    private final DXToolWindowFactory dxToolWindowFactory;
    private final Project project;
    private final DXInsightService service;
    private JBPanel<JBPanel<?>> mainPanel;

    JTable metricsTable;


    NewDXToolWindow(DXToolWindowFactory dxToolWindowFactory, Project project, ToolWindow toolWindow) {
        this.dxToolWindowFactory = dxToolWindowFactory;
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

    public void updateStats() {
        DXInsightService service = project.getService(DXInsightService.class);
        DXInsightService.State state = service.getState();

        var model = generateMetricsTableModel(Objects.requireNonNull(state));

        metricsTable.setModel(model);
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

        mainPanel = new JBPanel<>();
        mainPanel.setLayout(new BorderLayout());

        // Weekly summary at top
        JPanel summaryPanel = weeklySummary(weeklyExecutions, weeklySuccessful, weeklyFailed, weeklyDuration);

        mainPanel.add(summaryPanel, NORTH);

        // Table in center
        metricsTable = new JTable(model);

        tableInCenter(mainPanel, metricsTable);

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
                        dxToolWindowFactory.formatDate(date),
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
