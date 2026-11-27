package org.oquira.capability.domain.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Represents the assignment of a capability version to a participant profile version.
 */
public final class ParticipantCapability {

    private final UUID assignmentId;
    private final UUID capabilityVersionId;
    private final UUID profileVersionId;
    private final ParticipantCapabilityAvailabilityStatus availabilityStatus;
    private final CapabilityConfiguration configuration;
    private final Set<String> requiredPermissions;

    public ParticipantCapability(
            UUID assignmentId,
            UUID capabilityVersionId,
            UUID profileVersionId,
            ParticipantCapabilityAvailabilityStatus availabilityStatus,
            CapabilityConfiguration configuration,
            Set<String> requiredPermissions
    ) {
        this.assignmentId = Objects.requireNonNull(
                assignmentId,
                "assignmentId must not be null"
        );

        this.capabilityVersionId = Objects.requireNonNull(
                capabilityVersionId,
                "capabilityVersionId must not be null"
        );

        this.profileVersionId = Objects.requireNonNull(
                profileVersionId,
                "profileVersionId must not be null"
        );

        this.availabilityStatus = Objects.requireNonNull(
                availabilityStatus,
                "availabilityStatus must not be null"
        );

        this.configuration = Objects.requireNonNull(
                configuration,
                "configuration must not be null"
        );

        this.requiredPermissions = immutablePermissions(requiredPermissions);
    }

    private static Set<String> immutablePermissions(Set<String> permissions) {
        Objects.requireNonNull(
                permissions,
                "requiredPermissions must not be null"
        );

        Set<String> validatedPermissions = new LinkedHashSet<>();

        for (String permission : permissions) {
            Objects.requireNonNull(
                    permission,
                    "requiredPermissions must not contain null values"
            );

            if (permission.isBlank()) {
                throw new IllegalArgumentException(
                        "requiredPermissions must not contain blank values"
                );
            }

            validatedPermissions.add(permission);
        }

        return Collections.unmodifiableSet(validatedPermissions);
    }

    public UUID getAssignmentId() {
        return assignmentId;
    }

    public UUID getCapabilityVersionId() {
        return capabilityVersionId;
    }

    public UUID getProfileVersionId() {
        return profileVersionId;
    }

    public ParticipantCapabilityAvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public CapabilityConfiguration getConfiguration() {
        return configuration;
    }

    public Set<String> getRequiredPermissions() {
        return requiredPermissions;
    }
}