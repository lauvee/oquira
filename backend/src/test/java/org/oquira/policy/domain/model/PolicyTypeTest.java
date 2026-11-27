package org.oquira.policy.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PolicyTypeTest {

    @Test
    void shouldCreatePolicyType() {
        PolicyType type = new PolicyType(
                "TRANSACTION_LIMIT",
                "Transaction limit",
                "Controls transaction limits"
        );

        assertThat(type.getCode()).isEqualTo("TRANSACTION_LIMIT");
        assertThat(type.getName()).isEqualTo("Transaction limit");
    }

    @Test
    void shouldRejectInvalidCode() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new PolicyType(
                        "transaction-limit", "Name", "Description"
                ));
    }

    @Test
    void shouldRejectBlankName() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new PolicyType(
                        "TRANSACTION_LIMIT", " ", "Description"
                ));
    }

    @Test
    void shouldCompareByCode() {
        PolicyType first = new PolicyType(
                "TRANSACTION_LIMIT", "First", "Description one"
        );
        PolicyType second = new PolicyType(
                "TRANSACTION_LIMIT", "Second", "Description two"
        );

        assertThat(first).isEqualTo(second);
        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }
}