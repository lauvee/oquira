package org.oquira.institution.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a real-world organization or identifiable financial ecosystem actor.
 */
public final class Institution {

    private final UUID institutionId;
    private final String legalName;
    private final String displayName;
    private final InstitutionType institutionType;
    private final String description;
    private final String countryCode;
    private final InstitutionIdentityStatus identityStatus;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Institution(
            UUID institutionId,
            String legalName,
            String displayName,
            InstitutionType institutionType,
            String description,
            String countryCode,
            InstitutionIdentityStatus identityStatus,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.institutionId = Objects.requireNonNull(
                institutionId, "institutionId must not be null"
        );

        this.legalName = legalName == null ? null : requireNonBlank(legalName, "legalName");
        this.displayName = requireNonBlank(displayName, "displayName");

        this.institutionType = Objects.requireNonNull(
                institutionType, "institutionType must not be null"
        );

        this.description = description;

        this.countryCode = requireValidCountryCode(countryCode);

        this.identityStatus = Objects.requireNonNull(
                identityStatus, "identityStatus must not be null"
        );

        this.createdAt = Objects.requireNonNull(
                createdAt, "createdAt must not be null"
        );

        this.updatedAt = Objects.requireNonNull(
                updatedAt, "updatedAt must not be null"
        );

        if (updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException(
                    "updatedAt must not be before createdAt"
            );
        }
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

    private static String requireValidCountryCode(String countryCode) {
        Objects.requireNonNull(countryCode, "countryCode must not be null");

        if (!countryCode.matches("[A-Z]{2}")) {
            throw new IllegalArgumentException(
                    "countryCode must be a two-letter uppercase ISO 3166-1 alpha-2 code"
            );
        }

        return countryCode;
    }

    public UUID getInstitutionId() {
        return institutionId;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public InstitutionType getInstitutionType() {
        return institutionType;
    }

    public String getDescription() {
        return description;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public InstitutionIdentityStatus getIdentityStatus() {
        return identityStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}