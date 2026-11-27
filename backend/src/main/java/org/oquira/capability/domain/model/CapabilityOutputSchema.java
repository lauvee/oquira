package org.oquira.capability.domain.model;

import java.util.Objects;

/**
 * Represents the expected output structure of a capability.
 */
public final class CapabilityOutputSchema {

    private final String json;

    public CapabilityOutputSchema(String json) {
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