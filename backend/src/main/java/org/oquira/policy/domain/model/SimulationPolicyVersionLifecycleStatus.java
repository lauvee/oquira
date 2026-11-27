package org.oquira.policy.domain.model;

/**
 * Lifecycle of an immutable simulation policy version.
 */
public enum SimulationPolicyVersionLifecycleStatus {
    DRAFT,
    PUBLISHED,
    DEPRECATED,
    RETIRED
}