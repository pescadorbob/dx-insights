package com.dx.insights.core.domain;

import com.dx.insights.core.defense.guards.Guard;

public record BuildId(String id) {
    public BuildId {
        id = Guard.againstNull(id, "BuildId cannot be null");
    }
}
