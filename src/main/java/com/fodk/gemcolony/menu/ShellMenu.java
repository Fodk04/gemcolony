package com.fodk.gemcolony.menu;

import com.fodk.gemcolony.block.entity.custom.ShellBlockEntity;
import com.fodk.gemcolony.block.entity.screen.MachineSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ShellMenu extends AbstractContainerMenu {

    private final ShellBlockEntity shell;

    public ShellMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf data) {
        super(ModMenus.SHELL_MENU.get(), containerId);

        int x = data.readInt();
        int y = data.readInt();
        int z = data.readInt();

        this.shell = (ShellBlockEntity) playerInventory.player.level().getBlockEntity(new BlockPos(x, y, z));

        addSlots(playerInventory);
        addDataSlots();
    }

    public ShellMenu(int containerId, Inventory playerInventory, ShellBlockEntity shell) {
        super(ModMenus.SHELL_MENU.get(), containerId);

        this.shell = shell;

        addSlots(playerInventory);
        addDataSlots();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();

        int shellSlots = shell.getContainerSize();

        if (index < shellSlots) {
            if (!this.moveItemStackTo(stack, shellSlots, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!this.moveItemStackTo(stack, 0, shellSlots, false)) {
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
        return shell.stillValid(player);
    }

    private void addSlots(Inventory playerInventory) {

        // Shell slots
        this.addSlot(new MachineSlot(shell, 0, 18, 29, 0));
        this.addSlot(new MachineSlot(shell, 1, 18, 53, 1));

        // Player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(
                        playerInventory,
                        col + row * 9 + 9,
                        8 + col * 18,
                        84 + row * 18 + 2
                ));
            }
        }

        // Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(
                    playerInventory,
                    col,
                    8 + col * 18,
                    142 + 2
            ));
        }
    }

    private void addDataSlots() {
        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return shell.getWhiteEssenceAmount();
            }

            @Override
            public void set(int value) {
                shell.getWhiteTank().setAmount(value);
            }
        });
    }

    public ShellBlockEntity getShell() {
        return shell;
    }
}