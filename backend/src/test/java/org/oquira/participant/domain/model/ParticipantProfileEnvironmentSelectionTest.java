package org.oquira.participant.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParticipantProfileEnvironmentSelectionTest {

    private final UUID selectionId = UUID.randomUUID();
    private final UUID profileId = UUID.randomUUID();
    private final UUID profileVersionId = UUID.randomUUID();

    private final Instant createdAt =
            Instant.parse("2026-01-01T10:00:00Z");

    private final Instant publishedAt =
            Instant.parse("2026-01-02T10:00:00Z");

    @Test
    void shouldCreateSelectionForPublishedVersion() {
        ParticipantProfileVersion version = createVersion(
                profileId,
                ParticipantEnvironment.DEMO,
                ParticipantProfileVersionStatus.PUBLISHED
        );

        ParticipantProfileEnvironmentSelection selection =
                new ParticipantProfileEnvironmentSelection(
                        selectionId,
                        profileId,
                        ParticipantEnvironment.DEMO,
                        version
                );

        assertThat(selection.getSelectionId()).isEqualTo(selectionId);
        assertThat(selection.getProfileId()).isEqualTo(profileId);
        assertThat(selection.getEnvironment()).isEqualTo(ParticipantEnvironment.DEMO);
        assertThat(selection.getProfileVersionId()).isEqualTo(profileVersionId);
    }

    @Test
    void shouldRejectNullSelectionId() {
        ParticipantProfileVersion version = createValidVersion();

        assertThatThrownBy(() -> new ParticipantProfileEnvironmentSelection(
                null, profileId, ParticipantEnvironment.DEMO, version
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullProfileId() {
        ParticipantProfileVersion version = createValidVersion();

        assertThatThrownBy(() -> new ParticipantProfileEnvironmentSelection(
                selectionId, null, ParticipantEnvironment.DEMO, version
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullEnvironment() {
        ParticipantProfileVersion version = createValidVersion();

        assertThatThrownBy(() -> new ParticipantProfileEnvironmentSelection(
                selectionId, profileId, null, version
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullSelectedVersion() {
        assertThatThrownBy(() -> new ParticipantProfileEnvironmentSelection(
                selectionId, profileId, ParticipantEnvironment.DEMO, null
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectVersionFromAnotherProfile() {
        ParticipantProfileVersion version = createVersion(
                UUID.randomUUID(),
                ParticipantEnvironment.DEMO,
                ParticipantProfileVersionStatus.PUBLISHED
        );

        assertThatThrownBy(() -> new ParticipantProfileEnvironmentSelection(
                selectionId, profileId, ParticipantEnvironment.DEMO, version
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectVersionFromAnotherEnvironment() {
        ParticipantProfileVersion version = createVersion(
                profileId,
                ParticipantEnvironment.TEST,
                ParticipantProfileVersionStatus.PUBLISHED
        );

        assertThatThrownBy(() -> new ParticipantProfileEnvironmentSelection(
                selectionId, profileId, ParticipantEnvironment.DEMO, version
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectDraftVersion() {
        assertVersionStatusRejected(ParticipantProfileVersionStatus.DRAFT);
    }

    @Test
    void shouldRejectDeprecatedVersion() {
        assertVersionStatusRejected(ParticipantProfileVersionStatus.DEPRECATED);
    }

    @Test
    void shouldRejectWithdrawnVersion() {
        assertVersionStatusRejected(ParticipantProfileVersionStatus.WITHDRAWN);
    }

    private void assertVersionStatusRejected(
            ParticipantProfileVersionStatus status
    ) {
        ParticipantProfileVersion version = createVersion(
                profileId,
                ParticipantEnvironment.DEMO,
                status
        );

        assertThatThrownBy(() -> new ParticipantProfileEnvironmentSelection(
                selectionId, profileId, ParticipantEnvironment.DEMO, version
        )).isInstanceOf(IllegalArgumentException.class);
    }

    private ParticipantProfileVersion createValidVersion() {
        return createVersion(
                profileId,
                ParticipantEnvironment.DEMO,
                ParticipantProfileVersionStatus.PUBLISHED
        );
    }

    private ParticipantProfileVersion createVersion(
            UUID selectedProfileId,
            ParticipantEnvironment environment,
            ParticipantProfileVersionStatus status
    ) {
        Instant publicationDate =
                status == ParticipantProfileVersionStatus.DRAFT
                        ? null
                        : publishedAt;

        return new ParticipantProfileVersion(
                profileVersionId,
                selectedProfileId,
                1,
                environment,
                new ProfileConfiguration("{\"enabled\":true}"),
                status,
                createdAt,
                publicationDate,
                "user-001",
                "Test version"
        );
    }
}