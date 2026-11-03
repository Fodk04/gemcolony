package com.fodk.gemcolony.modonomicon;

import com.klikli_dev.modonomicon.api.datagen.book.BookTextHolderModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.book.page.BookPage;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class FluidMultiblockPageModel extends BookPageModel<FluidMultiblockPageModel> {

    protected BookTextHolderModel multiblockName = new BookTextHolderModel("");
    protected BookTextHolderModel text = new BookTextHolderModel("");
    protected Identifier multiblockId = null;
    protected boolean showVisualizeButton = true;

    protected FluidMultiblockPageModel() {
        super(FluidMultiblockPage.ID);
    }

    public static FluidMultiblockPageModel create() {
        return new FluidMultiblockPageModel();
    }

    public BookTextHolderModel getMultiblockName() {
        return this.multiblockName;
    }

    public Identifier getMultiblockId() {
        return this.multiblockId;
    }

    public BookTextHolderModel getText() {
        return this.text;
    }

    public boolean showVisualizeButton() {
        return this.showVisualizeButton;
    }

    @Override
    public BookPage toBookPage(HolderLookup.Provider provider) {
        return new FluidMultiblockPage(
                this.multiblockName.toBookTextHolder(),
                this.text.toBookTextHolder(),
                this.multiblockId,
                this.showVisualizeButton,
                this.id,
                this.condition(provider)
        );
    }

    public FluidMultiblockPageModel withMultiblockName(String title) {
        this.multiblockName = new BookTextHolderModel(title);
        return this;
    }

    public FluidMultiblockPageModel withMultiblockName(Component title) {
        this.multiblockName = new BookTextHolderModel(title);
        return this;
    }

    public FluidMultiblockPageModel withMultiblockId(Identifier multiblockId) {
        this.multiblockId = multiblockId;
        return this;
    }

    public FluidMultiblockPageModel withText(String text) {
        this.text = new BookTextHolderModel(text);
        return this;
    }

    public FluidMultiblockPageModel withText(Component text) {
        this.text = new BookTextHolderModel(text);
        return this;
    }

    public FluidMultiblockPageModel withVisualizeButton(boolean showVisualizeButton) {
        this.showVisualizeButton = showVisualizeButton;
        return this;
    }
}
