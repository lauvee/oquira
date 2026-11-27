package org.oquira.capability.domain.model;

import java.util.Objects;

/**
 * Represents participant-specific configuration for a capability assignment.
 */
public final class CapabilityConfiguration {

    private final String json;

    public CapabilityConfiguration(String json) {
        Objects.requireNonNull(json, "json must not be null");

        if (json.isBlank()) {
            throw new IllegalArgumentException("json must not be blank");
        }

        this.json = json;
    }

    public String getJson() {
        return json;
    }
}