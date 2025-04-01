package com.dx.insights.core.usecases.start;

import com.dx.insights.core.domain.BuildScan;

public interface ForNotifyingUI {
    void notifyUpdated(BuildScan buildScan);
}
