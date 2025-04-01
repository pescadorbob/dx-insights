package com.dx.insights.core.usecases.start;

class IntellijPersistentStateBuildScanRepositoryShould extends BuildScanRepositoryShould{
    @Override
    protected ForStoringScans getSomethingForStoringScans() {
        return new IntellijPersistentStateBuildScanRepository();
    }
}
