package org.oquira.simulation.domain.model;

/**
 * Outcome of an evaluated simulation policy.
 */
public enum EvaluationStatus {
    APPLIED,
    REJECTED,
    SKIPPED,
    FAILED
}