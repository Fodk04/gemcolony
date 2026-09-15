package com.fodk.gemcolony.entity;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.custom.GemRisingItemEntity;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, GemColony.MOD_ID);

    public static final Supplier<EntityType<PeridotEntity>> PERIDOT = ENTITY_TYPES.register("peridot", () -> EntityType.Builder.of(PeridotEntity::new, MobCategory.CREATURE)
            .sized(0.6f,2.2f)
            .fireImmune()
            .immuneTo(BlockTags.STRAY_IMMUNE_TO)
            .build(getRK("peridot")));

    public static final Supplier<EntityType<GemRisingItemEntity>> GEM_RISING_ITEM = ENTITY_TYPES.register("gem_rising_item",
            () -> EntityType.Builder.of(GemRisingItemEntity::new, MobCategory.MISC)
                    .noLootTable()
                    .sized(0.25F, 0.25F)
                    .updateInterval(1)
                    .build(getRK("gem_rising_item")));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }

    public static ResourceKey<EntityType<?>> getRK(String path){
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, path));
    }
}
