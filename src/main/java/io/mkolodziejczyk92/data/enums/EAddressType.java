package io.mkolodziejczyk92.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EAddressType {
    INVESTMENT("investment"),
    RESIDENCE("residence");


    private String type;
}
