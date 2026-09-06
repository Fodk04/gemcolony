package com.fodk.gemcolony;

import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.keymapping.ModKeyMappings;
import com.fodk.gemcolony.networking.packet.TestPacketC2S;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = GemColony.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = GemColony.MOD_ID, value = Dist.CLIENT)
public class GemColonyClient {
    public GemColonyClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        ModKeyMappings.register();
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        GemColony.LOGGER.info("HELLO FROM CLIENT SETUP");
        GemColony.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void onComputeFovModifierEvent(ComputeFovModifierEvent event){
        if(event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() == ModItems.CHROMA_BOW.get()) {
            float fovModifier = 1f;
            int ticksUsingItem = event.getPlayer().getTicksUsingItem();
            float deltaTicks = (float)ticksUsingItem / 20f;
            if(deltaTicks > 1f) {
                deltaTicks = 1f;
            } else {
                deltaTicks *= deltaTicks;
            }
            fovModifier *= 1f - deltaTicks * 0.15f;
            event.setNewFovModifier(fovModifier);
        }
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ModKeyMappings.PRESS_BUBBLE_GEM.get());
    }

    private static boolean wasDown = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        boolean isDown = ModKeyMappings.PRESS_BUBBLE_GEM.get().isDown();

        if (isDown && !wasDown) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Bubbled a gem"));
            ClientPacketDistributor.sendToServer(new TestPacketC2S("test", 69));
        }

        wasDown = isDown;
    }
}
