package com.fodk.gemcolony.worldgen;

import com.fodk.gemcolony.GemColony;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_WHITE_CHROMA_DEPOSIT = registerKey("add_white_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_LIGHT_GRAY_CHROMA_DEPOSIT = registerKey("add_light_gray_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_GRAY_CHROMA_DEPOSIT = registerKey("add_gray_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_BLACK_CHROMA_DEPOSIT = registerKey("add_black_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_BROWN_CHROMA_DEPOSIT = registerKey("add_brown_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_RED_CHROMA_DEPOSIT = registerKey("add_red_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_ORANGE_CHROMA_DEPOSIT = registerKey("add_orange_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_YELLOW_CHROMA_DEPOSIT = registerKey("add_yellow_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_LIME_CHROMA_DEPOSIT = registerKey("add_lime_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_GREEN_CHROMA_DEPOSIT = registerKey("add_green_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_CYAN_CHROMA_DEPOSIT = registerKey("add_cyan_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_LIGHT_BLUE_CHROMA_DEPOSIT = registerKey("add_light_blue_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_BLUE_CHROMA_DEPOSIT = registerKey("add_blue_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_PURPLE_CHROMA_DEPOSIT = registerKey("add_purple_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_MAGENTA_CHROMA_DEPOSIT = registerKey("add_magenta_chroma_deposit");
    public static final ResourceKey<BiomeModifier> ADD_PINK_CHROMA_DEPOSIT = registerKey("add_pink_chroma_deposit");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(
                ADD_WHITE_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.SNOWY_SLOPES),
                                biomes.getOrThrow(Biomes.GROVE)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.WHITE_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
        context.register(
                ADD_LIGHT_GRAY_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LIGHT_GRAY_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );
        context.register(
                ADD_GRAY_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GRAY_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );
        context.register(
                ADD_BLACK_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(Biomes.BASALT_DELTAS)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BLACK_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );
        context.register(
                ADD_BROWN_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.OLD_GROWTH_PINE_TAIGA),
                                biomes.getOrThrow(Biomes.OLD_GROWTH_SPRUCE_TAIGA),
                                biomes.getOrThrow(Biomes.WOODED_BADLANDS)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BROWN_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
        context.register(
                ADD_RED_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(Biomes.NETHER_WASTES)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.RED_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );
        context.register(
                ADD_ORANGE_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_BADLANDS),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ORANGE_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
        context.register(
                ADD_YELLOW_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(Biomes.DESERT)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.YELLOW_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
        context.register(
                ADD_LIME_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.JUNGLE),
                                biomes.getOrThrow(Biomes.SPARSE_JUNGLE),
                                biomes.getOrThrow(Biomes.BAMBOO_JUNGLE)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LIME_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
        context.register(
                ADD_GREEN_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.SWAMP),
                                biomes.getOrThrow(Biomes.MANGROVE_SWAMP)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GREEN_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
        context.register(
                ADD_CYAN_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.WARM_OCEAN),
                                biomes.getOrThrow(Biomes.LUKEWARM_OCEAN),
                                biomes.getOrThrow(Biomes.DEEP_LUKEWARM_OCEAN)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CYAN_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
        context.register(
                ADD_LIGHT_BLUE_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(Biomes.LUSH_CAVES)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LIGHT_BLUE_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );
        context.register(
                ADD_BLUE_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.ICE_SPIKES),
                                biomes.getOrThrow(Biomes.FROZEN_PEAKS),
                                biomes.getOrThrow(Biomes.FROZEN_OCEAN)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BLUE_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
        context.register(
                ADD_PURPLE_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.FLOWER_FOREST),
                                biomes.getOrThrow(Biomes.MEADOW)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PURPLE_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
        context.register(
                ADD_MAGENTA_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomes.getOrThrow(Biomes.FLOWER_FOREST),
                                biomes.getOrThrow(Biomes.MEADOW)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.MAGENTA_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
        context.register(
                ADD_PINK_CHROMA_DEPOSIT,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(Biomes.SULFUR_CAVES)),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PINK_CHROMA_DEPOSIT_PLACED)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name));
    }
}
