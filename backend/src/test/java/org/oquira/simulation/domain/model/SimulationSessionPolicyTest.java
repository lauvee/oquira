package org.oquira.simulation.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class SimulationSessionPolicyTest {

    private SimulationSessionPolicy createPolicy() {
        return new SimulationSessionPolicy(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                1,
                EvaluationStatus.APPLIED,
                new EvaluationResult("{\"approved\":true}"),
                Instant.parse("2026-01-01T10:00:00Z")
        );
    }

    @Test
    void shouldCreateSessionPolicyEvaluation() {
        SimulationSessionPolicy policy = createPolicy();

        assertThat(policy.getEvaluationStatus())
                .isEqualTo(EvaluationStatus.APPLIED);

        assertThat(policy.getEvaluationOrder()).isEqualTo(1);

        assertThat(policy.getEvaluatedAt())
                .isEqualTo(Instant.parse("2026-01-01T10:00:00Z"));
    }

    @Test
    void shouldAcceptZeroEvaluationOrder() {
        SimulationSessionPolicy policy = new SimulationSessionPolicy(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                0,
                EvaluationStatus.SKIPPED,
                new EvaluationResult("{\"reason\":\"not_applicable\"}"),
                Instant.now()
        );

        assertThat(policy.getEvaluationOrder()).isZero();
    }

    @Test
    void shouldRejectNegativeEvaluationOrder() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new SimulationSessionPolicy(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        -1,
                        EvaluationStatus.APPLIED,
                        new EvaluationResult("{}"),
                        Instant.now()
                ));
    }

    @Test
    void shouldRejectNullEvaluationStatus() {
        assertThatNullPointerException()
                .isThrownBy(() -> new SimulationSessionPolicy(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        1,
                        null,
                        new EvaluationResult("{}"),
                        Instant.now()
                ));
    }

    @Test
    void shouldRejectNullEvaluationResult() {
        assertThatNullPointerException()
                .isThrownBy(() -> new SimulationSessionPolicy(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        1,
                        EvaluationStatus.APPLIED,
                        null,
                        Instant.now()
                ));
    }

    @Test
    void shouldRejectNullEvaluatedAt() {
        assertThatNullPointerException()
                .isThrownBy(() -> new SimulationSessionPolicy(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        1,
                        EvaluationStatus.APPLIED,
                        new EvaluationResult("{}"),
                        null
                ));
    }
}