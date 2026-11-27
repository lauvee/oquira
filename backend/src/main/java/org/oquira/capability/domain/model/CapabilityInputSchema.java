package org.oquira.capability.domain.model;

import java.util.Objects;

/**
 * Represents the expected input structure of a capability.
 */
public final class CapabilityInputSchema {

    private final String json;

    public CapabilityInputSchema(String json) {
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