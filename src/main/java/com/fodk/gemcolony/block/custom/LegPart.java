package com.fodk.gemcolony.block.custom;

import net.minecraft.util.StringRepresentable;

public enum LegPart implements StringRepresentable {
    CENTER("center"),
    TOP("top"),
    BOTTOM("bottom"),
    SIDE("side"),
    SIDE_TOP("side_top"),
    SIDE_BOTTOM("side_bottom");

    private final String name;

    LegPart(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
