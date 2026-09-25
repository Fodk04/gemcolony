package com.fodk.gemcolony.block.state;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class GemColonyBlockStateProperties {
    public static final IntegerProperty CONSTRUCTION_STAGE =
            IntegerProperty.create("construction_stage", 0, 1);
}
