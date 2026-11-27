package org.oquira.participant.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ProductTypeTest {

    @Test
    void shouldCreateValidProductType() {
        ProductType type = new ProductType(
                "SAVINGS_ACCOUNT",
                "Savings Account",
                "A deposit account"
        );

        assertThat(type.getCode()).isEqualTo("SAVINGS_ACCOUNT");
        assertThat(type.getName()).isEqualTo("Savings Account");
        assertThat(type.getDescription()).isEqualTo("A deposit account");
    }

    @Test
    void shouldAcceptValidCodes() {
        assertThat(new ProductType("ACCOUNT", "Account", "Description").getCode())
                .isEqualTo("ACCOUNT");

        assertThat(new ProductType("PAYMENT_CARD", "Card", "Description").getCode())
                .isEqualTo("PAYMENT_CARD");

        assertThat(new ProductType("TYPE2", "Type", "Description").getCode())
                .isEqualTo("TYPE2");
    }

    @Test
    void shouldRejectNullCode() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ProductType(null, "Name", "Description"));
    }

    @Test
    void shouldRejectInvalidCode() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ProductType(
                        "invalid-code", "Name", "Description"
                ));
    }

    @Test
    void shouldRejectNullName() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ProductType(
                        "ACCOUNT", null, "Description"
                ));
    }

    @Test
    void shouldRejectBlankName() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ProductType(
                        "ACCOUNT", " ", "Description"
                ));
    }

    @Test
    void shouldRejectNullDescription() {
        assertThatNullPointerException()
                .isThrownBy(() -> new ProductType(
                        "ACCOUNT", "Account", null
                ));
    }

    @Test
    void shouldRejectBlankDescription() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new ProductType(
                        "ACCOUNT", "Account", " "
                ));
    }

    @Test
    void shouldUseCodeForEquality() {
        ProductType first = new ProductType(
                "ACCOUNT", "Account", "First description"
        );

        ProductType second = new ProductType(
                "ACCOUNT", "Different name", "Second description"
        );

        assertThat(first)
                .isEqualTo(second)
                .hasSameHashCodeAs(second);
    }

    @Test
    void shouldNotBeEqualWhenCodesDiffer() {
        ProductType first = new ProductType(
                "ACCOUNT", "Account", "Description"
        );

        ProductType second = new ProductType(
                "CARD", "Card", "Description"
        );

        assertThat(first).isNotEqualTo(second);
    }
}