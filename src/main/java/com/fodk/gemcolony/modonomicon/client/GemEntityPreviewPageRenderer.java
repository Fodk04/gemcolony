package com.fodk.gemcolony.modonomicon.client;

import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.modonomicon.GemPreviewPage;
import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.api.ModonomiconConstants;
import com.klikli_dev.modonomicon.client.ClientTicks;
import com.klikli_dev.modonomicon.client.gui.book.BookContentRenderer;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookPageRenderer;
import com.klikli_dev.modonomicon.client.render.page.PageWithTextRenderer;
import com.klikli_dev.modonomicon.book.page.BookEntityPage;
import com.klikli_dev.modonomicon.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.awt.*;

public class GemEntityPreviewPageRenderer extends BookPageRenderer<BookEntityPage> implements PageWithTextRenderer {

    private Entity entity;
    private boolean errored;
    private float renderScale;
    private float renderOffset;

    public GemEntityPreviewPageRenderer(GemPreviewPage page) {
        super(page);
    }

    private void renderEntity(GuiGraphicsExtractor guiGraphics, int left, int top, float rotation) {
        if (!(this.entity instanceof LivingEntity livingEntity))
            return;

        EntityRenderDispatcher erd = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> entityrenderer = erd.getRenderer(livingEntity);
        EntityRenderState entityrenderstate = entityrenderer.createRenderState(livingEntity, 10F);

        entityrenderstate.lightCoords = 15728880;
        entityrenderstate.shadowPieces.clear();
        entityrenderstate.outlineColor = 0;

        int frameX = BookEntryScreen.PAGE_WIDTH / 2 - 53;
        int frameY = 7;

        int boxX = this.parentScreen.getBookLeft() + left + frameX;
        int boxY = this.parentScreen.getBookTop() + top + frameY;
        int boxX2 = boxX + 106;
        int boxY2 = boxY + 106;

        float scale = this.renderScale;
        float yOffset = this.renderOffset;

        Vector3f translation = new Vector3f(0, yOffset, 0f);

        Quaternionf rotationQuat = new Quaternionf().rotateZ((float) Math.PI);
        rotationQuat.mul(new Quaternionf().rotateY(rotation * (float) Math.PI / 180.0F));
        rotationQuat.mul(new Quaternionf().rotateX((float) Math.toRadians(0.01)));
        guiGraphics.entity(entityrenderstate, scale, translation, rotationQuat, null, boxX, boxY, boxX2, boxY2);
    }

    private void loadEntity(Level world) {
        if (!this.errored && (this.entity == null || !this.entity.isAlive())) {
            try {
                String entityId = this.page.getEntityId();

                if (GemEntityPreviewRegistry.hasFactory(entityId)) {
                    this.entity = GemEntityPreviewRegistry.create(entityId, world);
                } else {
                    var entityLoader = EntityUtil.getEntityLoader(entityId);
                    this.entity = entityLoader.apply(world);
                }

                this.entity.setId(-1);
                int variant = ((GemPreviewPage)page).getVariant();
                if(variant >= 0){
                    ((GemEntity) entity).initializeGemPreview(variant);
                }else{
                    ((GemEntity) entity).initializeGemPreview();
                }

                if (this.entity instanceof LivingEntity living) {
                    living.setYRot(0);
                    living.setYBodyRot(0);
                    living.setYHeadRot(0);
                }

                float width = this.entity.getBbWidth();
                float height = this.entity.getBbHeight();

                float entitySize = Math.max(1F, Math.max(width, height));

                this.renderScale = 100F / entitySize * 0.5F * this.getPage().getScale();
                this.renderOffset = Math.max(height, entitySize) * 0.8f + this.getPage().getOffset();

            } catch (Exception e) {
                this.errored = true;
                Modonomicon.LOG.error("Failed to load entity {}", this.page.getEntityId(), e);
            }
        }
    }

    @Override
    public void onBeginDisplayPage(BookEntryScreen parentScreen, int left, int top) {
        super.onBeginDisplayPage(parentScreen, left, top);
        this.loadEntity(Minecraft.getInstance().level);
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float ticks) {
        if (!this.page.getEntityName().isEmpty()) {
            this.renderTitle(guiGraphics, this.page.getEntityName(), false, BookEntryScreen.PAGE_WIDTH / 2, 0);
        }

        int textY = this.getTextY();

        this.renderBookTextHolder(guiGraphics, this.getPage().getText(),
                0, textY, BookEntryScreen.PAGE_WIDTH,
                BookEntryScreen.PAGE_HEIGHT - textY);

        int x = BookEntryScreen.PAGE_WIDTH / 2 - 53;
        int y = 7;

        BookContentRenderer.drawSprite(guiGraphics, this.getPage().getBook().theme().content().mediaFrame(), x, y);

        if (this.errored) {
            guiGraphics.text(this.font, Component.translatable(ModonomiconConstants.I18n.Gui.PAGE_ENTITY_LOADING_ERROR), 58, 60, 0xFF0000, true);
        }

        if (this.entity != null) {
            float rotation = this.page.doesRotate() ? ClientTicks.total : this.page.getDefaultRotation();
            this.renderEntity(guiGraphics, this.left, this.top, rotation);
        }

        var style = this.getClickedComponentStyleAt(mouseX, mouseY);

        if (style != null) {
            this.parentScreen.renderComponentHoverEffect(guiGraphics, style, mouseX, mouseY);
        }
    }

    @Nullable
    @Override
    public Style getClickedComponentStyleAt(double mouseX, double mouseY) {
        if (mouseX > 0 && mouseY > 0) {
            if (!this.page.getEntityName().isEmpty()) {
                var titleStyle = this.getClickedComponentStyleAtForTitle(this.page.getEntityName(), BookEntryScreen.PAGE_WIDTH / 2, 0, mouseX, mouseY);

                if (titleStyle != null)
                    return titleStyle;
            }

            var bounds = this.getBookTextHolderBounds(
                    0, this.getTextY(),
                    BookEntryScreen.PAGE_WIDTH,
                    BookEntryScreen.PAGE_HEIGHT - this.getTextY()
            );
            var textStyle = this.getClickedComponentStyleAtForTextHolder(
                    this.page.getText(),
                    bounds.x, bounds.y, bounds.width, bounds.height,
                    mouseX, mouseY
            );

            if (textStyle != null)
                return textStyle;
        }

        return super.getClickedComponentStyleAt(mouseX, mouseY);
    }

    @Override
    public int getTextY() {
        return 115;
    }
}

