package org.oquira.policy.domain.model;

import java.util.Objects;

/**
 * Represents the expected JSON structure of a policy configuration.
 *
 * Schema interpretation and validation are responsibilities
 * of the application layer and Policy Evaluator.
 */
public final class PolicyConfigurationSchema {

    private final String json;

    public PolicyConfigurationSchema(String json) {
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