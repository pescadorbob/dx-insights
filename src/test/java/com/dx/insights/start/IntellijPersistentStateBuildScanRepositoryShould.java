package com.dx.insights.start;

import com.dx.insights.scan.start.ForStoringScans;

class IntellijPersistentStateBuildScanRepositoryShould extends BuildScanRepositoryShould{
    @Override
    protected ForStoringScans getSomethingForStoringScans() {
        return new IntellijPersistentStateBuildScanRepository();
    }
}
