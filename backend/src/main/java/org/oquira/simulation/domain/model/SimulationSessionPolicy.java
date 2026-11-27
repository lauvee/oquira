package org.oquira.simulation.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Records a policy version actually evaluated during a simulation session.
 */
public final class SimulationSessionPolicy {

    private final UUID sessionPolicyId;
    private final UUID sessionId;
    private final UUID assignmentId;
    private final UUID policyVersionId;
    private final int evaluationOrder;
    private final EvaluationStatus evaluationStatus;
    private final EvaluationResult evaluationResult;
    private final Instant evaluatedAt;

    public SimulationSessionPolicy(
            UUID sessionPolicyId,
            UUID sessionId,
            UUID assignmentId,
            UUID policyVersionId,
            int evaluationOrder,
            EvaluationStatus evaluationStatus,
            EvaluationResult evaluationResult,
            Instant evaluatedAt
    ) {
        this.sessionPolicyId = Objects.requireNonNull(
                sessionPolicyId, "sessionPolicyId must not be null"
        );

        this.sessionId = Objects.requireNonNull(
                sessionId, "sessionId must not be null"
        );

        this.assignmentId = Objects.requireNonNull(
                assignmentId, "assignmentId must not be null"
        );

        this.policyVersionId = Objects.requireNonNull(
                policyVersionId, "policyVersionId must not be null"
        );

        if (evaluationOrder < 0) {
            throw new IllegalArgumentException(
                    "evaluationOrder must not be negative"
            );
        }

        this.evaluationOrder = evaluationOrder;

        this.evaluationStatus = Objects.requireNonNull(
                evaluationStatus, "evaluationStatus must not be null"
        );

        this.evaluationResult = Objects.requireNonNull(
                evaluationResult, "evaluationResult must not be null"
        );

        this.evaluatedAt = Objects.requireNonNull(
                evaluatedAt, "evaluatedAt must not be null"
        );
    }

    public UUID getSessionPolicyId() {
        return sessionPolicyId;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public UUID getAssignmentId() {
        return assignmentId;
    }

    public UUID getPolicyVersionId() {
        return policyVersionId;
    }

    public int getEvaluationOrder() {
        return evaluationOrder;
    }

    public EvaluationStatus getEvaluationStatus() {
        return evaluationStatus;
    }

    public EvaluationResult getEvaluationResult() {
        return evaluationResult;
    }

    public Instant getEvaluatedAt() {
        return evaluatedAt;
    }
}