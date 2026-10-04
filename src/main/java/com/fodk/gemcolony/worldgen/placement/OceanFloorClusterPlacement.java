package com.fodk.gemcolony.worldgen.placement;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class OceanFloorClusterPlacement extends PlacementModifier {

    public static final MapCodec<OceanFloorClusterPlacement> CODEC = MapCodec.unit(new OceanFloorClusterPlacement());

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos origin) {
        int x = origin.getX() + random.nextInt(5) - 2;
        int z = origin.getZ() + random.nextInt(5) - 2;

        int y = context.getHeight(Heightmap.Types.OCEAN_FLOOR, x, z);

        return Stream.of(new BlockPos(x, y, z));
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementModifiers.OCEAN_FLOOR_CLUSTER.get();
    }
}
