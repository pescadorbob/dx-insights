package com.dx.insights.domain.guards;

import com.dx.insights.scan.start.BuildId;

public interface ForGeneratingBuildIds {
    BuildId next();
}
