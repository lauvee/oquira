package org.oquira.capability.domain.model;

import java.util.Objects;

/**
 * Represents validated assignment-specific policy parameters.
 *
 * JSON syntax and permission validation are responsibilities
 * of the application layer and Policy Evaluator.
 */
public final class PolicyConfigurationOverrides {

    private final String json;

    public PolicyConfigurationOverrides(String json) {
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