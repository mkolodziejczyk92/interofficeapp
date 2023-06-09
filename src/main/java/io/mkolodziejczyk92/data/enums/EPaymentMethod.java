package io.mkolodziejczyk92.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EPaymentMethod {
    CASH("cash"),
    BANK_TRANSFER("bank transfer");

    private final String name;

}
