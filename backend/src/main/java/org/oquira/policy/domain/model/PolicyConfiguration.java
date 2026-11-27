package org.oquira.policy.domain.model;

import java.util.Objects;

/**
 * Represents the JSON configuration of a simulation policy version.
 *
 * JSON syntax and structural validation are responsibilities
 * of the application layer and Policy Evaluator.
 */
public final class PolicyConfiguration {

    private final String json;

    public PolicyConfiguration(String json) {
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