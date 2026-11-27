package org.oquira.capability.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Associates a participant capability assignment with a product
 * available in the same participant profile version.
 */
public final class ParticipantCapabilityProduct {

    private final UUID assignmentId;
    private final UUID profileProductId;

    public ParticipantCapabilityProduct(
            UUID assignmentId,
            UUID profileProductId
    ) {
        this.assignmentId = Objects.requireNonNull(
                assignmentId, "assignmentId must not be null"
        );

        this.profileProductId = Objects.requireNonNull(
                profileProductId, "profileProductId must not be null"
        );
    }

    public UUID getAssignmentId() {
        return assignmentId;
    }

    public UUID getProfileProductId() {
        return profileProductId;
    }
}