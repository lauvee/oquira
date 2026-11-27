package org.oquira.capability.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CapabilityJsonValueObjectTest {

    @Test
    void shouldPreserveInputSchemaJson() {
        String json = "{\"accountId\":\"string\"}";

        assertThat(new CapabilityInputSchema(json).getJson())
                .isEqualTo(json);
    }

    @Test
    void shouldPreserveOutputSchemaJson() {
        String json = "{\"balance\":\"number\"}";

        assertThat(new CapabilityOutputSchema(json).getJson())
                .isEqualTo(json);
    }

    @Test
    void shouldPreserveConfigurationJson() {
        String json = "{\"currency\":\"DOP\"}";

        assertThat(new CapabilityConfiguration(json).getJson())
                .isEqualTo(json);
    }

    @Test
    void shouldRejectNullJsonInAllValueObjects() {
        assertThatThrownBy(() -> new CapabilityInputSchema(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> new CapabilityOutputSchema(null))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> new CapabilityConfiguration(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankJsonInAllValueObjects() {
        assertThatThrownBy(() -> new CapabilityInputSchema("   "))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new CapabilityOutputSchema("   "))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new CapabilityConfiguration("   "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotValidateJsonSyntaxInDomain() {
        assertThat(new CapabilityInputSchema("not-json").getJson())
                .isEqualTo("not-json");

        assertThat(new CapabilityOutputSchema("not-json").getJson())
                .isEqualTo("not-json");

        assertThat(new CapabilityConfiguration("not-json").getJson())
                .isEqualTo("not-json");
    }
}