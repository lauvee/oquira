package org.oquira.policy.domain.model;

import org.oquira.capability.domain.model.SemanticVersion;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents an immutable version of a simulation policy.
 */
public final class SimulationPolicyVersion {

    private final UUID policyVersionId;
    private final UUID policyId;
    private final SemanticVersion versionNumber;
    private final PolicyConfigurationSchema configurationSchema;
    private final PolicyConfiguration configuration;
    private final SimulationPolicyVersionLifecycleStatus lifecycleStatus;
    private final Instant publishedAt;

    public SimulationPolicyVersion(
            UUID policyVersionId,
            UUID policyId,
            SemanticVersion versionNumber,
            PolicyConfigurationSchema configurationSchema,
            PolicyConfiguration configuration,
            SimulationPolicyVersionLifecycleStatus lifecycleStatus,
            Instant publishedAt
    ) {
        this.policyVersionId = Objects.requireNonNull(
                policyVersionId, "policyVersionId must not be null"
        );

        this.policyId = Objects.requireNonNull(
                policyId, "policyId must not be null"
        );

        this.versionNumber = Objects.requireNonNull(
                versionNumber, "versionNumber must not be null"
        );

        this.configurationSchema = Objects.requireNonNull(
                configurationSchema, "configurationSchema must not be null"
        );

        this.configuration = Objects.requireNonNull(
                configuration, "configuration must not be null"
        );

        this.lifecycleStatus = Objects.requireNonNull(
                lifecycleStatus, "lifecycleStatus must not be null"
        );

        this.publishedAt = publishedAt;

        validatePublicationDate();
    }

    private void validatePublicationDate() {
        boolean isDraft =
                lifecycleStatus == SimulationPolicyVersionLifecycleStatus.DRAFT;

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
    }

    public UUID getPolicyVersionId() {
        return policyVersionId;
    }

    public UUID getPolicyId() {
        return policyId;
    }

    public SemanticVersion getVersionNumber() {
        return versionNumber;
    }

    public PolicyConfigurationSchema getConfigurationSchema() {
        return configurationSchema;
    }

    public PolicyConfiguration getConfiguration() {
        return configuration;
    }

    public SimulationPolicyVersionLifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }
}