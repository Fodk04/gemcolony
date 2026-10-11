package com.fodk.gemcolony.entity.client.screen;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class GemSlot extends Slot {

    private boolean active = true;
    private int layoutRows = 0;

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

    public int getLayoutRows() {
        return layoutRows;
    }

    public void setLayoutRows(int layoutRows) {
        this.layoutRows = layoutRows;
    }
}
