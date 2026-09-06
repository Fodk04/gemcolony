package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.block.custom.ChromaCrop;
import com.fodk.gemcolony.block.custom.ChromaDeposit;
import com.fodk.gemcolony.block.custom.DestabilizerWallGenerator;
import com.fodk.gemcolony.block.custom.StrawberryBushBlock;
import com.fodk.gemcolony.item.ModArmorMaterials;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.util.ColorToIndex;
import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Map;
import java.util.Optional;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, GemColony.MOD_ID);
    }

    private static final Map<ColorToIndex, DeferredBlock<Block>> CHROMA_DEPOSITS = Map.ofEntries(
            Map.entry(ColorToIndex.WHITE, ModBlocks.WHITE_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.LIGHT_GRAY, ModBlocks.LIGHT_GRAY_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.GRAY, ModBlocks.GRAY_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.BLACK, ModBlocks.BLACK_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.BROWN, ModBlocks.BROWN_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.RED, ModBlocks.RED_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.ORANGE, ModBlocks.ORANGE_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.YELLOW, ModBlocks.YELLOW_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.LIME, ModBlocks.LIME_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.GREEN, ModBlocks.GREEN_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.CYAN, ModBlocks.CYAN_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.LIGHT_BLUE, ModBlocks.LIGHT_BLUE_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.BLUE, ModBlocks.BLUE_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.PURPLE, ModBlocks.PURPLE_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.MAGENTA, ModBlocks.MAGENTA_CHROMA_DEPOSIT),
            Map.entry(ColorToIndex.PINK, ModBlocks.PINK_CHROMA_DEPOSIT)
    );

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
        blockModels.createCropBlock(ModBlocks.STRAWBERRY_BUSH.get(), StrawberryBushBlock.AGE, 0, 1, 2, 3);

        itemModels.generateFlatItem(ModItems.PINK_ESSENCE_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_ESSENCE_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.YELLOW_ESSENCE_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.WHITE_ESSENCE_BOTTLE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.CHROMA_SEED.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.PEBBLE_GEM.get(), ModelTemplates.FLAT_ITEM);

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
        blockModels.family(ModBlocks.CHROMA_BLOCK.get())
                .stairs(ModBlocks.CHROMA_STAIRS.get())
                .slab(ModBlocks.CHROMA_SLAB.get())
                .pressurePlate(ModBlocks.CHROMA_PRESSURE_PLATE.get())
                .button(ModBlocks.CHROMA_BUTTON.get())
                .fence(ModBlocks.CHROMA_FENCE.get())
                .fenceGate(ModBlocks.CHROMA_FENCE_GATE.get())
                .wall(ModBlocks.CHROMA_WALL.get())
                .door(ModBlocks.CHROMA_DOOR.get())
                .trapdoor(ModBlocks.CHROMA_TRAPDOOR.get());

        blockModels.createTrivialCube(ModBlocks.DRAINED_STONE.get());

        //custom 3d model blocks

        for (ColorToIndex color : ColorToIndex.values()) {
            Block depositBlock = CHROMA_DEPOSITS.get(color).get();
            Identifier texture = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "block/" + color.getColorName() + "_chroma_deposit");

            Material material = new Material(texture);

            Identifier modelLoc = CHROMA_DEPOSIT_TEMPLATE.create(
                    depositBlock,
                    new TextureMapping().put(SLOT_ZERO, material).put(TextureSlot.PARTICLE, material),
                    blockModels.modelOutput
            );

            /*blockModels.blockStateOutput.accept(
                    BlockModelGenerators.createSimpleBlock(depositBlock, BlockModelGenerators.plainVariant(modelLoc))
            );*/
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

        //on/off blocks
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.DESTABILIZER_WALL_GENERATOR.get()).with(BlockModelGenerators.createBooleanModelDispatch(DestabilizerWallGenerator.ACTIVE,
                        BlockModelGenerators.plainVariant(blockModels.createSuffixedVariant(ModBlocks.DESTABILIZER_WALL_GENERATOR.get(), "_on", ModelTemplates.CUBE_ALL, TextureMapping::cube)),
                        BlockModelGenerators.plainVariant(TexturedModel.CUBE.create(ModBlocks.DESTABILIZER_WALL_GENERATOR.get(), blockModels.modelOutput)))));
    }
}
