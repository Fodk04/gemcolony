package com.fodk.gemcolony.worldgen.structure;

import com.fodk.gemcolony.GemColony;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.Optional;

public class ModStructurePools {

    public static final ResourceKey<StructureTemplatePool> ROSE_QUARTZ_FOUNTAIN = registerKey("rose_quartz_fountain");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {

        context.register(
                ROSE_QUARTZ_FOUNTAIN,
                new StructureTemplatePool(
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        List.of(Pair.of(BuriedSinglePoolElement.buried("gemcolony:rose_quartz_fountain", context.lookup(Registries.PROCESSOR_LIST).getOrThrow(ResourceKey.create(Registries.PROCESSOR_LIST, Identifier.fromNamespaceAndPath("minecraft", "empty"))), 4), 1)),
                        StructureTemplatePool.Projection.RIGID
                )
        );
    }

    private static ResourceKey<StructureTemplatePool> registerKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name));
    }
}
