package com.fodk.gemcolony;

import com.fodk.gemcolony.block.entity.screen.InjectorScreen;
import com.fodk.gemcolony.construction.Constructor;
import com.fodk.gemcolony.construction.ConstructorManager;
import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.entity.client.render.GemRenderPipelines;
import com.fodk.gemcolony.entity.client.renderer.ConstructorRenderer;
import com.fodk.gemcolony.entity.client.renderer.PebbleRenderer;
import com.fodk.gemcolony.entity.client.renderer.PeridotRenderer;
import com.fodk.gemcolony.entity.client.screen.GemScreen;
import com.fodk.gemcolony.entity.custom.gem.starter.StarterGemEntity;
import com.fodk.gemcolony.fluid.ModFluidTypes;
import com.fodk.gemcolony.fluid.ModFluids;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.keymapping.ModKeyMappings;
import com.fodk.gemcolony.menu.InjectorMenu;
import com.fodk.gemcolony.menu.ModMenus;
import com.fodk.gemcolony.networking.packet.BubblingPacketC2S;
import com.fodk.gemcolony.networking.packet.ConfirmConstructionPacketC2S;
import com.fodk.gemcolony.util.ColorUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.fluid.FluidTintSources;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.minecraft.client.resources.model.sprite.Material;
import org.joml.Vector4f;

