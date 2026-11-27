package org.oquira.institution.domain.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a source record supporting an institutional claim.
 */
public final class EvidenceRecord {

    private final UUID evidenceId;
    private final UUID institutionId;
    private final String sourceName;
    private final String sourceUrl;
    private final EvidenceSourceType sourceType;
    private final Instant observedAt;
    private final LocalDate publishedAt;
    private final String claimSupported;
    private final EvidenceVerificationStatus verificationStatus;
    private final String notes;

    public EvidenceRecord(
            UUID evidenceId,
            UUID institutionId,
            String sourceName,
            String sourceUrl,
            EvidenceSourceType sourceType,
            Instant observedAt,
            LocalDate publishedAt,
            String claimSupported,
            EvidenceVerificationStatus verificationStatus,
            String notes
    ) {
        this.evidenceId = Objects.requireNonNull(
                evidenceId, "evidenceId must not be null"
        );
        this.institutionId = Objects.requireNonNull(
                institutionId, "institutionId must not be null"
        );
        this.sourceName = requireNonBlank(sourceName, "sourceName");
        this.sourceUrl = requireValidSourceUrl(sourceUrl);
        this.sourceType = Objects.requireNonNull(
                sourceType, "sourceType must not be null"
        );
        this.observedAt = Objects.requireNonNull(
                observedAt, "observedAt must not be null"
        );
        this.publishedAt = publishedAt;
        this.claimSupported = requireNonBlank(
                claimSupported, "claimSupported"
        );
        this.verificationStatus = Objects.requireNonNull(
                verificationStatus, "verificationStatus must not be null"
        );
        this.notes = notes;
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

    private static String requireValidSourceUrl(String sourceUrl) {
        Objects.requireNonNull(sourceUrl, "sourceUrl must not be null");

        try {
            URI uri = new URI(sourceUrl);
            String scheme = uri.getScheme();

            if (!uri.isAbsolute()
                    || scheme == null
                    || !(scheme.equalsIgnoreCase("http")
                    || scheme.equalsIgnoreCase("https"))
                    || uri.getHost() == null
                    || uri.getHost().isBlank()) {
                throw new IllegalArgumentException(
                        "sourceUrl must be an absolute HTTP or HTTPS URL with a host"
                );
            }

            return sourceUrl;
        } catch (URISyntaxException exception) {
            throw new IllegalArgumentException(
                    "sourceUrl must be a valid HTTP or HTTPS URL",
                    exception
            );
        }
    }

    public UUID getEvidenceId() {
        return evidenceId;
    }

    public UUID getInstitutionId() {
        return institutionId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public EvidenceSourceType getSourceType() {
        return sourceType;
    }

    public Instant getObservedAt() {
        return observedAt;
    }

    public LocalDate getPublishedAt() {
        return publishedAt;
    }

    public String getClaimSupported() {
        return claimSupported;
    }

    public EvidenceVerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public String getNotes() {
        return notes;
    }
}