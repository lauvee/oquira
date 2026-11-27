package org.oquira.participant.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Associates a stable product identity with a specific participant profile version.
 */
public final class ParticipantProfileProduct {

    private final UUID profileProductId;
    private final UUID profileVersionId;
    private final UUID productId;
    private final ProductConfiguration configuration;
    private final ProfileProductLifecycleStatus lifecycleStatus;

    public ParticipantProfileProduct(
            UUID profileProductId,
            UUID profileVersionId,
            UUID productId,
            ProductConfiguration configuration,
            ProfileProductLifecycleStatus lifecycleStatus
    ) {
        this.profileProductId = Objects.requireNonNull(
                profileProductId, "profileProductId must not be null"
        );

        this.profileVersionId = Objects.requireNonNull(
                profileVersionId, "profileVersionId must not be null"
        );

        this.productId = Objects.requireNonNull(
                productId, "productId must not be null"
        );

        this.configuration = Objects.requireNonNull(
                configuration, "configuration must not be null"
        );

        this.lifecycleStatus = Objects.requireNonNull(
                lifecycleStatus, "lifecycleStatus must not be null"
        );
    }

    public UUID getProfileProductId() {
        return profileProductId;
    }

    public UUID getProfileVersionId() {
        return profileVersionId;
    }

    public UUID getProductId() {
        return productId;
    }

    public ProductConfiguration getConfiguration() {
        return configuration;
    }

    public ProfileProductLifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }
}