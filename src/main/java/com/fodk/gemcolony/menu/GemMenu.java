package com.fodk.gemcolony.menu;

import com.fodk.gemcolony.networking.AppearanceChange;
import com.fodk.gemcolony.entity.client.screen.GemSlot;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.networking.packet.SetAppearancePacketC2S;
import com.fodk.gemcolony.networking.packet.SetNicknamePacketC2S;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class GemMenu extends AbstractContainerMenu {

    private final GemEntity gemEntity;
    private static final int SLOTS_PER_PAGE = 54;

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
            // Gem inventory to Player inventory
            if (!this.moveItemStackTo(
                    stack,
                    gemSlots,
                    this.slots.size(),
                    true
            )) {
                return ItemStack.EMPTY;
            }
        } else {
            // Player inventory to Gem inventory
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
        // Gem inventory
        for (int i = 0; i < gemEntity.getContainerSize(); i++) {
            int pageSlot = i % SLOTS_PER_PAGE;

            int row = pageSlot / 9;
            int col = pageSlot % 9;
            this.addSlot(new GemSlot(
                    gemEntity,
                    i,
                    8 + col * 18,
                    18 + row * 18
            ));
        }

        // Player inventory layouts for 1–6 gem rows
        for (int layoutRows = 1; layoutRows <= 6; layoutRows++) {

            int playerInventoryY = 18 + layoutRows * 18 + 12 + 2;
            int hotbarY = playerInventoryY + 54 + 4;

            // Player inventory
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 9; col++) {
                    GemSlot slot = new GemSlot(
                            playerInventory,
                            col + row * 9 + 9,
                            8 + col * 18,
                            playerInventoryY + row * 18
                    );

                    slot.setLayoutRows(layoutRows);
                    slot.setActive(false);

                    this.addSlot(slot);
                }
            }

            // Hotbar
            for (int col = 0; col < 9; col++) {
                GemSlot slot = new GemSlot(
                        playerInventory,
                        col,
                        8 + col * 18,
                        hotbarY
                );

                slot.setLayoutRows(layoutRows);
                slot.setActive(false);

                this.addSlot(slot);
            }
        }
    }

    public GemEntity getGemEntity() {
        return gemEntity;
    }

    public void sendNickname(String nickname) {
        ClientPacketDistributor.sendToServer(new SetNicknamePacketC2S(gemEntity.getId(), nickname));
    }

    public void changeOutfit(int direction) {
        int next = getNext(gemEntity.getOutfit(),direction, gemEntity.getMaxOutfits());

        sendAppearanceChange(AppearanceChange.OUTFIT, next);
    }

    public void changeInsignia(int direction) {
        int next = getNext(gemEntity.getInsignia(),direction, gemEntity.getMaxInsignias());

        sendAppearanceChange(AppearanceChange.INSIGNIA, next);
    }

    public void changeHairstyle(int direction) {
        int next = getNext(gemEntity.getHairstyle(),direction, gemEntity.getMaxHairstyles());

        sendAppearanceChange(AppearanceChange.HAIRSTYLE, next);
    }

    public void changeVisor(int direction) {
        int next = getNextVisor(gemEntity.getVisor(),direction, gemEntity.getMaxVisors());

        sendAppearanceChange(AppearanceChange.VISOR, next);
    }

    int getNext(int current, int direction, int max){
        int next = current + direction;

        if (next < 0) {
            next = max - 1;
        } else if (next >= max) {
            next = 0;
        }

        return next;
    }

    int getNextVisor(int current, int direction, int max){
        int next = current + direction;

        if (next < -1) {
            next = max - 1;
        } else if (next >= max) {
            next = -1;
        }

        return next;
    }

    private void sendAppearanceChange(AppearanceChange appearance, int value) {
        ClientPacketDistributor.sendToServer(new SetAppearancePacketC2S(gemEntity.getId(), appearance, value));
    }
}
