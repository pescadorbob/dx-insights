package com.github.pescadorbob.dxinsights.toolwindow;

import com.github.pescadorbob.dxinsights.service.TestMetricsService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DXToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        var dxToolWindow = new DXToolWindow(toolWindow );
        var content = ContentFactory.getInstance().createContent(dxToolWindow.getContent(), null, false);
        toolWindow.getContentManager().addContent(content);
    }

    class DXToolWindow {
        private final TestMetricsService service;

        DXToolWindow(ToolWindow toolWindow){
            service = toolWindow.getProject().getService(TestMetricsService.class);
        }

        public @Nullable JComponent getContent() {
            // Create stats table
            String[] columnNames = {"Date", "Test Executions", "Successful", "Failed", "Avg Duration (ms)"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            TestMetricsService.State state = service.getState();

            // Sort dates in reverse order (newest first)
            List<String> dates = new ArrayList<>(state.dailyStats.keySet());
            dates.sort((a, b) -> b.compareTo(a));

            for (String date : dates) {
                TestMetricsService.DailyStats stats = state.dailyStats.get(date);
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

                TestMetricsService.DailyStats stats = state.dailyStats.get(dateStr);
                if (stats != null) {
                    weeklyExecutions += stats.testExecutions;
                    weeklySuccessful += stats.successfulTests;
                    weeklyFailed += stats.failedTests;
                    weeklyDuration += stats.totalDuration;
                }
            }

            var panel = new JBPanel<>();
            panel.setLayout(new BorderLayout());

            // Weekly summary at top
            JPanel summaryPanel = new JPanel(new GridLayout(1, 3));
            summaryPanel.add(new JLabel("Last 7 days: " + weeklyExecutions + " test executions"));
            summaryPanel.add(new JLabel("Success rate: " +
                    (weeklyExecutions > 0 ? (weeklySuccessful * 100 / weeklyExecutions) + "%" : "N/A")));
            summaryPanel.add(new JLabel("Avg duration: " +
                    (weeklyExecutions > 0 ? (weeklyDuration / weeklyExecutions) + " ms" : "N/A")));

            panel.add(summaryPanel, BorderLayout.NORTH);

            // Table in center
            JTable table = new JTable(model);
            table.setFillsViewportHeight(true);
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);

            // Note at bottom
            JLabel noteLabel = new JLabel("Note: Stats are tracked per IDE session and persist across restarts.");
            panel.add(noteLabel, BorderLayout.SOUTH);
            return panel;
        }
    }
    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }
    private String formatDate(String isoDate) {
        try {
            LocalDate date = LocalDate.parse(isoDate);
            return date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        } catch (Exception e) {
            return isoDate;
        }
    }

}
