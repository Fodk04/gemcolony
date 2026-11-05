package com.fodk.gemcolony.entity.custom.gem.base;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.List;

public record GemDefinition(Identifier id, String name, List<Item> items, EntityType<? extends GemEntity> gem) {
}
