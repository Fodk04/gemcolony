package com.fodk.gemcolony.worldgen.placement;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class CaveSurfacePlacement extends PlacementModifier {

    public static final MapCodec<CaveSurfacePlacement> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BuiltInRegistries.BLOCK.byNameCodec()
                            .listOf()
                            .fieldOf("blocks")
                            .forGetter(placement -> placement.blocks)).
                    apply(instance, CaveSurfacePlacement::new));

    private final List<Block> blocks;

    public CaveSurfacePlacement(Block... blocks) {
        this(List.of(blocks));
    }

    public CaveSurfacePlacement(List<Block> blocks) {
        this.blocks = List.copyOf(blocks);
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos origin) {
        List<BlockPos> candidates = new ArrayList<>();

        int centerX = origin.getX();
        int centerZ = origin.getZ();

        int minY = context.getMinY();
        int maxY = minY + context.getGenDepth();

        for (int x = centerX - 2; x <= centerX + 2; x++) {
            for (int z = centerZ - 2; z <= centerZ + 2; z++) {
                for (int y = minY + 1; y < maxY - 1; y++) {

                    BlockPos pos = new BlockPos(x, y, z);

                    if (!context.getBlockState(pos).isAir()) {
                        continue;
                    }

                    for (Direction direction : Direction.values()) {
                        BlockPos supportPos = pos.relative(direction);
                        BlockState supportState = context.getBlockState(supportPos);

                        if (blocks.contains(supportState.getBlock())) {
                            candidates.add(pos);
                            break;
                        }
                    }
                }
            }
        }

        if (candidates.isEmpty()) {
            return Stream.empty();
        }

        return Stream.of(candidates.get(random.nextInt(candidates.size())));
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementModifiers.CAVE_SURFACE.get();
    }
}
