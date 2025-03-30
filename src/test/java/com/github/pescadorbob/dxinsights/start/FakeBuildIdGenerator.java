package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.domain.guards.BuildIdGenerator;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;

public class FakeBuildIdGenerator implements BuildIdGenerator {
    private final FakeIdGenerator fakeIdGenerator;

    public FakeBuildIdGenerator(FakeIdGenerator fakeIdGenerator) {
        this.fakeIdGenerator = fakeIdGenerator;
    }


    @Override
    public BuildId next() {
        return new BuildId(fakeIdGenerator.next());
    }
}
