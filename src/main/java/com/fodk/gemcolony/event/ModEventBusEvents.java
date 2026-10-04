package com.fodk.gemcolony.event;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.entity.custom.gem.PearlEntity;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.fodk.gemcolony.entity.custom.gem.quartz.QuartzEntity;
import com.fodk.gemcolony.entity.custom.gem.starter.PebbleEntity;
import com.fodk.gemcolony.entity.custom.gem.starter.StarterGemEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = GemColony.MOD_ID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){

    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.PERIDOT.get(), PeridotEntity.createAttributes().build());

        event.put(ModEntities.QUARTZ.get(), QuartzEntity.createAttributes().build());
        event.put(ModEntities.PEARL.get(), PearlEntity.createAttributes().build());

        event.put(ModEntities.PEBBLE.get(), StarterGemEntity.createAttributes().build());
        event.put(ModEntities.SHALE.get(), StarterGemEntity.createAttributes().build());
        event.put(ModEntities.MICA.get(), StarterGemEntity.createAttributes().build());
        event.put(ModEntities.NACRE.get(), StarterGemEntity.createAttributes().build());
    }
}
