package com.fodk.gemcolony.entity.client.screen.ability;

import com.fodk.gemcolony.construction.Assembly;
import com.fodk.gemcolony.construction.ConstructorManager;
import com.fodk.gemcolony.entity.client.screen.GemButton;
import com.fodk.gemcolony.entity.client.screen.GemScreen;
import com.fodk.gemcolony.entity.custom.gem.starter.StarterGemEntity;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class ConstructorUI implements GemAbilityUI {

    private GemScreen screen;
    private StarterGemEntity gem;
    private int textColor;
    private int gemColor;

    @Override
    public void init(GemScreen screen, int textColor, int gemColor) {
        this.screen = screen;
        this.gem = (StarterGemEntity) screen.getMenu().getGemEntity();
        this.textColor = textColor;
        this.gemColor = gemColor;

        int x = screen.getLeftPos() + 105;
        int y = screen.getTopPos() + 80;

        for (Assembly assembly : gem.getConstructableAssemblies()) {

            GemButton button = new GemButton(
                    x,
                    y,
                    60,
                    16,
                    Component.literal(assembly.name()),
                    textColor,
                    gemColor,
                    () -> selectAssembly(assembly)
            );

            screen.addAbilityWidget(button);

            y += 22;
        }
    }

    private void selectAssembly(Assembly assembly) {
        ConstructorManager.beginPlacement(screen.getMinecraft().player, gem, assembly);

        screen.onClose();
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
    }

    @Override
    public void clear() {
        screen = null;
        gem = null;
    }
}
