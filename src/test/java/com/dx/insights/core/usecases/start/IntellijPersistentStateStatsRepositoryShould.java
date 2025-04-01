package com.dx.insights.core.usecases.start;

class IntellijPersistentStateStatsRepositoryShould extends StatsRepositoryShould {
    @Override
    protected ForStoringStats getSomethingForStoringStats() {
        return new IntellijPersistentStateBuildScanRepository();
    }


}