package org.oquira.policy.domain.model;

import org.junit.jupiter.api.Test;
import org.oquira.capability.domain.model.SemanticVersion;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class SimulationPolicyVersionTest {

    private SimulationPolicyVersion createVersion(
            SimulationPolicyVersionLifecycleStatus status,
            Instant publishedAt
    ) {
        return new SimulationPolicyVersion(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new SemanticVersion(1, 0, 0),
                new PolicyConfigurationSchema("{\"type\":\"object\"}"),
                new PolicyConfiguration("{\"limit\":100}"),
                status,
                publishedAt
        );
    }

    @Test
    void shouldCreateDraftWithoutPublicationDate() {
        SimulationPolicyVersion version = createVersion(
                SimulationPolicyVersionLifecycleStatus.DRAFT,
                null
        );

        assertThat(version.getPublishedAt()).isNull();
        assertThat(version.getLifecycleStatus())
                .isEqualTo(SimulationPolicyVersionLifecycleStatus.DRAFT);
    }

    @Test
    void shouldCreatePublishedVersionWithPublicationDate() {
        Instant publishedAt = Instant.parse("2026-01-01T10:00:00Z");

        SimulationPolicyVersion version = createVersion(
                SimulationPolicyVersionLifecycleStatus.PUBLISHED,
                publishedAt
        );

        assertThat(version.getPublishedAt()).isEqualTo(publishedAt);
    }

    @Test
    void shouldRejectDraftWithPublicationDate() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> createVersion(
                        SimulationPolicyVersionLifecycleStatus.DRAFT,
                        Instant.now()
                ));
    }

    @Test
    void shouldRejectPublishedVersionWithoutPublicationDate() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> createVersion(
                        SimulationPolicyVersionLifecycleStatus.PUBLISHED,
                        null
                ));
    }

    @Test
    void shouldRejectNullConfiguration() {
        assertThatNullPointerException()
                .isThrownBy(() -> new SimulationPolicyVersion(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        new SemanticVersion(1, 0, 0),
                        new PolicyConfigurationSchema("{}"),
                        null,
                        SimulationPolicyVersionLifecycleStatus.DRAFT,
                        null
                ));
    }
}