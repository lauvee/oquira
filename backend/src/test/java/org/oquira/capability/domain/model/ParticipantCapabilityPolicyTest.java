package org.oquira.capability.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ParticipantCapabilityPolicyTest {

    private final UUID assignmentId = UUID.randomUUID();
    private final UUID policyVersionId = UUID.randomUUID();

    @Test
    void shouldCreateValidAssociation() {
        ParticipantCapabilityPolicy association =
                new ParticipantCapabilityPolicy(
                        assignmentId,
                        policyVersionId,
                        1,
                        null
                );

        assertThat(association.getAssignmentId()).isEqualTo(assignmentId);
        assertThat(association.getPolicyVersionId()).isEqualTo(policyVersionId);
        assertThat(association.getEvaluationOrder()).isEqualTo(1);
        assertThat(association.getConfigurationOverrides()).isNull();
    }

    @Test
    void shouldAllowOptionalEvaluationOrder() {
        ParticipantCapabilityPolicy association =
                new ParticipantCapabilityPolicy(
                        assignmentId, policyVersionId, null, null
                );

        assertThat(association.getEvaluationOrder()).isNull();
    }

    @Test
    void shouldAllowZeroEvaluationOrder() {
        ParticipantCapabilityPolicy association =
                new ParticipantCapabilityPolicy(
                        assignmentId, policyVersionId, 0, null
                );

        assertThat(association.getEvaluationOrder()).isZero();
    }

    @Test
    void shouldRejectNegativeEvaluationOrder() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ParticipantCapabilityPolicy(
                        assignmentId, policyVersionId, -1, null
                ));
    }

    @Test
    void shouldAllowConfigurationOverrides() {
        PolicyConfigurationOverrides overrides =
                new PolicyConfigurationOverrides("{\"maxAmount\":5000}");

        ParticipantCapabilityPolicy association =
                new ParticipantCapabilityPolicy(
                        assignmentId, policyVersionId, 1, overrides
                );

        assertThat(association.getConfigurationOverrides())
                .isSameAs(overrides);
    }

    @Test
    void shouldRejectNullAssignmentId() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ParticipantCapabilityPolicy(
                        null, policyVersionId, null, null
                ));
    }

    @Test
    void shouldRejectNullPolicyVersionId() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ParticipantCapabilityPolicy(
                        assignmentId, null, null, null
                ));
    }
}