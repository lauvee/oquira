package org.oquira.institution.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a financial ecosystem sector.
 */
public final class Sector {

    private final UUID sectorId;
    private final String code;
    private final String name;
    private final String description;
    private final SectorStatus status;

    public Sector(
            UUID sectorId,
            String code,
            String name,
            String description,
            SectorStatus status
    ) {
        this.sectorId = Objects.requireNonNull(
                sectorId, "sectorId must not be null"
        );

        this.code = requireValidCode(code);
        this.name = requireNonBlank(name, "name");
        this.description = requireNonBlank(description, "description");

        this.status = Objects.requireNonNull(
                status, "status must not be null"
        );
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

    public UUID getSectorId() {
        return sectorId;
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

    public SectorStatus getStatus() {
        return status;
    }
}