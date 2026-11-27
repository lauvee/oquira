package org.oquira.capability.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a stable, reusable financial operation or data-access function.
 */
public final class Capability {

    private final UUID capabilityId;
    private final String code;
    private final String name;
    private final String domain;
    private final String description;
    private final CapabilityLifecycleStatus lifecycleStatus;

    public Capability(
            UUID capabilityId,
            String code,
            String name,
            String domain,
            String description,
            CapabilityLifecycleStatus lifecycleStatus
    ) {
        this.capabilityId = Objects.requireNonNull(
                capabilityId, "capabilityId must not be null"
        );

        this.code = requireNonBlank(code, "code");
        this.name = requireNonBlank(name, "name");
        this.domain = requireNonBlank(domain, "domain");
        this.description = description;

        this.lifecycleStatus = Objects.requireNonNull(
                lifecycleStatus, "lifecycleStatus must not be null"
        );
    }

    private static String requireNonBlank(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be blank"
            );
        }

        return value;
    }

    public UUID getCapabilityId() {
        return capabilityId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }

    public String getDescription() {
        return description;
    }

    public CapabilityLifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }
}
