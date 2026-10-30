package com.fodk.gemcolony.modonomicon;

import com.fodk.gemcolony.GemColony;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;

public class GemColonyBook extends SingleBookSubProvider {

    public GemColonyBook() {
        super("gem_colony", GemColony.MOD_ID);
    }

    @Override
    protected void registerDefaultMacros() {
    }

    @Override
    protected void generateCategories() {
        this.add(new GettingStartedCategory(this).generate());
        this.add(new GemsCategory(this).generate());
    }

    @Override
    protected String bookName() {
        return "Gem Colony Guide";
    }

    @Override
    protected String bookTooltip() {
        return "A guide to starting your own gem colony !";
    }
}
