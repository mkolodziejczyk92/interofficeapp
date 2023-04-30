package io.mkolodziejczyk92.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ECommodityType {
    INTERNAL_DOORS("Internal Doors"),
    EXTERNAL_DOORS("External Doors"),
    WINDOWS("Windows"),
    EXTERNAL_WINDOWS_BLINDS_TYPE_C("External Windows Blinds Type C"),
    EXTERNAL_WINDOWS_BLINDS_TYPE_Z("External Windows Blinds Type C"),
    EXTERNAL_ROLLER_SHUTTER("External Roller Shutter"),
    INTERIOR_WINDOW_SHADES("Interior Window Shades");

    private final String name;
}
