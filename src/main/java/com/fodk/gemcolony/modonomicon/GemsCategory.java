package com.fodk.gemcolony.modonomicon;


import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.item.ModItems;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class GemsCategory extends CategoryProvider {

    public GemsCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    public String categoryId() {
        return "gems";
    }

    @Override
    protected String categoryName() {
        return "Gems";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(ModItems.ROSE_QUARTZ_GEM);
    }

    @Override
    protected void generateEntries() {

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/starter_gems"), "book.gemcolony.starter_gems")
                        .withIcon(ModItems.BLUE_ESSENCE_BOTTLE)
                        .withLocation(0, 0)
                        .withPages(
                                BookTextPageModel.create()
                                        .withId("starter_gems_intro")
                                        .withTitle("book.gemcolony.starter_gems.title")
                                        .withText("book.gemcolony.starter_gems.text"),

                                BookSpotlightPageModel.create()
                                        .withId("pebble_gem")
                                        .withTitle("book.gemcolony.starter_gems.pebble.title")
                                        .withText("book.gemcolony.starter_gems.pebble.text")
                                        .withItem(Ingredient.of(ModItems.PEBBLE_GEM)),

                                BookSpotlightPageModel.create()
                                        .withId("mica_gem")
                                        .withTitle("book.gemcolony.starter_gems.mica.title")
                                        .withText("book.gemcolony.starter_gems.mica.text")
                                        .withItem(Ingredient.of(ModItems.MICA_GEM)),

                                BookSpotlightPageModel.create()
                                        .withId("shale_gem")
                                        .withTitle("book.gemcolony.starter_gems.shale.title")
                                        .withText("book.gemcolony.starter_gems.shale.text")
                                        .withItem(Ingredient.of(ModItems.SHALE_GEM)),

                                BookSpotlightPageModel.create()
                                        .withId("nacre_gem")
                                        .withTitle("book.gemcolony.starter_gems.nacre.title")
                                        .withText("book.gemcolony.starter_gems.nacre.text")
                                        .withItem(Ingredient.of(ModItems.NACRE_GEM))
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/pebble"), "entity.gemcolony.pebble")
                        .withIcon(ModItems.PEBBLE_GEM)
                        .withLocation(-3, -2)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/starter_gems")))
                        .withPages(
                                BookSpotlightPageModel.create()
                                        .withId("pebble_spotlight")
                                        .withText("book.gemcolony.pebble.text")
                                        .withItem(Ingredient.of(ModItems.PEBBLE_GEM)),

                                GemPreviewPageModel.create()
                                        .withId("pebble_entity")
                                        .withEntityId("gemcolony:pebble")
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/shale"), "entity.gemcolony.shale")
                        .withIcon(ModItems.SHALE_GEM)
                        .withLocation(-1, -2)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/starter_gems")))
                        .withPages(
                                BookSpotlightPageModel.create()
                                        .withId("shale_spotlight")
                                        .withText("book.gemcolony.shale.text")
                                        .withItem(Ingredient.of(ModItems.SHALE_GEM)),

                                GemPreviewPageModel.create()
                                        .withId("shale_entity")
                                        .withEntityId("gemcolony:shale")
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/mica"), "entity.gemcolony.mica")
                        .withIcon(ModItems.MICA_GEM)
                        .withLocation(1, -2)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/starter_gems")))
                        .withPages(
                                BookSpotlightPageModel.create()
                                        .withId("mica_spotlight")
                                        .withText("book.gemcolony.mica.text")
                                        .withItem(Ingredient.of(ModItems.MICA_GEM)),

                                GemPreviewPageModel.create()
                                        .withId("mica_entity")
                                        .withEntityId("gemcolony:mica")
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/injector"), "book.gemcolony.injector")
                        .withIcon(ModBlocks.TANK)
                        .withLocation(1, -4)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/mica")))
                        .withPages(
                                BookMultiblockPageModel.create()
                                        .withId("injector_multiblock")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "injector_multiblock"))
                                        .withMultiblockName("book.gemcolony.injector_multiblock.name")
                                        .withText("book.gemcolony.injector_multiblock.text")
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/nacre"), "entity.gemcolony.nacre")
                        .withIcon(ModItems.NACRE_GEM)
                        .withLocation(3, -2)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/starter_gems")))
                        .withPages(
                                BookSpotlightPageModel.create()
                                        .withId("nacre_spotlight")
                                        .withText("book.gemcolony.nacre.text")
                                        .withItem(Ingredient.of(ModItems.NACRE_GEM)),

                                GemPreviewPageModel.create()
                                        .withId("nacre_entity")
                                        .withEntityId("gemcolony:nacre")
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/shell"), "book.gemcolony.shell")
                        .withIcon(ModBlocks.SHELL)
                        .withLocation(3, -4)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/nacre")))
                        .withPages(
                                BookMultiblockPageModel.create()
                                        .withId("shell_multiblock")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "shell_multiblock"))
                                        .withMultiblockName("book.gemcolony.shell_multiblock.name")
                                        .withText("book.gemcolony.shell_multiblock.text")
                        )
        );

    }
}

