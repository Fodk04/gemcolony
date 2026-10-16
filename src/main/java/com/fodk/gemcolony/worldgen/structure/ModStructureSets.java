package com.fodk.gemcolony.worldgen.structure;

import com.fodk.gemcolony.GemColony;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class ModStructureSets {

    public static final ResourceKey<StructureSet> ROSE_QUARTZ_FOUNTAIN = registerKey("rose_quartz_fountain");
    public static final ResourceKey<StructureSet> BLUE_DIAMOND_ONSEN = registerKey("blue_diamond_onsen");
    public static final ResourceKey<StructureSet> YELLOW_DIAMOND_SAUNA = registerKey("yellow_diamond_sauna");
    public static final ResourceKey<StructureSet> BLUE_DIAMOND_ONSEN_RUINS = registerKey("blue_diamond_onsen_ruins");
    public static final ResourceKey<StructureSet> YELLOW_DIAMOND_SAUNA_RUINS = registerKey("yellow_diamond_sauna_ruins");

    public static void bootstrap(BootstrapContext<StructureSet> context) {
        Holder.Reference<Structure> fountain = context.lookup(Registries.STRUCTURE).getOrThrow(ModStructures.ROSE_QUARTZ_FOUNTAIN);

        context.register(
                ROSE_QUARTZ_FOUNTAIN,
                new StructureSet(fountain,
                        new RandomSpreadStructurePlacement(
                                160,
                                0,
                                RandomSpreadType.LINEAR,
                                123456789
                        )
                )
        );

        context.register(
                BLUE_DIAMOND_ONSEN,
                new StructureSet(
                        context.lookup(Registries.STRUCTURE).getOrThrow(ModStructures.BLUE_DIAMOND_ONSEN),
                        new RandomSpreadStructurePlacement(
                                160,
                                0,
                                RandomSpreadType.LINEAR,
                                246813579
                        )
                )
        );

        context.register(
                YELLOW_DIAMOND_SAUNA,
                new StructureSet(
                        context.lookup(Registries.STRUCTURE).getOrThrow(ModStructures.YELLOW_DIAMOND_SAUNA),
                        new RandomSpreadStructurePlacement(
                                160,
                                0,
                                RandomSpreadType.LINEAR,
                                975318642
                        )
                )
        );

        context.register(BLUE_DIAMOND_ONSEN_RUINS, new StructureSet(
                context.lookup(Registries.STRUCTURE).getOrThrow(ModStructures.BLUE_DIAMOND_ONSEN_RUINS),
                new RandomSpreadStructurePlacement(
                        160, 0, RandomSpreadType.LINEAR, 246813579)));

        context.register(YELLOW_DIAMOND_SAUNA_RUINS, new StructureSet(
                context.lookup(Registries.STRUCTURE).getOrThrow(ModStructures.YELLOW_DIAMOND_SAUNA_RUINS),
                new RandomSpreadStructurePlacement(
                        160, 0, RandomSpreadType.LINEAR, 975318642)));
    }

    private static ResourceKey<StructureSet> registerKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name));
    }
}
