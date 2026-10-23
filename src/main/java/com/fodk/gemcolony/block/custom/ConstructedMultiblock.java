package com.fodk.gemcolony.block.custom;

import com.fodk.gemcolony.construction.ConstructionRequirement;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import java.util.List;

public interface ConstructedMultiblock {

    default int getMaxConstructionStage() {
        return 1;
    }

    default List<ConstructionRequirement> getConstructionRequirements() {
        return List.of();
    }

    default BlockState getConstructionState(Level level, BlockPos pos, BlockState state) {
        return state;
    }

    default BlockPos getConstructionCenter(Level level, BlockPos pos, BlockState state) {
        return pos;
    }

    void placeStructure(Level level, BlockPos pos, BlockState state);

    default boolean canPlace(Level level, BlockPos pos, BlockState state) {
        return true;
    }

    BlockPos getController(Level level, BlockPos pos, BlockState state);

    default BlockPos getConstructionApproachPosition(Level level, BlockPos pos, BlockState state) {
        return getController(level, pos, state);
    }

    void advanceConstruction(Level level, BlockPos centerPos, int stage);

    default int getProgressNeeded() {
        return 200;
    }
}