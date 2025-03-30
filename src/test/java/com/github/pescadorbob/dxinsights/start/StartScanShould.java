package com.github.pescadorbob.dxinsights.start;


import com.github.pescadorbob.dxinsights.domain.BuildScan;
import com.github.pescadorbob.dxinsights.scan.start.BuildId;
import com.github.pescadorbob.dxinsights.scan.start.ForNotifyingUI;
import com.github.pescadorbob.dxinsights.scan.start.ForStoringScans;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.github.pescadorbob.dxinsights.start.BuildScanStatus.IN_PROGRESS;
import static com.github.pescadorbob.dxinsights.start.BuildScanTestBuilder.aBuildScan;
import static com.github.pescadorbob.dxinsights.start.StartScanResultTestBuilder.aStartScanResult;
import static com.github.pescadorbob.dxinsights.start.StartScanTestBuilder.aStartScan;
import static com.github.pescadorbob.dxinsights.start.StartScanTestBuilder.clockAtTime;
import static org.assertj.core.api.Assertions.assertThat;

class StartScanShould {

    @Test
    void startABuildScan_givenAConfiguration_AndBuildScan(){
        var scanRepository = new FakeScanRepository();
        var fakeUi = new FakeUi();
        var fakeGenerator = new FakeIdGenerator();
        fakeGenerator.setupNext("1");
        var buildIdGenerator = new FakeBuildIdGenerator(fakeGenerator);
        var clock = clockAtTime("10:00:00.000");
        var useCase = aStartScan()
                .withClock(clock)
                .withScanRepository(scanRepository)
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

        var actualResult = useCase.execute();

        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(fakeUi.isNotified()).isTrue();
        assertThat(scanRepository.getBuild(new BuildId("1")))
                .usingRecursiveComparison()
                .isEqualTo(expectedBuildScan);
    }



    private static class FakeScanRepository implements ForStoringScans {
        public Map<BuildId, BuildScan> buildScans;

        public FakeScanRepository(){
            buildScans = new HashMap<>();
        }
        @Override
        public BuildScan getBuild(BuildId id) {
            var buildScan = buildScans.get(id);
            Objects.requireNonNull(buildScan,"Build scan not found");

            return buildScan.toBuilder().build();
        }

        @Override
        public void save(BuildScan buildScan) {
            buildScans.put(buildScan.getBuildId(),buildScan);
        }
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