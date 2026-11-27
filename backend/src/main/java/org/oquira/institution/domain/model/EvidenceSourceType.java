package org.oquira.institution.domain.model;

import java.util.Objects;

/**
 * Represents a type in the evidence source classification catalog.
 */
public final class EvidenceSourceType {

    private final String code;
    private final String name;
    private final String description;

    public EvidenceSourceType(String code, String name, String description) {
        this.code = requireValidCode(code);
        this.name = requireNonBlank(name, "name");
        this.description = requireNonBlank(description, "description");
    }

    private static String requireValidCode(String code) {
        Objects.requireNonNull(code, "code must not be null");

        if (!code.matches("[A-Z][A-Z0-9_]*")) {
            throw new IllegalArgumentException(
                    "code must contain uppercase letters, digits or underscores "
                            + "and must start with a letter"
            );
        }

        return code;
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

        if (!(object instanceof EvidenceSourceType other)) {
            return false;
        }

        return code.equals(other.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}