package com.fodk.gemcolony.block.custom;

import net.minecraft.util.StringRepresentable;

public enum TankHalf implements StringRepresentable {
    TANK_BOTTOM("tank_bottom"),
    TANK_TOP("tank_top");

    private final String name;

    TankHalf(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}