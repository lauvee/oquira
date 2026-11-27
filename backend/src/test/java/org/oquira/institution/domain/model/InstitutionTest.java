package org.oquira.institution.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstitutionTest {

    private final UUID institutionId = UUID.randomUUID();

    private final InstitutionType institutionType = new InstitutionType(
            "FINANCIAL_INTERMEDIARY",
            "Financial Intermediary",
            "An organization providing financial intermediation services"
    );

    private final Instant createdAt = Instant.parse("2026-01-01T10:00:00Z");
    private final Instant updatedAt = Instant.parse("2026-01-02T10:00:00Z");

    @Test
    void shouldCreateInstitutionWithValidData() {
        Institution institution = createValidInstitution();

        assertThat(institution.getInstitutionId()).isEqualTo(institutionId);
        assertThat(institution.getLegalName()).isEqualTo("Example Financial Corporation");
        assertThat(institution.getDisplayName()).isEqualTo("Example Bank");
        assertThat(institution.getInstitutionType()).isEqualTo(institutionType);
        assertThat(institution.getDescription()).isEqualTo("Example institution");
        assertThat(institution.getCountryCode()).isEqualTo("DO");
        assertThat(institution.getIdentityStatus())
                .isEqualTo(InstitutionIdentityStatus.VERIFIED);
        assertThat(institution.getCreatedAt()).isEqualTo(createdAt);
        assertThat(institution.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldAllowInstitutionWithoutLegalName() {
        Institution institution = new Institution(
                institutionId,
                null,
                "Example Institution",
                institutionType,
                null,
                "DO",
                InstitutionIdentityStatus.UNVERIFIED,
                createdAt,
                updatedAt
        );

        assertThat(institution.getLegalName()).isNull();
    }

    @Test
    void shouldAllowInstitutionWithoutDescription() {
        Institution institution = new Institution(
                institutionId,
                "Example Financial Corporation",
                "Example Bank",
                institutionType,
                null,
                "DO",
                InstitutionIdentityStatus.VERIFIED,
                createdAt,
                updatedAt
        );

        assertThat(institution.getDescription()).isNull();
    }

    @Test
    void shouldRejectNullInstitutionId() {
        assertThatThrownBy(() -> new Institution(
                null,
                null,
                "Example Bank",
                institutionType,
                null,
                "DO",
                InstitutionIdentityStatus.UNVERIFIED,
                createdAt,
                updatedAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankDisplayName() {
        assertThatThrownBy(() -> new Institution(
                institutionId,
                null,
                "   ",
                institutionType,
                null,
                "DO",
                InstitutionIdentityStatus.UNVERIFIED,
                createdAt,
                updatedAt
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullInstitutionType() {
        assertThatThrownBy(() -> new Institution(
                institutionId,
                null,
                "Example Bank",
                null,
                null,
                "DO",
                InstitutionIdentityStatus.UNVERIFIED,
                createdAt,
                updatedAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectInvalidCountryCode() {
        assertThatThrownBy(() -> new Institution(
                institutionId,
                null,
                "Example Bank",
                institutionType,
                null,
                "dom",
                InstitutionIdentityStatus.UNVERIFIED,
                createdAt,
                updatedAt
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullCountryCode() {
        assertThatThrownBy(() -> new Institution(
                institutionId,
                null,
                "Example Bank",
                institutionType,
                null,
                null,
                InstitutionIdentityStatus.UNVERIFIED,
                createdAt,
                updatedAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullIdentityStatus() {
        assertThatThrownBy(() -> new Institution(
                institutionId,
                null,
                "Example Bank",
                institutionType,
                null,
                "DO",
                null,
                createdAt,
                updatedAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullCreatedAt() {
        assertThatThrownBy(() -> new Institution(
                institutionId,
                null,
                "Example Bank",
                institutionType,
                null,
                "DO",
                InstitutionIdentityStatus.UNVERIFIED,
                null,
                updatedAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullUpdatedAt() {
        assertThatThrownBy(() -> new Institution(
                institutionId,
                null,
                "Example Bank",
                institutionType,
                null,
                "DO",
                InstitutionIdentityStatus.UNVERIFIED,
                createdAt,
                null
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectUpdatedAtBeforeCreatedAt() {
        Instant earlierDate = Instant.parse("2025-12-31T10:00:00Z");

        assertThatThrownBy(() -> new Institution(
                institutionId,
                null,
                "Example Bank",
                institutionType,
                null,
                "DO",
                InstitutionIdentityStatus.UNVERIFIED,
                createdAt,
                earlierDate
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldAllowUpdatedAtEqualToCreatedAt() {
        Institution institution = new Institution(
                institutionId,
                null,
                "Example Bank",
                institutionType,
                null,
                "DO",
                InstitutionIdentityStatus.UNVERIFIED,
                createdAt,
                createdAt
        );

        assertThat(institution.getUpdatedAt()).isEqualTo(createdAt);
    }

    private Institution createValidInstitution() {
        return new Institution(
                institutionId,
                "Example Financial Corporation",
                "Example Bank",
                institutionType,
                "Example institution",
                "DO",
                InstitutionIdentityStatus.VERIFIED,
                createdAt,
                updatedAt
        );
    }
}