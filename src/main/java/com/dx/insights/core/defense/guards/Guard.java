package com.dx.insights.core.defense.guards;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class Guard {
    public static <T> T againstNull(T value, String message) {
        return Objects.requireNonNull(value, message);
    }
}
