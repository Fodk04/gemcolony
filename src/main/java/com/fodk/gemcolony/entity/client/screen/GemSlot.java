package com.fodk.gemcolony.entity.client.screen;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class GemSlot extends Slot {

    private boolean active = true;

    public GemSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
