package com.dx.insights.scan.start;

import com.dx.insights.domain.guards.Guard;

public record BuildId(String id) {
    public BuildId {
        id = Guard.againstNull(id, "BuildId cannot be null");
    }
}
