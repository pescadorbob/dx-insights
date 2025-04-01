package com.dx.insights.start;

class IntellijPersistentStateStatsRepositoryShould extends StatsRepositoryShould {
    @Override
    protected ForStoringStats getSomethingForStoringStats() {
        return new IntellijPersistentStateBuildScanRepository();
    }


}