package com.fodk.gemcolony.block.entity.screen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.entity.custom.InjectorBlockEntity;
import com.fodk.gemcolony.menu.InjectorMenu;
import com.fodk.gemcolony.networking.packet.CycleInjectorOrientationPacketC2S;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;


public class InjectorScreen extends AbstractContainerScreen<InjectorMenu> {

    private static final Identifier INJECTOR_BG = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/injector_inventory.png");
    private static final Identifier ESSENCE_STILL = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/block/essence_still.png");

    private Button orientationButton;

    private static final int ESSENCE_BARS_OFFSET_Y = -10;

    public InjectorScreen(InjectorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 166);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                INJECTOR_BG,
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

        InjectorBlockEntity injector = this.menu.getInjector();

        drawEssenceBar(
                graphics,
                injector.getBlueEssenceAmount(),
                injector.getBlueTank().getCapacity(),
                55,
                28 + ESSENCE_BARS_OFFSET_Y,
                55,
                7,
                0xFF0A3CFF
        );

        drawEssenceBar(
                graphics,
                injector.getYellowEssenceAmount(),
                injector.getYellowTank().getCapacity(),
                55,
                40 + ESSENCE_BARS_OFFSET_Y,
                55,
                7,
                0xFFDCFA00
        );

        drawEssenceBar(
                graphics,
                injector.getWhiteEssenceAmount(),
                injector.getWhiteTank().getCapacity(),
                55,
                52 + ESSENCE_BARS_OFFSET_Y,
                55,
                7,
                0xFFFFFFFF
        );

        drawEssenceBar(
                graphics,
                injector.getPinkEssenceAmount(),
                injector.getPinkTank().getCapacity(),
                55,
                64 + ESSENCE_BARS_OFFSET_Y,
                55,
                7,
                0xFFD214C8
        );

        drawMixedEssenceTank(
                graphics,
                injector.getTotalEssenceAmount(),
                injector.getTotalEssenceCapacity(),
                injector.getEssenceColor(),
                135,
                16,
                24,
                54
        );

        if (this.orientationButton != null) {
            this.orientationButton.setMessage(Component.literal(getOrientationText()));
        }
    }

    private void drawEssenceBar(GuiGraphicsExtractor graphics, int amount, int capacity, int x, int y, int width, int height, int color) {
        float fill = capacity <= 0 ? 0.0F : (float) amount / capacity;

        int filledWidth = Math.round(width * fill);

        // Bar background
        graphics.fill(
                this.leftPos + x,
                this.topPos + y,
                this.leftPos + x + width,
                this.topPos + y + height,
                0xFF303030
        );

        if (filledWidth <= 0) {
            return;
        }

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                ESSENCE_STILL,
                this.leftPos + x,
                this.topPos + y,
                0,
                0,
                filledWidth,
                height,
                16,
                16,
                0xFF000000 | color
        );
    }

    private void drawMixedEssenceTank(GuiGraphicsExtractor graphics, int amount, int capacity, int color, int x, int y, int width, int height) {
        float fill = capacity <= 0 ? 0.0F : (float) amount / capacity;

        int filledHeight = Math.round(height * fill);

        // Tank background
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
                0xFF000000 | color
        );
    }

    @Override
    protected void init() {
        super.init();

        this.orientationButton = this.addRenderableWidget(
                Button.builder(
                                Component.literal(getOrientationText()),
                                button -> cycleOrientation()
                        )
                        .bounds(
                                this.leftPos + 70,
                                this.topPos + 62,
                                24,
                                10
                        )
                        .build()
        );
    }

    private String getOrientationText() {
        return switch (this.menu.getInjector().getInjectionOrientation()) {
            case NORTH_SOUTH -> "N/S";
            case EAST_WEST -> "E/W";
        };
    }

    private void cycleOrientation() {
        ClientPacketDistributor.sendToServer(new CycleInjectorOrientationPacketC2S(this.menu.getInjector().getBlockPos()));
    }
}