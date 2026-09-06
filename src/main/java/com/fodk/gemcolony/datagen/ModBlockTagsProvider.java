package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockIds;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, GemColony.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.getRK(ModBlocks.CHROMA_BLOCK))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_STAIRS))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_SLAB))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_PRESSURE_PLATE))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_FENCE))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_FENCE_GATE))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_WALL))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_DOOR))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_TRAPDOOR))

                .add(ModBlocks.getRK(ModBlocks.DRAINED_STONE))
                .add(ModBlocks.getRK(ModBlocks.DESTABILIZER_WALL_GENERATOR))
                .addTag(ModTags.Blocks.CHROMA_DEPOSITS);

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.getRK(ModBlocks.CHROMA_BLOCK))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_STAIRS))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_SLAB))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_PRESSURE_PLATE))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_FENCE))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_FENCE_GATE))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_WALL))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_DOOR))
                .add(ModBlocks.getRK(ModBlocks.CHROMA_TRAPDOOR))

                .addTag(ModTags.Blocks.CHROMA_DEPOSITS);

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

        tag(BlockTags.STAIRS)
                .add(ModBlocks.getRK(ModBlocks.CHROMA_STAIRS));
        tag(BlockTags.SLABS)
                .add(ModBlocks.getRK(ModBlocks.CHROMA_SLAB));
        tag(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.getRK(ModBlocks.CHROMA_PRESSURE_PLATE));
        tag(BlockTags.BUTTONS)
                .add(ModBlocks.getRK(ModBlocks.CHROMA_BUTTON));
        tag(BlockTags.FENCES)
                .add(ModBlocks.getRK(ModBlocks.CHROMA_FENCE));
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.getRK(ModBlocks.CHROMA_FENCE_GATE));
        tag(BlockTags.WALLS)
                .add(ModBlocks.getRK(ModBlocks.CHROMA_WALL));
        tag(BlockTags.DOORS)
                .add(ModBlocks.getRK(ModBlocks.CHROMA_DOOR));
        tag(BlockTags.TRAPDOORS)
                .add(ModBlocks.getRK(ModBlocks.CHROMA_TRAPDOOR));

        tag(ModTags.Blocks.GEM_DRAINABLES)
                .addTag(Tags.Blocks.ORES)
                .addTag(Tags.Blocks.STONES)
                .addTag(Tags.Blocks.SANDS)
                .addTag(Tags.Blocks.NATURAL_LOGS);

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
                .add(BlockItemIds.STONE.block())
                .add(BlockItemIds.GRANITE.block());
    }
}
