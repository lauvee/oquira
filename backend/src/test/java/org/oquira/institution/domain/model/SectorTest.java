package org.oquira.institution.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SectorTest {

    private final UUID sectorId = UUID.randomUUID();

    @Test
    void shouldCreateSectorWithValidData() {
        Sector sector = createValidSector();

        assertThat(sector.getSectorId()).isEqualTo(sectorId);
        assertThat(sector.getCode()).isEqualTo("BANKING");
        assertThat(sector.getName()).isEqualTo("Banking");
        assertThat(sector.getDescription())
                .isEqualTo("Financial institutions providing banking services");
        assertThat(sector.getStatus()).isEqualTo(SectorStatus.ACTIVE);
    }

    @Test
    void shouldRejectNullSectorId() {
        assertThatThrownBy(() -> new Sector(
                null, "BANKING", "Banking", "Description", SectorStatus.ACTIVE
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectNullCode() {
        assertThatThrownBy(() -> new Sector(
                sectorId, null, "Banking", "Description", SectorStatus.ACTIVE
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectInvalidCode() {
        assertThatThrownBy(() -> new Sector(
                sectorId, "banking", "Banking", "Description", SectorStatus.ACTIVE
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectCodeStartingWithDigit() {
        assertThatThrownBy(() -> new Sector(
                sectorId, "1_BANKING", "Banking", "Description", SectorStatus.ACTIVE
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldAcceptCodeWithUppercaseLettersDigitsAndUnderscores() {
        Sector sector = new Sector(
                sectorId,
                "FINANCIAL_SECTOR_01",
                "Financial Sector",
                "Description",
                SectorStatus.ACTIVE
        );

        assertThat(sector.getCode()).isEqualTo("FINANCIAL_SECTOR_01");
    }

    @Test
    void shouldRejectNullName() {
        assertThatThrownBy(() -> new Sector(
                sectorId, "BANKING", null, "Description", SectorStatus.ACTIVE
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankName() {
        assertThatThrownBy(() -> new Sector(
                sectorId, "BANKING", "   ", "Description", SectorStatus.ACTIVE
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullDescription() {
        assertThatThrownBy(() -> new Sector(
                sectorId, "BANKING", "Banking", null, SectorStatus.ACTIVE
        )).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldRejectBlankDescription() {
        assertThatThrownBy(() -> new Sector(
                sectorId, "BANKING", "Banking", "   ", SectorStatus.ACTIVE
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullStatus() {
        assertThatThrownBy(() -> new Sector(
                sectorId, "BANKING", "Banking", "Description", null
        )).isInstanceOf(NullPointerException.class);
    }

    private Sector createValidSector() {
        return new Sector(
                sectorId,
                "BANKING",
                "Banking",
                "Financial institutions providing banking services",
                SectorStatus.ACTIVE
        );
    }
}