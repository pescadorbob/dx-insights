package com.dx.insights.usecases.start;

import com.dx.insights.domain.BuildScan;

public interface ForNotifyingUI {
    void notifyUpdated(BuildScan buildScan);
}
