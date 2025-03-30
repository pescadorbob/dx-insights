package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.scan.start.ForStoringScans;

class FakeBuildScanRepositoryShould extends BuildScanRepositoryShould{
    @Override
    protected ForStoringScans getSomethingForStoringScans() {
        return new FakeScanRepository();
    }
}
