package com.dx.insights.service;

import com.dx.insights.core.defense.guards.ForGeneratingBuildIds;
import com.dx.insights.core.usecases.start.BuildId;

public class UUIDBuildIdGenerator implements ForGeneratingBuildIds {
    @Override
    public BuildId next() {
        return new BuildId(java.util.UUID.randomUUID().toString());
    }
}
