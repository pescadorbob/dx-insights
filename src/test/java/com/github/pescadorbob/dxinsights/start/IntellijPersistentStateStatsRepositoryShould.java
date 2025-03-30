package com.github.pescadorbob.dxinsights.start;

class IntellijPersistentStateStatsRepositoryShould extends StatsRepositoryShould {
    @Override
    protected ForStoringStats getSomethingForStoringStats() {
        return new IntellijPersistentStateBuildScanRepository();
    }


}