package com.dx.insights.start;

import com.dx.insights.scan.start.ForStoringScans;

class FakeBuildScanRepositoryShould extends BuildScanRepositoryShould{
    @Override
    protected ForStoringScans getSomethingForStoringScans() {
        return new FakeScanRepository();
    }
}
