package io.mkolodziejczyk92.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ECountry {
    POLAND("Polska"),
    GERMANY("Niemcy"),
    UNITED_KINGDOM("Wielka Brytania");

    private String country;

}
