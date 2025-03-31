package com.github.pescadorbob.dxinsights.complete;


import com.github.pescadorbob.dxinsights.domain.BuildScan;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;
import com.github.pescadorbob.dxinsights.scan.start.ForNotifyingUI;
import com.github.pescadorbob.dxinsights.start.FakeBuildIdGenerator;
import com.github.pescadorbob.dxinsights.start.FakeIdGenerator;
import com.github.pescadorbob.dxinsights.start.FakeScanRepository;
import com.github.pescadorbob.dxinsights.start.FakeStatsRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.github.pescadorbob.dxinsights.complete.CompleteScanRequestTestBuilder.aCompleteScanRequest;
import static com.github.pescadorbob.dxinsights.complete.CompleteScanResultTestBuilder.aCompleteScanResult;
import static com.github.pescadorbob.dxinsights.complete.CompleteScanTestBuilder.aCompleteScan;
import static com.github.pescadorbob.dxinsights.complete.CompleteScanTestBuilder.atTime;
import static com.github.pescadorbob.dxinsights.start.BuildScanStatus.IN_PROGRESS;
import static com.github.pescadorbob.dxinsights.start.BuildScanStatus.SUCCESSFUL;
import static com.github.pescadorbob.dxinsights.start.BuildScanTestBuilder.DEFAULT_START_TIME;
import static com.github.pescadorbob.dxinsights.start.BuildScanTestBuilder.aBuildScan;
import static com.github.pescadorbob.dxinsights.start.DailyStatsTestBuilder.aDailyStats;
import static com.github.pescadorbob.dxinsights.start.StartScanResultTestBuilder.aStartScanResult;
import static com.github.pescadorbob.dxinsights.start.StartScanTestBuilder.aStartScan;
import static com.github.pescadorbob.dxinsights.start.StartScanTestBuilder.clockAtTime;
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