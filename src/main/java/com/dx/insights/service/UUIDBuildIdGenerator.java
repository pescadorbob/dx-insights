package com.dx.insights.service;

import com.dx.insights.domain.guards.ForGeneratingBuildIds;
import com.dx.insights.scan.start.BuildId;

public class UUIDBuildIdGenerator implements ForGeneratingBuildIds {
    @Override
    public BuildId next() {
        return new BuildId(java.util.UUID.randomUUID().toString());
    }
}
