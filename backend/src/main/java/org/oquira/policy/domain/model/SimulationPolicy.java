package org.oquira.policy.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents the stable identity of a reusable simulation policy.
 */
public final class SimulationPolicy {

    private final UUID policyId;
    private final String code;
    private final String name;
    private final PolicyType policyType;
    private final String description;
    private final SimulationPolicyLifecycleStatus lifecycleStatus;

    public SimulationPolicy(
            UUID policyId,
            String code,
            String name,
            PolicyType policyType,
            String description,
            SimulationPolicyLifecycleStatus lifecycleStatus
    ) {
        this.policyId = Objects.requireNonNull(
                policyId, "policyId must not be null"
        );

        this.code = requireNonBlank(code, "code");
        this.name = requireNonBlank(name, "name");

        this.policyType = Objects.requireNonNull(
                policyType, "policyType must not be null"
        );

        this.description = description;

        if (description != null && description.isBlank()) {
            throw new IllegalArgumentException(
                    "description must not be blank when provided"
            );
        }

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

    public UUID getPolicyId() {
        return policyId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public PolicyType getPolicyType() {
        return policyType;
    }

    public String getDescription() {
        return description;
    }

    public SimulationPolicyLifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }
}