package com.fodk.gemcolony.modonomicon.client;

import com.fodk.gemcolony.modonomicon.ClipPage;
import com.klikli_dev.modonomicon.client.gui.book.BookContentRenderer;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookImagePageRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.Nullable;

public class ClipPageRenderer extends BookImagePageRenderer {

    private static final int IMAGE_X = 12;
    private static final int IMAGE_Y = 12;
    private static final int IMAGE_SIZE = 100;
    private static final int FRAME_PADDING = 3;

    private final ClipPage clipPage;
    private long startTimeNanos;

    public ClipPageRenderer(ClipPage page) {
        super(page);
        this.clipPage = page;
    }

    @Override
    public void onBeginDisplayPage(BookEntryScreen parentScreen, int left, int top) {
        super.onBeginDisplayPage(parentScreen, left, top);
        this.startTimeNanos = System.nanoTime();
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float ticks) {
        if (this.page.hasTitle()) {
            this.renderTitle(guiGraphics, this.page.getTitle(), false, BookEntryScreen.PAGE_WIDTH / 2, 0);
        }

        int textY = this.getTextY();

        this.renderBookTextHolder(
                guiGraphics,
                this.page.getText(),
                0,
                textY,
                BookEntryScreen.PAGE_WIDTH,
                BookEntryScreen.PAGE_HEIGHT - textY
        );

        renderCurrentFrame(guiGraphics);

        if (this.page.hasBorder()) {
            int frameX = IMAGE_X - FRAME_PADDING;
            int frameY = IMAGE_Y - FRAME_PADDING;
            int frameSize = IMAGE_SIZE + FRAME_PADDING * 2;

            BookContentRenderer.drawSpriteRegion(
                    guiGraphics,
                    this.getPage().getBook().theme().content().mediaFrame(),
                    frameX,
                    frameY,
                    frameSize,
                    frameSize
            );
        }

        Style style = this.getClickedComponentStyleAt(mouseX, mouseY);

        if (style != null) {
            this.parentScreen.renderComponentHoverEffect(guiGraphics, style, mouseX, mouseY);
        }
    }

    private void renderCurrentFrame(GuiGraphicsExtractor guiGraphics) {
        int frameCount = Math.max(1, Math.min(Math.round(this.clipPage.getDuration() * ClipPage.FPS), ClipPage.COLUMNS * ClipPage.ROWS));

        long elapsedNanos = System.nanoTime() - this.startTimeNanos;
        long elapsedFrames = elapsedNanos * ClipPage.FPS / 1_000_000_000L;
        int frameIndex = (int) (elapsedFrames % frameCount);

        int column = frameIndex % ClipPage.COLUMNS;
        int row = frameIndex / ClipPage.COLUMNS;

        int u = column * ClipPage.FRAME_WIDTH;
        int v = row * ClipPage.FRAME_HEIGHT;

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().scale(0.5F, 0.5F);

        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED,
                this.clipPage.getTexture(),
                IMAGE_X * 2,
                IMAGE_Y * 2,
                u,
                v,
                IMAGE_SIZE * 2,
                IMAGE_SIZE * 2,
                ClipPage.FRAME_WIDTH,
                ClipPage.FRAME_HEIGHT,
                ClipPage.FRAME_WIDTH * ClipPage.COLUMNS,
                ClipPage.FRAME_HEIGHT * ClipPage.ROWS
        );

        guiGraphics.pose().popMatrix();
    }

    @Override
    public int getTextY() {
        return IMAGE_Y + IMAGE_SIZE + FRAME_PADDING + 2;
    }

    @Nullable
    @Override
    public Style getClickedComponentStyleAt(double mouseX, double mouseY) {
        return super.getClickedComponentStyleAt(mouseX, mouseY);
    }
}

