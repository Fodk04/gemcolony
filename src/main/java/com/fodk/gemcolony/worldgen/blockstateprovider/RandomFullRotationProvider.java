package com.fodk.gemcolony.worldgen.blockstateprovider;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

import java.util.List;

public class RandomFullRotationProvider extends BlockStateProvider {

    public static final MapCodec<RandomFullRotationProvider> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BuiltInRegistries.BLOCK.byNameCodec()
                            .fieldOf("block")
                            .forGetter(provider -> provider.block),

                    BuiltInRegistries.BLOCK.byNameCodec()
                            .listOf()
                            .fieldOf("attachment_blocks")
                            .forGetter(provider -> provider.attachmentBlocks))

                    .apply(instance, RandomFullRotationProvider::new));

    private final Block block;
    private final List<Block> attachmentBlocks;

    public RandomFullRotationProvider(Block block, Block... attachmentBlocks) {
        this(block, List.of(attachmentBlocks));
    }

    public RandomFullRotationProvider(Block block, List<Block> attachmentBlocks) {
        this.block = block;
        this.attachmentBlocks = List.copyOf(attachmentBlocks);
    }

    @Override
    protected BlockStateProviderType<?> type() {
        return ModStateProviders.RANDOM_FULL_ROTATION.get();
    }

    @Override
    public BlockState getState(WorldGenLevel worldGenLevel, RandomSource randomSource, BlockPos blockPos) {
        BlockState state = block.defaultBlockState();

        List<Direction> supportingDirections = new java.util.ArrayList<>();

        for (Direction direction : Direction.values()) {
            BlockPos neighbourPos = blockPos.relative(direction);
            BlockState neighbourState = worldGenLevel.getBlockState(neighbourPos);

            if (attachmentBlocks.contains(neighbourState.getBlock())) {
                supportingDirections.add(direction);
            }
        }

        if (!supportingDirections.isEmpty()) {

            Direction support = supportingDirections.get(randomSource.nextInt(supportingDirections.size()));

            if (support == Direction.DOWN) {

                state = state.setValue(FaceAttachedHorizontalDirectionalBlock.FACE, AttachFace.FLOOR);
                state = state.setValue(HorizontalDirectionalBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomSource));

            } else if (support == Direction.UP) {

                state = state.setValue(FaceAttachedHorizontalDirectionalBlock.FACE, AttachFace.CEILING);
                state = state.setValue(HorizontalDirectionalBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomSource));

            } else {

                state = state.setValue(FaceAttachedHorizontalDirectionalBlock.FACE, AttachFace.WALL);
                state = state.setValue(HorizontalDirectionalBlock.FACING, support.getOpposite());
            }
        }

        if (state.hasProperty(BlockStateProperties.WATERLOGGED) && worldGenLevel.getFluidState(blockPos).is(FluidTags.WATER)) {

            state = state.setValue(BlockStateProperties.WATERLOGGED, true);
        }

        return state;
    }
}
