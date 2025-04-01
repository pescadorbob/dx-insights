package com.dx.insights.usecases.start;

class IntellijPersistentStateBuildScanRepositoryShould extends BuildScanRepositoryShould{
    @Override
    protected ForStoringScans getSomethingForStoringScans() {
        return new IntellijPersistentStateBuildScanRepository();
    }
}
