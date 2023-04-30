package io.mkolodziejczyk92.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EPurchaseStatus {
    NOT_PAID("Not paid"),
    PAID_NOT_ORDERED("Paid - not ordered"),
    ORDERED("Ordered"),
    SUPPLIED_FOR_STORAGE("Supplied for storage"),
    COMPLETED("Completed");

    private final String statusName;


}
