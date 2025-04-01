package com.dx.insights.usecases.start;

class FakeStatsRepositoryShould extends StatsRepositoryShould {
    @Override
    protected ForStoringStats getSomethingForStoringStats() {
        return new FakeStatsRepository();
    }


}