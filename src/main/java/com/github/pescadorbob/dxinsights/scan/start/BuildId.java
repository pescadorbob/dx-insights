package com.github.pescadorbob.dxinsights.scan.start;

import com.github.pescadorbob.dxinsights.domain.guards.Guard;

public record BuildId(String id) {
    public BuildId {
        id = Guard.againstNull(id, "BuildId cannot be null");
    }
}
