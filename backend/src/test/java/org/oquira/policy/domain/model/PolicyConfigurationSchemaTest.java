package org.oquira.policy.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PolicyConfigurationSchemaTest {

    @Test
    void shouldAcceptNonBlankSchema() {
        PolicyConfigurationSchema schema =
                new PolicyConfigurationSchema("{\"type\":\"object\"}");

        assertThat(schema.getJson()).isEqualTo("{\"type\":\"object\"}");
    }

    @Test
    void shouldRejectNullSchema() {
        assertThatNullPointerException()
                .isThrownBy(() -> new PolicyConfigurationSchema(null));
    }

    @Test
    void shouldRejectBlankSchema() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new PolicyConfigurationSchema(" "));
    }
}