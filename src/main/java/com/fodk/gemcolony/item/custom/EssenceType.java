package com.fodk.gemcolony.item.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public enum EssenceType {
    PINK(Blocks.STONE, "§d§lPebble§r"),
    BLUE(Blocks.CLAY, "§1§lShale§r"),
    YELLOW(Blocks.SAND, "§e§lMica§r"),
    WHITE(Blocks.CALCITE, "§f§lNacre§r");

    private final Block blockNeeded;
    private final String gem;

    EssenceType(Block blockNeeded, String gem) {
        this.blockNeeded = blockNeeded;
        this.gem = gem;
    }

    public String getGem(){
        return gem;
    }

    public Block getBlockNeeded(){
        return blockNeeded;
    }
}
