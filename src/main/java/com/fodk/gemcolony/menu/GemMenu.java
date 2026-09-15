package com.fodk.gemcolony.menu;

import com.fodk.gemcolony.entity.custom.GemEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class GemMenu extends AbstractContainerMenu {

    private final GemEntity gemEntity;

    public GemMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf data) {
        super(ModMenus.GEM_MENU.get(), containerId);
        int entityId = data.readInt();
        this.gemEntity = (GemEntity) playerInventory.player.level().getEntity(entityId);
        addSlots(playerInventory);
    }

    public GemMenu(int containerId, Inventory playerInventory, GemEntity gemEntity) {
        super(ModMenus.GEM_MENU.get(), containerId);
        this.gemEntity = gemEntity;
        addSlots(playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();

        int gemSlots = gemEntity.getContainerSize();

        if (index < gemSlots) {
            // Gem inventory → Player inventory
            if (!this.moveItemStackTo(
                    stack,
                    gemSlots,
                    this.slots.size(),
                    true
            )) {
                return ItemStack.EMPTY;
            }
        } else {
            // Player inventory → Gem inventory
            if (!this.moveItemStackTo(
                    stack,
                    0,
                    gemSlots,
                    false
            )) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return original;
    }


    @Override
    public boolean stillValid(Player player) {
        return gemEntity.stillValid(player);
    }

    private void addSlots(Inventory playerInventory) {

        int gemRows = (gemEntity.getContainerSize() + 8) / 9;

        // Gem inventory
        for (int i = 0; i < gemEntity.getContainerSize(); i++) {
            int row = i / 9;
            int col = i % 9;
            this.addSlot(new Slot(
                    gemEntity,
                    i,
                    8 + col * 18,
                    18 + row * 18
            ));
        }

        // Player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(
                        playerInventory,
                        col + row * 9 + 9,
                        8 + col * 18,
                        18 + gemRows * 18 + 12 + row * 18 + 2
                ));
            }
        }

        // Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(
                    playerInventory,
                    col,
                    8 + col * 18,
                    18 + gemRows * 18 + 12 + 54 + 6
            ));
        }
    }

    public GemEntity getGemEntity() {
        return gemEntity;
    }
}
