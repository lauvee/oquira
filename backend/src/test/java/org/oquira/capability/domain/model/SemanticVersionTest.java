package org.oquira.capability.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SemanticVersionTest {

    @Test
    void shouldCreateSemanticVersion() {
        SemanticVersion version = new SemanticVersion(1, 2, 3);

        assertThat(version.getMajor()).isEqualTo(1);
        assertThat(version.getMinor()).isEqualTo(2);
        assertThat(version.getPatch()).isEqualTo(3);
        assertThat(version.toString()).isEqualTo("1.2.3");
    }

    @Test
    void shouldAllowZeroVersionComponents() {
        SemanticVersion version = new SemanticVersion(0, 0, 0);

        assertThat(version.toString()).isEqualTo("0.0.0");
    }

    @Test
    void shouldRejectNegativeMajorVersion() {
        assertThatThrownBy(() -> new SemanticVersion(-1, 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNegativeMinorVersion() {
        assertThatThrownBy(() -> new SemanticVersion(1, -1, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNegativePatchVersion() {
        assertThatThrownBy(() -> new SemanticVersion(1, 2, -1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldCompareVersionsByValue() {
        SemanticVersion first = new SemanticVersion(1, 2, 3);
        SemanticVersion second = new SemanticVersion(1, 2, 3);

        assertThat(first)
                .isEqualTo(second)
                .hasSameHashCodeAs(second);
    }
}