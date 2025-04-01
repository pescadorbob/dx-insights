package com.dx.insights.usecases.start;

import com.dx.insights.domain.guards.ForGeneratingBuildIds;

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
