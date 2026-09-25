package com.fodk.gemcolony.event;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.block.entity.ModBlockEntities;
import com.fodk.gemcolony.block.entity.custom.InjectorBlockEntity;
import com.fodk.gemcolony.entity.custom.GemRisingItemEntity;
import com.fodk.gemcolony.fluid.ModFluids;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.item.custom.EssenceType;
import com.fodk.gemcolony.item.custom.GemItem;
import com.fodk.gemcolony.networking.ClientPayloadHandler;
import com.fodk.gemcolony.networking.packet.*;
import com.fodk.gemcolony.potion.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

@EventBusSubscriber(modid = GemColony.MOD_ID)
public class ModEvents {
    //GEM STAFFS ???
    //REBEL STAFF FOR DEBUG
    //POOF STAFF FOR DEBUG
    //COMMAND STAFF FOR GAMEPLAY ???
    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, ModItems.BLUE_ESSENCE_BOTTLE.get(), ModPotions.SADNESS_POTION);
    }

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1")
                .executesOn(HandlerThread.MAIN);

        registrar.playToServer(BubblingPacketC2S.TYPE, BubblingPacketC2S.STREAM_CODEC, ClientPayloadHandler::handleBubblingPacket);
        registrar.playToServer(SetNicknamePacketC2S.TYPE, SetNicknamePacketC2S.STREAM_CODEC, ClientPayloadHandler::handleSetNicknamePacket);
        registrar.playToServer(SetAppearancePacketC2S.TYPE, SetAppearancePacketC2S.STREAM_CODEC, ClientPayloadHandler::handleSetAppearancePacket);
        registrar.playToServer(StartAnalysisPacketC2S.TYPE, StartAnalysisPacketC2S.STREAM_CODEC, ClientPayloadHandler::handleStartAnalysisPacket);
        registrar.playToClient(AnalysisResultsPacketS2C.TYPE, AnalysisResultsPacketS2C.STREAM_CODEC, ClientPayloadHandler::handleAnalysisResultsPacket);
        registrar.playToServer(ConfirmConstructionPacketC2S.TYPE, ConfirmConstructionPacketC2S.STREAM_CODEC, ClientPayloadHandler::handleConfirmConstructionPacket);
        registrar.playToServer(CycleInjectorOrientationPacketC2S.TYPE, CycleInjectorOrientationPacketC2S.STREAM_CODEC, ClientPayloadHandler::handleCycleInjectorOrientationPacket);
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof ItemEntity itemEntity))
            return;

        if(itemEntity instanceof GemRisingItemEntity)
            return;

        if (itemEntity.level().isClientSide())
            return;

        if (!(itemEntity.getItem().getItem() instanceof GemItem gemItem))
            return;


        gemItem.tickReformation(itemEntity);
    }

    @SubscribeEvent
    public static void fillEssenceBottle(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Level level = player.level();

        if (level.isClientSide()) {
            return;
        }

        ItemStack stack = player.getItemInHand(event.getHand());

        if (!stack.is(Items.GLASS_BOTTLE)) {
            return;
        }

        BlockHitResult hit = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (hit.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockPos pos = hit.getBlockPos();
        FluidState fluidState = level.getFluidState(pos);

        if (!fluidState.isSource()) {
            return;
        }

        EssenceType essenceType = null;

        for (EssenceType type : EssenceType.values()) {
            if (fluidState.getType() == type.getFluid()) {
                essenceType = type;
                break;
            }
        }

        if (essenceType == null) {
            return;
        }

        ItemStack essenceBottle = new ItemStack(essenceType.getBottle());

        stack.shrink(1);

        if (stack.isEmpty()) {
            player.setItemInHand(event.getHand(), essenceBottle);
        } else if (!player.getInventory().add(essenceBottle)) {
            player.drop(essenceBottle, false);
        }

        // 1/3 chance to consume the esssence source
        if (level.getRandom().nextInt(3) == 0) {
            level.setBlock(
                    pos,
                    Blocks.AIR.defaultBlockState(),
                    3
            );
        }

        level.playSound(
                null,
                pos,
                SoundEvents.BOTTLE_FILL,
                SoundSource.BLOCKS,
                1.0F,
                1.0F
        );

        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.Fluid.BLOCK,
                ModBlockEntities.INJECTOR_BE.get(),
                (blockEntity, direction) -> blockEntity.getEssenceHandler()
        );
    }
}
