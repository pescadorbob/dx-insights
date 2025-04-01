package com.dx.insights.complete;

public class CompleteScanRequestTestBuilder {
    private String id;

    public static CompleteScanRequestTestBuilder aCompleteScanRequest(){
        return new CompleteScanRequestTestBuilder();
    }

    public CompleteScanRequest build(){
        return new CompleteScanRequest(id);
    }

    public CompleteScanRequestTestBuilder withBuildId(String id) {
        this.id = id;
        return this;
    }
}
