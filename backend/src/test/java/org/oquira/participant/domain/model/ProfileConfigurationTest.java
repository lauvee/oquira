package org.oquira.participant.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileConfigurationTest {

    @Test
    void shouldPreserveJsonContent() {
        String json = "{\"currency\":\"DOP\",\"enabled\":true}";

        ProfileConfiguration configuration = new ProfileConfiguration(json);

        assertThat(configuration.getJson()).isEqualTo(json);
    }

    @Test
    void shouldRejectNullJson() {
        assertThatThrownBy(() -> new ProfileConfiguration(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankJson() {
        assertThatThrownBy(() -> new ProfileConfiguration("   "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotPerformJsonSyntaxValidationInDomain() {
        ProfileConfiguration configuration =
                new ProfileConfiguration("not-validated-json");

        assertThat(configuration.getJson())
                .isEqualTo("not-validated-json");
    }
}