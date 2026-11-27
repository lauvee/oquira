package org.oquira.participant.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class ParticipantProfileProductTest {

    private final UUID profileProductId = UUID.randomUUID();
    private final UUID profileVersionId = UUID.randomUUID();
    private final UUID productId = UUID.randomUUID();

    private final ProductConfiguration configuration =
            new ProductConfiguration("{\"currency\":\"DOP\"}");

    private ParticipantProfileProduct createValidAssociation() {
        return new ParticipantProfileProduct(
                profileProductId,
                profileVersionId,
                productId,
                configuration,
                ProfileProductLifecycleStatus.ENABLED
        );
    }

    @Test
    void shouldCreateValidAssociation() {
        ParticipantProfileProduct association = createValidAssociation();

        assertThat(association.getProfileProductId())
                .isEqualTo(profileProductId);
        assertThat(association.getProfileVersionId())
                .isEqualTo(profileVersionId);
        assertThat(association.getProductId()).isEqualTo(productId);
        assertThat(association.getConfiguration()).isEqualTo(configuration);
        assertThat(association.getLifecycleStatus())
                .isEqualTo(ProfileProductLifecycleStatus.ENABLED);
    }

    @Test
    void shouldSupportAllAvailabilityStatuses() {
        for (ProfileProductLifecycleStatus status
                : ProfileProductLifecycleStatus.values()) {

            ParticipantProfileProduct association =
                    new ParticipantProfileProduct(
                            profileProductId,
                            profileVersionId,
                            productId,
                            configuration,
                            status
                    );

            assertThat(association.getLifecycleStatus()).isEqualTo(status);
        }
    }

    @Test
    void shouldRejectNullProfileProductId() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ParticipantProfileProduct(
                        null, profileVersionId, productId,
                        configuration, ProfileProductLifecycleStatus.ENABLED
                ));
    }

    @Test
    void shouldRejectNullProfileVersionId() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ParticipantProfileProduct(
                        profileProductId, null, productId,
                        configuration, ProfileProductLifecycleStatus.ENABLED
                ));
    }

    @Test
    void shouldRejectNullProductId() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ParticipantProfileProduct(
                        profileProductId, profileVersionId, null,
                        configuration, ProfileProductLifecycleStatus.ENABLED
                ));
    }

    @Test
    void shouldRejectNullConfiguration() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ParticipantProfileProduct(
                        profileProductId, profileVersionId, productId,
                        null, ProfileProductLifecycleStatus.ENABLED
                ));
    }

    @Test
    void shouldRejectNullLifecycleStatus() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ParticipantProfileProduct(
                        profileProductId, profileVersionId, productId,
                        configuration, null
                ));
    }

    @Test
    void shouldAllowDisabledProductAssociation() {
        ParticipantProfileProduct association =
                new ParticipantProfileProduct(
                        profileProductId,
                        profileVersionId,
                        productId,
                        configuration,
                        ProfileProductLifecycleStatus.DISABLED
                );

        assertThat(association.getLifecycleStatus())
                .isEqualTo(ProfileProductLifecycleStatus.DISABLED);
    }

    @Test
    void shouldPreserveConfigurationReference() {
        ParticipantProfileProduct association = createValidAssociation();

        assertThat(association.getConfiguration()).isSameAs(configuration);
    }
}