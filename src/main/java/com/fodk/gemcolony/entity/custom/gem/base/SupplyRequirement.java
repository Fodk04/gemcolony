package com.fodk.gemcolony.entity.custom.gem.base;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import java.util.Objects;


public record SupplyRequirement(Item item, TagKey<Item> tag, Item displayItem, int count, boolean ifPossible) {
    public SupplyRequirement {
        if ((item == null) == (tag == null)) {
            throw new IllegalArgumentException("A supply requirement must specify either an item or a tag.");
        }

        Objects.requireNonNull(displayItem, "displayItem");

        if (count <= 0) {
            throw new IllegalArgumentException("Supply requirement count must be positive.");
        }
    }

    public static SupplyRequirement exact(Item item, int count) {
        return new SupplyRequirement(item, null, item, count, false);
    }

    public static SupplyRequirement tag(
            TagKey<Item> tag, Item displayItem, int count) {
        return new SupplyRequirement(null, tag, displayItem, count, false);
    }

    public static SupplyRequirement upTo(Item item, int count) {
        return new SupplyRequirement(item, null, item, count, true);
    }

    public static SupplyRequirement upTo(
            TagKey<Item> tag, Item displayItem, int count) {
        return new SupplyRequirement(null, tag, displayItem, count, true);
    }

    public boolean matches(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        return item != null ? stack.is(item) : stack.is(tag);
    }
}

