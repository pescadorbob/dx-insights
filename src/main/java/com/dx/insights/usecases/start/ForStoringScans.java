package com.dx.insights.usecases.start;

import com.dx.insights.domain.BuildScan;

public interface ForStoringScans {
    BuildScan getBuild(BuildId number);

    void save(BuildScan buildScan);
}
