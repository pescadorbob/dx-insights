package com.dx.insights.start;


import com.dx.insights.domain.BuildScan;
import com.dx.insights.scan.start.BuildId;
import com.dx.insights.scan.start.ForNotifyingUI;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.dx.insights.start.BuildScanStatus.IN_PROGRESS;
import static com.dx.insights.start.BuildScanTestBuilder.aBuildScan;
import static com.dx.insights.start.DailyStatsTestBuilder.aDailyStats;
import static com.dx.insights.start.StartScanResultTestBuilder.aStartScanResult;
import static com.dx.insights.start.StartScanTestBuilder.aStartScan;
import static com.dx.insights.start.StartScanTestBuilder.clockAtTime;
import static org.assertj.core.api.Assertions.assertThat;

class StartScanShould {

    @Test
    void startABuildScan_givenAConfiguration_AndBuildScan() {
        var statsRepository = new FakeStatsRepository();
        var scanRepository = new FakeScanRepository();
        var fakeUi = new FakeUi();
        var fakeGenerator = new FakeIdGenerator();
        fakeGenerator.setupNext("1");
        var buildIdGenerator = new FakeBuildIdGenerator(fakeGenerator);
        var clock = clockAtTime("10:00:00.000");
        var useCase = aStartScan()
                .withClock(clock)
                .withScanRepository(scanRepository)
                .withStatsRepository(statsRepository)
                .withScanUI(fakeUi)
                .withBuildIdGenerator(buildIdGenerator)
                .build();
        var expectedResult = aStartScanResult().withBuildId("1").build();
        var expectedBuildScan = aBuildScan()
                .withBuildId("1")
                .withStartTime("10:00:00.000")
                .withNoDuration()
                .withBuildScanStatus(IN_PROGRESS)
                .build();
        var expectedDailyStats = aDailyStats()
                .withTestExecutions(1)
                .build();


        var actualResult = useCase.execute();

        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(fakeUi.isNotified()).isTrue();
        assertThat(scanRepository.getBuild(new BuildId("1")))
                .usingRecursiveComparison()
                .isEqualTo(expectedBuildScan);
        assertThat(statsRepository.getDailyStats(LocalDate.parse("2025-03-29"))).isEqualTo(expectedDailyStats);
    }


    private static class FakeUi implements ForNotifyingUI {
        private boolean isNotified = false;

        public boolean isNotified() {
            return isNotified;
        }

        @Override
        public void notifyUpdated(BuildScan buildScan) {
            isNotified = true;
        }
    }
}