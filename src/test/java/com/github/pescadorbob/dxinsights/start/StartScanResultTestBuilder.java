package com.github.pescadorbob.dxinsights.start;

import com.github.pescadorbob.dxinsights.scan.start.StartScanResult;

public class StartScanResultTestBuilder {
    private String id;

    public static  StartScanResultTestBuilder aStartScanResult() {
        return new StartScanResultTestBuilder();
    }

    public StartScanResultTestBuilder withBuildId(String id) {
        this.id = id;
        return this;
    }
    public StartScanResult build(){
        return new StartScanResult(id);
    }
}
