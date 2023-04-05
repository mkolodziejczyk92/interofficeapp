package io.mkolodziejczyk92.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EAddressType {
    INVESTMENT("inwestycyjny"),
    RESIDENCE("zamieszkania");

    private String type;
}
