package com.dx.insights.usecases.start;

import com.dx.insights.domain.guards.Guard;

public record BuildId(String id) {
    public BuildId {
        id = Guard.againstNull(id, "BuildId cannot be null");
    }
}
