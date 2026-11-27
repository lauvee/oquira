package org.oquira.capability.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Associates a capability assignment with an exact policy version.
 */
public final class ParticipantCapabilityPolicy {

    private final UUID assignmentId;
    private final UUID policyVersionId;
    private final Integer evaluationOrder;
    private final PolicyConfigurationOverrides configurationOverrides;

    public ParticipantCapabilityPolicy(
            UUID assignmentId,
            UUID policyVersionId,
            Integer evaluationOrder,
            PolicyConfigurationOverrides configurationOverrides
    ) {
        this.assignmentId = Objects.requireNonNull(
                assignmentId, "assignmentId must not be null"
        );

        this.policyVersionId = Objects.requireNonNull(
                policyVersionId, "policyVersionId must not be null"
        );

        if (evaluationOrder != null && evaluationOrder < 0) {
            throw new IllegalArgumentException(
                    "evaluationOrder must not be negative"
            );
        }

        this.evaluationOrder = evaluationOrder;
        this.configurationOverrides = configurationOverrides;
    }

    public UUID getAssignmentId() {
        return assignmentId;
    }

    public UUID getPolicyVersionId() {
        return policyVersionId;
    }

    public Integer getEvaluationOrder() {
        return evaluationOrder;
    }

    public PolicyConfigurationOverrides getConfigurationOverrides() {
        return configurationOverrides;
    }
}