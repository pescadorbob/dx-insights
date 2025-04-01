package com.dx.insights.core.domain.guards;

import com.dx.insights.core.usecases.start.BuildId;

public interface ForGeneratingBuildIds {
    BuildId next();
}
