package io.mkolodziejczyk92.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EInvoiceType {
    ADVANCE("advance"),
    FINAL("final");
    private final String name;
}
