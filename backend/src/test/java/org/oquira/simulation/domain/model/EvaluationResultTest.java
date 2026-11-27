package org.oquira.simulation.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EvaluationResultTest {

    @Test
    void shouldAcceptNonBlankJson() {
        EvaluationResult result =
                new EvaluationResult("{\"approved\":true}");

        assertThat(result.getJson()).isEqualTo("{\"approved\":true}");
    }

    @Test
    void shouldRejectNullJson() {
        assertThatNullPointerException()
                .isThrownBy(() -> new EvaluationResult(null));
    }

    @Test
    void shouldRejectBlankJson() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new EvaluationResult(" "));
    }
}