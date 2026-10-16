package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, GemColony.MOD_ID);
    }

    private void addBlocksToTag(TagKey<Block> tagKey, List<? extends DeferredBlock<? extends Block>> blocks) {
        for (DeferredBlock<? extends Block> block : blocks) {
            tag(tagKey).add(ModBlocks.getRK(block));
        }
    }

    private List<DeferredBlock<? extends Block>> getChromaStairs() {
        return List.of(
                ModBlocks.WHITE_CHROMA_STAIRS,
                ModBlocks.LIGHT_GRAY_CHROMA_STAIRS,
                ModBlocks.GRAY_CHROMA_STAIRS,
                ModBlocks.BLACK_CHROMA_STAIRS,
                ModBlocks.BROWN_CHROMA_STAIRS,
                ModBlocks.RED_CHROMA_STAIRS,
                ModBlocks.ORANGE_CHROMA_STAIRS,
                ModBlocks.YELLOW_CHROMA_STAIRS,
                ModBlocks.LIME_CHROMA_STAIRS,
                ModBlocks.GREEN_CHROMA_STAIRS,
                ModBlocks.CYAN_CHROMA_STAIRS,
                ModBlocks.LIGHT_BLUE_CHROMA_STAIRS,
                ModBlocks.BLUE_CHROMA_STAIRS,
                ModBlocks.PURPLE_CHROMA_STAIRS,
                ModBlocks.MAGENTA_CHROMA_STAIRS,
                ModBlocks.PINK_CHROMA_STAIRS,

                ModBlocks.WHITE_POLISHED_CHROMA_STAIRS,
                ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_STAIRS,
                ModBlocks.GRAY_POLISHED_CHROMA_STAIRS,
                ModBlocks.BLACK_POLISHED_CHROMA_STAIRS,
                ModBlocks.BROWN_POLISHED_CHROMA_STAIRS,
                ModBlocks.RED_POLISHED_CHROMA_STAIRS,
                ModBlocks.ORANGE_POLISHED_CHROMA_STAIRS,
                ModBlocks.YELLOW_POLISHED_CHROMA_STAIRS,
                ModBlocks.LIME_POLISHED_CHROMA_STAIRS,
                ModBlocks.GREEN_POLISHED_CHROMA_STAIRS,
                ModBlocks.CYAN_POLISHED_CHROMA_STAIRS,
                ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_STAIRS,
                ModBlocks.BLUE_POLISHED_CHROMA_STAIRS,
                ModBlocks.PURPLE_POLISHED_CHROMA_STAIRS,
                ModBlocks.MAGENTA_POLISHED_CHROMA_STAIRS,
                ModBlocks.PINK_POLISHED_CHROMA_STAIRS,

                ModBlocks.WHITE_SMOOTH_CHROMA_STAIRS,
                ModBlocks.LIGHT_GRAY_SMOOTH_CHROMA_STAIRS,
                ModBlocks.GRAY_SMOOTH_CHROMA_STAIRS,
                ModBlocks.BLACK_SMOOTH_CHROMA_STAIRS,
                ModBlocks.BROWN_SMOOTH_CHROMA_STAIRS,
                ModBlocks.RED_SMOOTH_CHROMA_STAIRS,
                ModBlocks.ORANGE_SMOOTH_CHROMA_STAIRS,
                ModBlocks.YELLOW_SMOOTH_CHROMA_STAIRS,
                ModBlocks.LIME_SMOOTH_CHROMA_STAIRS,
                ModBlocks.GREEN_SMOOTH_CHROMA_STAIRS,
                ModBlocks.CYAN_SMOOTH_CHROMA_STAIRS,
                ModBlocks.LIGHT_BLUE_SMOOTH_CHROMA_STAIRS,
                ModBlocks.BLUE_SMOOTH_CHROMA_STAIRS,
                ModBlocks.PURPLE_SMOOTH_CHROMA_STAIRS,
                ModBlocks.MAGENTA_SMOOTH_CHROMA_STAIRS,
                ModBlocks.PINK_SMOOTH_CHROMA_STAIRS
        );
    }

    private List<DeferredBlock<? extends Block>> getChromaSlabs() {
        return List.of(
                ModBlocks.WHITE_CHROMA_SLAB,
                ModBlocks.LIGHT_GRAY_CHROMA_SLAB,
                ModBlocks.GRAY_CHROMA_SLAB,
                ModBlocks.BLACK_CHROMA_SLAB,
                ModBlocks.BROWN_CHROMA_SLAB,
                ModBlocks.RED_CHROMA_SLAB,
                ModBlocks.ORANGE_CHROMA_SLAB,
                ModBlocks.YELLOW_CHROMA_SLAB,
                ModBlocks.LIME_CHROMA_SLAB,
                ModBlocks.GREEN_CHROMA_SLAB,
                ModBlocks.CYAN_CHROMA_SLAB,
                ModBlocks.LIGHT_BLUE_CHROMA_SLAB,
                ModBlocks.BLUE_CHROMA_SLAB,
                ModBlocks.PURPLE_CHROMA_SLAB,
                ModBlocks.MAGENTA_CHROMA_SLAB,
                ModBlocks.PINK_CHROMA_SLAB,

                ModBlocks.WHITE_POLISHED_CHROMA_SLAB,
                ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_SLAB,
                ModBlocks.GRAY_POLISHED_CHROMA_SLAB,
                ModBlocks.BLACK_POLISHED_CHROMA_SLAB,
                ModBlocks.BROWN_POLISHED_CHROMA_SLAB,
                ModBlocks.RED_POLISHED_CHROMA_SLAB,
                ModBlocks.ORANGE_POLISHED_CHROMA_SLAB,
                ModBlocks.YELLOW_POLISHED_CHROMA_SLAB,
                ModBlocks.LIME_POLISHED_CHROMA_SLAB,
                ModBlocks.GREEN_POLISHED_CHROMA_SLAB,
                ModBlocks.CYAN_POLISHED_CHROMA_SLAB,
                ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_SLAB,
                ModBlocks.BLUE_POLISHED_CHROMA_SLAB,
                ModBlocks.PURPLE_POLISHED_CHROMA_SLAB,
                ModBlocks.MAGENTA_POLISHED_CHROMA_SLAB,
                ModBlocks.PINK_POLISHED_CHROMA_SLAB,

                ModBlocks.WHITE_SMOOTH_CHROMA_SLAB,
                ModBlocks.LIGHT_GRAY_SMOOTH_CHROMA_SLAB,
                ModBlocks.GRAY_SMOOTH_CHROMA_SLAB,
                ModBlocks.BLACK_SMOOTH_CHROMA_SLAB,
                ModBlocks.BROWN_SMOOTH_CHROMA_SLAB,
                ModBlocks.RED_SMOOTH_CHROMA_SLAB,
                ModBlocks.ORANGE_SMOOTH_CHROMA_SLAB,
                ModBlocks.YELLOW_SMOOTH_CHROMA_SLAB,
                ModBlocks.LIME_SMOOTH_CHROMA_SLAB,
                ModBlocks.GREEN_SMOOTH_CHROMA_SLAB,
                ModBlocks.CYAN_SMOOTH_CHROMA_SLAB,
                ModBlocks.LIGHT_BLUE_SMOOTH_CHROMA_SLAB,
                ModBlocks.BLUE_SMOOTH_CHROMA_SLAB,
                ModBlocks.PURPLE_SMOOTH_CHROMA_SLAB,
                ModBlocks.MAGENTA_SMOOTH_CHROMA_SLAB,
                ModBlocks.PINK_SMOOTH_CHROMA_SLAB
        );
    }

    private List<DeferredBlock<? extends Block>> getChromaBuildingBlocks() {
        return List.of(
                // Regular Chroma blocks
                ModBlocks.WHITE_CHROMA_BLOCK,
                ModBlocks.LIGHT_GRAY_CHROMA_BLOCK,
                ModBlocks.GRAY_CHROMA_BLOCK,
                ModBlocks.BLACK_CHROMA_BLOCK,
                ModBlocks.BROWN_CHROMA_BLOCK,
                ModBlocks.RED_CHROMA_BLOCK,
                ModBlocks.ORANGE_CHROMA_BLOCK,
                ModBlocks.YELLOW_CHROMA_BLOCK,
                ModBlocks.LIME_CHROMA_BLOCK,
                ModBlocks.GREEN_CHROMA_BLOCK,
                ModBlocks.CYAN_CHROMA_BLOCK,
                ModBlocks.LIGHT_BLUE_CHROMA_BLOCK,
                ModBlocks.BLUE_CHROMA_BLOCK,
                ModBlocks.PURPLE_CHROMA_BLOCK,
                ModBlocks.MAGENTA_CHROMA_BLOCK,
                ModBlocks.PINK_CHROMA_BLOCK,

                // Polished Chroma blocks
                ModBlocks.WHITE_POLISHED_CHROMA_BLOCK,
                ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_BLOCK,
                ModBlocks.GRAY_POLISHED_CHROMA_BLOCK,
                ModBlocks.BLACK_POLISHED_CHROMA_BLOCK,
                ModBlocks.BROWN_POLISHED_CHROMA_BLOCK,
                ModBlocks.RED_POLISHED_CHROMA_BLOCK,
                ModBlocks.ORANGE_POLISHED_CHROMA_BLOCK,
                ModBlocks.YELLOW_POLISHED_CHROMA_BLOCK,
                ModBlocks.LIME_POLISHED_CHROMA_BLOCK,
                ModBlocks.GREEN_POLISHED_CHROMA_BLOCK,
                ModBlocks.CYAN_POLISHED_CHROMA_BLOCK,
                ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_BLOCK,
                ModBlocks.BLUE_POLISHED_CHROMA_BLOCK,
                ModBlocks.PURPLE_POLISHED_CHROMA_BLOCK,
                ModBlocks.MAGENTA_POLISHED_CHROMA_BLOCK,
                ModBlocks.PINK_POLISHED_CHROMA_BLOCK,

                // Smooth Chroma blocks
                ModBlocks.WHITE_SMOOTH_CHROMA_BLOCK,
                ModBlocks.LIGHT_GRAY_SMOOTH_CHROMA_BLOCK,
                ModBlocks.GRAY_SMOOTH_CHROMA_BLOCK,
                ModBlocks.BLACK_SMOOTH_CHROMA_BLOCK,
                ModBlocks.BROWN_SMOOTH_CHROMA_BLOCK,
                ModBlocks.RED_SMOOTH_CHROMA_BLOCK,
                ModBlocks.ORANGE_SMOOTH_CHROMA_BLOCK,
                ModBlocks.YELLOW_SMOOTH_CHROMA_BLOCK,
                ModBlocks.LIME_SMOOTH_CHROMA_BLOCK,
                ModBlocks.GREEN_SMOOTH_CHROMA_BLOCK,
                ModBlocks.CYAN_SMOOTH_CHROMA_BLOCK,
                ModBlocks.LIGHT_BLUE_SMOOTH_CHROMA_BLOCK,
                ModBlocks.BLUE_SMOOTH_CHROMA_BLOCK,
                ModBlocks.PURPLE_SMOOTH_CHROMA_BLOCK,
                ModBlocks.MAGENTA_SMOOTH_CHROMA_BLOCK,
                ModBlocks.PINK_SMOOTH_CHROMA_BLOCK,

                // Chroma pillars
                ModBlocks.WHITE_CHROMA_PILLAR,
                ModBlocks.LIGHT_GRAY_CHROMA_PILLAR,
                ModBlocks.GRAY_CHROMA_PILLAR,
                ModBlocks.BLACK_CHROMA_PILLAR,
                ModBlocks.BROWN_CHROMA_PILLAR,
                ModBlocks.RED_CHROMA_PILLAR,
                ModBlocks.ORANGE_CHROMA_PILLAR,
                ModBlocks.YELLOW_CHROMA_PILLAR,
                ModBlocks.LIME_CHROMA_PILLAR,
                ModBlocks.GREEN_CHROMA_PILLAR,
                ModBlocks.CYAN_CHROMA_PILLAR,
                ModBlocks.LIGHT_BLUE_CHROMA_PILLAR,
                ModBlocks.BLUE_CHROMA_PILLAR,
                ModBlocks.PURPLE_CHROMA_PILLAR,
                ModBlocks.MAGENTA_CHROMA_PILLAR,
                ModBlocks.PINK_CHROMA_PILLAR
        );
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_PRESSURE_PLATE))
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_FENCE))
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_FENCE_GATE))
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_WALL))
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_DOOR))
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_TRAPDOOR))

                .add(ModBlocks.getRK(ModBlocks.DRAINED_STONE))
                .add(ModBlocks.getRK(ModBlocks.DESTABILIZER_WALL_GENERATOR))
                .addTag(ModTags.Blocks.CHROMA_DEPOSITS);

        tag(BlockTags.NEEDS_IRON_TOOL)
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_PRESSURE_PLATE))
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_FENCE))
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_FENCE_GATE))
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_WALL))
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_DOOR))
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_TRAPDOOR))

                .addTag(ModTags.Blocks.CHROMA_DEPOSITS);

        List<DeferredBlock<? extends Block>> chromaBuildingBlocks = new ArrayList<>(getChromaBuildingBlocks());

        chromaBuildingBlocks.addAll(getChromaStairs());
        chromaBuildingBlocks.addAll(getChromaSlabs());

        addBlocksToTag(BlockTags.MINEABLE_WITH_PICKAXE, chromaBuildingBlocks);
        addBlocksToTag(BlockTags.NEEDS_IRON_TOOL, chromaBuildingBlocks);


        tag(ModTags.Blocks.NEEDS_CHROMA_TOOL)
                .add(ModBlocks.getRK(ModBlocks.DESTABILIZER_WALL_GENERATOR))
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_CHROMA_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_CHROMA_TOOL);

        tag(Tags.Blocks.NEEDS_WOOD_TOOL)
                .add(ModBlocks.getRK(ModBlocks.DRAINED_STONE));

        //remove mineable with chroma tools from lower levels to chroma level
        tag(BlockTags.INCORRECT_FOR_COPPER_TOOL).addTag(ModTags.Blocks.NEEDS_CHROMA_TOOL);
        tag(BlockTags.INCORRECT_FOR_GOLD_TOOL).addTag(ModTags.Blocks.NEEDS_CHROMA_TOOL);
        tag(BlockTags.INCORRECT_FOR_STONE_TOOL).addTag(ModTags.Blocks.NEEDS_CHROMA_TOOL);
        tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL).addTag(ModTags.Blocks.NEEDS_CHROMA_TOOL);

        addBlocksToTag(BlockTags.STAIRS, getChromaStairs());
        addBlocksToTag(BlockTags.SLABS, getChromaSlabs());

        //tag(BlockTags.PRESSURE_PLATES)
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_PRESSURE_PLATE));
        //tag(BlockTags.BUTTONS)
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_BUTTON));
        //tag(BlockTags.FENCES)
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_FENCE));
        //tag(BlockTags.FENCE_GATES)
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_FENCE_GATE));
        //tag(BlockTags.WALLS)
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_WALL));
        //tag(BlockTags.DOORS)
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_DOOR));
        //tag(BlockTags.TRAPDOORS)
                //.add(ModBlocks.getRK(ModBlocks.CHROMA_TRAPDOOR));

        tag(ModTags.Blocks.INJECTOR_DRAINABLE)
                .addTag(Tags.Blocks.ORES)
                .addTag(Tags.Blocks.STONES)
                .addTag(Tags.Blocks.SANDS)
                .addTag(Tags.Blocks.NATURAL_LOGS)
                .addTag(Tags.Blocks.GRAVELS)
                .addTag(Tags.Blocks.COBBLESTONES)
                .addTag(BlockTags.GRASS_BLOCKS)
                .addTag(Tags.Blocks.SANDSTONE_BLOCKS)
                .addTag(BlockTags.SUBSTRATE_OVERWORLD)
                .addTag(BlockTags.TERRACOTTA)
                .addTag(BlockTags.BASE_STONE_NETHER);

        tag(ModTags.Blocks.SHELL_DRAINABLE)
                .addTag(Tags.Blocks.SANDS)
                .addTag(Tags.Blocks.GRAVELS)
                .add(BlockItemIds.CLAY.block());

        tag(ModTags.Blocks.GEM_DRAINED)
                .add(ModBlocks.getRK(ModBlocks.DRAINED_STONE));

        tag(ModTags.Blocks.CHROMA_DEPOSITS)
                .add(ModBlocks.getRK(ModBlocks.WHITE_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.LIGHT_GRAY_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.GRAY_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.BLACK_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.BROWN_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.RED_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.ORANGE_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.YELLOW_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.LIME_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.GREEN_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.CYAN_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.LIGHT_BLUE_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.BLUE_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.PURPLE_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.MAGENTA_CHROMA_DEPOSIT))
                .add(ModBlocks.getRK(ModBlocks.PINK_CHROMA_DEPOSIT));

        tag(ModTags.Blocks.CHROMA_PLANTABLE)
                .add(BlockItemIds.STONE.block())
                .add(BlockItemIds.CALCITE.block())
                .add(BlockItemIds.ANDESITE.block())
                .add(BlockItemIds.DEEPSLATE.block())
                .add(BlockItemIds.BLACKSTONE.block())
                .add(BlockItemIds.DIRT.block())
                .add(BlockItemIds.NETHERRACK.block())
                .add(BlockItemIds.RED_SAND.block())
                .add(BlockItemIds.SAND.block())
                .add(BlockItemIds.GRASS_BLOCK.block())
                .add(BlockItemIds.MOSS_BLOCK.block())
                .add(BlockItemIds.PRISMARINE.block())
                .add(BlockItemIds.CLAY.block())
                .add(BlockItemIds.BLUE_ICE.block())
                .add(BlockItemIds.AMETHYST_BLOCK.block())
                .add(BlockItemIds.PURPUR_BLOCK.block())
                .add(BlockItemIds.GRANITE.block());
    }
}
