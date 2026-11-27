package org.oquira.institution.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents the relationship between an institution and a sector.
 */
public final class InstitutionSector {

    private final UUID institutionId;
    private final UUID sectorId;
    private final InstitutionSectorRelationshipType relationshipType;
    private final LocalDate validFrom;
    private final LocalDate validUntil;

    public InstitutionSector(
            UUID institutionId,
            UUID sectorId,
            InstitutionSectorRelationshipType relationshipType,
            LocalDate validFrom,
            LocalDate validUntil
    ) {
        this.institutionId = Objects.requireNonNull(
                institutionId, "institutionId must not be null"
        );

        this.sectorId = Objects.requireNonNull(
                sectorId, "sectorId must not be null"
        );

        this.relationshipType = Objects.requireNonNull(
                relationshipType, "relationshipType must not be null"
        );

        this.validFrom = Objects.requireNonNull(
                validFrom, "validFrom must not be null"
        );

        if (validUntil != null && validUntil.isBefore(validFrom)) {
            throw new IllegalArgumentException(
                    "validUntil must not be before validFrom"
            );
        }

        this.validUntil = validUntil;
    }

    public UUID getInstitutionId() {
        return institutionId;
    }

    public UUID getSectorId() {
        return sectorId;
    }

    public InstitutionSectorRelationshipType getRelationshipType() {
        return relationshipType;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof InstitutionSector other)) {
            return false;
        }

        return institutionId.equals(other.institutionId)
                && sectorId.equals(other.sectorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(institutionId, sectorId);
    }
}