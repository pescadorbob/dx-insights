package com.dx.insights.core.defense.guards;

import com.dx.insights.core.usecases.start.BuildId;

public interface ForGeneratingBuildIds {
    BuildId next();
}
