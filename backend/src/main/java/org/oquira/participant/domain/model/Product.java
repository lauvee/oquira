package org.oquira.participant.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents the stable identity of a financial product offered by a participant.
 */
public final class Product {

    private final UUID productId;
    private final UUID participantId;
    private final String productCode;
    private final String name;
    private final ProductType productType;
    private final String description;
    private final ProductLifecycleStatus lifecycleStatus;

    public Product(
            UUID productId,
            UUID participantId,
            String productCode,
            String name,
            ProductType productType,
            String description,
            ProductLifecycleStatus lifecycleStatus
    ) {
        this.productId = Objects.requireNonNull(
                productId, "productId must not be null"
        );

        this.participantId = Objects.requireNonNull(
                participantId, "participantId must not be null"
        );

        this.productCode = requireNonBlank(productCode, "productCode");
        this.name = requireNonBlank(name, "name");

        this.productType = Objects.requireNonNull(
                productType, "productType must not be null"
        );

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

    public UUID getProductId() {
        return productId;
    }

    public UUID getParticipantId() {
        return participantId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getDescription() {
        return description;
    }

    public ProductLifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }
}