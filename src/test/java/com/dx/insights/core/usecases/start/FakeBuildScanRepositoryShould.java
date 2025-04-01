package com.dx.insights.core.usecases.start;

class FakeBuildScanRepositoryShould extends BuildScanRepositoryShould{
    @Override
    protected ForStoringScans getSomethingForStoringScans() {
        return new FakeScanRepository();
    }
}
