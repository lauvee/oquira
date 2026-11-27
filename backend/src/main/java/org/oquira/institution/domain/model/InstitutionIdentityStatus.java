package org.oquira.institution.domain.model;

/**
 * Defines the verification status of an institution's identity.
 */
public enum InstitutionIdentityStatus {

    UNVERIFIED,

    PENDING_REVIEW,

    VERIFIED,

    DISPUTED,

    REJECTED
}