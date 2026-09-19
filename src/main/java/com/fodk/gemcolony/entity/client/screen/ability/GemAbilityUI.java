package com.fodk.gemcolony.entity.client.screen.ability;

import com.fodk.gemcolony.entity.client.screen.GemScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public interface GemAbilityUI {

    void init(GemScreen screen, int textColor, int gemColor);

    void render(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float partialTick
    );

    void clear();
}
