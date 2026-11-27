package org.oquira.participant.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents the explicitly selected published profile version for an environment.
 */
public final class ParticipantProfileEnvironmentSelection {

    private final UUID selectionId;
    private final UUID profileId;
    private final ParticipantEnvironment environment;
    private final UUID profileVersionId;

    public ParticipantProfileEnvironmentSelection(
            UUID selectionId,
            UUID profileId,
            ParticipantEnvironment environment,
            ParticipantProfileVersion selectedVersion
    ) {
        this.selectionId = Objects.requireNonNull(
                selectionId, "selectionId must not be null"
        );

        this.profileId = Objects.requireNonNull(
                profileId, "profileId must not be null"
        );

        this.environment = Objects.requireNonNull(
                environment, "environment must not be null"
        );

        Objects.requireNonNull(
                selectedVersion, "selectedVersion must not be null"
        );

        if (!profileId.equals(selectedVersion.getProfileId())) {
            throw new IllegalArgumentException(
                    "Selected version must belong to the specified profile"
            );
        }

        if (environment != selectedVersion.getEnvironment()) {
            throw new IllegalArgumentException(
                    "Selected version must belong to the specified environment"
            );
        }

        if (selectedVersion.getLifecycleStatus()
                != ParticipantProfileVersionStatus.PUBLISHED) {
            throw new IllegalArgumentException(
                    "Selected version must have PUBLISHED lifecycle status"
            );
        }

        this.profileVersionId = selectedVersion.getProfileVersionId();
    }

    public UUID getSelectionId() {
        return selectionId;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public ParticipantEnvironment getEnvironment() {
        return environment;
    }

    public UUID getProfileVersionId() {
        return profileVersionId;
    }
}
