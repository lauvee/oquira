package org.oquira.policy.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PolicyConfigurationTest {

    @Test
    void shouldAcceptNonBlankJson() {
        PolicyConfiguration configuration =
                new PolicyConfiguration("{\"limit\":100}");

        assertThat(configuration.getJson()).isEqualTo("{\"limit\":100}");
    }

    @Test
    void shouldRejectNullJson() {
        assertThatNullPointerException()
                .isThrownBy(() -> new PolicyConfiguration(null));
    }

    @Test
    void shouldRejectBlankJson() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new PolicyConfiguration(" "));
    }
}