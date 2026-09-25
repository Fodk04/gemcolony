package com.fodk.gemcolony.block.custom;

import net.minecraft.util.StringRepresentable;

public enum DrillPart implements StringRepresentable {
    CENTER("center"),

    TOP_NORTH_WEST("top_north_west"),
    TOP_NORTH("top_north"),
    TOP_NORTH_EAST("top_north_east"),
    TOP_WEST("top_west"),
    TOP_CENTER("top_center"),
    TOP_EAST("top_east"),
    TOP_SOUTH_WEST("top_south_west"),
    TOP_SOUTH("top_south"),
    TOP_SOUTH_EAST("top_south_east"),

    BOTTOM("bottom");

    private final String name;

    DrillPart(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}