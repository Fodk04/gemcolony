package com.fodk.gemcolony.event;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.custom.GemRisingItemEntity;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.item.custom.GemItem;
import com.fodk.gemcolony.networking.ClientPayloadHandler;
import com.fodk.gemcolony.networking.packet.*;
import com.fodk.gemcolony.potion.ModPotions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

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
}
