package com.fodk.gemcolony.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Supplier;

public record GemDefinition(String id, String name, List<Item> items, EntityType<? extends GemEntity> gem) {
}