import javax.annotation.Nullable;
import java.awt.*;

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
        //regiister renderers
    }

    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.PERIDOT.get(), context -> new PeridotRenderer(context));
        event.registerEntityRenderer(ModEntities.PEBBLE.get(), context -> new PebbleRenderer(context));
        event.registerEntityRenderer(ModEntities.GEM_RISING_ITEM.get(), ItemEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerRenderPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(GemRenderPipelines.GEM_REFORM);
        event.registerPipeline(GemRenderPipelines.CONSTRUCTOR_GHOST);
    }

    @SubscribeEvent
    public static void register(RegisterFluidModelsEvent event) {

        event.register(
                new FluidModel.Unbaked(
                        material("essence_still"),
                        material("essence_flow"),
                        null,
                        FluidTintSources.constant(ColorUtil.colorToInt(new Color(10,60,200)))),
                ModFluids.BLUE_ESSENCE,
                ModFluids.FLOWING_BLUE_ESSENCE);

        event.register(
                new FluidModel.Unbaked(
                        material("essence_still"),
                        material("essence_flow"),
                        null,
                        FluidTintSources.constant(ColorUtil.colorToInt(new Color(220,250,0)))),
                ModFluids.YELLOW_ESSENCE,
                ModFluids.FLOWING_YELLOW_ESSENCE
        );

        event.register(
                new FluidModel.Unbaked(
                        material("essence_still"),
                        material("essence_flow"),
                        null,
                        FluidTintSources.constant(ColorUtil.colorToInt(new Color(255,255,255)))),
                ModFluids.WHITE_ESSENCE,
                ModFluids.FLOWING_WHITE_ESSENCE
        );

        event.register(
                new FluidModel.Unbaked(
                        material("essence_still"),
                        material("essence_flow"),
                        null,
                        FluidTintSources.constant(ColorUtil.colorToInt(new Color(210,20,200)))),
                ModFluids.PINK_ESSENCE,
                ModFluids.FLOWING_PINK_ESSENCE
        );
    }

    private static Material material(String texture) {
        return new Material(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "block/" + texture));
    }

    @SubscribeEvent
    public static void register(RegisterClientExtensionsEvent event) {

        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
                fluidFogColor.set(0.1F, 0.35F, 1.0F, 1.0F);
            }

            @Override
            public void modifyFogRender(Camera camera, @Nullable FogEnvironment environment, float renderDistance, float partialTick, FogData fogData) {
                fogData.environmentalStart = 0.0F;
                fogData.environmentalEnd = 12.0F;
                fogData.skyEnd = 12.0F;
                fogData.cloudEnd = 12.0F;
            }
        }, ModFluidTypes.BLUE_ESSENCE);

        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
                fluidFogColor.set(1.0f, 0.8f, 0.1f, 1.0f);
            }

            @Override
            public void modifyFogRender(Camera camera, @Nullable FogEnvironment environment, float renderDistance, float partialTick, FogData fogData) {
                fogData.environmentalStart = 0.0F;
                fogData.environmentalEnd = 12.0F;
                fogData.skyEnd = 12.0F;
                fogData.cloudEnd = 12.0F;
            }
        }, ModFluidTypes.YELLOW_ESSENCE);

        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
                fluidFogColor.set(1.0f, 1.0f, 1.0f, 1.0f);
            }

            @Override
            public void modifyFogRender(Camera camera, @Nullable FogEnvironment environment, float renderDistance, float partialTick, FogData fogData) {
                fogData.environmentalStart = 0.0F;
                fogData.environmentalEnd = 12.0F;
                fogData.skyEnd = 12.0F;
                fogData.cloudEnd = 12.0F;
            }
        }, ModFluidTypes.WHITE_ESSENCE);

        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
                fluidFogColor.set(1.0f, 0.25f, 0.65f, 1.0f);
            }

            @Override
            public void modifyFogRender(Camera camera, @Nullable FogEnvironment environment, float renderDistance, float partialTick, FogData fogData) {
                fogData.environmentalStart = 0.0F;
                fogData.environmentalEnd = 12.0F;
                fogData.skyEnd = 12.0F;
                fogData.cloudEnd = 12.0F;
            }
        }, ModFluidTypes.PINK_ESSENCE);
    }

    @SubscribeEvent
    public static void renderConstructorGhost(SubmitCustomGeometryEvent event) {
        ConstructorRenderer.render(event);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event){
        event.register(ModMenus.GEM_MENU.get(), GemScreen::new);
        event.register(ModMenus.INJECTOR_MENU.get(), InjectorScreen::new);
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
        event.register(ModKeyMappings.CONSTRUCTOR_UP.get());
        event.register(ModKeyMappings.CONSTRUCTOR_DOWN.get());
        event.register(ModKeyMappings.CONSTRUCTOR_LEFT.get());
        event.register(ModKeyMappings.CONSTRUCTOR_RIGHT.get());
        event.register(ModKeyMappings.CONSTRUCTOR_RAISE.get());
        event.register(ModKeyMappings.CONSTRUCTOR_LOWER.get());
        event.register(ModKeyMappings.CONSTRUCTOR_ROTATE.get());
        event.register(ModKeyMappings.CONSTRUCTOR_CONFIRM.get());
        event.register(ModKeyMappings.CONSTRUCTOR_CANCEL.get());
    }

    private static boolean wasDown = false;
    private static boolean constructorRotateWasDown = false;
    private static boolean wasPlacing = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        //GEM BUBBLING
        boolean isDown = ModKeyMappings.PRESS_BUBBLE_GEM.get().isDown();

        if (isDown && !wasDown) {
            ClientPacketDistributor.sendToServer(new BubblingPacketC2S());
        }

        wasDown = isDown;

        //CONSTRUCTION
        if (Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;

            Constructor constructor = ConstructorManager.get(player);

            boolean placing = constructor.isPlacing();

            if (placing && !wasPlacing) {
                System.out.println(
                        "Entered Constructor placement mode at: "
                                + constructor.getPlacementPos()
                );
            }

            if (!placing && wasPlacing) {
                System.out.println("Exited Constructor placement mode");
            }

            wasPlacing = placing;

            if (constructor.isPlacing()) {

                if (ModKeyMappings.CONSTRUCTOR_CANCEL.get().consumeClick()) {
                    constructor.cancelPlacement();
                    return;
                }

                if (ModKeyMappings.CONSTRUCTOR_CONFIRM.get().consumeClick()) {
                    StarterGemEntity gem = constructor.getGem();

                    if (gem != null) {
                        ClientPacketDistributor.sendToServer(
                                new ConfirmConstructionPacketC2S(
                                        gem.getId(),
                                        constructor.getSelectedAssembly().id(),
                                        constructor.getPlacementPos(),
                                        constructor.getPlacementRotation()
                                ));

                        constructor.cancelPlacement();
                        constructorRotateWasDown = false;
                        return;
                    }
                }

                if (ModKeyMappings.CONSTRUCTOR_UP.get().isDown()) {
                    constructor.moveForward(player);
                }

                if (ModKeyMappings.CONSTRUCTOR_DOWN.get().isDown()) {
                    constructor.moveBackward(player);
                }

                if (ModKeyMappings.CONSTRUCTOR_LEFT.get().isDown()) {
                    constructor.moveLeft(player);
                }

                if (ModKeyMappings.CONSTRUCTOR_RIGHT.get().isDown()) {
                    constructor.moveRight(player);
                }

                if (ModKeyMappings.CONSTRUCTOR_RAISE.get().isDown()) {
                    constructor.raisePlacement();
                }

                if (ModKeyMappings.CONSTRUCTOR_LOWER.get().isDown()) {
                    constructor.lowerPlacement();
                }

                boolean rotateDown = ModKeyMappings.CONSTRUCTOR_ROTATE.get().isDown();

                if (rotateDown && !constructorRotateWasDown) {
                    constructor.rotatePlacement();
                }

                constructorRotateWasDown = rotateDown;
            }
        }
    }
}
