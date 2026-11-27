package org.oquira.capability.domain.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CapabilityVersionTest {

    private final UUID capabilityVersionId = UUID.randomUUID();
    private final UUID capabilityId = UUID.randomUUID();

    private final SemanticVersion versionNumber =
            new SemanticVersion(1, 0, 0);

    private final CapabilityInputSchema inputSchema =
            new CapabilityInputSchema("{\"accountId\":\"string\"}");

    private final CapabilityOutputSchema outputSchema =
            new CapabilityOutputSchema("{\"balance\":\"number\"}");

    private final Instant publishedAt =
            Instant.parse("2026-01-01T10:00:00Z");

    @Test
    void shouldCreateActiveCapabilityVersion() {
        CapabilityVersion version = createValidVersion();

        assertThat(version.getCapabilityVersionId())
                .isEqualTo(capabilityVersionId);

        assertThat(version.getCapabilityId())
                .isEqualTo(capabilityId);

        assertThat(version.getVersionNumber())
                .isEqualTo(versionNumber);

        assertThat(version.getInputSchema())
                .isEqualTo(inputSchema);

        assertThat(version.getOutputSchema())
                .isEqualTo(outputSchema);

        assertThat(version.getExecutionMode())
                .isEqualTo(ExecutionMode.SYNCHRONOUS);

        assertThat(version.getRequiredPermissions())
                .containsExactlyInAnyOrder("accounts.read", "balances.read");

        assertThat(version.getLifecycleStatus())
                .isEqualTo(CapabilityVersionLifecycleStatus.ACTIVE);

        assertThat(version.getPublishedAt()).isEqualTo(publishedAt);
    }

    @Test
    void shouldAllowEmptyRequiredPermissions() {
        CapabilityVersion version = new CapabilityVersion(
                capabilityVersionId,
                capabilityId,
                versionNumber,
                inputSchema,
                outputSchema,
                ExecutionMode.SYNCHRONOUS,
                Set.of(),
                CapabilityVersionLifecycleStatus.ACTIVE,
                publishedAt
        );

        assertThat(version.getRequiredPermissions()).isEmpty();
    }

    @Test
    void shouldAllowAsynchronousExecution() {
        CapabilityVersion version = new CapabilityVersion(
                capabilityVersionId,
                capabilityId,
                versionNumber,
                inputSchema,
                outputSchema,
                ExecutionMode.ASYNCHRONOUS,
                Set.of(),
                CapabilityVersionLifecycleStatus.ACTIVE,
                publishedAt
        );

        assertThat(version.getExecutionMode())
                .isEqualTo(ExecutionMode.ASYNCHRONOUS);
    }

    @Test
    void shouldAllowDraftWithoutPublicationDate() {
        CapabilityVersion version = createVersion(
                CapabilityVersionLifecycleStatus.DRAFT,
                null
        );

        assertThat(version.getPublishedAt()).isNull();
    }

    @Test
    void shouldAllowProposedWithoutPublicationDate() {
        CapabilityVersion version = createVersion(
                CapabilityVersionLifecycleStatus.PROPOSED,
                null
        );

        assertThat(version.getPublishedAt()).isNull();
    }

    @Test
    void shouldRequirePublicationDateForActiveVersion() {
        assertThatThrownBy(() -> createVersion(
                CapabilityVersionLifecycleStatus.ACTIVE,
                null
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRequirePublicationDateForDeprecatedVersion() {
        assertThatThrownBy(() -> createVersion(
                CapabilityVersionLifecycleStatus.DEPRECATED,
                null
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRequirePublicationDateForRetiredVersion() {
        assertThatThrownBy(() -> createVersion(
                CapabilityVersionLifecycleStatus.RETIRED,
                null
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectPublicationDateForDraftVersion() {
        assertThatThrownBy(() -> createVersion(
                CapabilityVersionLifecycleStatus.DRAFT,
                publishedAt
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectPublicationDateForProposedVersion() {
        assertThatThrownBy(() -> createVersion(
                CapabilityVersionLifecycleStatus.PROPOSED,
                publishedAt
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullRequiredPermissions() {
        assertThatThrownBy(() -> new CapabilityVersion(
                capabilityVersionId,
                capabilityId,
                versionNumber,
                inputSchema,
                outputSchema,
                ExecutionMode.SYNCHRONOUS,
                null,
                CapabilityVersionLifecycleStatus.ACTIVE,
                publishedAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankPermission() {
        assertThatThrownBy(() -> new CapabilityVersion(
                capabilityVersionId,
                capabilityId,
                versionNumber,
                inputSchema,
                outputSchema,
                ExecutionMode.SYNCHRONOUS,
                Set.of(" "),
                CapabilityVersionLifecycleStatus.ACTIVE,
                publishedAt
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullPermissionEntry() {
        Set<String> permissions = new LinkedHashSet<>();
        permissions.add(null);

        assertThatThrownBy(() -> new CapabilityVersion(
                capabilityVersionId,
                capabilityId,
                versionNumber,
                inputSchema,
                outputSchema,
                ExecutionMode.SYNCHRONOUS,
                permissions,
                CapabilityVersionLifecycleStatus.ACTIVE,
                publishedAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullExecutionMode() {
        assertThatThrownBy(() -> new CapabilityVersion(
                capabilityVersionId,
                capabilityId,
                versionNumber,
                inputSchema,
                outputSchema,
                null,
                Set.of(),
                CapabilityVersionLifecycleStatus.ACTIVE,
                publishedAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullLifecycleStatus() {
        assertThatThrownBy(() -> new CapabilityVersion(
                capabilityVersionId,
                capabilityId,
                versionNumber,
                inputSchema,
                outputSchema,
                ExecutionMode.SYNCHRONOUS,
                Set.of(),
                null,
                publishedAt
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldDefensivelyCopyRequiredPermissions() {
        Set<String> permissions = new LinkedHashSet<>();
        permissions.add("accounts.read");

        CapabilityVersion version = new CapabilityVersion(
                capabilityVersionId,
                capabilityId,
                versionNumber,
                inputSchema,
                outputSchema,
                ExecutionMode.SYNCHRONOUS,
                permissions,
                CapabilityVersionLifecycleStatus.ACTIVE,
                publishedAt
        );

        permissions.add("balances.read");

        assertThat(version.getRequiredPermissions())
                .containsExactly("accounts.read");
    }

    @Test
    void shouldNotAllowModificationOfRequiredPermissions() {
        CapabilityVersion version = createValidVersion();

        assertThatThrownBy(() ->
                version.getRequiredPermissions().add("payments.create")
        ).isInstanceOf(UnsupportedOperationException.class);
    }

    private CapabilityVersion createValidVersion() {
        return new CapabilityVersion(
                capabilityVersionId,
                capabilityId,
                versionNumber,
                inputSchema,
                outputSchema,
                ExecutionMode.SYNCHRONOUS,
                Set.of("accounts.read", "balances.read"),
                CapabilityVersionLifecycleStatus.ACTIVE,
                publishedAt
        );
    }

    private CapabilityVersion createVersion(
            CapabilityVersionLifecycleStatus status,
            Instant publicationDate
    ) {
        return new CapabilityVersion(
                capabilityVersionId,
                capabilityId,
                versionNumber,
                inputSchema,
                outputSchema,
                ExecutionMode.SYNCHRONOUS,
                Set.of(),
                status,
                publicationDate
        );
    }
}