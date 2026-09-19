package com.fodk.gemcolony.entity.client.screen;

import com.fodk.gemcolony.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class GemButton extends AbstractWidget {

    private final Runnable onPress;
    private final int textColor;
    private final int gemColor;

    public GemButton(int x, int y, int width, int height, Component message, int textColor, int gemColor, Runnable onPress) {
        super(x, y, width, height, message);
        this.textColor = textColor;
        this.onPress = onPress;
        this.gemColor = gemColor;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        boolean hovered = isMouseOver(mouseX, mouseY);

        int backgroundColor = ColorUtil.multiplyColors(
                hovered ? 0xCCBBBBBB : 0xAA858585,
                gemColor
        );

        int borderColor = ColorUtil.multiplyColors(
                hovered ? 0xCC535353 : 0xAA383838,
                gemColor
        );

        // Background
        graphics.fill(
                getX(),
                getY(),
                getX() + width,
                getY() + height,
                backgroundColor
        );

        // Border
        graphics.fill(
                getX(),
                getY(),
                getX() + width,
                getY() + 1,
                borderColor
        );

        graphics.fill(
                getX(),
                getY() + height - 1,
                getX() + width,
                getY() + height,
                borderColor
        );

        graphics.fill(
                getX(),
                getY(),
                getX() + 1,
                getY() + height,
                borderColor
        );

        graphics.fill(
                getX() + width - 1,
                getY(),
                getX() + width,
                getY() + height,
                borderColor
        );

        // Text
        graphics.pose().pushMatrix();

        float scale = 0.75f;

        graphics.pose().scale(scale, scale);

        int textWidth = Minecraft.getInstance().font.width(getMessage());
        int textHeight = Minecraft.getInstance().font.lineHeight;

        int scaledButtonWidth = (int) (width / scale);
        int scaledButtonHeight = (int) (height / scale);

        int textX =
                (int) (getX() / scale)
                        + (scaledButtonWidth - textWidth) / 2;

        int textY =
                (int) (getY() / scale)
                        + (scaledButtonHeight - textHeight) / 2;

        graphics.text(
                Minecraft.getInstance().font,
                getMessage(),
                textX,
                textY,
                textColor,
                false
        );

        graphics.pose().popMatrix();
    }


    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == 0) {
            onPress.run();
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
