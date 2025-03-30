package com.github.pescadorbob.dxinsights.scan.start;

import com.github.pescadorbob.dxinsights.domain.BuildScan;

public interface ForNotifyingUI {
    void notifyUpdated(BuildScan buildScan);
}
