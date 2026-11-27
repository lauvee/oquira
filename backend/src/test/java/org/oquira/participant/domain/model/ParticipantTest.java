package org.oquira.participant.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParticipantTest {

    private final UUID participantId = UUID.randomUUID();
    private final UUID institutionId = UUID.randomUUID();

    private final Instant createdAt = Instant.parse("2026-01-01T10:00:00Z");
    private final Instant updatedAt = Instant.parse("2026-01-02T10:00:00Z");

    @Test
    void shouldCreateParticipantWithValidData() {
        Participant participant = createValidParticipant();

        assertThat(participant.getParticipantId()).isEqualTo(participantId);
        assertThat(participant.getInstitutionId()).isEqualTo(institutionId);
        assertThat(participant.getCode()).isEqualTo("BANK-001");
        assertThat(participant.getDisplayName()).isEqualTo("Example Bank");
        assertThat(participant.getRepresentationType())
                .isEqualTo(ParticipantRepresentationType.VERIFIED_REAL);
        assertThat(participant.getLifecycleStatus())
                .isEqualTo(ParticipantLifecycleStatus.DRAFT);
        assertThat(participant.getDescription())
                .isEqualTo("Example participant");
        assertThat(participant.getCreatedAt()).isEqualTo(createdAt);
        assertThat(participant.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldAllowParticipantWithoutInstitution() {
        Participant participant = new Participant(
                participantId,
                null,
                "SYNTH-001",
                "Synthetic Bank",
                ParticipantRepresentationType.SYNTHETIC_ARCHETYPE,
                ParticipantLifecycleStatus.DRAFT,
                "Synthetic participant",
                createdAt,
                updatedAt
        );

        assertThat(participant.getInstitutionId()).isNull();
        assertThat(participant.getRepresentationType())
                .isEqualTo(ParticipantRepresentationType.SYNTHETIC_ARCHETYPE);
    }

    @Test
    void shouldRejectNullParticipantId() {
        assertThatThrownBy(() -> new Participant(
                null,
                institutionId,
                "BANK-001",
                "Example Bank",
                ParticipantRepresentationType.VERIFIED_REAL,
                ParticipantLifecycleStatus.DRAFT,
                null,
                createdAt,
                updatedAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankCode() {
        assertThatThrownBy(() -> new Participant(
                participantId,
                institutionId,
                "   ",
                "Example Bank",
                ParticipantRepresentationType.VERIFIED_REAL,
                ParticipantLifecycleStatus.DRAFT,
                null,
                createdAt,
                updatedAt
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectBlankDisplayName() {
        assertThatThrownBy(() -> new Participant(
                participantId,
                institutionId,
                "BANK-001",
                "   ",
                ParticipantRepresentationType.VERIFIED_REAL,
                ParticipantLifecycleStatus.DRAFT,
                null,
                createdAt,
                updatedAt
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullRepresentationType() {
        assertThatThrownBy(() -> new Participant(
                participantId,
                institutionId,
                "BANK-001",
                "Example Bank",
                null,
                ParticipantLifecycleStatus.DRAFT,
                null,
                createdAt,
                updatedAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullLifecycleStatus() {
        assertThatThrownBy(() -> new Participant(
                participantId,
                institutionId,
                "BANK-001",
                "Example Bank",
                ParticipantRepresentationType.VERIFIED_REAL,
                null,
                null,
                createdAt,
                updatedAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectUpdatedAtBeforeCreatedAt() {
        Instant earlierDate = Instant.parse("2025-12-31T10:00:00Z");

        assertThatThrownBy(() -> new Participant(
                participantId,
                institutionId,
                "BANK-001",
                "Example Bank",
                ParticipantRepresentationType.VERIFIED_REAL,
                ParticipantLifecycleStatus.DRAFT,
                null,
                createdAt,
                earlierDate
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldAllowUpdatedAtEqualToCreatedAt() {
        Participant participant = new Participant(
                participantId,
                institutionId,
                "BANK-001",
                "Example Bank",
                ParticipantRepresentationType.VERIFIED_REAL,
                ParticipantLifecycleStatus.DRAFT,
                null,
                createdAt,
                createdAt
        );

        assertThat(participant.getUpdatedAt()).isEqualTo(createdAt);
    }

    private Participant createValidParticipant() {
        return new Participant(
                participantId,
                institutionId,
                "BANK-001",
                "Example Bank",
                ParticipantRepresentationType.VERIFIED_REAL,
                ParticipantLifecycleStatus.DRAFT,
                "Example participant",
                createdAt,
                updatedAt
        );
    }
}