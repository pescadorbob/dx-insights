package com.dx.insights.complete;

public class CompleteScanResultTestBuilder {
    private String id;

    public static CompleteScanResultTestBuilder aCompleteScanResult() {
        return new CompleteScanResultTestBuilder();
    }

    public CompleteScanResultTestBuilder withBuildId(String id) {
        this.id = id;
        return this;
    }
    public CompleteScanResult build(){
        return new CompleteScanResult(id);
    }
}
