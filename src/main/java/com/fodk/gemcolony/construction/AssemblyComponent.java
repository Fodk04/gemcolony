package com.fodk.gemcolony.construction;

import com.fodk.gemcolony.block.custom.TankHalf;
import net.minecraft.world.level.block.Rotation;

public record AssemblyComponent(
        Blueprint blueprint,
        int x,
        int y,
        int z,
        Rotation rotation
) {
}
