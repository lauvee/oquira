package org.oquira.institution.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EvidenceSourceTypeTest {

    @Test
    void shouldCreateEvidenceSourceTypeWithValidData() {
        EvidenceSourceType type = createValidType();

        assertThat(type.getCode()).isEqualTo("OFFICIAL_REGISTRY");
        assertThat(type.getName()).isEqualTo("Official Registry");
        assertThat(type.getDescription())
                .isEqualTo("An official institutional registry");
    }

    @Test
    void shouldRejectNullCode() {
        assertThatThrownBy(() -> new EvidenceSourceType(
                null,
                "Official Registry",
                "Description"
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectInvalidCode() {
        assertThatThrownBy(() -> new EvidenceSourceType(
                "official-registry",
                "Official Registry",
                "Description"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectCodeStartingWithDigit() {
        assertThatThrownBy(() -> new EvidenceSourceType(
                "1_REGISTRY",
                "Official Registry",
                "Description"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullName() {
        assertThatThrownBy(() -> new EvidenceSourceType(
                "OFFICIAL_REGISTRY",
                null,
                "Description"
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankName() {
        assertThatThrownBy(() -> new EvidenceSourceType(
                "OFFICIAL_REGISTRY",
                "   ",
                "Description"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullDescription() {
        assertThatThrownBy(() -> new EvidenceSourceType(
                "OFFICIAL_REGISTRY",
                "Official Registry",
                null
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankDescription() {
        assertThatThrownBy(() -> new EvidenceSourceType(
                "OFFICIAL_REGISTRY",
                "Official Registry",
                "   "
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldConsiderTypesWithSameCodeEqual() {
        EvidenceSourceType first = new EvidenceSourceType(
                "OFFICIAL_REGISTRY",
                "Official Registry",
                "First description"
        );

        EvidenceSourceType second = new EvidenceSourceType(
                "OFFICIAL_REGISTRY",
                "Different name",
                "Second description"
        );

        assertThat(first).isEqualTo(second);
    }

    @Test
    void shouldHaveSameHashCodeForTypesWithSameCode() {
        EvidenceSourceType first = new EvidenceSourceType(
                "OFFICIAL_REGISTRY",
                "Official Registry",
                "First description"
        );

        EvidenceSourceType second = new EvidenceSourceType(
                "OFFICIAL_REGISTRY",
                "Different name",
                "Second description"
        );

        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }

    @Test
    void shouldConsiderTypesWithDifferentCodesDifferent() {
        EvidenceSourceType first = createValidType();

        EvidenceSourceType second = new EvidenceSourceType(
                "PUBLICATION",
                "Publication",
                "A published document"
        );

        assertThat(first).isNotEqualTo(second);
    }

    private EvidenceSourceType createValidType() {
        return new EvidenceSourceType(
                "OFFICIAL_REGISTRY",
                "Official Registry",
                "An official institutional registry"
        );
    }
}