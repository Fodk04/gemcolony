package com.fodk.gemcolony.block.custom;

import net.minecraft.util.StringRepresentable;

public enum CrystalPart implements StringRepresentable {
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

    MIDDLE_NORTH_WEST("middle_north_west"),
    MIDDLE_NORTH("middle_north"),
    MIDDLE_NORTH_EAST("middle_north_east"),
    MIDDLE_WEST("middle_west"),
    MIDDLE_EAST("middle_east"),
    MIDDLE_SOUTH_WEST("middle_south_west"),
    MIDDLE_SOUTH("middle_south"),
    MIDDLE_SOUTH_EAST("middle_south_east"),

    BOTTOM_NORTH_WEST("bottom_north_west"),
    BOTTOM_NORTH("bottom_north"),
    BOTTOM_NORTH_EAST("bottom_north_east"),
    BOTTOM_WEST("bottom_west"),
    BOTTOM_CENTER("bottom_center"),
    BOTTOM_EAST("bottom_east"),
    BOTTOM_SOUTH_WEST("bottom_south_west"),
    BOTTOM_SOUTH("bottom_south"),
    BOTTOM_SOUTH_EAST("bottom_south_east");

    private final String name;

    CrystalPart(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
