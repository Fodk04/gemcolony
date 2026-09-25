package com.fodk.gemcolony.construction;

import net.minecraft.world.item.Item;

public record ConstructionRequirement(
        int count,
        Item item
) {
}