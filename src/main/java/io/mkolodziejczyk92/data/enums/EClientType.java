package io.mkolodziejczyk92.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EClientType {
    INDIVIDUAL("indywidualny"),
    COMPANY("firma");

    private String type;
}
