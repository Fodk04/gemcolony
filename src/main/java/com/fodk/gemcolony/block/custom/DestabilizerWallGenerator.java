package com.fodk.gemcolony.block.custom;

import com.fodk.gemcolony.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class DestabilizerWallGenerator extends Block {

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public DestabilizerWallGenerator(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if(!level.isClientSide()){
            level.setBlockAndUpdate(pos, state.cycle(ACTIVE));
        }

        level.addParticle(ParticleTypes.GLOW,
                pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f,
                0,1,0);
        level.playSound(player, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 2, 1);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState onState, Entity entity) {
        if(onState.getValue(ACTIVE)){
            if(entity instanceof Player player){
                player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 10, 5));
            }

            if(entity instanceof ItemEntity itemEntity){
                if(itemDestabilizable(itemEntity.getItem())){
                    itemEntity.setItem(new ItemStack(Items.GUNPOWDER, itemEntity.getItem().getCount()));
                }
            }
        }

        super.stepOn(level, pos, onState, entity);
    }

    private boolean itemDestabilizable(ItemStack item) {
        return item.is(ModTags.Items.CHROMAS);
    }
}
