package org.oquira.participant.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParticipantProfileTest {

    private final UUID profileId = UUID.randomUUID();
    private final UUID participantId = UUID.randomUUID();
    private final Instant createdAt = Instant.parse("2026-01-01T10:00:00Z");

    @Test
    void shouldCreateProfileWithValidData() {
        ParticipantProfile profile = createValidProfile();

        assertThat(profile.getProfileId()).isEqualTo(profileId);
        assertThat(profile.getParticipantId()).isEqualTo(participantId);
        assertThat(profile.getProfileCode()).isEqualTo("RETAIL_STANDARD");
        assertThat(profile.getDescription()).isEqualTo("Standard retail profile");
        assertThat(profile.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void shouldAllowNullDescription() {
        ParticipantProfile profile = new ParticipantProfile(
                profileId,
                participantId,
                "RETAIL_STANDARD",
                null,
                createdAt
        );

        assertThat(profile.getDescription()).isNull();
    }

    @Test
    void shouldRejectNullProfileId() {
        assertThatThrownBy(() -> new ParticipantProfile(
                null, participantId, "RETAIL_STANDARD", null, createdAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullParticipantId() {
        assertThatThrownBy(() -> new ParticipantProfile(
                profileId, null, "RETAIL_STANDARD", null, createdAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullProfileCode() {
        assertThatThrownBy(() -> new ParticipantProfile(
                profileId, participantId, null, null, createdAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankProfileCode() {
        assertThatThrownBy(() -> new ParticipantProfile(
                profileId, participantId, "   ", null, createdAt
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullCreatedAt() {
        assertThatThrownBy(() -> new ParticipantProfile(
                profileId, participantId, "RETAIL_STANDARD", null, null
        )).isInstanceOf(NullPointerException.class);
    }

    private ParticipantProfile createValidProfile() {
        return new ParticipantProfile(
                profileId,
                participantId,
                "RETAIL_STANDARD",
                "Standard retail profile",
                createdAt
        );
    }
}