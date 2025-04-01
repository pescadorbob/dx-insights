package com.dx.insights.domain.guards;

import com.dx.insights.usecases.start.BuildId;

public interface ForGeneratingBuildIds {
    BuildId next();
}
