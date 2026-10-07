package com.fodk.gemcolony.block.entity.screen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.entity.custom.ShellBlockEntity;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.menu.ShellMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ShellScreen extends AbstractContainerScreen<ShellMenu> {

    private static final Identifier SHELL_BG = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/shell_inventory.png");

    private static final Identifier ESSENCE_STILL = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/block/essence_still.png");

    private static final Identifier PEARL_PROGRESS = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/shell_progress_pearl.png");
    private static final int PEARL_X = 60;
    private static final int PEARL_Y = 24;
    private static final int PEARL_SIZE = 48;

    public ShellScreen(ShellMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 166);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                SHELL_BG,
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

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractContents(graphics, mouseX, mouseY, partialTick);

        ShellBlockEntity shell = this.menu.getShell();

        drawWhiteEssenceTank(
                graphics,
                shell.getWhiteEssenceAmount(),
                shell.getWhiteEssenceCapacity(),
                135,
                16,
                24,
                54
        );

        drawPearlPreview(graphics, shell);
    }

    private void drawWhiteEssenceTank(GuiGraphicsExtractor graphics, int amount, int capacity, int x, int y, int width, int height) {
        float fill = capacity <= 0 ? 0.0F : (float) amount / capacity;
        int filledHeight = Math.round(height * fill);

        graphics.fill(
                this.leftPos + x,
                this.topPos + y,
                this.leftPos + x + width,
                this.topPos + y + height,
                0xFF303030
        );

        if (filledHeight <= 0) {
            return;
        }

        int liquidY = this.topPos + y + height - filledHeight;

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                ESSENCE_STILL,
                this.leftPos + x,
                liquidY,
                0,
                0,
                width,
                filledHeight,
                16,
                16,
                0xFFFFFFFF
        );
    }

    private void drawPearlPreview(GuiGraphicsExtractor graphics, ShellBlockEntity shell) {
        int x = this.leftPos + PEARL_X;
        int y = this.topPos + PEARL_Y;

        //gray base Pearl
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                PEARL_PROGRESS,
                x,
                y,
                0,
                0,
                PEARL_SIZE,
                PEARL_SIZE,
                16,
                16,
                16,
                16,
                0xFF555555
        );

        float progress = shell.getPearlProgress();

        int filledHeight = Math.round(PEARL_SIZE * progress);

        if (filledHeight <= 0) {
            return;
        }

        int filledY = y + PEARL_SIZE - filledHeight;

        graphics.enableScissor(
                x,
                filledY,
                x + PEARL_SIZE,
                y + PEARL_SIZE
        );

        //white pearl
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                PEARL_PROGRESS,
                x,
                y,
                0,
                0,
                PEARL_SIZE,
                PEARL_SIZE,
                16,
                16,
                16,
                16,
                0xFFFFFFFF
        );

        graphics.disableScissor();
    }
}
