package com.fodk.gemcolony.modonomicon.client;

import com.fodk.gemcolony.modonomicon.FluidMultiblockPage;
import com.klikli_dev.modonomicon.client.ClientTicks;
import com.klikli_dev.modonomicon.client.gui.book.BookContentRenderer;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookMultiblockPageRenderer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class FluidMultiblockPageRenderer extends BookMultiblockPageRenderer {

    private static final RandomSource randomSource = RandomSource.createThreadLocalInstance();

    private final Map<BlockPos, BlockEntity> blockEntityCache = new Object2ObjectOpenHashMap<>();
    private final Set<BlockEntity> erroredBlockEntities =
            Collections.newSetFromMap(new WeakHashMap<>());

    public FluidMultiblockPageRenderer(FluidMultiblockPage page) {
        super(page);
    }

    private void renderFluidMultiblock(GuiGraphicsExtractor guiGraphics) {
        var multiblock = this.page.getMultiblock();
        var facingRotation = Rotation.NONE;

        var size = multiblock.getSize();
        int sizeX = size.getX();
        int sizeY = size.getY();
        int sizeZ = size.getZ();

        float maxX = 90;
        float maxY = 90;
        float diag = (float) Math.sqrt(sizeX * sizeX + sizeZ * sizeZ);
        float scaleX = maxX / Math.max(1.0F, diag);
        float scaleY = maxY / Math.max(1.0F, sizeY);
        float scale = Math.min(scaleX, scaleY);

        float time = this.parentScreen.getTicksInBook() * 0.5F;
        if (!Minecraft.getInstance().hasShiftDown()) {
            time += ClientTicks.partialTicks;
        }

        int frameX = BookEntryScreen.PAGE_WIDTH / 2 - 53;
        int frameY = 7;

        int x0 = this.parentScreen.getBookLeft() + this.left + frameX;
        int y0 = this.parentScreen.getBookTop() + this.top + frameY;
        int x1 = x0 + 106;
        int y1 = y0 + 106;

        var state = new FluidMultiblockRenderState(
                multiblock,
                this.multiblockSimulation.getSecond(),
                size,
                time,
                facingRotation,
                null,
                this.blockEntityCache,
                this.erroredBlockEntities,
                randomSource,
                x0, y0, x1, y1,
                scale,
                guiGraphics.peekScissorStack()
        );

        guiGraphics.guiRenderState.addPicturesInPictureState(state);
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float ticks) {
        int x = BookEntryScreen.PAGE_WIDTH / 2 - 53;
        int y = 7;

        BookContentRenderer.drawSprite(
                guiGraphics,
                this.page.getBook().theme().content().mediaFrame(),
                x,
                y
        );

        if (!this.page.getMultiblockName().isEmpty()) {
            this.renderTitle(
                    guiGraphics,
                    this.page.getMultiblockName(),
                    false,
                    BookEntryScreen.PAGE_WIDTH / 2,
                    0
            );
        }

        this.renderFluidMultiblock(guiGraphics);

        var textY = this.getTextY();
        this.renderBookTextHolder(
                guiGraphics,
                this.page.getText(),
                0,
                textY,
                BookEntryScreen.PAGE_WIDTH,
                BookEntryScreen.PAGE_HEIGHT - textY
        );

        var style = this.getClickedComponentStyleAt(mouseX, mouseY);
        if (style != null) {
            this.parentScreen.renderComponentHoverEffect(
                    guiGraphics,
                    style,
                    mouseX,
                    mouseY
            );
        }
    }
}