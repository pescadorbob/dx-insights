package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.domain.guards.ForGeneratingBuildIds;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;

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
