package com.fodk.gemcolony.block;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.custom.*;
import com.fodk.gemcolony.block.custom.DrillBlock;
import com.fodk.gemcolony.block.custom.TankBlock;
import com.fodk.gemcolony.fluid.ModFluids;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.util.ColorUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;
import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(GemColony.MOD_ID);


    // CHROMA BLOCKS
    public static final DeferredBlock<Block> WHITE_CHROMA_BLOCK =
            registerChromaBlock("white_chroma_block", MapColor.SNOW);
    public static final DeferredBlock<Block> LIGHT_GRAY_CHROMA_BLOCK =
            registerChromaBlock("light_gray_chroma_block", MapColor.COLOR_LIGHT_GRAY);
    public static final DeferredBlock<Block> GRAY_CHROMA_BLOCK =
            registerChromaBlock("gray_chroma_block", MapColor.COLOR_GRAY);
    public static final DeferredBlock<Block> BLACK_CHROMA_BLOCK =
            registerChromaBlock("black_chroma_block", MapColor.COLOR_BLACK);
    public static final DeferredBlock<Block> BROWN_CHROMA_BLOCK =
            registerChromaBlock("brown_chroma_block", MapColor.COLOR_BROWN);
    public static final DeferredBlock<Block> RED_CHROMA_BLOCK =
            registerChromaBlock("red_chroma_block", MapColor.COLOR_RED);
    public static final DeferredBlock<Block> ORANGE_CHROMA_BLOCK =
            registerChromaBlock("orange_chroma_block", MapColor.COLOR_ORANGE);
    public static final DeferredBlock<Block> YELLOW_CHROMA_BLOCK =
            registerChromaBlock("yellow_chroma_block", MapColor.COLOR_YELLOW);
    public static final DeferredBlock<Block> LIME_CHROMA_BLOCK =
            registerChromaBlock("lime_chroma_block", MapColor.COLOR_LIGHT_GREEN);
    public static final DeferredBlock<Block> GREEN_CHROMA_BLOCK =
            registerChromaBlock("green_chroma_block", MapColor.COLOR_GREEN);
    public static final DeferredBlock<Block> CYAN_CHROMA_BLOCK =
            registerChromaBlock("cyan_chroma_block", MapColor.COLOR_CYAN);
    public static final DeferredBlock<Block> LIGHT_BLUE_CHROMA_BLOCK =
            registerChromaBlock("light_blue_chroma_block", MapColor.COLOR_LIGHT_BLUE);
    public static final DeferredBlock<Block> BLUE_CHROMA_BLOCK =
            registerChromaBlock("blue_chroma_block", MapColor.COLOR_BLUE);
    public static final DeferredBlock<Block> PURPLE_CHROMA_BLOCK =
            registerChromaBlock("purple_chroma_block", MapColor.COLOR_PURPLE);
    public static final DeferredBlock<Block> MAGENTA_CHROMA_BLOCK =
            registerChromaBlock("magenta_chroma_block", MapColor.COLOR_MAGENTA);
    public static final DeferredBlock<Block> PINK_CHROMA_BLOCK =
            registerChromaBlock("pink_chroma_block", MapColor.COLOR_PINK);

    // CHROMA STAIRS
    public static final DeferredBlock<StairBlock> WHITE_CHROMA_STAIRS =
            registerChromaStairs("white_chroma_stairs", WHITE_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> LIGHT_GRAY_CHROMA_STAIRS =
            registerChromaStairs("light_gray_chroma_stairs", LIGHT_GRAY_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> GRAY_CHROMA_STAIRS =
            registerChromaStairs("gray_chroma_stairs", GRAY_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> BLACK_CHROMA_STAIRS =
            registerChromaStairs("black_chroma_stairs", BLACK_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> BROWN_CHROMA_STAIRS =
            registerChromaStairs("brown_chroma_stairs", BROWN_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> RED_CHROMA_STAIRS =
            registerChromaStairs("red_chroma_stairs", RED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> ORANGE_CHROMA_STAIRS =
            registerChromaStairs("orange_chroma_stairs", ORANGE_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> YELLOW_CHROMA_STAIRS =
            registerChromaStairs("yellow_chroma_stairs", YELLOW_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> LIME_CHROMA_STAIRS =
            registerChromaStairs("lime_chroma_stairs", LIME_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> GREEN_CHROMA_STAIRS =
            registerChromaStairs("green_chroma_stairs", GREEN_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> CYAN_CHROMA_STAIRS =
            registerChromaStairs("cyan_chroma_stairs", CYAN_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> LIGHT_BLUE_CHROMA_STAIRS =
            registerChromaStairs("light_blue_chroma_stairs", LIGHT_BLUE_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> BLUE_CHROMA_STAIRS =
            registerChromaStairs("blue_chroma_stairs", BLUE_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> PURPLE_CHROMA_STAIRS =
            registerChromaStairs("purple_chroma_stairs", PURPLE_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> MAGENTA_CHROMA_STAIRS =
            registerChromaStairs("magenta_chroma_stairs", MAGENTA_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> PINK_CHROMA_STAIRS =
            registerChromaStairs("pink_chroma_stairs", PINK_CHROMA_BLOCK);

    // CHROMA SLABS
    public static final DeferredBlock<SlabBlock> WHITE_CHROMA_SLAB =
            registerChromaSlab("white_chroma_slab");
    public static final DeferredBlock<SlabBlock> LIGHT_GRAY_CHROMA_SLAB =
            registerChromaSlab("light_gray_chroma_slab");
    public static final DeferredBlock<SlabBlock> GRAY_CHROMA_SLAB =
            registerChromaSlab("gray_chroma_slab");
    public static final DeferredBlock<SlabBlock> BLACK_CHROMA_SLAB =
            registerChromaSlab("black_chroma_slab");
    public static final DeferredBlock<SlabBlock> BROWN_CHROMA_SLAB =
            registerChromaSlab("brown_chroma_slab");
    public static final DeferredBlock<SlabBlock> RED_CHROMA_SLAB =
            registerChromaSlab("red_chroma_slab");
    public static final DeferredBlock<SlabBlock> ORANGE_CHROMA_SLAB =
            registerChromaSlab("orange_chroma_slab");
    public static final DeferredBlock<SlabBlock> YELLOW_CHROMA_SLAB =
            registerChromaSlab("yellow_chroma_slab");
    public static final DeferredBlock<SlabBlock> LIME_CHROMA_SLAB =
            registerChromaSlab("lime_chroma_slab");
    public static final DeferredBlock<SlabBlock> GREEN_CHROMA_SLAB =
            registerChromaSlab("green_chroma_slab");
    public static final DeferredBlock<SlabBlock> CYAN_CHROMA_SLAB =
            registerChromaSlab("cyan_chroma_slab");
    public static final DeferredBlock<SlabBlock> LIGHT_BLUE_CHROMA_SLAB =
            registerChromaSlab("light_blue_chroma_slab");
    public static final DeferredBlock<SlabBlock> BLUE_CHROMA_SLAB =
            registerChromaSlab("blue_chroma_slab");
    public static final DeferredBlock<SlabBlock> PURPLE_CHROMA_SLAB =
            registerChromaSlab("purple_chroma_slab");
    public static final DeferredBlock<SlabBlock> MAGENTA_CHROMA_SLAB =
            registerChromaSlab("magenta_chroma_slab");
    public static final DeferredBlock<SlabBlock> PINK_CHROMA_SLAB =
            registerChromaSlab("pink_chroma_slab");

    // POLISHED CHROMA BLOCKS
    public static final DeferredBlock<Block> WHITE_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("white_polished_chroma_block", MapColor.SNOW);
    public static final DeferredBlock<Block> LIGHT_GRAY_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("light_gray_polished_chroma_block", MapColor.COLOR_LIGHT_GRAY);
    public static final DeferredBlock<Block> GRAY_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("gray_polished_chroma_block", MapColor.COLOR_GRAY);
    public static final DeferredBlock<Block> BLACK_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("black_polished_chroma_block", MapColor.COLOR_BLACK);
    public static final DeferredBlock<Block> BROWN_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("brown_polished_chroma_block", MapColor.COLOR_BROWN);
    public static final DeferredBlock<Block> RED_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("red_polished_chroma_block", MapColor.COLOR_RED);
    public static final DeferredBlock<Block> ORANGE_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("orange_polished_chroma_block", MapColor.COLOR_ORANGE);
    public static final DeferredBlock<Block> YELLOW_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("yellow_polished_chroma_block", MapColor.COLOR_YELLOW);
    public static final DeferredBlock<Block> LIME_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("lime_polished_chroma_block", MapColor.COLOR_LIGHT_GREEN);
    public static final DeferredBlock<Block> GREEN_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("green_polished_chroma_block", MapColor.COLOR_GREEN);
    public static final DeferredBlock<Block> CYAN_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("cyan_polished_chroma_block", MapColor.COLOR_CYAN);
    public static final DeferredBlock<Block> LIGHT_BLUE_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("light_blue_polished_chroma_block", MapColor.COLOR_LIGHT_BLUE);
    public static final DeferredBlock<Block> BLUE_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("blue_polished_chroma_block", MapColor.COLOR_BLUE);
    public static final DeferredBlock<Block> PURPLE_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("purple_polished_chroma_block", MapColor.COLOR_PURPLE);
    public static final DeferredBlock<Block> MAGENTA_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("magenta_polished_chroma_block", MapColor.COLOR_MAGENTA);
    public static final DeferredBlock<Block> PINK_POLISHED_CHROMA_BLOCK =
            registerChromaBlock("pink_polished_chroma_block", MapColor.COLOR_PINK);

    // POLISHED CHROMA STAIRS
    public static final DeferredBlock<StairBlock> WHITE_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("white_polished_chroma_stairs", WHITE_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> LIGHT_GRAY_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("light_gray_polished_chroma_stairs", LIGHT_GRAY_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> GRAY_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("gray_polished_chroma_stairs", GRAY_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> BLACK_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("black_polished_chroma_stairs", BLACK_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> BROWN_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("brown_polished_chroma_stairs", BROWN_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> RED_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("red_polished_chroma_stairs", RED_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> ORANGE_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("orange_polished_chroma_stairs", ORANGE_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> YELLOW_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("yellow_polished_chroma_stairs", YELLOW_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> LIME_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("lime_polished_chroma_stairs", LIME_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> GREEN_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("green_polished_chroma_stairs", GREEN_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> CYAN_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("cyan_polished_chroma_stairs", CYAN_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> LIGHT_BLUE_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("light_blue_polished_chroma_stairs", LIGHT_BLUE_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> BLUE_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("blue_polished_chroma_stairs", BLUE_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> PURPLE_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("purple_polished_chroma_stairs", PURPLE_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> MAGENTA_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("magenta_polished_chroma_stairs", MAGENTA_POLISHED_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> PINK_POLISHED_CHROMA_STAIRS =
            registerChromaStairs("pink_polished_chroma_stairs", PINK_POLISHED_CHROMA_BLOCK);

    // POLISHED CHROMA SLABS
    public static final DeferredBlock<SlabBlock> WHITE_POLISHED_CHROMA_SLAB =
            registerChromaSlab("white_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> LIGHT_GRAY_POLISHED_CHROMA_SLAB =
            registerChromaSlab("light_gray_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> GRAY_POLISHED_CHROMA_SLAB =
            registerChromaSlab("gray_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> BLACK_POLISHED_CHROMA_SLAB =
            registerChromaSlab("black_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> BROWN_POLISHED_CHROMA_SLAB =
            registerChromaSlab("brown_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> RED_POLISHED_CHROMA_SLAB =
            registerChromaSlab("red_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> ORANGE_POLISHED_CHROMA_SLAB =
            registerChromaSlab("orange_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> YELLOW_POLISHED_CHROMA_SLAB =
            registerChromaSlab("yellow_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> LIME_POLISHED_CHROMA_SLAB =
            registerChromaSlab("lime_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> GREEN_POLISHED_CHROMA_SLAB =
            registerChromaSlab("green_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> CYAN_POLISHED_CHROMA_SLAB =
            registerChromaSlab("cyan_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> LIGHT_BLUE_POLISHED_CHROMA_SLAB =
            registerChromaSlab("light_blue_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> BLUE_POLISHED_CHROMA_SLAB =
            registerChromaSlab("blue_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> PURPLE_POLISHED_CHROMA_SLAB =
            registerChromaSlab("purple_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> MAGENTA_POLISHED_CHROMA_SLAB =
            registerChromaSlab("magenta_polished_chroma_slab");
    public static final DeferredBlock<SlabBlock> PINK_POLISHED_CHROMA_SLAB =
            registerChromaSlab("pink_polished_chroma_slab");

    // SMOOTH CHROMA BLOCKS
    public static final DeferredBlock<Block> WHITE_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("white_smooth_chroma_block", MapColor.SNOW);
    public static final DeferredBlock<Block> LIGHT_GRAY_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("light_gray_smooth_chroma_block", MapColor.COLOR_LIGHT_GRAY);
    public static final DeferredBlock<Block> GRAY_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("gray_smooth_chroma_block", MapColor.COLOR_GRAY);
    public static final DeferredBlock<Block> BLACK_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("black_smooth_chroma_block", MapColor.COLOR_BLACK);
    public static final DeferredBlock<Block> BROWN_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("brown_smooth_chroma_block", MapColor.COLOR_BROWN);
    public static final DeferredBlock<Block> RED_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("red_smooth_chroma_block", MapColor.COLOR_RED);
    public static final DeferredBlock<Block> ORANGE_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("orange_smooth_chroma_block", MapColor.COLOR_ORANGE);
    public static final DeferredBlock<Block> YELLOW_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("yellow_smooth_chroma_block", MapColor.COLOR_YELLOW);
    public static final DeferredBlock<Block> LIME_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("lime_smooth_chroma_block", MapColor.COLOR_LIGHT_GREEN);
    public static final DeferredBlock<Block> GREEN_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("green_smooth_chroma_block", MapColor.COLOR_GREEN);
    public static final DeferredBlock<Block> CYAN_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("cyan_smooth_chroma_block", MapColor.COLOR_CYAN);
    public static final DeferredBlock<Block> LIGHT_BLUE_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("light_blue_smooth_chroma_block", MapColor.COLOR_LIGHT_BLUE);
    public static final DeferredBlock<Block> BLUE_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("blue_smooth_chroma_block", MapColor.COLOR_BLUE);
    public static final DeferredBlock<Block> PURPLE_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("purple_smooth_chroma_block", MapColor.COLOR_PURPLE);
    public static final DeferredBlock<Block> MAGENTA_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("magenta_smooth_chroma_block", MapColor.COLOR_MAGENTA);
    public static final DeferredBlock<Block> PINK_SMOOTH_CHROMA_BLOCK =
            registerChromaBlock("pink_smooth_chroma_block", MapColor.COLOR_PINK);

    // SMOOTH CHROMA STAIRS
    public static final DeferredBlock<StairBlock> WHITE_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("white_smooth_chroma_stairs", WHITE_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> LIGHT_GRAY_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("light_gray_smooth_chroma_stairs", LIGHT_GRAY_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> GRAY_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("gray_smooth_chroma_stairs", GRAY_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> BLACK_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("black_smooth_chroma_stairs", BLACK_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> BROWN_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("brown_smooth_chroma_stairs", BROWN_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> RED_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("red_smooth_chroma_stairs", RED_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> ORANGE_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("orange_smooth_chroma_stairs", ORANGE_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> YELLOW_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("yellow_smooth_chroma_stairs", YELLOW_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> LIME_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("lime_smooth_chroma_stairs", LIME_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> GREEN_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("green_smooth_chroma_stairs", GREEN_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> CYAN_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("cyan_smooth_chroma_stairs", CYAN_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> LIGHT_BLUE_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("light_blue_smooth_chroma_stairs", LIGHT_BLUE_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> BLUE_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("blue_smooth_chroma_stairs", BLUE_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> PURPLE_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("purple_smooth_chroma_stairs", PURPLE_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> MAGENTA_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("magenta_smooth_chroma_stairs", MAGENTA_SMOOTH_CHROMA_BLOCK);
    public static final DeferredBlock<StairBlock> PINK_SMOOTH_CHROMA_STAIRS =
            registerChromaStairs("pink_smooth_chroma_stairs", PINK_SMOOTH_CHROMA_BLOCK);

    // SMOOTH CHROMA SLABS
    public static final DeferredBlock<SlabBlock> WHITE_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("white_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> LIGHT_GRAY_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("light_gray_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> GRAY_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("gray_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> BLACK_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("black_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> BROWN_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("brown_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> RED_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("red_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> ORANGE_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("orange_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> YELLOW_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("yellow_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> LIME_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("lime_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> GREEN_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("green_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> CYAN_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("cyan_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> LIGHT_BLUE_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("light_blue_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> BLUE_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("blue_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> PURPLE_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("purple_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> MAGENTA_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("magenta_smooth_chroma_slab");
    public static final DeferredBlock<SlabBlock> PINK_SMOOTH_CHROMA_SLAB =
            registerChromaSlab("pink_smooth_chroma_slab");

    // CHROMA PILLARS
    public static final DeferredBlock<RotatedPillarBlock> WHITE_CHROMA_PILLAR =
            registerChromaPillar("white_chroma_pillar", MapColor.SNOW);
    public static final DeferredBlock<RotatedPillarBlock> LIGHT_GRAY_CHROMA_PILLAR =
            registerChromaPillar("light_gray_chroma_pillar", MapColor.COLOR_LIGHT_GRAY);
    public static final DeferredBlock<RotatedPillarBlock> GRAY_CHROMA_PILLAR =
            registerChromaPillar("gray_chroma_pillar", MapColor.COLOR_GRAY);
    public static final DeferredBlock<RotatedPillarBlock> BLACK_CHROMA_PILLAR =
            registerChromaPillar("black_chroma_pillar", MapColor.COLOR_BLACK);
    public static final DeferredBlock<RotatedPillarBlock> BROWN_CHROMA_PILLAR =
            registerChromaPillar("brown_chroma_pillar", MapColor.COLOR_BROWN);
    public static final DeferredBlock<RotatedPillarBlock> RED_CHROMA_PILLAR =
            registerChromaPillar("red_chroma_pillar", MapColor.COLOR_RED);
    public static final DeferredBlock<RotatedPillarBlock> ORANGE_CHROMA_PILLAR =
            registerChromaPillar("orange_chroma_pillar", MapColor.COLOR_ORANGE);
    public static final DeferredBlock<RotatedPillarBlock> YELLOW_CHROMA_PILLAR =
            registerChromaPillar("yellow_chroma_pillar", MapColor.COLOR_YELLOW);
    public static final DeferredBlock<RotatedPillarBlock> LIME_CHROMA_PILLAR =
            registerChromaPillar("lime_chroma_pillar", MapColor.COLOR_LIGHT_GREEN);
    public static final DeferredBlock<RotatedPillarBlock> GREEN_CHROMA_PILLAR =
            registerChromaPillar("green_chroma_pillar", MapColor.COLOR_GREEN);
    public static final DeferredBlock<RotatedPillarBlock> CYAN_CHROMA_PILLAR =
            registerChromaPillar("cyan_chroma_pillar", MapColor.COLOR_CYAN);
    public static final DeferredBlock<RotatedPillarBlock> LIGHT_BLUE_CHROMA_PILLAR =
            registerChromaPillar("light_blue_chroma_pillar", MapColor.COLOR_LIGHT_BLUE);
    public static final DeferredBlock<RotatedPillarBlock> BLUE_CHROMA_PILLAR =
            registerChromaPillar("blue_chroma_pillar", MapColor.COLOR_BLUE);
    public static final DeferredBlock<RotatedPillarBlock> PURPLE_CHROMA_PILLAR =
            registerChromaPillar("purple_chroma_pillar", MapColor.COLOR_PURPLE);
    public static final DeferredBlock<RotatedPillarBlock> MAGENTA_CHROMA_PILLAR =
            registerChromaPillar("magenta_chroma_pillar", MapColor.COLOR_MAGENTA);
    public static final DeferredBlock<RotatedPillarBlock> PINK_CHROMA_PILLAR =
            registerChromaPillar("pink_chroma_pillar", MapColor.COLOR_PINK);

    /*public static final DeferredBlock<Block> CHROMA_PRESSURE_PLATE = registerBlock("chroma_pressure_plate",
            properties -> new PressurePlateBlock(BlockSetType.GOLD, properties
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(0.5F)
                    .requiresCorrectToolForDrops()
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_BUTTON = registerBlock("chroma_button",
            properties -> new ButtonBlock(BlockSetType.GOLD, 30, properties
                    .noCollision()
                    .strength(0.5F)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_FENCE = registerBlock("chroma_fence",
            properties -> new FenceBlock(properties
                    .strength(2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_FENCE_GATE = registerBlock("chroma_fence_gate",
            properties -> new FenceGateBlock(WoodType.ACACIA, properties
                    .strength(2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_WALL = registerBlock("chroma_wall",
            properties -> new WallBlock(properties
                    .strength(2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_DOOR = registerBlock("chroma_door",
            properties -> new DoorBlock(BlockSetType.GOLD, properties
                    .strength(2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .noOcclusion()
            ));

    public static final DeferredBlock<Block> CHROMA_TRAPDOOR = registerBlock("chroma_trapdoor",
            properties -> new TrapDoorBlock(BlockSetType.GOLD, properties
                    .strength(2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .noOcclusion()
            ));*/

    public static final DeferredBlock<Block> DRAINED_STONE = registerBlock("drained_stone",
            properties -> new Block(properties
                    .strength(2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
            ));

    public static final DeferredBlock<Block> DESTABILIZER_WALL_GENERATOR = registerBlock("destabilizer_wall_generator",
            properties -> new DestabilizerWallGenerator(properties
                    .strength(1)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.GLASS)
                    .lightLevel(state -> state.getValue(DestabilizerWallGenerator.ACTIVE) ? 10 : 0)
            ), Component.translatable("tooltip.gemcolony.destabilizer_wall_generator.tooltip"));

    //CHROMA DEPOSITS
    public static final DeferredBlock<Block> WHITE_CHROMA_DEPOSIT = registerBlock("white_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.WHITE.getColorIndex()));
    public static final DeferredBlock<Block> LIGHT_GRAY_CHROMA_DEPOSIT = registerBlock("light_gray_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.LIGHT_GRAY.getColorIndex()));
    public static final DeferredBlock<Block> GRAY_CHROMA_DEPOSIT = registerBlock("gray_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.GRAY.getColorIndex()));
    public static final DeferredBlock<Block> BLACK_CHROMA_DEPOSIT = registerBlock("black_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.BLACK.getColorIndex()));
    public static final DeferredBlock<Block> BROWN_CHROMA_DEPOSIT = registerBlock("brown_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.BROWN.getColorIndex()));
    public static final DeferredBlock<Block> RED_CHROMA_DEPOSIT = registerBlock("red_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.RED.getColorIndex()));
    public static final DeferredBlock<Block> ORANGE_CHROMA_DEPOSIT = registerBlock("orange_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.ORANGE.getColorIndex()));
    public static final DeferredBlock<Block> YELLOW_CHROMA_DEPOSIT = registerBlock("yellow_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.YELLOW.getColorIndex()));
    public static final DeferredBlock<Block> LIME_CHROMA_DEPOSIT = registerBlock("lime_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.LIME.getColorIndex()));
    public static final DeferredBlock<Block> GREEN_CHROMA_DEPOSIT = registerBlock("green_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.GREEN.getColorIndex()));
    public static final DeferredBlock<Block> CYAN_CHROMA_DEPOSIT = registerBlock("cyan_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.CYAN.getColorIndex()));
    public static final DeferredBlock<Block> LIGHT_BLUE_CHROMA_DEPOSIT = registerBlock("light_blue_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.LIGHT_BLUE.getColorIndex()));
    public static final DeferredBlock<Block> BLUE_CHROMA_DEPOSIT = registerBlock("blue_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.BLUE.getColorIndex()));
    public static final DeferredBlock<Block> PURPLE_CHROMA_DEPOSIT = registerBlock("purple_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.PURPLE.getColorIndex()));
    public static final DeferredBlock<Block> MAGENTA_CHROMA_DEPOSIT = registerBlock("magenta_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.MAGENTA.getColorIndex()));
    public static final DeferredBlock<Block> PINK_CHROMA_DEPOSIT = registerBlock("pink_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorUtil.PINK.getColorIndex()));

    //chroma crop
    public static final DeferredBlock<Block> CHROMA_CROP = BLOCKS.registerBlock("chroma_crop",
            properties -> new ChromaCrop(properties));

    public static final DeferredBlock<Block> STRAWBERRY_BUSH = BLOCKS.registerBlock("strawberry_bush",
            properties -> new StrawberryBushBlock(properties));

    //custom size
    public static final DeferredBlock<Block> LEG = registerBlock(
            "leg",
            properties -> new LegBlock(
                    properties
                            .strength(2F)
                            .explosionResistance(Float.MAX_VALUE)
                            .noLootTable()
                            .sound(SoundType.METAL)
                            .noOcclusion()
            )
    );

    public static final DeferredBlock<Block> CRYSTAL = registerBlock(
            "crystal",
            properties -> new CrystalBlock(
                    properties
                            .strength(2F)
                            .explosionResistance(Float.MAX_VALUE)
                            .noLootTable()
                            .sound(SoundType.GLASS)
                            .noOcclusion()
            )
    );

    public static final DeferredBlock<Block> TANK = registerBlock(
            "tank",
            properties -> new TankBlock(
                    properties
                            .strength(2F)
                            .explosionResistance(Float.MAX_VALUE)
                            .noLootTable()
                            .sound(SoundType.METAL)
                            .noOcclusion()
            )
    );

    //block entities
    public static final DeferredBlock<Block> DRILL = registerBlock(
            "drill",
            properties -> new DrillBlock(
                    properties
                            .strength(2F)
                            .explosionResistance(Float.MAX_VALUE)
                            .noLootTable()
                            .sound(SoundType.METAL)
                            .noOcclusion()
            )
    );

    public static final DeferredBlock<Block> SHELL = registerBlock(
            "shell",
            properties -> new ShellBlock(
                    properties
                            .strength(2F).explosionResistance(Float.MAX_VALUE)
                            .noLootTable()
                            .sound(SoundType.METAL)
                            .noOcclusion()
            )
    );

    public static final DeferredBlock<Block> GEM_SEED = registerBlock(
            "gem_seed_block",
            properties -> new GemSeedBlock(
                    properties
                            .strength(1F)
                            .noLootTable()
                            .sound(SoundType.MOSS)
                            .noOcclusion()
            )
    );

    //fluids
    public static final DeferredBlock<LiquidBlock> BLUE_ESSENCE =
            BLOCKS.registerBlock(
                    "blue_essence",
                    properties -> new LiquidBlock(
                            ModFluids.FLOWING_BLUE_ESSENCE.get(),
                            BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
                                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "blue_essence")))
                    )
            );
    public static final DeferredBlock<LiquidBlock> YELLOW_ESSENCE =
            BLOCKS.registerBlock(
                    "yellow_essence",
                    properties -> new LiquidBlock(
                            ModFluids.FLOWING_YELLOW_ESSENCE.get(),
                            BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
                                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "yellow_essence")))
                    )
            );
    public static final DeferredBlock<LiquidBlock> WHITE_ESSENCE =
            BLOCKS.registerBlock(
                    "white_essence",
                    properties -> new LiquidBlock(
                            ModFluids.FLOWING_WHITE_ESSENCE.get(),
                            BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
                                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "white_essence")))
                    )
            );
    public static final DeferredBlock<LiquidBlock> PINK_ESSENCE =
            BLOCKS.registerBlock(
                    "pink_essence",
                    properties -> new LiquidBlock(
                            ModFluids.FLOWING_PINK_ESSENCE.get(),
                            BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
                                    .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "pink_essence")))
                    )
            );

    //get resource key for tags
    public static ResourceKey<Block> getRK(DeferredBlock deferredBlock){
        return BuiltInRegistries.BLOCK.getResourceKey((Block) deferredBlock.get()).get();
    }


    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block, Component... components){
        ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()){
            @Override
            public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
                for(var component : components){
                    builder.accept(component);
                }
                super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
            }
        });
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function){
        DeferredBlock<T> blockToReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, blockToReturn);
        return blockToReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function, Component... components){
        DeferredBlock<T> blockToReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, blockToReturn, components);
        return blockToReturn;
    }

    private static DeferredBlock<Block> registerChromaBlock(String name, MapColor mapColor) {
        return registerBlock(name, properties -> new Block(
                properties
                        .mapColor(mapColor)
                        .strength(2.0F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.AMETHYST)
        ));
    }

    private static DeferredBlock<StairBlock> registerChromaStairs(String name, DeferredBlock<Block> baseBlock) {
        return registerBlock(name, properties -> new StairBlock(
                baseBlock.get().defaultBlockState(),
                properties
                        .strength(2.0F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.AMETHYST)
        ));
    }

    private static DeferredBlock<SlabBlock> registerChromaSlab(String name) {
        return registerBlock(name, properties -> new SlabBlock(
                properties
                        .strength(2.0F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.AMETHYST)
        ));
    }


    private static DeferredBlock<RotatedPillarBlock> registerChromaPillar(String name, MapColor mapColor) {
        return registerBlock(name, properties -> new RotatedPillarBlock(
                properties
                        .mapColor(mapColor)
                        .strength(2.0F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.AMETHYST)
        ));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
