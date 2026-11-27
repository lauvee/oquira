package org.oquira.participant.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParticipantProfileVersionTest {

    private final UUID profileVersionId = UUID.randomUUID();
    private final UUID profileId = UUID.randomUUID();

    private final ProfileConfiguration configuration =
            new ProfileConfiguration("{\"currency\":\"DOP\"}");

    private final Instant createdAt =
            Instant.parse("2026-01-01T10:00:00Z");

    private final Instant publishedAt =
            Instant.parse("2026-01-02T10:00:00Z");

    @Test
    void shouldCreateDraftVersionWithoutPublicationDate() {
        ParticipantProfileVersion version = createVersion(
                ParticipantProfileVersionStatus.DRAFT,
                null
        );

        assertThat(version.getProfileVersionId()).isEqualTo(profileVersionId);
        assertThat(version.getProfileId()).isEqualTo(profileId);
        assertThat(version.getVersionNumber()).isEqualTo(1);
        assertThat(version.getEnvironment()).isEqualTo(ParticipantEnvironment.DEMO);
        assertThat(version.getConfiguration()).isEqualTo(configuration);
        assertThat(version.getLifecycleStatus())
                .isEqualTo(ParticipantProfileVersionStatus.DRAFT);
        assertThat(version.getCreatedAt()).isEqualTo(createdAt);
        assertThat(version.getPublishedAt()).isNull();
        assertThat(version.getCreatedBy()).isEqualTo("user-001");
        assertThat(version.getChangeSummary()).isEqualTo("Initial version");
    }

    @Test
    void shouldCreatePublishedVersionWithPublicationDate() {
        ParticipantProfileVersion version = createVersion(
                ParticipantProfileVersionStatus.PUBLISHED,
                publishedAt
        );

        assertThat(version.getLifecycleStatus())
                .isEqualTo(ParticipantProfileVersionStatus.PUBLISHED);
        assertThat(version.getPublishedAt()).isEqualTo(publishedAt);
    }

    @Test
    void shouldAllowDeprecatedVersionWithPublicationDate() {
        ParticipantProfileVersion version = createVersion(
                ParticipantProfileVersionStatus.DEPRECATED,
                publishedAt
        );

        assertThat(version.getLifecycleStatus())
                .isEqualTo(ParticipantProfileVersionStatus.DEPRECATED);
    }

    @Test
    void shouldAllowWithdrawnVersionWithPublicationDate() {
        ParticipantProfileVersion version = createVersion(
                ParticipantProfileVersionStatus.WITHDRAWN,
                publishedAt
        );

        assertThat(version.getLifecycleStatus())
                .isEqualTo(ParticipantProfileVersionStatus.WITHDRAWN);
    }

    @Test
    void shouldRejectZeroVersionNumber() {
        assertThatThrownBy(() -> new ParticipantProfileVersion(
                profileVersionId, profileId, 0,
                ParticipantEnvironment.DEMO, configuration,
                ParticipantProfileVersionStatus.DRAFT,
                createdAt, null, "user-001", "Initial version"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNegativeVersionNumber() {
        assertThatThrownBy(() -> new ParticipantProfileVersion(
                profileVersionId, profileId, -1,
                ParticipantEnvironment.DEMO, configuration,
                ParticipantProfileVersionStatus.DRAFT,
                createdAt, null, "user-001", "Initial version"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullProfileVersionId() {
        assertThatThrownBy(() -> new ParticipantProfileVersion(
                null, profileId, 1,
                ParticipantEnvironment.DEMO, configuration,
                ParticipantProfileVersionStatus.DRAFT,
                createdAt, null, "user-001", "Initial version"
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullProfileId() {
        assertThatThrownBy(() -> new ParticipantProfileVersion(
                profileVersionId, null, 1,
                ParticipantEnvironment.DEMO, configuration,
                ParticipantProfileVersionStatus.DRAFT,
                createdAt, null, "user-001", "Initial version"
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullEnvironment() {
        assertThatThrownBy(() -> new ParticipantProfileVersion(
                profileVersionId, profileId, 1,
                null, configuration,
                ParticipantProfileVersionStatus.DRAFT,
                createdAt, null, "user-001", "Initial version"
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullConfiguration() {
        assertThatThrownBy(() -> new ParticipantProfileVersion(
                profileVersionId, profileId, 1,
                ParticipantEnvironment.DEMO, null,
                ParticipantProfileVersionStatus.DRAFT,
                createdAt, null, "user-001", "Initial version"
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullLifecycleStatus() {
        assertThatThrownBy(() -> new ParticipantProfileVersion(
                profileVersionId, profileId, 1,
                ParticipantEnvironment.DEMO, configuration,
                null, createdAt, null, "user-001", "Initial version"
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullCreatedAt() {
        assertThatThrownBy(() -> new ParticipantProfileVersion(
                profileVersionId, profileId, 1,
                ParticipantEnvironment.DEMO, configuration,
                ParticipantProfileVersionStatus.DRAFT,
                null, null, "user-001", "Initial version"
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectDraftVersionWithPublicationDate() {
        assertThatThrownBy(() -> createVersion(
                ParticipantProfileVersionStatus.DRAFT,
                publishedAt
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectPublishedVersionWithoutPublicationDate() {
        assertThatThrownBy(() -> createVersion(
                ParticipantProfileVersionStatus.PUBLISHED,
                null
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectDeprecatedVersionWithoutPublicationDate() {
        assertThatThrownBy(() -> createVersion(
                ParticipantProfileVersionStatus.DEPRECATED,
                null
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectWithdrawnVersionWithoutPublicationDate() {
        assertThatThrownBy(() -> createVersion(
                ParticipantProfileVersionStatus.WITHDRAWN,
                null
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectPublicationDateBeforeCreationDate() {
        Instant earlierDate = Instant.parse("2025-12-31T10:00:00Z");

        assertThatThrownBy(() -> createVersion(
                ParticipantProfileVersionStatus.PUBLISHED,
                earlierDate
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldAllowPublicationDateEqualToCreationDate() {
        ParticipantProfileVersion version = createVersion(
                ParticipantProfileVersionStatus.PUBLISHED,
                createdAt
        );

        assertThat(version.getPublishedAt()).isEqualTo(createdAt);
    }

    @Test
    void shouldRejectBlankCreatedBy() {
        assertThatThrownBy(() -> new ParticipantProfileVersion(
                profileVersionId, profileId, 1,
                ParticipantEnvironment.DEMO, configuration,
                ParticipantProfileVersionStatus.DRAFT,
                createdAt, null, "   ", "Initial version"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectBlankChangeSummary() {
        assertThatThrownBy(() -> new ParticipantProfileVersion(
                profileVersionId, profileId, 1,
                ParticipantEnvironment.DEMO, configuration,
                ParticipantProfileVersionStatus.DRAFT,
                createdAt, null, "user-001", "   "
        )).isInstanceOf(IllegalArgumentException.class);
    }

    private ParticipantProfileVersion createVersion(
            ParticipantProfileVersionStatus status,
            Instant publicationDate
    ) {
        return new ParticipantProfileVersion(
                profileVersionId,
                profileId,
                1,
                ParticipantEnvironment.DEMO,
                configuration,
                status,
                createdAt,
                publicationDate,
                "user-001",
                "Initial version"
        );
    }
}