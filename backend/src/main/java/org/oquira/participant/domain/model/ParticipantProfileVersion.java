package org.oquira.participant.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents an immutable version of a participant profile configuration.
 */
public final class ParticipantProfileVersion {

    private final UUID profileVersionId;
    private final UUID profileId;
    private final int versionNumber;
    private final ParticipantEnvironment environment;
    private final ProfileConfiguration configuration;
    private final ParticipantProfileVersionStatus lifecycleStatus;
    private final Instant createdAt;
    private final Instant publishedAt;
    private final String createdBy;
    private final String changeSummary;

    public ParticipantProfileVersion(
            UUID profileVersionId,
            UUID profileId,
            int versionNumber,
            ParticipantEnvironment environment,
            ProfileConfiguration configuration,
            ParticipantProfileVersionStatus lifecycleStatus,
            Instant createdAt,
            Instant publishedAt,
            String createdBy,
            String changeSummary
    ) {
        this.profileVersionId = Objects.requireNonNull(
                profileVersionId, "profileVersionId must not be null"
        );

        this.profileId = Objects.requireNonNull(
                profileId, "profileId must not be null"
        );

        if (versionNumber <= 0) {
            throw new IllegalArgumentException(
                    "versionNumber must be greater than zero"
            );
        }
        this.versionNumber = versionNumber;

        this.environment = Objects.requireNonNull(
                environment, "environment must not be null"
        );

        this.configuration = Objects.requireNonNull(
                configuration, "configuration must not be null"
        );

        this.lifecycleStatus = Objects.requireNonNull(
                lifecycleStatus, "lifecycleStatus must not be null"
        );

        this.createdAt = Objects.requireNonNull(
                createdAt, "createdAt must not be null"
        );

        this.publishedAt = publishedAt;

        this.createdBy = requireNonBlank(createdBy, "createdBy");
        this.changeSummary = requireNonBlank(changeSummary, "changeSummary");

        validatePublicationDates();
    }

    private void validatePublicationDates() {
        boolean isDraft = lifecycleStatus == ParticipantProfileVersionStatus.DRAFT;

        if (isDraft && publishedAt != null) {
            throw new IllegalArgumentException(
                    "publishedAt must be null for DRAFT versions"
            );
        }

        if (!isDraft && publishedAt == null) {
            throw new IllegalArgumentException(
                    "publishedAt must not be null for non-DRAFT versions"
            );
        }

        if (publishedAt != null && publishedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException(
                    "publishedAt must not be before createdAt"
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

    public UUID getProfileVersionId() {
        return profileVersionId;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public ParticipantEnvironment getEnvironment() {
        return environment;
    }

    public ProfileConfiguration getConfiguration() {
        return configuration;
    }

    public ParticipantProfileVersionStatus getLifecycleStatus() {
        return lifecycleStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getChangeSummary() {
        return changeSummary;
    }
}
