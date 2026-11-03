package com.fodk.gemcolony.item.custom;

import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.fluid.EssenceFluid;
import com.fodk.gemcolony.fluid.ModFluids;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.awt.*;

public enum EssenceType {
    PINK(
            Blocks.GRAVEL,
            "§d§lPebble§r",
            ModFluids.PINK_ESSENCE,
            ModItems.PINK_ESSENCE_BOTTLE,
            ModEntities.PEBBLE.get(),
            new Color(255, 127, 165)
    ),
    BLUE(
            Blocks.CLAY,
            "§1§lShale§r",
            ModFluids.BLUE_ESSENCE,
            ModItems.BLUE_ESSENCE_BOTTLE,
            ModEntities.SHALE.get(),
            new Color(9, 225, 209)
    ),
    YELLOW(
            Blocks.SAND,
            "§e§lMica§r",
            ModFluids.YELLOW_ESSENCE,
            ModItems.YELLOW_ESSENCE_BOTTLE,
            ModEntities.MICA.get(),
            new Color(220, 250,0)
    ),
    WHITE(
            Blocks.CALCITE,
            "§f§lNacre§r",
            ModFluids.WHITE_ESSENCE,
            ModItems.WHITE_ESSENCE_BOTTLE,
            ModEntities.NACRE.get(),
            new Color(255, 255, 255)
    );

    private final Block blockNeeded;
    private final String gem;
    private final DeferredHolder<Fluid, EssenceFluid.Source> fluid;
    private final DeferredItem<Item> bottle;
    private final EntityType<? extends GemEntity> entityType;
    private final Color tint;

    EssenceType(Block blockNeeded, String gem, DeferredHolder<Fluid, EssenceFluid.Source> fluid, DeferredItem<Item> bottle, EntityType<? extends GemEntity> entityType, Color tint) {
        this.blockNeeded = blockNeeded;
        this.gem = gem;
        this.fluid = fluid;
        this.bottle = bottle;
        this.entityType = entityType;
        this.tint = tint;
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

    public EntityType<? extends GemEntity> getEntityType() {
        return entityType;
    }

    public Color getTint() {
        return tint;
    }
}
