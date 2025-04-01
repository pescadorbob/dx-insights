package com.dx.insights.scan.start;

import com.dx.insights.domain.BuildScan;

public interface ForStoringScans {
    BuildScan getBuild(BuildId number);

    void save(BuildScan buildScan);
}
