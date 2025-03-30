package com.github.pescadorbob.dxinsights.start;

class FakeStatsRepositoryShould extends StatsRepositoryShould {
    @Override
    protected ForStoringStats getSomethingForStoringStats() {
        return new FakeStatsRepository();
    }


}