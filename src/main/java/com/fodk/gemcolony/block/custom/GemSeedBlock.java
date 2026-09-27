package com.fodk.gemcolony.block.custom;

import com.fodk.gemcolony.block.entity.ModBlockEntities;
import com.fodk.gemcolony.block.entity.custom.GemSeedBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class GemSeedBlock extends BaseEntityBlock {

    public GemSeedBlock(Properties properties) {
        super(properties.randomTicks());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(GemSeedBlock::new);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GemSeedBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type,
                ModBlockEntities.GEM_SEED_BE.get(),
                (level1, pos, state1, blockEntity) -> blockEntity.tick());
    }
}
