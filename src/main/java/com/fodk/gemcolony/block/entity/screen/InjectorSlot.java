package com.fodk.gemcolony.block.entity.screen;

import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.tags.ModTags;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class InjectorSlot extends Slot {

    private final int slotType;

    public InjectorSlot(Container container, int slot, int x, int y, int slotType) {
        super(container, slot, x, y);
        this.slotType = slotType;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return switch (slotType) {
            case 0 -> isChroma(stack);
            case 1 -> isGemSeed(stack);
            default -> false;
        };
    }

    private boolean isChroma(ItemStack stack) {
        return stack.is(ModTags.Items.CHROMAS);
    }

    private boolean isGemSeed(ItemStack stack) {
        return stack.is(ModItems.GEM_SEED.get());
    }
}