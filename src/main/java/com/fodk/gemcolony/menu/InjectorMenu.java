package com.fodk.gemcolony.menu;

import com.fodk.gemcolony.block.custom.InjectionOrientation;
import com.fodk.gemcolony.block.entity.screen.InjectorSlot;
import com.fodk.gemcolony.menu.ModMenus;
import com.fodk.gemcolony.block.entity.custom.InjectorBlockEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class InjectorMenu extends AbstractContainerMenu {

    private final InjectorBlockEntity injector;

    public InjectorMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf data) {
        super(ModMenus.INJECTOR_MENU.get(), containerId);

        int x = data.readInt();
        int y = data.readInt();
        int z = data.readInt();

        this.injector = (InjectorBlockEntity) playerInventory.player
                .level()
                .getBlockEntity(new net.minecraft.core.BlockPos(x, y, z));

        addSlots(playerInventory);
        addDataSlots();
    }

    public InjectorMenu(int containerId, Inventory playerInventory, InjectorBlockEntity injector) {
        super(ModMenus.INJECTOR_MENU.get(), containerId);

        this.injector = injector;

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

        int injectorSlots = injector.getContainerSize();

        if (index < injectorSlots) {
            // Injector inventory → Player inventory
            if (!this.moveItemStackTo(
                    stack,
                    injectorSlots,
                    this.slots.size(),
                    true
            )) {
                return ItemStack.EMPTY;
            }
        } else {
            // Player inventory → Injector inventory
            if (!this.moveItemStackTo(
                    stack,
                    0,
                    injectorSlots,
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
        return injector.stillValid(player);
    }

    private void addSlots(Inventory playerInventory) {

        // CHROMA
        this.addSlot(new InjectorSlot(
                injector,
                0,
                18,
                29,
                0
        ));

        // GEM SEED
        this.addSlot(new InjectorSlot(
                injector,
                1,
                18,
                53,
                1
        ));

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
                return injector.getInjectionOrientation().ordinal();
            }

            @Override
            public void set(int value) {
                InjectionOrientation[] orientations = InjectionOrientation.values();

                if (value >= 0 && value < orientations.length) {
                    // Client-side synchronization only.
                    injector.setInjectionOrientation(orientations[value]);
                }
            }
        });

        // BLUE
        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return injector.getBlueEssenceAmount();
            }

            @Override
            public void set(int value) {
                injector.getBlueTank().setAmount(value);
            }
        });

        // YELLOW
        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return injector.getYellowEssenceAmount();
            }

            @Override
            public void set(int value) {
                injector.getYellowTank().setAmount(value);
            }
        });

        // WHITE
        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return injector.getWhiteEssenceAmount();
            }

            @Override
            public void set(int value) {
                injector.getWhiteTank().setAmount(value);
            }
        });

        // PINK
        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return injector.getPinkEssenceAmount();
            }

            @Override
            public void set(int value) {
                injector.getPinkTank().setAmount(value);
            }
        });
    }

    public InjectorBlockEntity getInjector() {
        return injector;
    }

    public void cycleInjectionOrientation() {

    }
}