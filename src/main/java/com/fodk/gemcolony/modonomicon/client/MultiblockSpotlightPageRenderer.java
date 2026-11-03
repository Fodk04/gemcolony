package com.fodk.gemcolony.modonomicon.client;

import com.fodk.gemcolony.modonomicon.MultiblockSpotlightPage;
import com.klikli_dev.modonomicon.client.render.page.BookSpotlightPageRenderer;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;

public class MultiblockSpotlightPageRenderer extends BookSpotlightPageRenderer {

    private final MultiblockSpotlightPage page;

    public MultiblockSpotlightPageRenderer(MultiblockSpotlightPage page) {
        super(page);
        this.page = page;
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float ticks) {
        super.render(guiGraphics, mouseX, mouseY, ticks);

        var requirements = this.page.getRequirements();

        int columns = 5;
        int spacing = 20;
        int rows = (requirements.size() + columns - 1) / columns;

        int startY = BookEntryScreen.PAGE_HEIGHT - rows * spacing - 4;

        for (int i = 0; i < requirements.size(); i++) {
            var requirement = requirements.get(i);

            int row = i / columns;
            int column = i % columns;

            int itemsInRow = Math.min(columns, requirements.size() - row * columns);
            int rowWidth = itemsInRow * spacing;

            int startX = (BookEntryScreen.PAGE_WIDTH - rowWidth) / 2;

            int x = startX + column * spacing;
            int y = startY + row * spacing;

            ItemStack stack = new ItemStack(
                    requirement.item(),
                    requirement.count()
            );

            this.parentScreen.renderItemStack(
                    guiGraphics,
                    x,
                    y,
                    mouseX,
                    mouseY,
                    stack
            );
        }
    }
}