package com.dx.insights.complete;


import com.dx.insights.domain.BuildScan;
import com.dx.insights.scan.start.BuildId;
import com.dx.insights.scan.start.ForNotifyingUI;
import com.dx.insights.start.FakeBuildIdGenerator;
import com.dx.insights.start.FakeIdGenerator;
import com.dx.insights.start.FakeScanRepository;
import com.dx.insights.start.FakeStatsRepository;
import org.junit.jupiter.api.Test;

import static com.dx.insights.complete.CompleteScanRequestTestBuilder.aCompleteScanRequest;
import static com.dx.insights.complete.CompleteScanResultTestBuilder.aCompleteScanResult;
import static com.dx.insights.complete.CompleteScanTestBuilder.aCompleteScan;
import static com.dx.insights.complete.CompleteScanTestBuilder.atTime;
import static com.dx.insights.start.BuildScanStatus.SUCCESSFUL;
import static com.dx.insights.start.BuildScanTestBuilder.DEFAULT_START_TIME;
import static com.dx.insights.start.BuildScanTestBuilder.aBuildScan;
import static com.dx.insights.start.DailyStatsTestBuilder.aDailyStats;
import static com.dx.insights.start.StartScanTestBuilder.clockAtTime;
import static org.assertj.core.api.Assertions.assertThat;

class CompleteScanShould {

    @Test
    void completeABuildScan_givenABuildScan_id() {
        var statsRepository = new FakeStatsRepository();
        statsRepository.save(aDailyStats().withTestExecutions(1).build());
        var scanRepository = new FakeScanRepository();
        scanRepository.save(aBuildScan().build());
        var fakeUi = new FakeUi();
        var fakeGenerator = new FakeIdGenerator();
        fakeGenerator.setupNext("1");
        var buildIdGenerator = new FakeBuildIdGenerator(fakeGenerator);
        var clock = clockAtTime("10:00:08.000");
        var useCase = aCompleteScan()
                .withClock(clock)
                .withScanRepository(scanRepository)
                .withStatsRepository(statsRepository)
                .withScanUI(fakeUi)
                .withBuildIdGenerator(buildIdGenerator)
                .build();
        var expectedResult = aCompleteScanResult().withBuildId("1").build();
        var expectedBuildScan = aBuildScan()
                .withBuildId("1")
                .withStartTime("10:00:00.000")
                .withDuration("PT08S")
                .withBuildScanStatus(SUCCESSFUL)
                .build();
        var expectedDailyStats = aDailyStats()
                .withTestExecutions(1)
                .withTotalDuration(8000)
                .withSuccessfulTests(1)
                .withFailedTests(0)
                .build();
        var request = aCompleteScanRequest().withBuildId("1").build();

        var actualResult = useCase.execute(request);

        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(fakeUi.isNotified()).isTrue();
        assertThat(scanRepository.getBuild(new BuildId("1")))
                .usingRecursiveComparison()
                .isEqualTo(expectedBuildScan);
        assertThat(statsRepository.getDailyStats(atTime(DEFAULT_START_TIME).toLocalDate())).isEqualTo(expectedDailyStats);
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