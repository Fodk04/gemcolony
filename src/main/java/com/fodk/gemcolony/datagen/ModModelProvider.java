package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.block.custom.*;
import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.item.BubbleColorTintSource;
import com.fodk.gemcolony.item.ModArmorMaterials;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.util.ColorUtil;
import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, GemColony.MOD_ID);
    }

    private static final Map<ColorUtil, DeferredBlock<Block>> CHROMA_DEPOSITS = Map.ofEntries(
            Map.entry(ColorUtil.WHITE, ModBlocks.WHITE_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.LIGHT_GRAY, ModBlocks.LIGHT_GRAY_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.GRAY, ModBlocks.GRAY_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.BLACK, ModBlocks.BLACK_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.BROWN, ModBlocks.BROWN_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.RED, ModBlocks.RED_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.ORANGE, ModBlocks.ORANGE_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.YELLOW, ModBlocks.YELLOW_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.LIME, ModBlocks.LIME_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.GREEN, ModBlocks.GREEN_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.CYAN, ModBlocks.CYAN_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.LIGHT_BLUE, ModBlocks.LIGHT_BLUE_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.BLUE, ModBlocks.BLUE_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.PURPLE, ModBlocks.PURPLE_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.MAGENTA, ModBlocks.MAGENTA_CHROMA_DEPOSIT),
            Map.entry(ColorUtil.PINK, ModBlocks.PINK_CHROMA_DEPOSIT)
    );

    private record ChromaBlockSet(
            ColorUtil color,
            DeferredBlock<Block> regular,
            DeferredBlock<StairBlock> regularStairs,
            DeferredBlock<SlabBlock> regularSlab,
            DeferredBlock<Block> polished,
            DeferredBlock<StairBlock> polishedStairs,
            DeferredBlock<SlabBlock> polishedSlab,
            DeferredBlock<Block> smooth,
            DeferredBlock<StairBlock> smoothStairs,
            DeferredBlock<SlabBlock> smoothSlab
    ) {}

    private static final List<ChromaBlockSet> CHROMA_BLOCK_SETS = List.of(
            new ChromaBlockSet(ColorUtil.WHITE,
                    ModBlocks.WHITE_CHROMA_BLOCK, ModBlocks.WHITE_CHROMA_STAIRS, ModBlocks.WHITE_CHROMA_SLAB,
                    ModBlocks.WHITE_POLISHED_CHROMA_BLOCK, ModBlocks.WHITE_POLISHED_CHROMA_STAIRS, ModBlocks.WHITE_POLISHED_CHROMA_SLAB,
                    ModBlocks.WHITE_SMOOTH_CHROMA_BLOCK, ModBlocks.WHITE_SMOOTH_CHROMA_STAIRS, ModBlocks.WHITE_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.LIGHT_GRAY,
                    ModBlocks.LIGHT_GRAY_CHROMA_BLOCK, ModBlocks.LIGHT_GRAY_CHROMA_STAIRS, ModBlocks.LIGHT_GRAY_CHROMA_SLAB,
                    ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_BLOCK, ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_STAIRS, ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_SLAB,
                    ModBlocks.LIGHT_GRAY_SMOOTH_CHROMA_BLOCK, ModBlocks.LIGHT_GRAY_SMOOTH_CHROMA_STAIRS, ModBlocks.LIGHT_GRAY_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.GRAY,
                    ModBlocks.GRAY_CHROMA_BLOCK, ModBlocks.GRAY_CHROMA_STAIRS, ModBlocks.GRAY_CHROMA_SLAB,
                    ModBlocks.GRAY_POLISHED_CHROMA_BLOCK, ModBlocks.GRAY_POLISHED_CHROMA_STAIRS, ModBlocks.GRAY_POLISHED_CHROMA_SLAB,
                    ModBlocks.GRAY_SMOOTH_CHROMA_BLOCK, ModBlocks.GRAY_SMOOTH_CHROMA_STAIRS, ModBlocks.GRAY_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.BLACK,
                    ModBlocks.BLACK_CHROMA_BLOCK, ModBlocks.BLACK_CHROMA_STAIRS, ModBlocks.BLACK_CHROMA_SLAB,
                    ModBlocks.BLACK_POLISHED_CHROMA_BLOCK, ModBlocks.BLACK_POLISHED_CHROMA_STAIRS, ModBlocks.BLACK_POLISHED_CHROMA_SLAB,
                    ModBlocks.BLACK_SMOOTH_CHROMA_BLOCK, ModBlocks.BLACK_SMOOTH_CHROMA_STAIRS, ModBlocks.BLACK_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.BROWN,
                    ModBlocks.BROWN_CHROMA_BLOCK, ModBlocks.BROWN_CHROMA_STAIRS, ModBlocks.BROWN_CHROMA_SLAB,
                    ModBlocks.BROWN_POLISHED_CHROMA_BLOCK, ModBlocks.BROWN_POLISHED_CHROMA_STAIRS, ModBlocks.BROWN_POLISHED_CHROMA_SLAB,
                    ModBlocks.BROWN_SMOOTH_CHROMA_BLOCK, ModBlocks.BROWN_SMOOTH_CHROMA_STAIRS, ModBlocks.BROWN_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.RED,
                    ModBlocks.RED_CHROMA_BLOCK, ModBlocks.RED_CHROMA_STAIRS, ModBlocks.RED_CHROMA_SLAB,
                    ModBlocks.RED_POLISHED_CHROMA_BLOCK, ModBlocks.RED_POLISHED_CHROMA_STAIRS, ModBlocks.RED_POLISHED_CHROMA_SLAB,
                    ModBlocks.RED_SMOOTH_CHROMA_BLOCK, ModBlocks.RED_SMOOTH_CHROMA_STAIRS, ModBlocks.RED_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.ORANGE,
                    ModBlocks.ORANGE_CHROMA_BLOCK, ModBlocks.ORANGE_CHROMA_STAIRS, ModBlocks.ORANGE_CHROMA_SLAB,
                    ModBlocks.ORANGE_POLISHED_CHROMA_BLOCK, ModBlocks.ORANGE_POLISHED_CHROMA_STAIRS, ModBlocks.ORANGE_POLISHED_CHROMA_SLAB,
                    ModBlocks.ORANGE_SMOOTH_CHROMA_BLOCK, ModBlocks.ORANGE_SMOOTH_CHROMA_STAIRS, ModBlocks.ORANGE_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.YELLOW,
                    ModBlocks.YELLOW_CHROMA_BLOCK, ModBlocks.YELLOW_CHROMA_STAIRS, ModBlocks.YELLOW_CHROMA_SLAB,
                    ModBlocks.YELLOW_POLISHED_CHROMA_BLOCK, ModBlocks.YELLOW_POLISHED_CHROMA_STAIRS, ModBlocks.YELLOW_POLISHED_CHROMA_SLAB,
                    ModBlocks.YELLOW_SMOOTH_CHROMA_BLOCK, ModBlocks.YELLOW_SMOOTH_CHROMA_STAIRS, ModBlocks.YELLOW_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.LIME,
                    ModBlocks.LIME_CHROMA_BLOCK, ModBlocks.LIME_CHROMA_STAIRS, ModBlocks.LIME_CHROMA_SLAB,
                    ModBlocks.LIME_POLISHED_CHROMA_BLOCK, ModBlocks.LIME_POLISHED_CHROMA_STAIRS, ModBlocks.LIME_POLISHED_CHROMA_SLAB,
                    ModBlocks.LIME_SMOOTH_CHROMA_BLOCK, ModBlocks.LIME_SMOOTH_CHROMA_STAIRS, ModBlocks.LIME_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.GREEN,
                    ModBlocks.GREEN_CHROMA_BLOCK, ModBlocks.GREEN_CHROMA_STAIRS, ModBlocks.GREEN_CHROMA_SLAB,
                    ModBlocks.GREEN_POLISHED_CHROMA_BLOCK, ModBlocks.GREEN_POLISHED_CHROMA_STAIRS, ModBlocks.GREEN_POLISHED_CHROMA_SLAB,
                    ModBlocks.GREEN_SMOOTH_CHROMA_BLOCK, ModBlocks.GREEN_SMOOTH_CHROMA_STAIRS, ModBlocks.GREEN_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.CYAN,
                    ModBlocks.CYAN_CHROMA_BLOCK, ModBlocks.CYAN_CHROMA_STAIRS, ModBlocks.CYAN_CHROMA_SLAB,
                    ModBlocks.CYAN_POLISHED_CHROMA_BLOCK, ModBlocks.CYAN_POLISHED_CHROMA_STAIRS, ModBlocks.CYAN_POLISHED_CHROMA_SLAB,
                    ModBlocks.CYAN_SMOOTH_CHROMA_BLOCK, ModBlocks.CYAN_SMOOTH_CHROMA_STAIRS, ModBlocks.CYAN_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.LIGHT_BLUE,
                    ModBlocks.LIGHT_BLUE_CHROMA_BLOCK, ModBlocks.LIGHT_BLUE_CHROMA_STAIRS, ModBlocks.LIGHT_BLUE_CHROMA_SLAB,
                    ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_BLOCK, ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_STAIRS, ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_SLAB,
                    ModBlocks.LIGHT_BLUE_SMOOTH_CHROMA_BLOCK, ModBlocks.LIGHT_BLUE_SMOOTH_CHROMA_STAIRS, ModBlocks.LIGHT_BLUE_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.BLUE,
                    ModBlocks.BLUE_CHROMA_BLOCK, ModBlocks.BLUE_CHROMA_STAIRS, ModBlocks.BLUE_CHROMA_SLAB,
                    ModBlocks.BLUE_POLISHED_CHROMA_BLOCK, ModBlocks.BLUE_POLISHED_CHROMA_STAIRS, ModBlocks.BLUE_POLISHED_CHROMA_SLAB,
                    ModBlocks.BLUE_SMOOTH_CHROMA_BLOCK, ModBlocks.BLUE_SMOOTH_CHROMA_STAIRS, ModBlocks.BLUE_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.PURPLE,
                    ModBlocks.PURPLE_CHROMA_BLOCK, ModBlocks.PURPLE_CHROMA_STAIRS, ModBlocks.PURPLE_CHROMA_SLAB,
                    ModBlocks.PURPLE_POLISHED_CHROMA_BLOCK, ModBlocks.PURPLE_POLISHED_CHROMA_STAIRS, ModBlocks.PURPLE_POLISHED_CHROMA_SLAB,
                    ModBlocks.PURPLE_SMOOTH_CHROMA_BLOCK, ModBlocks.PURPLE_SMOOTH_CHROMA_STAIRS, ModBlocks.PURPLE_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.MAGENTA,
                    ModBlocks.MAGENTA_CHROMA_BLOCK, ModBlocks.MAGENTA_CHROMA_STAIRS, ModBlocks.MAGENTA_CHROMA_SLAB,
                    ModBlocks.MAGENTA_POLISHED_CHROMA_BLOCK, ModBlocks.MAGENTA_POLISHED_CHROMA_STAIRS, ModBlocks.MAGENTA_POLISHED_CHROMA_SLAB,
                    ModBlocks.MAGENTA_SMOOTH_CHROMA_BLOCK, ModBlocks.MAGENTA_SMOOTH_CHROMA_STAIRS, ModBlocks.MAGENTA_SMOOTH_CHROMA_SLAB),

            new ChromaBlockSet(ColorUtil.PINK,
                    ModBlocks.PINK_CHROMA_BLOCK, ModBlocks.PINK_CHROMA_STAIRS, ModBlocks.PINK_CHROMA_SLAB,
                    ModBlocks.PINK_POLISHED_CHROMA_BLOCK, ModBlocks.PINK_POLISHED_CHROMA_STAIRS, ModBlocks.PINK_POLISHED_CHROMA_SLAB,
                    ModBlocks.PINK_SMOOTH_CHROMA_BLOCK, ModBlocks.PINK_SMOOTH_CHROMA_STAIRS, ModBlocks.PINK_SMOOTH_CHROMA_SLAB)
    );

    private void registerChromaPillar(BlockModelGenerators blockModels, RotatedPillarBlock pillar, String name) {
        Material side = new Material(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "block/" + name + "_side"));
        Material end = new Material(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "block/" + name + "_top"));

        TextureMapping mapping = TextureMapping.column(side, end);

        blockModels.blockStateOutput.accept(BlockModelGenerators.createPillarBlockUVLocked(pillar, mapping, blockModels.modelOutput));
        blockModels.registerSimpleItemModel(pillar, ModelLocationUtils.getModelLocation(pillar).withSuffix("_y"));
    }

    private static final TextureSlot SLOT_ZERO = TextureSlot.create("0");

    private static final ModelTemplate CHROMA_DEPOSIT_TEMPLATE = new ModelTemplate(
            Optional.of(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "block/chroma_deposit")),
            Optional.empty(),
            SLOT_ZERO,
            TextureSlot.PARTICLE
    );

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels){
        //items
        itemModels.generateFlatItem(ModItems.WHITE_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LIGHT_GRAY_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GRAY_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BLACK_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BROWN_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RED_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORANGE_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.YELLOW_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LIME_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GREEN_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CYAN_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LIGHT_BLUE_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PURPLE_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.MAGENTA_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PINK_CHROMA.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.GEM_SHARDS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GEM_SEED.get(), ModelTemplates.FLAT_ITEM);
        blockModels.createCropBlock(ModBlocks.STRAWBERRY_BUSH.get(), StrawberryBushBlock.AGE, 0, 1, 2, 3);

        itemModels.generateFlatItem(ModItems.PINK_ESSENCE_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_ESSENCE_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.YELLOW_ESSENCE_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.WHITE_ESSENCE_BOTTLE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.PINK_ESSENCE_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_ESSENCE_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.YELLOW_ESSENCE_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.WHITE_ESSENCE_BUCKET.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.CHROMA_SEED.get(), ModelTemplates.FLAT_ITEM);

        generateGemItem(ModItems.PEBBLE_GEM.get(), itemModels);
        generateGemItem(ModItems.SHALE_GEM.get(), itemModels);
        generateGemItem(ModItems.MICA_GEM.get(), itemModels);
        generateGemItem(ModItems.NACRE_GEM.get(), itemModels);

        generateGemItem(ModItems.PERIDOT_GEM.get(), itemModels);

        generateGemItem(ModItems.MILKY_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.PHANTOM_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.FLINT_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.ONYX_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.SMOKY_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.CARNELIAN_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.CHERT_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.CITRINE_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.PRASEOLITE_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.AVENTURINE_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.ANGEL_AURA_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.DUMORTIERITE_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.BLUE_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.AMETHYST_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.CHERRY_QUARTZ_GEM.get(), itemModels);
        generateGemItem(ModItems.ROSE_QUARTZ_GEM.get(), itemModels);

        generateGemItem(ModItems.SPIDERWEB_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.PORCELAIN_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.ZEBRA_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.CHRYSANTHEMUM_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.SNAKESKIN_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.BIGGS_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.NOREENA_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.HONEY_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.MORRISONITE_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.RHYOLITE_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.KAMBABA_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.OCEAN_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.SEA_SEDIMENT_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.ROYAL_PLUME_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.MOOKITE_JASPER_GEM.get(), itemModels);
        generateGemItem(ModItems.PEACH_PETAL_JASPER_GEM.get(), itemModels);

        generateGemItem(ModItems.WHITE_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.SPECTER_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.SILVER_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.ACKEE_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.CHOCOLATE_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.CHERRY_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.APRICOT_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.YELLOW_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.OLIVE_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.FOREST_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.SKY_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.BLUE_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.OCEAN_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.PLUM_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.FUCHSIA_PEARL_GEM.get(), itemModels);
        generateGemItem(ModItems.PINK_PEARL_GEM.get(), itemModels);

        generateGemItem(ModItems.RUBY_GEM.get(), itemModels);
        generateGemItem(ModItems.SAPPHIRE_GEM.get(), itemModels);

        generateGemItem(ModItems.FUSE.get(), itemModels);

        //tools
        itemModels.generateFlatItem(ModItems.CHROMA_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.CHROMA_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.CHROMA_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.CHROMA_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.CHROMA_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateSpear(ModItems.CHROMA_SPEAR.get());

        //music discs
        itemModels.generateFlatItem(ModItems.LOVE_LIKE_YOU_MUSIC_DISC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ITS_OVER_ISNT_IT_MUSIC_DISC.get(), ModelTemplates.FLAT_ITEM);

        //bow
        itemModels.createFlatItemModel(ModItems.CHROMA_BOW.get(), ModelTemplates.BOW);
        itemModels.generateBow(ModItems.CHROMA_BOW.get());

        //armor
        itemModels.generateTrimmableItem(ModItems.CHROMA_HELMET.get(), ModArmorMaterials.CHROMA_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModels.generateTrimmableItem(ModItems.CHROMA_CHESTPLATE.get(), ModArmorMaterials.CHROMA_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModels.generateTrimmableItem(ModItems.CHROMA_LEGGINGS.get(), ModArmorMaterials.CHROMA_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModels.generateTrimmableItem(ModItems.CHROMA_BOOTS.get(), ModArmorMaterials.CHROMA_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);

        itemModels.generateFlatItem(ModItems.CHROMA_HORSE_ARMOR.get(), ModelTemplates.FLAT_ITEM);

        //blocks
        /*blockModels.family(ModBlocks.CHROMA_BLOCK.get())
                .stairs(ModBlocks.CHROMA_STAIRS.get())
                .slab(ModBlocks.CHROMA_SLAB.get())
                .pressurePlate(ModBlocks.CHROMA_PRESSURE_PLATE.get())
                .button(ModBlocks.CHROMA_BUTTON.get())
                .fence(ModBlocks.CHROMA_FENCE.get())
                .fenceGate(ModBlocks.CHROMA_FENCE_GATE.get())
                .wall(ModBlocks.CHROMA_WALL.get())
                .door(ModBlocks.CHROMA_DOOR.get())
                .trapdoor(ModBlocks.CHROMA_TRAPDOOR.get());*/

        // COLORED CHROMA BLOCK FAMILIES
        for (ChromaBlockSet set : CHROMA_BLOCK_SETS) {
            blockModels.family(set.regular().get())
                    .stairs(set.regularStairs().get())
                    .slab(set.regularSlab().get());

            blockModels.family(set.polished().get())
                    .stairs(set.polishedStairs().get())
                    .slab(set.polishedSlab().get());

            blockModels.family(set.smooth().get())
                    .stairs(set.smoothStairs().get())
                    .slab(set.smoothSlab().get());
        }

        registerChromaPillar(blockModels, ModBlocks.WHITE_CHROMA_PILLAR.get(), "white_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.LIGHT_GRAY_CHROMA_PILLAR.get(), "light_gray_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.GRAY_CHROMA_PILLAR.get(), "gray_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.BLACK_CHROMA_PILLAR.get(), "black_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.BROWN_CHROMA_PILLAR.get(), "brown_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.RED_CHROMA_PILLAR.get(), "red_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.ORANGE_CHROMA_PILLAR.get(), "orange_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.YELLOW_CHROMA_PILLAR.get(), "yellow_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.LIME_CHROMA_PILLAR.get(), "lime_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.GREEN_CHROMA_PILLAR.get(), "green_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.CYAN_CHROMA_PILLAR.get(), "cyan_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.LIGHT_BLUE_CHROMA_PILLAR.get(), "light_blue_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.BLUE_CHROMA_PILLAR.get(), "blue_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.PURPLE_CHROMA_PILLAR.get(), "purple_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.MAGENTA_CHROMA_PILLAR.get(), "magenta_chroma_pillar");
        registerChromaPillar(blockModels, ModBlocks.PINK_CHROMA_PILLAR.get(), "pink_chroma_pillar");

        blockModels.createTrivialCube(ModBlocks.DRAINED_STONE.get());
        blockModels.createNonTemplateModelBlock(ModBlocks.GEM_SEED.get());

        //custom 3d model blocks with facing 6direc and age

        for (ColorUtil color : ColorUtil.values()) {
            Block depositBlock = CHROMA_DEPOSITS.get(color).get();
            Identifier texture = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "block/" + color.getColorName() + "_chroma_deposit");

            Material material = new Material(texture);

            Identifier modelLoc = CHROMA_DEPOSIT_TEMPLATE.create(
                    depositBlock,
                    new TextureMapping().put(SLOT_ZERO, material).put(TextureSlot.PARTICLE, material),
                    blockModels.modelOutput
            );

            blockModels.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(depositBlock)
                            .with(PropertyDispatch.initial(ChromaDeposit.FACE, ChromaDeposit.FACING)
                                    .generate((face, facing) -> {
                                        Quadrant xRot = switch (face) {
                                            case FLOOR -> Quadrant.R0;
                                            case CEILING -> Quadrant.R180;
                                            case WALL -> Quadrant.R90;
                                        };
                                        Quadrant yRot = switch (facing) {
                                            case NORTH -> Quadrant.R0;
                                            case EAST -> Quadrant.R90;
                                            case SOUTH -> Quadrant.R180;
                                            case WEST -> Quadrant.R270;
                                            default -> Quadrant.R0; // shouldn't hit UP/DOWN, FACING is horizontal-only
                                        };
                                        return BlockModelGenerators.plainVariant(modelLoc)
                                                .with(VariantMutator.X_ROT.withValue(xRot))
                                                .with(VariantMutator.Y_ROT.withValue(yRot));
                                    })
                            )
            );
        }

        //custom 3d model no rot
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.DRILL.get())
                        .with(
                                PropertyDispatch.initial(DrillBlock.PART, DrillBlock.CONSTRUCTION_STAGE).generate((part, stage) -> {
                                    boolean isComplete = stage == ((ConstructedMultiblock)ModBlocks.DRILL.get()).getMaxConstructionStage();
                                    String model = isComplete ? "drill" : "drill_stage" + stage;
                                    return BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "block/" + model));
                                })
                        )
        );

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.CRYSTAL.get())
                        .with(
                                PropertyDispatch.initial(CrystalBlock.CONSTRUCTION_STAGE).generate(stage -> {
                                    boolean isComplete = stage == ((ConstructedMultiblock)ModBlocks.CRYSTAL.get()).getMaxConstructionStage();
                                    String model = isComplete ? "crystal" : "crystal_stage" + stage;
                                    return BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "block/" + model));
                                })
                        )
        );

        //custom blocks with 4 direcs
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.LEG.get())
                        .with(
                                PropertyDispatch.initial(LegBlock.CONSTRUCTION_STAGE, BlockStateProperties.HORIZONTAL_FACING)
                                        .generate((stage, facing) -> {
                                    boolean isComplete = stage == ((ConstructedMultiblock)ModBlocks.LEG.get()).getMaxConstructionStage();
                                    String model = isComplete ? "leg" : "leg_stage0";

                                    Quadrant yRot = switch (facing) {
                                        case NORTH -> Quadrant.R0;
                                        case EAST -> Quadrant.R90;
                                        case SOUTH -> Quadrant.R180;
                                        case WEST -> Quadrant.R270;
                                        default -> Quadrant.R0;
                                    };

                                    return BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "block/" + model))
                                            .with(VariantMutator.Y_ROT.withValue(yRot));
                                })
                        )
        );

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.TANK.get())
                        .with(
                                PropertyDispatch.initial(TankBlock.HALF, TankBlock.CONSTRUCTION_STAGE, BlockStateProperties.HORIZONTAL_FACING)
                                        .generate((half, stage, facing) -> {

                                    boolean isComplete = stage == ((ConstructedMultiblock)ModBlocks.TANK.get()).getMaxConstructionStage();
                                    String model = switch (half) {
                                            case TANK_BOTTOM -> isComplete ? "tank_bottom" : "tank_bottom_stage" + stage;
                                            case TANK_TOP -> isComplete ? "tank_top" : "tank_top_stage" + stage;
                                    };

                                    Quadrant yRot = switch (facing) {
                                        case NORTH -> Quadrant.R0;
                                        case EAST -> Quadrant.R90;
                                        case SOUTH -> Quadrant.R180;
                                        case WEST -> Quadrant.R270;
                                        default -> Quadrant.R0;
                                    };

                                    return BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "block/" + model))
                                            .with(VariantMutator.Y_ROT.withValue(yRot));
                                })
                        )
        );

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.SHELL.get())
                        .with(
                                PropertyDispatch.initial(ShellBlock.CONSTRUCTION_STAGE, BlockStateProperties.HORIZONTAL_FACING)
                                        .generate((stage, facing) -> {
                                    boolean isComplete = stage == ((ConstructedMultiblock)ModBlocks.SHELL.get()).getMaxConstructionStage();
                                    String model = isComplete ? "shell" : "shell_stage" + stage;

                                    Quadrant yRot = switch (facing) {
                                        case NORTH -> Quadrant.R0;
                                        case EAST -> Quadrant.R90;
                                        case SOUTH -> Quadrant.R180;
                                        case WEST -> Quadrant.R270;
                                        default -> Quadrant.R0;
                                    };

                                    return BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "block/" + model))
                                            .with(VariantMutator.Y_ROT.withValue(yRot));
                                })
                        )
        );

        //chroma crop
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.CHROMA_CROP.get())
                        .with(
                                PropertyDispatch.initial(ChromaCrop.AGE)
                                        .select(0, BlockModelGenerators.variant(new Variant(
                                                Identifier.fromNamespaceAndPath(
                                                        GemColony.MOD_ID,
                                                        "block/chroma_crop_stage0"
                                                )
                                        )))
                                        .select(1, BlockModelGenerators.variant(new Variant(
                                                Identifier.fromNamespaceAndPath(
                                                        GemColony.MOD_ID,
                                                        "block/chroma_crop_stage1"
                                                )
                                        )))
                                        .select(2, BlockModelGenerators.variant(new Variant(
                                                Identifier.fromNamespaceAndPath(
                                                        GemColony.MOD_ID,
                                                        "block/chroma_crop_stage2"
                                                )
                                        )))
                                        .select(3, BlockModelGenerators.variant(new Variant(
                                                Identifier.fromNamespaceAndPath(
                                                        GemColony.MOD_ID,
                                                        "block/chroma_crop_stage3"
                                                )
                                        )))
                        )
        );

        //fluids
        blockModels.createNonTemplateModelBlock(ModBlocks.YELLOW_ESSENCE.get());
        blockModels.createNonTemplateModelBlock(ModBlocks.WHITE_ESSENCE.get());
        blockModels.createNonTemplateModelBlock(ModBlocks.PINK_ESSENCE.get());
        blockModels.createNonTemplateModelBlock(ModBlocks.BLUE_ESSENCE.get());

        //on/off blocks
        /*blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.DESTABILIZER_WALL_GENERATOR.get()).with(BlockModelGenerators.createBooleanModelDispatch(DestabilizerWallGenerator.ACTIVE,
                        BlockModelGenerators.plainVariant(blockModels.createSuffixedVariant(ModBlocks.DESTABILIZER_WALL_GENERATOR.get(), "_on", ModelTemplates.CUBE_ALL, TextureMapping::cube)),
                        BlockModelGenerators.plainVariant(TexturedModel.CUBE.create(ModBlocks.DESTABILIZER_WALL_GENERATOR.get(), blockModels.modelOutput)))));*/
    }

    private void generateGemItem(Item item, ItemModelGenerators itemModels) {
        var gemModel = ModelTemplates.FLAT_ITEM.create(
                item,
                TextureMapping.layer0(
                        new Material(
                                Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "item/" + BuiltInRegistries.ITEM.getKey(item).getPath())
                        )
                ),
                itemModels.modelOutput
        );

        var bubbleModel = ModelTemplates.FLAT_ITEM.create(
                ModelLocationUtils.getModelLocation(item, "_bubble"),
                TextureMapping.layer0(
                        new Material(
                                Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "item/gem_bubble")
                        )
                ),
                itemModels.modelOutput
        );

        itemModels.itemModelOutput.accept(
                item,
                ItemModelUtils.conditional(
                        ItemModelUtils.hasComponent(ModDataComponents.BUBBLED.get()),
                        ItemModelUtils.composite(
                                ItemModelUtils.plainModel(gemModel),
                                ItemModelUtils.tintedModel(
                                        ModelLocationUtils.getModelLocation(item, "_bubble"),
                                        BubbleColorTintSource.INSTANCE
                                )
                        ),
                        ItemModelUtils.plainModel(gemModel)
                )
        );
    }
}
