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
import java.awt.*;

public class DXToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        var dxToolWindow = new DXToolWindow(toolWindow );
        var content = ContentFactory.getInstance().createContent(dxToolWindow.getContent(), null, false);
        toolWindow.getContentManager().addContent(content);
    }

    class DXToolWindow {
        private TestMetricsService service;

        DXToolWindow(ToolWindow toolWindow){
            service = toolWindow.getProject().getService(TestMetricsService.class);
        }

        public @Nullable JComponent getContent() {
            var panel = new JBPanel<>();
            panel.setLayout(new BorderLayout());

            // Weekly summary at top
            JPanel summaryPanel = new JPanel(new GridLayout(1, 3));
            int weeklyExecutions = 1;
            summaryPanel.add(new JLabel("Last 7 days: " + weeklyExecutions + " test executions"));
            int weeklySuccessful = 1;
            summaryPanel.add(new JLabel("Success rate: " +
                    (weeklyExecutions > 0 ? (weeklySuccessful * 100 / weeklyExecutions) + "%" : "N/A")));
            int weeklyDuration = 1000;
            summaryPanel.add(new JLabel("Avg duration: " +
                    (weeklyExecutions > 0 ? (weeklyDuration / weeklyExecutions) + " ms" : "N/A")));

            panel.add(summaryPanel, BorderLayout.NORTH);

            var label = new JBLabel("Hello world");
            panel.add(label,BorderLayout.CENTER);
            return panel;
        }
    }
    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }

}
