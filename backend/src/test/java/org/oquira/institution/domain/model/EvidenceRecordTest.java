package org.oquira.institution.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EvidenceRecordTest {

    @Test
    void shouldCreateEvidenceRecordWithValidData() {
        EvidenceRecord record = createValidRecord();

        assertThat(record.getEvidenceId())
                .isEqualTo(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        assertThat(record.getInstitutionId())
                .isEqualTo(UUID.fromString("22222222-2222-2222-2222-222222222222"));
        assertThat(record.getSourceName()).isEqualTo("Central Bank");
        assertThat(record.getSourceUrl())
                .isEqualTo("https://www.example.com/registry");
        assertThat(record.getSourceType()).isEqualTo(createSourceType());
        assertThat(record.getObservedAt())
                .isEqualTo(Instant.parse("2026-11-25T12:00:00Z"));
        assertThat(record.getPublishedAt())
                .isEqualTo(LocalDate.of(2026, 10, 15));
        assertThat(record.getClaimSupported())
                .isEqualTo("The institution is listed in the official registry");
        assertThat(record.getVerificationStatus())
                .isEqualTo(EvidenceVerificationStatus.VERIFIED);
        assertThat(record.getNotes()).isEqualTo("Reviewed against the published registry");
    }

    @Test
    void shouldAcceptNullPublishedAtAndNotes() {
        EvidenceRecord record = new EvidenceRecord(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Central Bank",
                "https://www.example.com/registry",
                createSourceType(),
                Instant.parse("2026-11-25T12:00:00Z"),
                null,
                "Institution is listed in the registry",
                EvidenceVerificationStatus.PENDING,
                null
        );

        assertThat(record.getPublishedAt()).isNull();
        assertThat(record.getNotes()).isNull();
    }

    @Test
    void shouldAcceptBlankNotes() {
        EvidenceRecord record = new EvidenceRecord(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Central Bank",
                "https://www.example.com/registry",
                createSourceType(),
                Instant.parse("2026-11-25T12:00:00Z"),
                null,
                "Institution is listed in the registry",
                EvidenceVerificationStatus.PENDING,
                "   "
        );

        assertThat(record.getNotes()).isEqualTo("   ");
    }

    @Test
    void shouldAcceptHttpSourceUrl() {
        EvidenceRecord record = createRecordWithUrl(
                "http://www.example.com/registry"
        );

        assertThat(record.getSourceUrl())
                .isEqualTo("http://www.example.com/registry");
    }

    @Test
    void shouldAcceptHttpsSourceUrl() {
        EvidenceRecord record = createRecordWithUrl(
                "https://www.example.com/registry"
        );

        assertThat(record.getSourceUrl())
                .isEqualTo("https://www.example.com/registry");
    }

    @Test
    void shouldRejectNullEvidenceId() {
        assertThatThrownBy(() -> new EvidenceRecord(
                null,
                UUID.randomUUID(),
                "Central Bank",
                "https://www.example.com",
                createSourceType(),
                Instant.now(),
                null,
                "Supported claim",
                EvidenceVerificationStatus.PENDING,
                null
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullInstitutionId() {
        assertThatThrownBy(() -> new EvidenceRecord(
                UUID.randomUUID(),
                null,
                "Central Bank",
                "https://www.example.com",
                createSourceType(),
                Instant.now(),
                null,
                "Supported claim",
                EvidenceVerificationStatus.PENDING,
                null
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullSourceName() {
        assertThatThrownBy(() -> new EvidenceRecord(
                UUID.randomUUID(),
                UUID.randomUUID(),
                null,
                "https://www.example.com",
                createSourceType(),
                Instant.now(),
                null,
                "Supported claim",
                EvidenceVerificationStatus.PENDING,
                null
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankSourceName() {
        assertThatThrownBy(() -> new EvidenceRecord(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "   ",
                "https://www.example.com",
                createSourceType(),
                Instant.now(),
                null,
                "Supported claim",
                EvidenceVerificationStatus.PENDING,
                null
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullSourceUrl() {
        assertThatThrownBy(() -> createRecordWithUrl(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankSourceUrl() {
        assertThatThrownBy(() -> createRecordWithUrl("   "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectRelativeSourceUrl() {
        assertThatThrownBy(() -> createRecordWithUrl(
                "/institution/registry"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectUnsupportedSourceUrlScheme() {
        assertThatThrownBy(() -> createRecordWithUrl(
                "ftp://www.example.com/registry"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectSourceUrlWithoutHost() {
        assertThatThrownBy(() -> createRecordWithUrl(
                "https:///registry"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectMalformedSourceUrl() {
        assertThatThrownBy(() -> createRecordWithUrl(
                "https://example.com/invalid url"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullSourceType() {
        assertThatThrownBy(() -> new EvidenceRecord(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Central Bank",
                "https://www.example.com",
                null,
                Instant.now(),
                null,
                "Supported claim",
                EvidenceVerificationStatus.PENDING,
                null
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullObservedAt() {
        assertThatThrownBy(() -> new EvidenceRecord(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Central Bank",
                "https://www.example.com",
                createSourceType(),
                null,
                null,
                "Supported claim",
                EvidenceVerificationStatus.PENDING,
                null
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullClaimSupported() {
        assertThatThrownBy(() -> new EvidenceRecord(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Central Bank",
                "https://www.example.com",
                createSourceType(),
                Instant.now(),
                null,
                null,
                EvidenceVerificationStatus.PENDING,
                null
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankClaimSupported() {
        assertThatThrownBy(() -> new EvidenceRecord(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Central Bank",
                "https://www.example.com",
                createSourceType(),
                Instant.now(),
                null,
                "   ",
                EvidenceVerificationStatus.PENDING,
                null
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullVerificationStatus() {
        assertThatThrownBy(() -> new EvidenceRecord(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Central Bank",
                "https://www.example.com",
                createSourceType(),
                Instant.now(),
                null,
                "Supported claim",
                null,
                null
        )).isInstanceOf(NullPointerException.class);
    }

    private EvidenceRecord createValidRecord() {
        return new EvidenceRecord(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "Central Bank",
                "https://www.example.com/registry",
                createSourceType(),
                Instant.parse("2026-11-25T12:00:00Z"),
                LocalDate.of(2026, 10, 15),
                "The institution is listed in the official registry",
                EvidenceVerificationStatus.VERIFIED,
                "Reviewed against the published registry"
        );
    }

    private EvidenceRecord createRecordWithUrl(String sourceUrl) {
        return new EvidenceRecord(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Central Bank",
                sourceUrl,
                createSourceType(),
                Instant.parse("2026-11-25T12:00:00Z"),
                null,
                "Institution is listed in the registry",
                EvidenceVerificationStatus.PENDING,
                null
        );
    }

    private EvidenceSourceType createSourceType() {
        return new EvidenceSourceType(
                "OFFICIAL_REGISTRY",
                "Official Registry",
                "An official institutional registry"
        );
    }
}