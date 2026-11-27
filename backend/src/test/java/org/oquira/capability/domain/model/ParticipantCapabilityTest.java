package org.oquira.capability.domain.model;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParticipantCapabilityTest {

    private final UUID assignmentId = UUID.randomUUID();
    private final UUID capabilityVersionId = UUID.randomUUID();
    private final UUID profileVersionId = UUID.randomUUID();

    private final CapabilityConfiguration configuration =
            new CapabilityConfiguration("{\"currency\":\"DOP\"}");

    @Test
    void shouldCreateValidAssignment() {
        ParticipantCapability assignment = createValidAssignment();

        assertThat(assignment.getAssignmentId()).isEqualTo(assignmentId);
        assertThat(assignment.getCapabilityVersionId()).isEqualTo(capabilityVersionId);
        assertThat(assignment.getProfileVersionId()).isEqualTo(profileVersionId);
        assertThat(assignment.getAvailabilityStatus())
                .isEqualTo(ParticipantCapabilityAvailabilityStatus.ENABLED);
        assertThat(assignment.getConfiguration()).isEqualTo(configuration);
        assertThat(assignment.getRequiredPermissions())
                .containsExactlyInAnyOrder("accounts.read", "balances.read");
    }

    @Test
    void shouldAllowAllAvailabilityStatuses() {
        for (ParticipantCapabilityAvailabilityStatus status :
                ParticipantCapabilityAvailabilityStatus.values()) {

            ParticipantCapability assignment = new ParticipantCapability(
                    assignmentId,
                    capabilityVersionId,
                    profileVersionId,
                    status,
                    configuration,
                    Set.of()
            );

            assertThat(assignment.getAvailabilityStatus()).isEqualTo(status);
        }
    }

    @Test
    void shouldAllowEmptyAdditionalPermissions() {
        ParticipantCapability assignment = createAssignment(Set.of());

        assertThat(assignment.getRequiredPermissions()).isEmpty();
    }

    @Test
    void shouldRejectNullAssignmentId() {
        assertThatThrownBy(() -> new ParticipantCapability(
                null, capabilityVersionId, profileVersionId,
                ParticipantCapabilityAvailabilityStatus.ENABLED,
                configuration, Set.of()
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullCapabilityVersionId() {
        assertThatThrownBy(() -> new ParticipantCapability(
                assignmentId, null, profileVersionId,
                ParticipantCapabilityAvailabilityStatus.ENABLED,
                configuration, Set.of()
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullProfileVersionId() {
        assertThatThrownBy(() -> new ParticipantCapability(
                assignmentId, capabilityVersionId, null,
                ParticipantCapabilityAvailabilityStatus.ENABLED,
                configuration, Set.of()
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullAvailabilityStatus() {
        assertThatThrownBy(() -> new ParticipantCapability(
                assignmentId, capabilityVersionId, profileVersionId,
                null, configuration, Set.of()
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullConfiguration() {
        assertThatThrownBy(() -> new ParticipantCapability(
                assignmentId, capabilityVersionId, profileVersionId,
                ParticipantCapabilityAvailabilityStatus.ENABLED,
                null, Set.of()
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullRequiredPermissions() {
        assertThatThrownBy(() -> createAssignment(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankPermission() {
        assertThatThrownBy(() -> createAssignment(Set.of(" ")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullPermissionEntry() {
        Set<String> permissions = new LinkedHashSet<>();
        permissions.add(null);

        assertThatThrownBy(() -> createAssignment(permissions))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldDefensivelyCopyPermissions() {
        Set<String> permissions = new LinkedHashSet<>();
        permissions.add("accounts.read");

        ParticipantCapability assignment = createAssignment(permissions);

        permissions.add("balances.read");

        assertThat(assignment.getRequiredPermissions())
                .containsExactly("accounts.read");
    }

    @Test
    void shouldNotAllowPermissionModification() {
        ParticipantCapability assignment = createValidAssignment();

        assertThatThrownBy(() ->
                assignment.getRequiredPermissions().add("payments.create")
        ).isInstanceOf(UnsupportedOperationException.class);
    }

    private ParticipantCapability createValidAssignment() {
        return createAssignment(Set.of("accounts.read", "balances.read"));
    }

    private ParticipantCapability createAssignment(Set<String> permissions) {
        return new ParticipantCapability(
                assignmentId,
                capabilityVersionId,
                profileVersionId,
                ParticipantCapabilityAvailabilityStatus.ENABLED,
                configuration,
                permissions
        );
    }
}