package org.oquira.participant.domain.model;

import java.util.Objects;

/**
 * Represents the JSON content of a participant profile configuration.
 *
 * JSON syntax and structural validation are responsibilities
 * of the application layer.
 */
public final class ProfileConfiguration {

    private final String json;

    public ProfileConfiguration(String json) {
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
