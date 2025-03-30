package com.github.pescadorbob.dxinsights.scan.start;

import com.github.pescadorbob.dxinsights.start.BuildScan;

public interface ForStoringScans {
    BuildScan getBuild(BuildId number);

    void save(BuildScan buildScan);
}
