package com.fodk.gemcolony.block.custom;

import com.fodk.gemcolony.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.lwjgl.opengl.WGLARBRobustnessApplicationIsolation;

import java.util.Random;

public enum BlockToColoredChroma {
    WHITE(ModBlocks.WHITE_CHROMA_DEPOSIT, Blocks.CALCITE),
    LIGHT_GRAY(ModBlocks.LIGHT_GRAY_CHROMA_DEPOSIT, Blocks.ANDESITE),
    GRAY(ModBlocks.GRAY_CHROMA_DEPOSIT, Blocks.DEEPSLATE),
    BLACK(ModBlocks.BLACK_CHROMA_DEPOSIT, Blocks.BLACKSTONE),
    BROWN(ModBlocks.BROWN_CHROMA_DEPOSIT, Blocks.DIRT),
    RED(ModBlocks.RED_CHROMA_DEPOSIT, Blocks.NETHERRACK),
    ORANGE(ModBlocks.ORANGE_CHROMA_DEPOSIT, Blocks.RED_SAND),
    YELLOW(ModBlocks.YELLOW_CHROMA_DEPOSIT, Blocks.SAND),
    LIME(ModBlocks.LIME_CHROMA_DEPOSIT, Blocks.MOSS_BLOCK),
    GREEN(ModBlocks.GREEN_CHROMA_DEPOSIT, Blocks.GRASS_BLOCK),
    CYAN(ModBlocks.CYAN_CHROMA_DEPOSIT, Blocks.PRISMARINE),
    LIGHT_BLUE(ModBlocks.LIGHT_BLUE_CHROMA_DEPOSIT, Blocks.CLAY),
    BLUE(ModBlocks.BLUE_CHROMA_DEPOSIT, Blocks.BLUE_ICE),
    PURPLE(ModBlocks.PURPLE_CHROMA_DEPOSIT, Blocks.AMETHYST_BLOCK),
    MAGENTA(ModBlocks.MAGENTA_CHROMA_DEPOSIT, Blocks.PURPUR_BLOCK),
    PINK(ModBlocks.PINK_CHROMA_DEPOSIT, Blocks.GRANITE);

    Block growthBlock;
    DeferredBlock<Block> coloredChromaDeposit;

    BlockToColoredChroma(DeferredBlock<Block> coloredChromaDeposit, Block growthBlock) {
        this.growthBlock = growthBlock;
        this.coloredChromaDeposit = coloredChromaDeposit;
    }

    public static DeferredBlock<Block> depositFromGrowthBlock(Block block) {
        DeferredBlock<Block> chromaDepositToGrow = null;

        for (BlockToColoredChroma value : values()) {
            if (block == value.growthBlock) {
                return value.coloredChromaDeposit;
            } else if (chromaDepositToGrow == null) {
                Random random = new Random();
                if(random.nextInt(15) == 0){
                    chromaDepositToGrow = value.coloredChromaDeposit;
                }
            }
        }
        if (chromaDepositToGrow == null) {
            chromaDepositToGrow = ModBlocks.WHITE_CHROMA_DEPOSIT;
        }
        return chromaDepositToGrow;
    }
}
