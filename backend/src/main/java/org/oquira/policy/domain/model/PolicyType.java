package org.oquira.policy.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Extensible classification of simulation policies.
 */
public final class PolicyType {

    private static final Pattern CODE_PATTERN =
            Pattern.compile("[A-Z][A-Z0-9_]*");

    private final String code;
    private final String name;
    private final String description;

    public PolicyType(String code, String name, String description) {
        Objects.requireNonNull(code, "code must not be null");

        if (!CODE_PATTERN.matcher(code).matches()) {
            throw new IllegalArgumentException(
                    "code must match [A-Z][A-Z0-9_]*"
            );
        }

        this.code = code;
        this.name = requireNonBlank(name, "name");
        this.description = requireNonBlank(description, "description");
    }

    private static String requireNonBlank(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be blank"
            );
        }

        return value;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PolicyType other)) {
            return false;
        }

        return code.equals(other.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}