package com.github.pescadorbob.dxinsights.domain.guards;

import com.github.pescadorbob.dxinsights.scan.start.BuildId;

public interface ForGeneratingBuildIds {
    BuildId next();
}
