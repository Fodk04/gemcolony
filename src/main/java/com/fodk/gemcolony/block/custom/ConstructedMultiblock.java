package com.fodk.gemcolony.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ConstructedMultiblock {

    default BlockState getConstructionState(
            Level level,
            BlockPos pos,
            BlockState state
    ) {
        return state;
    }

    void placeStructure(
            Level level,
            BlockPos pos,
            BlockState state
    );
}