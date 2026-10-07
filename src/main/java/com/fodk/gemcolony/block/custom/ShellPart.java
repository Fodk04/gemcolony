package com.fodk.gemcolony.block.custom;

import net.minecraft.util.StringRepresentable;

public enum ShellPart implements StringRepresentable {
    CENTER("center"),

    BOTTOM_NORTH_WEST("bottom_north_west"),
    BOTTOM_NORTH("bottom_north"),
    BOTTOM_NORTH_EAST("bottom_north_east"),
    BOTTOM_WEST("bottom_west"),
    BOTTOM_EAST("bottom_east"),
    BOTTOM_SOUTH_WEST("bottom_south_west"),
    BOTTOM_SOUTH("bottom_south"),
    BOTTOM_SOUTH_EAST("bottom_south_east"),

    TOP_NORTH_WEST("top_north_west"),
    TOP_NORTH("top_north"),
    TOP_NORTH_EAST("top_north_east"),
    TOP_WEST("top_west"),
    TOP_CENTER("top_center"),
    TOP_EAST("top_east"),
    TOP_SOUTH_WEST("top_south_west"),
    TOP_SOUTH("top_south"),
    TOP_SOUTH_EAST("top_south_east");

    private final String name;

    ShellPart(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
