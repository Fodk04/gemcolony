package com.fodk.gemcolony.event;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.networking.ClientPayloadHandler;
import com.fodk.gemcolony.networking.packet.TestPacketC2S;
import com.fodk.gemcolony.potion.ModPotions;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
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

        builder.addMix(Potions.AWKWARD, ModItems.BLUE_ESSENCE_BOTTLE.get(), ModPotions.STINKY_POTION);
    }

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1")
                .executesOn(HandlerThread.MAIN);

        registrar.playToServer(TestPacketC2S.TYPE, TestPacketC2S.STREAM_CODEC, ClientPayloadHandler::handleTestPacket);
    }
}
