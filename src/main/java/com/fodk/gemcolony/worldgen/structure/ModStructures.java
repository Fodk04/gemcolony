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
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModStructures {

    public static final ResourceKey<Structure> ROSE_QUARTZ_FOUNTAIN = registerKey("rose_quartz_fountain");
    public static final ResourceKey<Structure> BLUE_DIAMOND_ONSEN = registerKey("blue_diamond_onsen");
    public static final ResourceKey<Structure> YELLOW_DIAMOND_SAUNA = registerKey("yellow_diamond_sauna");
    public static final ResourceKey<Structure> BLUE_DIAMOND_ONSEN_RUINS = registerKey("blue_diamond_onsen_ruins");
    public static final ResourceKey<Structure> YELLOW_DIAMOND_SAUNA_RUINS = registerKey("yellow_diamond_sauna_ruins");

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

        context.register(
                BLUE_DIAMOND_ONSEN,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                HolderSet.direct(
                                        biomes.getOrThrow(Biomes.TAIGA),
                                        biomes.getOrThrow(Biomes.SNOWY_TAIGA),
                                        biomes.getOrThrow(Biomes.OLD_GROWTH_SPRUCE_TAIGA),
                                        biomes.getOrThrow(Biomes.OLD_GROWTH_PINE_TAIGA)),
                                Map.of(),
                                GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
                                TerrainAdjustment.ENCAPSULATE),
                        pools.getOrThrow(ModStructurePools.BLUE_DIAMOND_ONSEN),
                        Optional.empty(),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(20)),
                        false,
                        Optional.empty(),
                        new JigsawStructure.MaxDistance(80),
                        List.of(),
                        JigsawStructure.DEFAULT_DIMENSION_PADDING,
                        LiquidSettings.IGNORE_WATERLOGGING
                )
        );

        context.register(
                YELLOW_DIAMOND_SAUNA,
                new JigsawStructure(
                        new Structure.StructureSettings(
                                HolderSet.direct(
                                        biomes.getOrThrow(Biomes.FOREST),
                                        biomes.getOrThrow(Biomes.FLOWER_FOREST),
                                        biomes.getOrThrow(Biomes.DARK_FOREST),
                                        biomes.getOrThrow(Biomes.BIRCH_FOREST)),
                                Map.of(),
                                GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
                                TerrainAdjustment.ENCAPSULATE),
                        pools.getOrThrow(ModStructurePools.YELLOW_DIAMOND_SAUNA),
                        Optional.empty(),
                        1,
                        ConstantHeight.of(VerticalAnchor.absolute(20)),
                        false,
                        Optional.empty(),
                        new JigsawStructure.MaxDistance(80),
                        List.of(),
                        JigsawStructure.DEFAULT_DIMENSION_PADDING,
                        LiquidSettings.IGNORE_WATERLOGGING
                )
        );

        context.register(BLUE_DIAMOND_ONSEN_RUINS, new JigsawStructure(
                new Structure.StructureSettings(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.TAIGA),
                                biomes.getOrThrow(Biomes.SNOWY_TAIGA),
                                biomes.getOrThrow(Biomes.OLD_GROWTH_SPRUCE_TAIGA),
                                biomes.getOrThrow(Biomes.OLD_GROWTH_PINE_TAIGA)),
                        Map.of(),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_THIN),
                pools.getOrThrow(ModStructurePools.BLUE_DIAMOND_ONSEN_RUINS),
                1,
                ConstantHeight.of(VerticalAnchor.absolute(0)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG));

        context.register(YELLOW_DIAMOND_SAUNA_RUINS, new JigsawStructure(
                new Structure.StructureSettings(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.FOREST),
                                biomes.getOrThrow(Biomes.FLOWER_FOREST),
                                biomes.getOrThrow(Biomes.DARK_FOREST),
                                biomes.getOrThrow(Biomes.BIRCH_FOREST)),
                        Map.of(),
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_THIN),
                pools.getOrThrow(ModStructurePools.YELLOW_DIAMOND_SAUNA_RUINS),
                1,
                ConstantHeight.of(VerticalAnchor.absolute(0)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG));
    }

    private static ResourceKey<Structure> registerKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name));
    }
}
