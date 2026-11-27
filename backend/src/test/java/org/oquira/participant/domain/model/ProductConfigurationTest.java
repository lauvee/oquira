package org.oquira.participant.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ProductConfigurationTest {

    @Test
    void shouldCreateValidConfiguration() {
        ProductConfiguration configuration =
                new ProductConfiguration("{\"currency\":\"DOP\"}");

        assertThat(configuration.getJson())
                .isEqualTo("{\"currency\":\"DOP\"}");
    }

    @Test
    void shouldPreserveOriginalJsonContent() {
        String json = " { \"currency\" : \"DOP\" } ";

        ProductConfiguration configuration =
                new ProductConfiguration(json);

        assertThat(configuration.getJson()).isEqualTo(json);
    }

    @Test
    void shouldRejectNullJson() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ProductConfiguration(null));
    }

    @Test
    void shouldRejectBlankJson() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ProductConfiguration("  "));
    }
}