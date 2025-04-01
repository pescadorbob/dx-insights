package com.dx.insights.core.defense.guards;

import com.dx.insights.core.domain.BuildId;

public interface ForGeneratingBuildIds {
    BuildId next();
}
