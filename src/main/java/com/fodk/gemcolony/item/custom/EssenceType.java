package com.fodk.gemcolony.item.custom;

import com.fodk.gemcolony.fluid.EssenceFluid;
import com.fodk.gemcolony.fluid.ModFluids;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

public enum EssenceType {
    PINK(
            Blocks.STONE,
            "§d§lPebble§r",
            ModFluids.PINK_ESSENCE,
            ModItems.PINK_ESSENCE_BOTTLE
    ),
    BLUE(
            Blocks.CLAY,
            "§1§lShale§r",
            ModFluids.BLUE_ESSENCE,
            ModItems.BLUE_ESSENCE_BOTTLE
    ),
    YELLOW(
            Blocks.SAND,
            "§e§lMica§r",
            ModFluids.YELLOW_ESSENCE,
            ModItems.YELLOW_ESSENCE_BOTTLE
    ),
    WHITE(
            Blocks.CALCITE,
            "§f§lNacre§r",
            ModFluids.WHITE_ESSENCE,
            ModItems.WHITE_ESSENCE_BOTTLE
    );

    private final Block blockNeeded;
    private final String gem;
    private final DeferredHolder<Fluid, EssenceFluid.Source> fluid;
    private final DeferredItem<Item> bottle;

    EssenceType(
            Block blockNeeded,
            String gem,
            DeferredHolder<Fluid, EssenceFluid.Source> fluid,
            DeferredItem<Item> bottle
    ) {
        this.blockNeeded = blockNeeded;
        this.gem = gem;
        this.fluid = fluid;
        this.bottle = bottle;
    }

    public String getGem() {
        return gem;
    }

    public Block getBlockNeeded() {
        return blockNeeded;
    }

    public Fluid getFluid() {
        return fluid.get();
    }

    public Item getBottle() {
        return bottle.get();
    }
}
