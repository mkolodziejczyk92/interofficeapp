package io.mkolodziejczyk92.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EVoivodeship {
    LOWER_SILESIA("dolnośląskie"),
    KUYAVIA_POMERANIA("kujawsko-pomorskie"),
    LODZKIE("łódzkie"),
    LUBLIN("lubelskie"),
    LUBUSZ("lubuskie"),
    LESSER_POLAND("małopolskie"),
    MASOVIA("mazowieckie"),
    SUBCARPATHIA("podkarpackie"),
    POMERANIA("pomorskie"),
    SILESIA("śląskie"),
    WARMIA_MASURIA("warmińsko_mazurskie"),
    GREATER_POLAND("wielkopolskie"),
    WEST_POMERANIA("zachodnio-pomorskie");

    private String nameOfVoivodeship;

}
