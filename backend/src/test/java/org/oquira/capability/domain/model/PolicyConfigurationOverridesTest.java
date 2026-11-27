package org.oquira.capability.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PolicyConfigurationOverridesTest {

    @Test
    void shouldCreateValidOverrides() {
        PolicyConfigurationOverrides overrides =
                new PolicyConfigurationOverrides("{\"maxAmount\":5000}");

        assertThat(overrides.getJson()).isEqualTo("{\"maxAmount\":5000}");
    }

    @Test
    void shouldPreserveOriginalJson() {
        String json = " { \"maxAmount\" : 5000 } ";

        PolicyConfigurationOverrides overrides =
                new PolicyConfigurationOverrides(json);

        assertThat(overrides.getJson()).isEqualTo(json);
    }

    @Test
    void shouldRejectNullJson() {
        assertThatNullPointerException()
                .isThrownBy(() -> new PolicyConfigurationOverrides(null));
    }

    @Test
    void shouldRejectBlankJson() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new PolicyConfigurationOverrides("  "));
    }
}