package org.oquira.institution.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstitutionSectorTest {

    private final UUID institutionId = UUID.randomUUID();
    private final UUID sectorId = UUID.randomUUID();

    private final LocalDate validFrom = LocalDate.of(2026, 1, 1);
    private final LocalDate validUntil = LocalDate.of(2026, 12, 31);

    @Test
    void shouldCreateInstitutionSectorWithValidData() {
        InstitutionSector relationship = createValidRelationship();

        assertThat(relationship.getInstitutionId()).isEqualTo(institutionId);
        assertThat(relationship.getSectorId()).isEqualTo(sectorId);
        assertThat(relationship.getRelationshipType())
                .isEqualTo(InstitutionSectorRelationshipType.PRIMARY);
        assertThat(relationship.getValidFrom()).isEqualTo(validFrom);
        assertThat(relationship.getValidUntil()).isEqualTo(validUntil);
    }

    @Test
    void shouldRejectNullInstitutionId() {
        assertThatThrownBy(() -> new InstitutionSector(
                null, sectorId, InstitutionSectorRelationshipType.PRIMARY,
                validFrom, validUntil
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullSectorId() {
        assertThatThrownBy(() -> new InstitutionSector(
                institutionId, null, InstitutionSectorRelationshipType.PRIMARY,
                validFrom, validUntil
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullRelationshipType() {
        assertThatThrownBy(() -> new InstitutionSector(
                institutionId, sectorId, null, validFrom, validUntil
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullValidFrom() {
        assertThatThrownBy(() -> new InstitutionSector(
                institutionId, sectorId, InstitutionSectorRelationshipType.PRIMARY,
                null, validUntil
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldAllowNullValidUntil() {
        InstitutionSector relationship = new InstitutionSector(
                institutionId,
                sectorId,
                InstitutionSectorRelationshipType.PRIMARY,
                validFrom,
                null
        );

        assertThat(relationship.getValidUntil()).isNull();
    }

    @Test
    void shouldRejectValidUntilBeforeValidFrom() {
        assertThatThrownBy(() -> new InstitutionSector(
                institutionId,
                sectorId,
                InstitutionSectorRelationshipType.PRIMARY,
                validFrom,
                LocalDate.of(2025, 12, 31)
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldAllowValidUntilEqualToValidFrom() {
        InstitutionSector relationship = new InstitutionSector(
                institutionId,
                sectorId,
                InstitutionSectorRelationshipType.PRIMARY,
                validFrom,
                validFrom
        );

        assertThat(relationship.getValidUntil()).isEqualTo(validFrom);
    }

    @Test
    void shouldAllowValidUntilAfterValidFrom() {
        InstitutionSector relationship = createValidRelationship();

        assertThat(relationship.getValidUntil()).isAfter(relationship.getValidFrom());
    }

    @Test
    void shouldConsiderRelationshipsWithSameIdentifiersEqual() {
        InstitutionSector first = createValidRelationship();

        InstitutionSector second = new InstitutionSector(
                institutionId,
                sectorId,
                InstitutionSectorRelationshipType.SECONDARY,
                LocalDate.of(2025, 1, 1),
                null
        );

        assertThat(first).isEqualTo(second);
    }

    @Test
    void shouldHaveSameHashCodeForRelationshipsWithSameIdentifiers() {
        InstitutionSector first = createValidRelationship();

        InstitutionSector second = new InstitutionSector(
                institutionId,
                sectorId,
                InstitutionSectorRelationshipType.OTHER,
                validFrom,
                null
        );

        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }

    @Test
    void shouldConsiderRelationshipsWithDifferentInstitutionIdsDifferent() {
        InstitutionSector first = createValidRelationship();

        InstitutionSector second = new InstitutionSector(
                UUID.randomUUID(),
                sectorId,
                InstitutionSectorRelationshipType.PRIMARY,
                validFrom,
                validUntil
        );

        assertThat(first).isNotEqualTo(second);
    }

    @Test
    void shouldConsiderRelationshipsWithDifferentSectorIdsDifferent() {
        InstitutionSector first = createValidRelationship();

        InstitutionSector second = new InstitutionSector(
                institutionId,
                UUID.randomUUID(),
                InstitutionSectorRelationshipType.PRIMARY,
                validFrom,
                validUntil
        );

        assertThat(first).isNotEqualTo(second);
    }

    @Test
    void shouldBeEqualToItself() {
        InstitutionSector relationship = createValidRelationship();

        assertThat(relationship).isEqualTo(relationship);
    }

    private InstitutionSector createValidRelationship() {
        return new InstitutionSector(
                institutionId,
                sectorId,
                InstitutionSectorRelationshipType.PRIMARY,
                validFrom,
                validUntil
        );
    }
}