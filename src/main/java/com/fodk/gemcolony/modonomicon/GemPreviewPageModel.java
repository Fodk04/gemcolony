package com.fodk.gemcolony.modonomicon;

import com.klikli_dev.modonomicon.api.datagen.book.BookTextHolderModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import net.minecraft.core.HolderLookup;
import com.klikli_dev.modonomicon.book.page.BookPage;
import net.minecraft.network.chat.Component;

public class GemPreviewPageModel extends BookPageModel<GemPreviewPageModel> {

    protected BookTextHolderModel entityName = new BookTextHolderModel("");
    protected BookTextHolderModel text = new BookTextHolderModel("");
    protected String entityId;
    protected float scale = 1.0f;
    protected float offset = 0f;
    protected boolean rotate = true;
    protected float defaultRotation = -45f;

    protected int variant = -1;

    protected GemPreviewPageModel() {
        super(GemPreviewPage.ID);
    }

    public static GemPreviewPageModel create() {
        return new GemPreviewPageModel();
    }

    @Override
    public BookPage toBookPage(HolderLookup.Provider provider) {
        return new GemPreviewPage(
                this.entityName.toBookTextHolder(),
                this.text.toBookTextHolder(),
                this.entityId,
                this.scale,
                this.offset,
                this.rotate,
                this.defaultRotation,
                this.variant,
                this.id,
                this.condition(provider)
        );
    }

    public GemPreviewPageModel withEntityName(String name) {
        this.entityName = new BookTextHolderModel(name);
        return this;
    }

    public GemPreviewPageModel withEntityName(Component name) {
        this.entityName = new BookTextHolderModel(name);
        return this;
    }

    public GemPreviewPageModel withEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public GemPreviewPageModel withText(String text) {
        this.text = new BookTextHolderModel(text);
        return this;
    }

    public GemPreviewPageModel withText(Component text) {
        this.text = new BookTextHolderModel(text);
        return this;
    }

    public GemPreviewPageModel withScale(float scale) {
        this.scale = scale;
        return this;
    }

    public GemPreviewPageModel withOffset(float offset) {
        this.offset = offset;
        return this;
    }

    public GemPreviewPageModel withRotate(boolean rotate) {
        this.rotate = rotate;
        return this;
    }

    public GemPreviewPageModel withDefaultRotation(float defaultRotation) {
        this.defaultRotation = defaultRotation;
        return this;
    }

    public GemPreviewPageModel withVariant(int variant) {
        this.variant = variant;
        return this;
    }
}
