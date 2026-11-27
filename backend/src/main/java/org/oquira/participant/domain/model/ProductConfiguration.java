package org.oquira.participant.domain.model;

import java.util.Objects;

/**
 * Represents the JSON configuration of a product within a profile version.
 *
 * JSON syntax and structural validation are responsibilities
 * of the application layer.
 */
public final class ProductConfiguration {

    private final String json;

    public ProductConfiguration(String json) {
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