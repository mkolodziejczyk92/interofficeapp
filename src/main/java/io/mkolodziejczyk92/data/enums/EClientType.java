package io.mkolodziejczyk92.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EClientType {
    INDIVIDUAL("Individual"),
    COMPANY("Company");

    private final String type;
}
