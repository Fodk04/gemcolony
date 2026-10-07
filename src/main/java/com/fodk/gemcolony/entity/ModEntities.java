package com.fodk.gemcolony.entity;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.custom.gem.pearl.HolopearlEntity;
import com.fodk.gemcolony.entity.custom.gem.pearl.PearlEntity;
import com.fodk.gemcolony.entity.custom.gem.base.GemRisingItemEntity;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.fodk.gemcolony.entity.custom.gem.bubble.BubbleEntity;
import com.fodk.gemcolony.entity.custom.gem.quartz.JasperEntity;
import com.fodk.gemcolony.entity.custom.gem.quartz.QuartzEntity;
import com.fodk.gemcolony.entity.custom.gem.starter.MicaEntity;
import com.fodk.gemcolony.entity.custom.gem.starter.NacreEntity;
import com.fodk.gemcolony.entity.custom.gem.starter.PebbleEntity;
import com.fodk.gemcolony.entity.custom.gem.starter.ShaleEntity;
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

    public static final Supplier<EntityType<PeridotEntity>> PERIDOT = ENTITY_TYPES.register("peridot",
            () -> EntityType.Builder.of(PeridotEntity::new, MobCategory.CREATURE)
            .sized(0.6f,1.8f)
            .fireImmune()
            .immuneTo(BlockTags.STRAY_IMMUNE_TO)
            .build(getRK("peridot")));

    public static final Supplier<EntityType<PebbleEntity>> PEBBLE = ENTITY_TYPES.register("pebble",
            () -> EntityType.Builder.of(PebbleEntity::new, MobCategory.CREATURE)
            .sized(0.2f,0.4f)
            .fireImmune()
            .immuneTo(BlockTags.STRAY_IMMUNE_TO)
            .build(getRK("pebble")));
    public static final Supplier<EntityType<ShaleEntity>> SHALE = ENTITY_TYPES.register("shale",
            () -> EntityType.Builder.of(ShaleEntity::new, MobCategory.CREATURE)
            .sized(0.2f,0.4f)
            .fireImmune()
            .immuneTo(BlockTags.STRAY_IMMUNE_TO)
            .build(getRK("shale")));
    public static final Supplier<EntityType<MicaEntity>> MICA = ENTITY_TYPES.register("mica",
            () -> EntityType.Builder.of(MicaEntity::new, MobCategory.CREATURE)
            .sized(0.2f,0.4f)
            .fireImmune()
            .immuneTo(BlockTags.STRAY_IMMUNE_TO)
            .build(getRK("mica")));
    public static final Supplier<EntityType<NacreEntity>> NACRE = ENTITY_TYPES.register("nacre",
            () -> EntityType.Builder.of(NacreEntity::new, MobCategory.CREATURE)
            .sized(0.2f,0.4f)
            .fireImmune()
            .immuneTo(BlockTags.STRAY_IMMUNE_TO)
            .build(getRK("nacre")));

    public static final Supplier<EntityType<QuartzEntity>> QUARTZ = ENTITY_TYPES.register("quartz",
            () -> EntityType.Builder.of(QuartzEntity::new, MobCategory.CREATURE)
            .sized(0.9f,2.2f)
            .fireImmune()
            .immuneTo(BlockTags.STRAY_IMMUNE_TO)
            .build(getRK("quartz")));

    public static final Supplier<EntityType<JasperEntity>> JASPER = ENTITY_TYPES.register("jasper",
            () -> EntityType.Builder.of(JasperEntity::new, MobCategory.CREATURE)
                    .sized(0.9f,2.2f)
                    .fireImmune()
                    .immuneTo(BlockTags.STRAY_IMMUNE_TO)
                    .build(getRK("jasper")));

    public static final Supplier<EntityType<PearlEntity>> PEARL = ENTITY_TYPES.register("pearl",
            () -> EntityType.Builder.of(PearlEntity::new, MobCategory.CREATURE)
                    .sized(0.8f,1.7f)
                    .fireImmune()
                    .immuneTo(BlockTags.STRAY_IMMUNE_TO)
                    .build(getRK("pearl")));

    public static final Supplier<EntityType<HolopearlEntity>> HOLOPEARL = ENTITY_TYPES.register("holopearl",
            () -> EntityType.Builder.of(HolopearlEntity::new, MobCategory.CREATURE)
                    .sized(0.8f,1.7f)
                    .fireImmune()
                    .immuneTo(BlockTags.STRAY_IMMUNE_TO)
                    .build(getRK("holopearl")));

    public static final Supplier<EntityType<GemRisingItemEntity>> GEM_RISING_ITEM = ENTITY_TYPES.register("gem_rising_item",
            () -> EntityType.Builder.of(GemRisingItemEntity::new, MobCategory.MISC)
                    .noLootTable()
                    .sized(0.25F, 0.25F)
                    .updateInterval(1)
                    .build(getRK("gem_rising_item")));

    public static final Supplier<EntityType<BubbleEntity>> BUBBLE = ENTITY_TYPES.register("bubble",
            () -> EntityType.Builder.of(BubbleEntity::new, MobCategory.MISC)
                    .sized(0.6f, 0.6f)
                    .fireImmune()
                    .build(getRK("bubble")));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }

    public static ResourceKey<EntityType<?>> getRK(String path){
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, path));
    }
}
