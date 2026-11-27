package org.oquira.participant.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents the stable configuration identity of a participant profile.
 */
public final class ParticipantProfile {

    private final UUID profileId;
    private final UUID participantId;
    private final String profileCode;
    private final String description;
    private final Instant createdAt;

    public ParticipantProfile(
            UUID profileId,
            UUID participantId,
            String profileCode,
            String description,
            Instant createdAt
    ) {
        this.profileId = Objects.requireNonNull(
                profileId, "profileId must not be null"
        );

        this.participantId = Objects.requireNonNull(
                participantId, "participantId must not be null"
        );

        this.profileCode = requireNonBlank(profileCode, "profileCode");
        this.description = description;

        this.createdAt = Objects.requireNonNull(
                createdAt, "createdAt must not be null"
        );
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

    public UUID getProfileId() {
        return profileId;
    }

    public UUID getParticipantId() {
        return participantId;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
