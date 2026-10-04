package com.fodk.gemcolony.worldgen.structure;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.worldgen.structure.BuriedSinglePoolElement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructurePoolElementTypes {

    public static final DeferredRegister<StructurePoolElementType<?>> TYPES =
            DeferredRegister.create(BuiltInRegistries.STRUCTURE_POOL_ELEMENT, GemColony.MOD_ID);

    public static final DeferredHolder<StructurePoolElementType<?>, StructurePoolElementType<BuriedSinglePoolElement>> BURIED_SINGLE =
            TYPES.register("buried_single", () -> () -> BuriedSinglePoolElement.CODEC);

    public static void register(IEventBus eventBus) {
        TYPES.register(eventBus);
    }
}