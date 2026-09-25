package com.fodk.gemcolony.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public record GemDefinition(String id, String name, Item item, EntityType<? extends GemEntity> gem) {
}
