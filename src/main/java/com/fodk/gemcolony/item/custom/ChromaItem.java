package com.fodk.gemcolony.item.custom;

import net.minecraft.world.item.Item;

public class ChromaItem extends Item {
    public final int colorIndex;

    public ChromaItem(Properties properties, int colorIndex) {
        super(properties);
        this.colorIndex = colorIndex;
    }
}
