package org.oquira.capability.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class ParticipantCapabilityProductTest {

    private final UUID assignmentId = UUID.randomUUID();
    private final UUID profileProductId = UUID.randomUUID();

    @Test
    void shouldCreateValidAssociation() {
        ParticipantCapabilityProduct association =
                new ParticipantCapabilityProduct(
                        assignmentId,
                        profileProductId
                );

        assertThat(association.getAssignmentId()).isEqualTo(assignmentId);
        assertThat(association.getProfileProductId())
                .isEqualTo(profileProductId);
    }

    @Test
    void shouldRejectNullAssignmentId() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ParticipantCapabilityProduct(
                        null,
                        profileProductId
                ));
    }

    @Test
    void shouldRejectNullProfileProductId() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ParticipantCapabilityProduct(
                        assignmentId,
                        null
                ));
    }

    @Test
    void shouldPreserveBothReferences() {
        ParticipantCapabilityProduct association =
                new ParticipantCapabilityProduct(
                        assignmentId,
                        profileProductId
                );

        assertThat(association.getAssignmentId()).isSameAs(assignmentId);
        assertThat(association.getProfileProductId())
                .isSameAs(profileProductId);
    }
}