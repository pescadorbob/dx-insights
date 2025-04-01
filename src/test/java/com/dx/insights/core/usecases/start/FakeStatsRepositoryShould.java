package com.dx.insights.core.usecases.start;

class FakeStatsRepositoryShould extends StatsRepositoryShould {
    @Override
    protected ForStoringStats getSomethingForStoringStats() {
        return new FakeStatsRepository();
    }


}