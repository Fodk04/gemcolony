package com.fodk.gemcolony.entity.client.screen.ability;

import com.fodk.gemcolony.entity.client.screen.GemScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class GenericAbilityUI implements GemAbilityUI {

    private GemScreen screen;

    @Override
    public void init(GemScreen screen, int textColor, int gemColor) {
        this.screen = screen;
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {

    }

    @Override
    public void clear() {
        screen = null;
    }
}
