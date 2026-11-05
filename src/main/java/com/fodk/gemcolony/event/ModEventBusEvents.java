package com.fodk.gemcolony.event;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.entity.custom.gem.pearl.PearlEntity;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.fodk.gemcolony.entity.custom.gem.quartz.JasperEntity;
import com.fodk.gemcolony.entity.custom.gem.quartz.QuartzEntity;
import com.fodk.gemcolony.entity.custom.gem.starter.StarterGemEntity;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import java.util.List;

@EventBusSubscriber(modid = GemColony.MOD_ID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){

    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.PERIDOT.get(), PeridotEntity.createAttributes().build());

        event.put(ModEntities.QUARTZ.get(), QuartzEntity.createAttributes().build());
        event.put(ModEntities.JASPER.get(), JasperEntity.createAttributes().build());
        event.put(ModEntities.PEARL.get(), PearlEntity.createAttributes().build());
        event.put(ModEntities.HOLOPEARL.get(), PearlEntity.createAttributes().build());

        event.put(ModEntities.PEBBLE.get(), StarterGemEntity.createAttributes().build());
        event.put(ModEntities.SHALE.get(), StarterGemEntity.createAttributes().build());
        event.put(ModEntities.MICA.get(), StarterGemEntity.createAttributes().build());
        event.put(ModEntities.NACRE.get(), StarterGemEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerGems(RegisterGemDefinitionsEvent event) {

        // Peridot
        event.RegisterGemDefinitionAndConditions(
                Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "peridot"),
                "Peridot",
                List.of(ModItems.PERIDOT_GEM.get()),
                ModEntities.PERIDOT.get(),
                0.8f, 1.2f, 0.3f, 0.3f, 1.0f
        );

        // Quartz
        event.RegisterGemDefinitionAndConditions(
                Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "quartz"),
                "Quartz",
                List.of(
                        ModItems.MILKY_QUARTZ_GEM.get(),
                        ModItems.PHANTOM_QUARTZ_GEM.get(),
                        ModItems.FLINT_QUARTZ_GEM.get(),
                        ModItems.ONYX_QUARTZ_GEM.get(),
                        ModItems.SMOKY_QUARTZ_GEM.get(),
                        ModItems.CARNELIAN_QUARTZ_GEM.get(),
                        ModItems.CHERT_QUARTZ_GEM.get(),
                        ModItems.CITRINE_QUARTZ_GEM.get(),
                        ModItems.PRASEOLITE_QUARTZ_GEM.get(),
                        ModItems.AVENTURINE_QUARTZ_GEM.get(),
                        ModItems.ANGEL_AURA_QUARTZ_GEM.get(),
                        ModItems.DUMORTIERITE_QUARTZ_GEM.get(),
                        ModItems.BLUE_QUARTZ_GEM.get(),
                        ModItems.AMETHYST_QUARTZ_GEM.get(),
                        ModItems.CHERRY_QUARTZ_GEM.get(),
                        ModItems.ROSE_QUARTZ_GEM.get()
                ),
                ModEntities.QUARTZ.get(),
                1.0f, 1.0f, 0.3f, 0.3f, 1.0f
        );

        // Jasper
        event.RegisterGemDefinitionAndConditions(
                Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "jasper"),
                "Jasper",
                List.of(
                        ModItems.SPIDERWEB_JASPER_GEM.get(),
                        ModItems.PORCELAIN_JASPER_GEM.get(),
                        ModItems.ZEBRA_JASPER_GEM.get(),
                        ModItems.CHRYSANTHEMUM_JASPER_GEM.get(),
                        ModItems.SNAKESKIN_JASPER_GEM.get(),
                        ModItems.BIGGS_JASPER_GEM.get(),
                        ModItems.NOREENA_JASPER_GEM.get(),
                        ModItems.HONEY_JASPER_GEM.get(),
                        ModItems.MORRISONITE_JASPER_GEM.get(),
                        ModItems.RHYOLITE_JASPER_GEM.get(),
                        ModItems.KAMBABA_JASPER_GEM.get(),
                        ModItems.OCEAN_JASPER_GEM.get(),
                        ModItems.SEA_SEDIMENT_JASPER_GEM.get(),
                        ModItems.ROYAL_PLUME_JASPER_GEM.get(),
                        ModItems.MOOKITE_JASPER_GEM.get(),
                        ModItems.PEACH_PETAL_JASPER_GEM.get()
                ),
                ModEntities.JASPER.get(),
                1.8f, 0.2f, 0.3f, 0.3f, 1.0f
        );

        // Pearl
        event.RegisterGemDefinition(
                Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "pearl"),
                "Pearl",
                List.of(
                        ModItems.WHITE_PEARL_GEM.get(),
                        ModItems.SPECTER_PEARL_GEM.get(),
                        ModItems.SILVER_PEARL_GEM.get(),
                        ModItems.ACKEE_PEARL_GEM.get(),
                        ModItems.CHOCOLATE_PEARL_GEM.get(),
                        ModItems.CHERRY_PEARL_GEM.get(),
                        ModItems.APRICOT_PEARL_GEM.get(),
                        ModItems.YELLOW_PEARL_GEM.get(),
                        ModItems.OLIVE_PEARL_GEM.get(),
                        ModItems.FOREST_PEARL_GEM.get(),
                        ModItems.SKY_PEARL_GEM.get(),
                        ModItems.BLUE_PEARL_GEM.get(),
                        ModItems.OCEAN_PEARL_GEM.get(),
                        ModItems.PLUM_PEARL_GEM.get(),
                        ModItems.FUCHSIA_PEARL_GEM.get(),
                        ModItems.PINK_PEARL_GEM.get()
                ),
                ModEntities.PEARL.get()
        );
    }
}
