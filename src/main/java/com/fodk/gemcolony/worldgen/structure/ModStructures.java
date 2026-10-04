package com.fodk.gemcolony.worldgen.structure;

import com.fodk.gemcolony.GemColony;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.Map;

public class ModStructures {

    public static final ResourceKey<Structure> ROSE_QUARTZ_FOUNTAIN = registerKey("rose_quartz_fountain");

    public static void bootstrap(BootstrapContext<Structure> context) {

        HolderGetter<StructureTemplatePool> pools = context.lookup(Registries.TEMPLATE_POOL);
        var biomes = context.lookup(Registries.BIOME);

        context.register(
                ROSE_QUARTZ_FOUNTAIN,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                HolderSet.direct(
                                        biomes.getOrThrow(Biomes.DESERT),
                                        biomes.getOrThrow(Biomes.WOODED_BADLANDS),
                                        biomes.getOrThrow(Biomes.ERODED_BADLANDS),
                                        biomes.getOrThrow(Biomes.BADLANDS)),
                                Map.of(),
                                GenerationStep.Decoration.SURFACE_STRUCTURES,
                                TerrainAdjustment.BEARD_BOX
                        ),
                        pools.getOrThrow(ModStructurePools.ROSE_QUARTZ_FOUNTAIN),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
    }

    private static ResourceKey<Structure> registerKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name));
    }
}
