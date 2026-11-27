package org.oquira.participant.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParticipantProfileVersionStatusTest {

    @Test
    void shouldDefineSupportedLifecycleStatuses() {
        assertThat(ParticipantProfileVersionStatus.values())
                .containsExactly(
                        ParticipantProfileVersionStatus.DRAFT,
                        ParticipantProfileVersionStatus.PUBLISHED,
                        ParticipantProfileVersionStatus.DEPRECATED,
                        ParticipantProfileVersionStatus.WITHDRAWN
                );
    }
}