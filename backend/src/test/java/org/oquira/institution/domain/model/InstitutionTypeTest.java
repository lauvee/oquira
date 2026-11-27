package org.oquira.institution.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstitutionTypeTest {

    @Test
    void shouldCreateInstitutionTypeWithValidData() {
        InstitutionType type = createValidType();

        assertThat(type.getCode()).isEqualTo("FINANCIAL_INTERMEDIARY");
        assertThat(type.getName()).isEqualTo("Financial Intermediary");
        assertThat(type.getDescription())
                .isEqualTo("An organization providing financial intermediation services");
    }

    @Test
    void shouldRejectNullCode() {
        assertThatThrownBy(() -> new InstitutionType(
                null,
                "Financial Intermediary",
                "Description"
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectInvalidCode() {
        assertThatThrownBy(() -> new InstitutionType(
                "financial-intermediary",
                "Financial Intermediary",
                "Description"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectCodeStartingWithDigit() {
        assertThatThrownBy(() -> new InstitutionType(
                "1_FINANCIAL",
                "Financial Intermediary",
                "Description"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullName() {
        assertThatThrownBy(() -> new InstitutionType(
                "FINANCIAL_INTERMEDIARY",
                null,
                "Description"
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankName() {
        assertThatThrownBy(() -> new InstitutionType(
                "FINANCIAL_INTERMEDIARY",
                "   ",
                "Description"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullDescription() {
        assertThatThrownBy(() -> new InstitutionType(
                "FINANCIAL_INTERMEDIARY",
                "Financial Intermediary",
                null
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankDescription() {
        assertThatThrownBy(() -> new InstitutionType(
                "FINANCIAL_INTERMEDIARY",
                "Financial Intermediary",
                "   "
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldConsiderTypesWithSameCodeEqual() {
        InstitutionType first = new InstitutionType(
                "FINANCIAL_INTERMEDIARY",
                "Financial Intermediary",
                "First description"
        );

        InstitutionType second = new InstitutionType(
                "FINANCIAL_INTERMEDIARY",
                "Different name",
                "Second description"
        );

        assertThat(first).isEqualTo(second);
    }

    @Test
    void shouldHaveSameHashCodeForTypesWithSameCode() {
        InstitutionType first = new InstitutionType(
                "FINANCIAL_INTERMEDIARY",
                "Financial Intermediary",
                "First description"
        );

        InstitutionType second = new InstitutionType(
                "FINANCIAL_INTERMEDIARY",
                "Different name",
                "Second description"
        );

        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }

    @Test
    void shouldConsiderTypesWithDifferentCodesDifferent() {
        InstitutionType first = createValidType();

        InstitutionType second = new InstitutionType(
                "COOPERATIVE",
                "Cooperative",
                "A cooperative financial organization"
        );

        assertThat(first).isNotEqualTo(second);
    }

    private InstitutionType createValidType() {
        return new InstitutionType(
                "FINANCIAL_INTERMEDIARY",
                "Financial Intermediary",
                "An organization providing financial intermediation services"
        );
    }
}