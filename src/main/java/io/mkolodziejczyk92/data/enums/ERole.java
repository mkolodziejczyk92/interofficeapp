package io.mkolodziejczyk92.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ERole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String type;
}
