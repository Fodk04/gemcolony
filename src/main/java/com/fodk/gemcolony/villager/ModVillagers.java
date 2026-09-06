package com.fodk.gemcolony.villager;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.datagen.villager.ModTradeSets;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, GemColony.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, GemColony.MOD_ID);

    public static final Holder<PoiType> GEMOLOGIST_POI = POI_TYPES.register("gemologist_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.DESTABILIZER_WALL_GENERATOR.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final Holder<VillagerProfession> GEMOLOGIST = VILLAGER_PROFESSIONS.register("gemologist",
            () -> new VillagerProfession(Component.literal("gemologist"), holder -> holder.value() == GEMOLOGIST_POI.value(),
                    holder -> holder.value() == GEMOLOGIST_POI.value(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.AMETHYST_BLOCK_CHIME, Int2ObjectMap.ofEntries(
                    Int2ObjectMap.entry(1, ModTradeSets.GEMOLOGIST_LEVEL_1),
                    Int2ObjectMap.entry(2, ModTradeSets.GEMOLOGIST_LEVEL_2))));



    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
