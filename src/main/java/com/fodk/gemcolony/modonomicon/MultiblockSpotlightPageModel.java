package com.fodk.gemcolony.modonomicon;

import com.fodk.gemcolony.construction.ConstructionRequirement;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.book.page.BookPage;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookConditionModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import java.util.List;

public class MultiblockSpotlightPageModel extends BookSpotlightPageModel {

    protected List<ConstructionRequirement> requirements = List.of();

    protected MultiblockSpotlightPageModel() {
        super();
    }

    public static MultiblockSpotlightPageModel create() {
        return new MultiblockSpotlightPageModel();
    }

    public MultiblockSpotlightPageModel withRequirements(List<ConstructionRequirement> requirements) {
        this.requirements = List.copyOf(requirements);
        return this;
    }

    @Override
    public MultiblockSpotlightPageModel withId(String id) {
        super.withId(id);
        return this;
    }

    @Override
    public MultiblockSpotlightPageModel withCondition(BookConditionModel<?> condition) {
        super.withCondition(condition);
        return this;
    }

    @Override
    public MultiblockSpotlightPageModel withTitle(String title) {
        super.withTitle(title);
        return this;
    }

    @Override
    public MultiblockSpotlightPageModel withTitle(Component title) {
        super.withTitle(title);
        return this;
    }

    @Override
    public MultiblockSpotlightPageModel withItem(Ingredient item) {
        super.withItem(item);
        return this;
    }

    @Override
    public MultiblockSpotlightPageModel withItem(ItemStackTemplate item) {
        super.withItem(item);
        return this;
    }

    @Override
    public MultiblockSpotlightPageModel withItem(ItemLike item) {
        super.withItem(item);
        return this;
    }

    @Override
    public MultiblockSpotlightPageModel withItem(Item item) {
        super.withItem(item);
        return this;
    }

    @Override
    public MultiblockSpotlightPageModel withText(String text) {
        super.withText(text);
        return this;
    }

    @Override
    public MultiblockSpotlightPageModel withText(Component text) {
        super.withText(text);
        return this;
    }

    @Override
    public MultiblockSpotlightPageModel withSortNumber(int sortNumber) {
        super.withSortNumber(sortNumber);
        return this;
    }

    @Override
    public BookPage toBookPage(HolderLookup.Provider provider) {
        return new MultiblockSpotlightPage(
                this.title.toBookTextHolder(),
                this.text.toBookTextHolder(),
                this.item,
                this.requirements,
                this.id,
                this.condition(provider)
        );
    }
}
