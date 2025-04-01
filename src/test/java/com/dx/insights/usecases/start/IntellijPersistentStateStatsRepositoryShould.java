package com.dx.insights.usecases.start;

class IntellijPersistentStateStatsRepositoryShould extends StatsRepositoryShould {
    @Override
    protected ForStoringStats getSomethingForStoringStats() {
        return new IntellijPersistentStateBuildScanRepository();
    }


}