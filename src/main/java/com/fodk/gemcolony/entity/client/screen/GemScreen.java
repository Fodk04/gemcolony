package com.fodk.gemcolony.entity.client.screen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.menu.GemMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class GemScreen extends AbstractContainerScreen<GemMenu> {

    private static final Identifier[] BACKGROUNDS = {
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_inventory_1.png"),
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_inventory_2.png"),
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_inventory_3.png"),
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_inventory_4.png"),
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_inventory_5.png"),
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_inventory_6.png")
    };

    public GemScreen(GemMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, getGuiHeight(menu));
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);

        int rows = (menu.getGemEntity().getContainerSize() + 8) / 9;

        Identifier background = BACKGROUNDS[rows - 1];

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                background,
                this.leftPos,
                this.topPos,
                0,
                0,
                this.imageWidth,
                this.imageHeight,
                256,
                256
        );
    }

    private int getGemRows() {
        return (menu.getGemEntity().getContainerSize() + 8) / 9;
    }

    private static int getGuiHeight(GemMenu menu) {
        int rows = (menu.getGemEntity().getContainerSize() + 8) / 9;

        return 166 + (rows - 1) * 18;
    }
}
