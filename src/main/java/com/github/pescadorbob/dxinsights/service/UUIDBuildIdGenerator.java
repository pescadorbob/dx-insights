package com.github.pescadorbob.dxinsights.service;

import com.github.pescadorbob.dxinsights.domain.guards.ForGeneratingBuildIds;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;

public class UUIDBuildIdGenerator implements ForGeneratingBuildIds {
    @Override
    public BuildId next() {
        return new BuildId(java.util.UUID.randomUUID().toString());
    }
}
