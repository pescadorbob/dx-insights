package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.scan.start.ForStoringScans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.pescadorbob.dxinsights.start.BuildScanTestBuilder.aBuildScan;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class BuildScanRepositoryShould {
    ForStoringScans buildScanRepository;

    @BeforeEach
    public void setupRepository(){
        buildScanRepository = getSomethingForStoringScans();
    }

    protected abstract ForStoringScans getSomethingForStoringScans();

    @Test
    public void saveBuildScan_givenABuildScan(){
        var buildScan = aBuildScan().build();
        buildScanRepository.save(buildScan);

        var actualBuildScan = buildScanRepository.getBuild(buildScan.getBuildId());

        assertThat(actualBuildScan).usingRecursiveComparison().isEqualTo(buildScan);

    }
}
