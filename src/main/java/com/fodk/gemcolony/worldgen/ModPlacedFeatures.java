package com.fodk.gemcolony.worldgen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.worldgen.placement.CaveSurfacePlacement;
import com.fodk.gemcolony.worldgen.placement.OceanFloorClusterPlacement;
import com.fodk.gemcolony.worldgen.placement.SurfaceClusterPlacement;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> WHITE_CHROMA_DEPOSIT_PLACED = registerKey("white_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> LIGHT_GRAY_CHROMA_DEPOSIT_PLACED = registerKey("light_gray_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> GRAY_CHROMA_DEPOSIT_PLACED = registerKey("gray_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> BLACK_CHROMA_DEPOSIT_PLACED = registerKey("black_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> BROWN_CHROMA_DEPOSIT_PLACED = registerKey("brown_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> RED_CHROMA_DEPOSIT_PLACED = registerKey("red_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> ORANGE_CHROMA_DEPOSIT_PLACED = registerKey("orange_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> YELLOW_CHROMA_DEPOSIT_PLACED = registerKey("yellow_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> LIME_CHROMA_DEPOSIT_PLACED = registerKey("lime_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> GREEN_CHROMA_DEPOSIT_PLACED = registerKey("green_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> CYAN_CHROMA_DEPOSIT_PLACED = registerKey("cyan_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> LIGHT_BLUE_CHROMA_DEPOSIT_PLACED = registerKey("light_blue_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> BLUE_CHROMA_DEPOSIT_PLACED = registerKey("blue_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> PURPLE_CHROMA_DEPOSIT_PLACED = registerKey("purple_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> MAGENTA_CHROMA_DEPOSIT_PLACED = registerKey("magenta_chroma_deposit_placed");
    public static final ResourceKey<PlacedFeature> PINK_CHROMA_DEPOSIT_PLACED = registerKey("pink_chroma_deposit_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(
                context,
                WHITE_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.WHITE_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(32),
                        CountPlacement.of(6),
                        new SurfaceClusterPlacement(),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW))
                )
        );
        register(
                context,
                LIGHT_GRAY_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.LIGHT_GRAY_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(9),
                        CountPlacement.of(6),
                        new CaveSurfacePlacement(Blocks.STONE),
                        BiomeFilter.biome()
                )
        );
        register(
                context,
                GRAY_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.GRAY_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(9),
                        CountPlacement.of(6),
                        new CaveSurfacePlacement(Blocks.DEEPSLATE),
                        BiomeFilter.biome()
                )
        );
        register(
                context,
                BLACK_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.BLACK_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(8),
                        CountPlacement.of(6),
                        new CaveSurfacePlacement(Blocks.BASALT, Blocks.BLACKSTONE),
                        BiomeFilter.biome()
                )
        );
        register(
                context,
                BROWN_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.BROWN_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(32),
                        CountPlacement.of(6),
                        new SurfaceClusterPlacement(),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.PODZOL, Blocks.COARSE_DIRT))
                )
        );
        register(
                context,
                RED_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RED_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(8),
                        CountPlacement.of(6),
                        new CaveSurfacePlacement(Blocks.NETHERRACK),
                        BiomeFilter.biome()
                )
        );
        register(
                context,
                ORANGE_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.ORANGE_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(32),
                        CountPlacement.of(6),
                        new SurfaceClusterPlacement(),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.RED_SAND))
                )
        );
        register(
                context,
                YELLOW_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.YELLOW_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(32),
                        CountPlacement.of(6),
                        new SurfaceClusterPlacement(),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.SAND))
                )
        );
        register(
                context,
                LIME_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.LIME_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(32),
                        CountPlacement.of(6),
                        new SurfaceClusterPlacement(),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK, Blocks.DIRT))
                )
        );
        register(
                context,
                GREEN_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.GREEN_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(32),
                        CountPlacement.of(6),
                        new SurfaceClusterPlacement(),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK))
                )
        );
        register(
                context,
                CYAN_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.CYAN_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(32),
                        CountPlacement.of(6),
                        new OceanFloorClusterPlacement(),
                        BiomeFilter.biome()
                )
        );
        register(
                context,
                LIGHT_BLUE_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.LIGHT_BLUE_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(2),
                        CountPlacement.of(6),
                        new CaveSurfacePlacement(Blocks.CLAY),
                        BiomeFilter.biome()
                )
        );
        register(
                context,
                BLUE_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.BLUE_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(32),
                        CountPlacement.of(6),
                        new SurfaceClusterPlacement(),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE))
                )
        );
        register(
                context,
                PURPLE_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.PURPLE_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(32),
                        CountPlacement.of(6),
                        new SurfaceClusterPlacement(),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK))
                )
        );
        register(
                context,
                MAGENTA_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.MAGENTA_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(32),
                        CountPlacement.of(6),
                        new SurfaceClusterPlacement(),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK))
                )
        );
        register(
                context,
                PINK_CHROMA_DEPOSIT_PLACED,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.PINK_CHROMA_DEPOSIT),
                List.of(
                        RarityFilter.onAverageOnceEvery(1),
                        CountPlacement.of(6),
                        new CaveSurfacePlacement(Blocks.CINNABAR, Blocks.GRANITE),
                        BiomeFilter.biome()
                )
        );

    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
