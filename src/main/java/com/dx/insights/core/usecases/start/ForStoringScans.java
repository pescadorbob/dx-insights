package com.dx.insights.core.usecases.start;

import com.dx.insights.core.domain.BuildId;
import com.dx.insights.core.domain.BuildScan;

public interface ForStoringScans {
    BuildScan getBuild(BuildId number);

    void save(BuildScan buildScan);
}
