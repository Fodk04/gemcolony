package com.fodk.gemcolony.worldgen.blockstateprovider;

import com.fodk.gemcolony.block.ModBlocks;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import org.jspecify.annotations.Nullable;

public class RandomHorizontalFacingProvider extends BlockStateProvider {

    public static final MapCodec<RandomHorizontalFacingProvider> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BuiltInRegistries.BLOCK.byNameCodec()
                            .fieldOf("block")
                            .forGetter(provider -> provider.block))
                    .apply(instance, RandomHorizontalFacingProvider::new));

    private final Block block;

    public RandomHorizontalFacingProvider(Block block) {
        this.block = block;
    }

    @Override
    protected BlockStateProviderType<?> type() {
        return ModStateProviders.RANDOM_HORIZONTAL_FACING.get();
    }

    @Override
    public BlockState getState(WorldGenLevel worldGenLevel, RandomSource randomSource, BlockPos blockPos) {
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(randomSource);
        BlockState state = block.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, direction);

        if (state.hasProperty(BlockStateProperties.WATERLOGGED) && worldGenLevel.getFluidState(blockPos).is(FluidTags.WATER)) {
            state = state.setValue(BlockStateProperties.WATERLOGGED, true);
        }

        return state;
    }
}
