package com.fodk.gemcolony.modonomicon;


import com.klikli_dev.modonomicon.api.datagen.book.BookTextHolderModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookConditionModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.book.ImageDisplayMode;
import com.klikli_dev.modonomicon.book.ImageScaleMode;
import com.klikli_dev.modonomicon.book.page.BookPage;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class ClipPageModel extends BookImagePageModel {

    protected Identifier texture;
    protected float duration = 1.0f;

    protected ClipPageModel() {
        super();
    }

    public static ClipPageModel create() {
        return new ClipPageModel();
    }

    @Override
    public BookPage toBookPage(HolderLookup.Provider provider) {
        return new ClipPage(title.toBookTextHolder(), text.toBookTextHolder(), texture, duration, border, id, condition(provider));
    }

    public ClipPageModel withTexture(Identifier texture) {
        this.texture = texture;
        return this;
    }

    public ClipPageModel withDuration(float duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public ClipPageModel withTitle(String title) {
        super.withTitle(title);
        return this;
    }

    @Override
    public ClipPageModel withTitle(Component title) {
        super.withTitle(title);
        return this;
    }

    @Override
    public ClipPageModel withText(String text) {
        super.withText(text);
        return this;
    }

    @Override
    public ClipPageModel withText(Component text) {
        super.withText(text);
        return this;
    }

    @Override
    public ClipPageModel withBorder(boolean border) {
        super.withBorder(border);
        return this;
    }

    @Override
    public ClipPageModel withLegacyRendering(boolean useLegacyRendering) {
        super.withLegacyRendering(useLegacyRendering);
        return this;
    }

    @Override
    public ClipPageModel withDisplayMode(ImageDisplayMode displayMode) {
        super.withDisplayMode(displayMode);
        return this;
    }

    @Override
    public ClipPageModel withImageScaleMode(ImageScaleMode imageScaleMode) {
        super.withImageScaleMode(imageScaleMode);
        return this;
    }

    @Override
    public ClipPageModel withImages(Identifier... images) {
        super.withImages(images);
        return this;
    }

    @Override
    public ClipPageModel withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public ClipPageModel withCondition(BookConditionModel<?> condition) {
        super.withCondition(condition);
        return this;
    }

    @Override
    public ClipPageModel withSortNumber(int sortNumber) {
        super.withSortNumber(sortNumber);
        return this;
    }
}
