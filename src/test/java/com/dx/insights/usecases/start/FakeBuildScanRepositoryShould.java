package com.dx.insights.usecases.start;

class FakeBuildScanRepositoryShould extends BuildScanRepositoryShould{
    @Override
    protected ForStoringScans getSomethingForStoringScans() {
        return new FakeScanRepository();
    }
}
