package com.dx.insights.core.usecases.start;

import com.dx.insights.core.defense.guards.ForGeneratingBuildIds;
import com.dx.insights.core.domain.BuildId;

public class FakeBuildIdGenerator implements ForGeneratingBuildIds {
    private final FakeIdGenerator fakeIdGenerator;

    public FakeBuildIdGenerator(FakeIdGenerator fakeIdGenerator) {
        this.fakeIdGenerator = fakeIdGenerator;
    }


    @Override
    public BuildId next() {
        return new BuildId(fakeIdGenerator.next());
    }
}
