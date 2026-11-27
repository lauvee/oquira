package org.oquira.institution.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EvidenceVerificationStatusTest {

    @Test
    void shouldContainAllExpectedStatuses() {
        assertThat(EvidenceVerificationStatus.values())
                .containsExactly(
                        EvidenceVerificationStatus.PENDING,
                        EvidenceVerificationStatus.VERIFIED,
                        EvidenceVerificationStatus.DISPUTED,
                        EvidenceVerificationStatus.EXPIRED
                );
    }

    @Test
    void shouldResolveStatusByName() {
        assertThat(EvidenceVerificationStatus.valueOf("PENDING"))
                .isEqualTo(EvidenceVerificationStatus.PENDING);

        assertThat(EvidenceVerificationStatus.valueOf("VERIFIED"))
                .isEqualTo(EvidenceVerificationStatus.VERIFIED);

        assertThat(EvidenceVerificationStatus.valueOf("DISPUTED"))
                .isEqualTo(EvidenceVerificationStatus.DISPUTED);

        assertThat(EvidenceVerificationStatus.valueOf("EXPIRED"))
                .isEqualTo(EvidenceVerificationStatus.EXPIRED);
    }
}