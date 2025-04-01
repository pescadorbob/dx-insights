package com.dx.insights.start;

class FakeStatsRepositoryShould extends StatsRepositoryShould {
    @Override
    protected ForStoringStats getSomethingForStoringStats() {
        return new FakeStatsRepository();
    }


}