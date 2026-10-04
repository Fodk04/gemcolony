package com.fodk.gemcolony.worldgen.placement;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public class SurfaceClusterPlacement extends PlacementModifier {

    public static final MapCodec<SurfaceClusterPlacement> CODEC = MapCodec.unit(new SurfaceClusterPlacement());

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos origin) {
        int x = origin.getX() + random.nextInt(5) - 2;
        int z = origin.getZ() + random.nextInt(5) - 2;

        int y = context.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

        return Stream.of(new BlockPos(x, y, z));
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementModifiers.NEARBY_SURFACE.get();
    }
}
