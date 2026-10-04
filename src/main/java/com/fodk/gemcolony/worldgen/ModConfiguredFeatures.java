package com.fodk.gemcolony.worldgen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.worldgen.blockstateprovider.RandomFullRotationProvider;
import com.fodk.gemcolony.worldgen.blockstateprovider.RandomHorizontalFacingProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TemplateFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RotatedBlockProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHROMA_DEPOSIT = registerKey("white_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHT_GRAY_CHROMA_DEPOSIT = registerKey("light_gray_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRAY_CHROMA_DEPOSIT = registerKey("gray_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_CHROMA_DEPOSIT = registerKey("black_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BROWN_CHROMA_DEPOSIT = registerKey("brown_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_CHROMA_DEPOSIT = registerKey("red_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_CHROMA_DEPOSIT = registerKey("orange_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_CHROMA_DEPOSIT = registerKey("yellow_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIME_CHROMA_DEPOSIT = registerKey("lime_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_CHROMA_DEPOSIT = registerKey("green_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CYAN_CHROMA_DEPOSIT = registerKey("cyan_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHT_BLUE_CHROMA_DEPOSIT = registerKey("light_blue_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_CHROMA_DEPOSIT = registerKey("blue_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_CHROMA_DEPOSIT = registerKey("purple_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAGENTA_CHROMA_DEPOSIT = registerKey("magenta_chroma_deposit");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_CHROMA_DEPOSIT = registerKey("pink_chroma_deposit");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(
                context,
                WHITE_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomHorizontalFacingProvider(ModBlocks.WHITE_CHROMA_DEPOSIT.get()))
        );
        register(
                context,
                LIGHT_GRAY_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomFullRotationProvider(ModBlocks.LIGHT_GRAY_CHROMA_DEPOSIT.get(), Blocks.STONE))
        );
        register(
                context,
                GRAY_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomFullRotationProvider(ModBlocks.GRAY_CHROMA_DEPOSIT.get(), Blocks.DEEPSLATE))
        );
        register(
                context,
                BLACK_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomFullRotationProvider(ModBlocks.BLACK_CHROMA_DEPOSIT.get(), Blocks.BASALT, Blocks.BLACKSTONE))
        );
        register(
                context,
                BROWN_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomHorizontalFacingProvider(ModBlocks.BROWN_CHROMA_DEPOSIT.get()))
        );
        register(
                context,
                RED_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomFullRotationProvider(ModBlocks.RED_CHROMA_DEPOSIT.get(), Blocks.NETHERRACK))
        );
        register(
                context,
                ORANGE_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomHorizontalFacingProvider(ModBlocks.ORANGE_CHROMA_DEPOSIT.get()))
        );
        register(
                context,
                YELLOW_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomHorizontalFacingProvider(ModBlocks.YELLOW_CHROMA_DEPOSIT.get()))
        );
        register(
                context,
                LIME_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomHorizontalFacingProvider(ModBlocks.LIME_CHROMA_DEPOSIT.get()))
        );
        register(
                context,
                GREEN_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomHorizontalFacingProvider(ModBlocks.GREEN_CHROMA_DEPOSIT.get()))
        );
        register(
                context,
                CYAN_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomHorizontalFacingProvider(ModBlocks.CYAN_CHROMA_DEPOSIT.get()))
        );
        register(
                context,
                LIGHT_BLUE_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomFullRotationProvider(ModBlocks.LIGHT_BLUE_CHROMA_DEPOSIT.get(), Blocks.CLAY))
        );
        register(
                context,
                BLUE_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomHorizontalFacingProvider(ModBlocks.BLUE_CHROMA_DEPOSIT.get()))
        );
        register(
                context,
                PURPLE_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomHorizontalFacingProvider(ModBlocks.PURPLE_CHROMA_DEPOSIT.get()))
        );
        register(
                context,
                MAGENTA_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomHorizontalFacingProvider(ModBlocks.MAGENTA_CHROMA_DEPOSIT.get()))
        );
        register(
                context,
                PINK_CHROMA_DEPOSIT,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new RandomFullRotationProvider(ModBlocks.PINK_CHROMA_DEPOSIT.get(), Blocks.CINNABAR, Blocks.GRANITE))
        );

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
