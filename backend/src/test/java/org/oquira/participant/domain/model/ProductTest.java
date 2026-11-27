package org.oquira.participant.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ProductTest {

    private final UUID productId = UUID.randomUUID();
    private final UUID participantId = UUID.randomUUID();

    private final ProductType productType = new ProductType(
            "SAVINGS_ACCOUNT",
            "Savings Account",
            "A deposit account"
    );

    private Product createValidProduct() {
        return new Product(
                productId,
                participantId,
                "SAV-001",
                "Premium Savings",
                productType,
                "A savings product",
                ProductLifecycleStatus.ACTIVE
        );
    }

    @Test
    void shouldCreateValidProduct() {
        Product product = createValidProduct();

        assertThat(product.getProductId()).isEqualTo(productId);
        assertThat(product.getParticipantId()).isEqualTo(participantId);
        assertThat(product.getProductCode()).isEqualTo("SAV-001");
        assertThat(product.getName()).isEqualTo("Premium Savings");
        assertThat(product.getProductType()).isEqualTo(productType);
        assertThat(product.getDescription()).isEqualTo("A savings product");
        assertThat(product.getLifecycleStatus())
                .isEqualTo(ProductLifecycleStatus.ACTIVE);
    }

    @Test
    void shouldAllowNullDescription() {
        Product product = new Product(
                productId,
                participantId,
                "SAV-001",
                "Premium Savings",
                productType,
                null,
                ProductLifecycleStatus.DRAFT
        );

        assertThat(product.getDescription()).isNull();
    }

    @Test
    void shouldSupportAllLifecycleStatuses() {
        for (ProductLifecycleStatus status : ProductLifecycleStatus.values()) {
            Product product = new Product(
                    productId,
                    participantId,
                    "SAV-001",
                    "Premium Savings",
                    productType,
                    null,
                    status
            );

            assertThat(product.getLifecycleStatus()).isEqualTo(status);
        }
    }

    @Test
    void shouldRejectNullProductId() {
        assertThatNullPointerException()
                .isThrownBy(() -> new Product(
                        null, participantId, "SAV-001", "Savings",
                        productType, null, ProductLifecycleStatus.DRAFT
                ));
    }

    @Test
    void shouldRejectNullParticipantId() {
        assertThatNullPointerException()
                .isThrownBy(() -> new Product(
                        productId, null, "SAV-001", "Savings",
                        productType, null, ProductLifecycleStatus.DRAFT
                ));
    }

    @Test
    void shouldRejectNullProductCode() {
        assertThatNullPointerException()
                .isThrownBy(() -> new Product(
                        productId, participantId, null, "Savings",
                        productType, null, ProductLifecycleStatus.DRAFT
                ));
    }

    @Test
    void shouldRejectBlankProductCode() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(
                        productId, participantId, "  ", "Savings",
                        productType, null, ProductLifecycleStatus.DRAFT
                ));
    }

    @Test
    void shouldRejectNullName() {
        assertThatNullPointerException()
                .isThrownBy(() -> new Product(
                        productId, participantId, "SAV-001", null,
                        productType, null, ProductLifecycleStatus.DRAFT
                ));
    }

    @Test
    void shouldRejectBlankName() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(
                        productId, participantId, "SAV-001", " ",
                        productType, null, ProductLifecycleStatus.DRAFT
                ));
    }

    @Test
    void shouldRejectNullProductType() {
        assertThatNullPointerException()
                .isThrownBy(() -> new Product(
                        productId, participantId, "SAV-001", "Savings",
                        null, null, ProductLifecycleStatus.DRAFT
                ));
    }

    @Test
    void shouldRejectNullLifecycleStatus() {
        assertThatNullPointerException()
                .isThrownBy(() -> new Product(
                        productId, participantId, "SAV-001", "Savings",
                        productType, null, null
                ));
    }
}