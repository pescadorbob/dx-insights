package com.github.pescadorbob.dxinsights.toolwindow;

import com.intellij.util.messages.Topic;

public interface TestMetricsChangedListener {
    Topic<TestMetricsChangedListener> TEST_METRICS_CHANGED_TOPIC = Topic.create("Test Metrics Changed", TestMetricsChangedListener.class);

    void testMetricsChanged();
}
