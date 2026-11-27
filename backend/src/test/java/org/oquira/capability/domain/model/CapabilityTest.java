package org.oquira.capability.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CapabilityTest {

    private final UUID capabilityId = UUID.randomUUID();

    @Test
    void shouldCreateCapabilityWithValidData() {
        Capability capability = createValidCapability();

        assertThat(capability.getCapabilityId()).isEqualTo(capabilityId);
        assertThat(capability.getCode()).isEqualTo("ACCOUNT_INFORMATION");
        assertThat(capability.getName()).isEqualTo("Account Information");
        assertThat(capability.getDomain()).isEqualTo("ACCOUNT_DATA");
        assertThat(capability.getDescription())
                .isEqualTo("Provides access to account information");
        assertThat(capability.getLifecycleStatus())
                .isEqualTo(CapabilityLifecycleStatus.ACTIVE);
    }

    @Test
    void shouldAllowCapabilityWithoutDescription() {
        Capability capability = new Capability(
                capabilityId,
                "ACCOUNT_INFORMATION",
                "Account Information",
                "ACCOUNT_DATA",
                null,
                CapabilityLifecycleStatus.ACTIVE
        );

        assertThat(capability.getDescription()).isNull();
    }

    @Test
    void shouldRejectNullCapabilityId() {
        assertThatThrownBy(() -> new Capability(
                null,
                "ACCOUNT_INFORMATION",
                "Account Information",
                "ACCOUNT_DATA",
                null,
                CapabilityLifecycleStatus.ACTIVE
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullCode() {
        assertThatThrownBy(() -> new Capability(
                capabilityId,
                null,
                "Account Information",
                "ACCOUNT_DATA",
                null,
                CapabilityLifecycleStatus.ACTIVE
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankCode() {
        assertThatThrownBy(() -> new Capability(
                capabilityId,
                "   ",
                "Account Information",
                "ACCOUNT_DATA",
                null,
                CapabilityLifecycleStatus.ACTIVE
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullName() {
        assertThatThrownBy(() -> new Capability(
                capabilityId,
                "ACCOUNT_INFORMATION",
                null,
                "ACCOUNT_DATA",
                null,
                CapabilityLifecycleStatus.ACTIVE
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankName() {
        assertThatThrownBy(() -> new Capability(
                capabilityId,
                "ACCOUNT_INFORMATION",
                "   ",
                "ACCOUNT_DATA",
                null,
                CapabilityLifecycleStatus.ACTIVE
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullDomain() {
        assertThatThrownBy(() -> new Capability(
                capabilityId,
                "ACCOUNT_INFORMATION",
                "Account Information",
                null,
                null,
                CapabilityLifecycleStatus.ACTIVE
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankDomain() {
        assertThatThrownBy(() -> new Capability(
                capabilityId,
                "ACCOUNT_INFORMATION",
                "Account Information",
                "   ",
                null,
                CapabilityLifecycleStatus.ACTIVE
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullLifecycleStatus() {
        assertThatThrownBy(() -> new Capability(
                capabilityId,
                "ACCOUNT_INFORMATION",
                "Account Information",
                "ACCOUNT_DATA",
                null,
                null
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldAcceptAllDefinedLifecycleStatuses() {
        for (CapabilityLifecycleStatus status : CapabilityLifecycleStatus.values()) {
            Capability capability = new Capability(
                    capabilityId,
                    "ACCOUNT_INFORMATION",
                    "Account Information",
                    "ACCOUNT_DATA",
                    null,
                    status
            );

            assertThat(capability.getLifecycleStatus()).isEqualTo(status);
        }
    }

    private Capability createValidCapability() {
        return new Capability(
                capabilityId,
                "ACCOUNT_INFORMATION",
                "Account Information",
                "ACCOUNT_DATA",
                "Provides access to account information",
                CapabilityLifecycleStatus.ACTIVE
        );
    }
}
