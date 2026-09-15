package com.fodk.gemcolony.item.custom;

import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class EssenceBottle extends Item {

    public final EssenceType essenceType;

    public EssenceBottle(Properties properties, EssenceType essenceType) {
        super(properties);
        this.essenceType = essenceType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos positionClicked = context.getClickedPos();
        Player player = context.getPlayer();

        if(!level.isClientSide()){
            if(checkGemStarterSpawnable(level, positionClicked)){
                player.sendSystemMessage(Component.literal(essenceType.getGem() + " has spawned at: "
                        + positionClicked.getX() + "," + positionClicked.getY() + "," + positionClicked.getZ()));

                //consume and return bottle / durability
                context.getItemInHand().consume(1, player);
                if(context.getItemInHand().isEmpty()){
                    player.setItemInHand(context.getHand(), new ItemStack(Items.GLASS_BOTTLE));
                }
                //sound
                level.playSound(null, positionClicked, ModSounds.GEM_POOF.get(), SoundSource.BLOCKS, 1.5f, 0.8f + 0.4f * level.getRandom().nextFloat());
                //particles
                spawnGemStarterParticles(level, positionClicked);
                //add a gem_item with the corresponding data to player
                ItemStack gemItem = new ItemStack(ModItems.PEBBLE_GEM.get());
                player.getInventory().add(gemItem);
            }
        }

        return InteractionResult.SUCCESS;
    }

    private void spawnGemStarterParticles(Level level, BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);

        for(int i = 0; i < 20; i++) {
            ServerLevel serverLevel = (ServerLevel) level;

            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState),
                    blockPos.getX() + 0.5d, blockPos.getY() + 1, blockPos.getZ() + 0.5d, 1,
                    Math.cos(i * 18) * 0.15d, 0.15d, Math.sin(i * 18) * 0.15d, 0.1);
        }
    }

    private boolean checkGemStarterSpawnable(Level level, BlockPos blockPos){
        BlockState blockState = level.getBlockState(blockPos);
        return blockState.is(essenceType.getBlockNeeded());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        if(Minecraft.getInstance().hasShiftDown()){
            builder.accept(Component.translatable("tooltip.gemcolony.essence_bottle.detailed_tooltip",
                    essenceType.getBlockNeeded().getName(),
                    Component.literal(essenceType.getGem())));
        }else{
            builder.accept(Component.translatable("tooltip.gemcolony.essence_bottle.tooltip"));
        }
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
    }
}
