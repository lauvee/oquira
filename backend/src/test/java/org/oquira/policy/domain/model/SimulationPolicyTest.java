package org.oquira.policy.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class SimulationPolicyTest {

    private PolicyType policyType() {
        return new PolicyType(
                "TRANSACTION_LIMIT",
                "Transaction limit",
                "Controls transaction limits"
        );
    }

    @Test
    void shouldCreatePolicy() {
        UUID id = UUID.randomUUID();

        SimulationPolicy policy = new SimulationPolicy(
                id,
                "DAILY_LIMIT",
                "Daily transaction limit",
                policyType(),
                "Limits daily transaction amounts",
                SimulationPolicyLifecycleStatus.ACTIVE
        );

        assertThat(policy.getPolicyId()).isEqualTo(id);
        assertThat(policy.getCode()).isEqualTo("DAILY_LIMIT");
        assertThat(policy.getLifecycleStatus())
                .isEqualTo(SimulationPolicyLifecycleStatus.ACTIVE);
    }

    @Test
    void shouldAllowNullDescription() {
        SimulationPolicy policy = new SimulationPolicy(
                UUID.randomUUID(),
                "DAILY_LIMIT",
                "Daily transaction limit",
                policyType(),
                null,
                SimulationPolicyLifecycleStatus.DRAFT
        );

        assertThat(policy.getDescription()).isNull();
    }

    @Test
    void shouldRejectBlankCode() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new SimulationPolicy(
                        UUID.randomUUID(),
                        " ",
                        "Name",
                        policyType(),
                        null,
                        SimulationPolicyLifecycleStatus.DRAFT
                ));
    }

    @Test
    void shouldRejectNullPolicyType() {
        assertThatNullPointerException()
                .isThrownBy(() -> new SimulationPolicy(
                        UUID.randomUUID(),
                        "DAILY_LIMIT",
                        "Name",
                        null,
                        null,
                        SimulationPolicyLifecycleStatus.DRAFT
                ));
    }
}