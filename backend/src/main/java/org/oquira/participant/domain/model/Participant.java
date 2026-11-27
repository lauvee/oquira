package org.oquira.participant.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents an actor configured in Oquira.
 */
public final class Participant {

    private final UUID participantId;
    private final UUID institutionId;
    private final String code;
    private final String displayName;
    private final ParticipantRepresentationType representationType;
    private final ParticipantLifecycleStatus lifecycleStatus;
    private final String description;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Participant(
            UUID participantId,
            UUID institutionId,
            String code,
            String displayName,
            ParticipantRepresentationType representationType,
            ParticipantLifecycleStatus lifecycleStatus,
            String description,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.participantId = Objects.requireNonNull(
                participantId, "participantId must not be null"
        );

        this.institutionId = institutionId;

        this.code = requireNonBlank(code, "code");
        this.displayName = requireNonBlank(displayName, "displayName");

        this.representationType = Objects.requireNonNull(
                representationType, "representationType must not be null"
        );

        this.lifecycleStatus = Objects.requireNonNull(
                lifecycleStatus, "lifecycleStatus must not be null"
        );

        this.description = description;

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

    public UUID getParticipantId() {
        return participantId;
    }

    public UUID getInstitutionId() {
        return institutionId;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ParticipantRepresentationType getRepresentationType() {
        return representationType;
    }

    public ParticipantLifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}