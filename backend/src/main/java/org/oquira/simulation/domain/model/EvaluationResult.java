package org.oquira.simulation.domain.model;

import java.util.Objects;

/**
 * Represents a structured and sanitized policy evaluation result.
 *
 * JSON syntax validation and sensitive-data sanitization
 * are responsibilities of the evaluation layer.
 */
public final class EvaluationResult {

    private final String json;

    public EvaluationResult(String json) {
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