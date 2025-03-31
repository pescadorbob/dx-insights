package com.github.pescadorbob.dxinsights.browse;


import com.github.pescadorbob.dxinsights.start.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.github.pescadorbob.dxinsights.browse.BrowseStatsResultTestBuilder.aBrowseStatsResult;
import static com.github.pescadorbob.dxinsights.browse.BrowseStatsTestBuilder.aBrowseStats;
import static com.github.pescadorbob.dxinsights.complete.BrowseStatsRequestTestBuilder.aBrowseStatsRequest;
import static com.github.pescadorbob.dxinsights.start.BuildScanTestBuilder.aBuildScan;
import static com.github.pescadorbob.dxinsights.start.DailyStatsTestBuilder.aDailyStats;
import static com.github.pescadorbob.dxinsights.start.StartScanTestBuilder.clockAtTime;
import static org.assertj.core.api.Assertions.assertThat;

class BrowseStatsShould {

    @Test
    void resultInTwoStats_givenTwoScansCompleted() {
        var statsRepository = new FakeStatsRepository();
        statsRepository.save(aDailyStats().withTestExecutions(1).build());
        statsRepository.save(aDailyStats().withDate("2025-03-30").withTestExecutions(1).build());
        var scanRepository = new FakeScanRepository();
        scanRepository.save(aBuildScan().build());
        var fakeUi = new FakeStatsBrowser();

        var useCase = aBrowseStats()
                .withStatsRepository(statsRepository)
                .withStatsBrowser(fakeUi)
                .build();
        var expectedResult = aBrowseStatsResult().build();
        var expectedBrowsingDailyStats = aDailyStats()
                .withTestExecutions(1)
                        .withDate("2025-03-29")
                        .build();
        var expectedBrowsingDailyStats2 = aDailyStats()
                .withTestExecutions(1)
                        .withDate("2025-03-30")
                        .build();
        var expectedStats = new HashMap<LocalDate,DailyStats>();
        expectedStats.put(expectedBrowsingDailyStats.getDate(),expectedBrowsingDailyStats);
        expectedStats.put(expectedBrowsingDailyStats2.getDate(),expectedBrowsingDailyStats2);
        var request = aBrowseStatsRequest().build();

        var actualResult = useCase.execute(request);

        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(fakeUi.getStats()).usingRecursiveComparison().isEqualTo(expectedStats);
    }


    private static class FakeStatsBrowser implements ForBrowsingStats {
        private Map<LocalDate, DailyStats> stats;

        public Map<LocalDate,DailyStats> getStats() {
            return stats;
        }

        @Override
        public void updateUi(Map<LocalDate, DailyStats> stats) {
            this.stats = stats;
        }
    }
}