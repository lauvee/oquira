package org.oquira.participant.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParticipantEnvironmentTest {

    @Test
    void shouldDefineSupportedEnvironments() {
        assertThat(ParticipantEnvironment.values())
                .containsExactly(
                        ParticipantEnvironment.DEVELOPMENT,
                        ParticipantEnvironment.TEST,
                        ParticipantEnvironment.DEMO
                );
    }
}