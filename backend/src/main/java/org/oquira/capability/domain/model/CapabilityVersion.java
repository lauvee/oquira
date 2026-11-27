package org.oquira.capability.domain.model;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Represents an immutable version of a capability definition and contract.
 */
public final class CapabilityVersion {

    private final UUID capabilityVersionId;
    private final UUID capabilityId;
    private final SemanticVersion versionNumber;
    private final CapabilityInputSchema inputSchema;
    private final CapabilityOutputSchema outputSchema;
    private final ExecutionMode executionMode;
    private final Set<String> requiredPermissions;
    private final CapabilityVersionLifecycleStatus lifecycleStatus;
    private final Instant publishedAt;

    public CapabilityVersion(
            UUID capabilityVersionId,
            UUID capabilityId,
            SemanticVersion versionNumber,
            CapabilityInputSchema inputSchema,
            CapabilityOutputSchema outputSchema,
            ExecutionMode executionMode,
            Set<String> requiredPermissions,
            CapabilityVersionLifecycleStatus lifecycleStatus,
            Instant publishedAt
    ) {
        this.capabilityVersionId = Objects.requireNonNull(
                capabilityVersionId,
                "capabilityVersionId must not be null"
        );

        this.capabilityId = Objects.requireNonNull(
                capabilityId,
                "capabilityId must not be null"
        );

        this.versionNumber = Objects.requireNonNull(
                versionNumber,
                "versionNumber must not be null"
        );

        this.inputSchema = Objects.requireNonNull(
                inputSchema,
                "inputSchema must not be null"
        );

        this.outputSchema = Objects.requireNonNull(
                outputSchema,
                "outputSchema must not be null"
        );

        this.executionMode = Objects.requireNonNull(
                executionMode,
                "executionMode must not be null"
        );

        this.requiredPermissions = immutablePermissions(requiredPermissions);

        this.lifecycleStatus = Objects.requireNonNull(
                lifecycleStatus,
                "lifecycleStatus must not be null"
        );

        this.publishedAt = publishedAt;

        validatePublicationDate();
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

    private void validatePublicationDate() {
        boolean publicationRequired =
                lifecycleStatus == CapabilityVersionLifecycleStatus.ACTIVE
                || lifecycleStatus == CapabilityVersionLifecycleStatus.DEPRECATED
                || lifecycleStatus == CapabilityVersionLifecycleStatus.RETIRED;

        if (publicationRequired && publishedAt == null) {
            throw new IllegalArgumentException(
                    "publishedAt must not be null for ACTIVE, DEPRECATED, or RETIRED versions"
            );
        }

        boolean publicationForbidden =
                lifecycleStatus == CapabilityVersionLifecycleStatus.DRAFT
                || lifecycleStatus == CapabilityVersionLifecycleStatus.PROPOSED;

        if (publicationForbidden && publishedAt != null) {
            throw new IllegalArgumentException(
                    "publishedAt must be null for DRAFT or PROPOSED versions"
            );
        }
    }

    public UUID getCapabilityVersionId() {
        return capabilityVersionId;
    }

    public UUID getCapabilityId() {
        return capabilityId;
    }

    public SemanticVersion getVersionNumber() {
        return versionNumber;
    }

    public CapabilityInputSchema getInputSchema() {
        return inputSchema;
    }

    public CapabilityOutputSchema getOutputSchema() {
        return outputSchema;
    }

    public ExecutionMode getExecutionMode() {
        return executionMode;
    }

    public Set<String> getRequiredPermissions() {
        return requiredPermissions;
    }

    public CapabilityVersionLifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }
}