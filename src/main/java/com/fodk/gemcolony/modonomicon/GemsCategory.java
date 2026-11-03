package com.fodk.gemcolony.modonomicon;


import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.block.custom.ConstructedMultiblock;
import com.fodk.gemcolony.block.custom.DrillBlock;
import com.fodk.gemcolony.entity.custom.gem.base.GemDefinitions;
import com.fodk.gemcolony.entity.custom.gem.pearl.PearlEntity;
import com.fodk.gemcolony.entity.custom.gem.variant.JasperVariants;
import com.fodk.gemcolony.entity.custom.gem.variant.PearlVariants;
import com.fodk.gemcolony.entity.custom.gem.variant.QuartzVariants;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.tags.ModTags;
import com.fodk.gemcolony.util.GemVariantUtil;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.*;
import com.klikli_dev.modonomicon.book.ImageDisplayMode;
import com.klikli_dev.modonomicon.book.ImageScaleMode;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.stream.IntStream;
import java.util.stream.Stream;

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
                        .withIcon(ModItems.WHITE_ESSENCE_BOTTLE)
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
                                        .withItem(Ingredient.of(ModItems.NACRE_GEM)),

                                BookImagePageModel.create()
                                        .withId("constructor")
                                        .withTitle("book.gemcolony.starter_gems.constructor.title")
                                        .withText("book.gemcolony.starter_gems.constructor.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "modonomicon/image/constructor.png"))
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
                                        .withMultiblockName("book.gemcolony.injector")
                                        .withText("book.gemcolony.injector_multiblock.text"),

                                MultiblockSpotlightPageModel.create()
                                        .withId("injector_drill")
                                        .withTitle("book.gemcolony.injector_drill.title")
                                        .withText("book.gemcolony.injector_drill.text")
                                        .withItem(Ingredient.of(ModBlocks.DRILL))
                                        .withRequirements(((ConstructedMultiblock) ModBlocks.DRILL.get()).getConstructionRequirements()),

                                MultiblockSpotlightPageModel.create()
                                        .withId("injector_tank")
                                        .withTitle("book.gemcolony.injector_tank.title")
                                        .withText("book.gemcolony.injector_tank.text")
                                        .withItem(Ingredient.of(ModBlocks.TANK))
                                        .withRequirements(((ConstructedMultiblock) ModBlocks.TANK.get()).getConstructionRequirements()),

                                MultiblockSpotlightPageModel.create()
                                        .withId("injector_crystal")
                                        .withTitle("book.gemcolony.injector_crystal.title")
                                        .withText("book.gemcolony.injector_crystal.text")
                                        .withItem(Ingredient.of(ModBlocks.CRYSTAL))
                                        .withRequirements(((ConstructedMultiblock) ModBlocks.CRYSTAL.get()).getConstructionRequirements()),

                                MultiblockSpotlightPageModel.create()
                                        .withId("injector_leg")
                                        .withTitle("book.gemcolony.injector_leg.title")
                                        .withText("book.gemcolony.injector_leg.text")
                                        .withItem(Ingredient.of(ModBlocks.LEG))
                                        .withRequirements(((ConstructedMultiblock) ModBlocks.LEG.get()).getConstructionRequirements()),

                                BookImagePageModel.create()
                                        .withId("injector_gui")
                                        .withTitle("book.gemcolony.injector_gui.title")
                                        .withText("book.gemcolony.injector_gui.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "modonomicon/image/injector_gui.png")),

                                BookImagePageModel.create()
                                        .withId("injector_orientation")
                                        .withTitle("book.gemcolony.injector_orientation.title")
                                        .withText("book.gemcolony.injector_orientation.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "modonomicon/image/injector_orientation_ew.png")),

                                ClipPageModel.create()
                                        .withId("injector_clip")
                                        .withText("book.gemcolony.injector_clip.text")
                                        .withTexture(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "modonomicon/clip/injector_clip.png"))
                                        .withDuration(18.0F),

                                BookImagePageModel.create()
                                        .withId("kindergarten")
                                        .withTitle("book.gemcolony.kindergarten.title")
                                        .withText("book.gemcolony.kindergarten.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "modonomicon/image/kindergarten.png"))
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
                                        .withMultiblockName("book.gemcolony.shell")
                                        .withText("book.gemcolony.shell_multiblock.text"),

                                MultiblockSpotlightPageModel.create()
                                        .withId("shell")
                                        .withTitle("book.gemcolony.shell")
                                        .withText("book.gemcolony.shell.text")
                                        .withItem(Ingredient.of(ModBlocks.SHELL))
                                        .withRequirements(((ConstructedMultiblock) ModBlocks.SHELL.get()).getConstructionRequirements()),

                                BookImagePageModel.create()
                                        .withId("shell_gui")
                                        .withTitle("block.gemcolony.shell_gui.title")
                                        .withText("book.gemcolony.shell_gui.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "modonomicon/image/shell_gui.png")),

                                ClipPageModel.create()
                                        .withId("shell_clip")
                                        .withText("book.gemcolony.shell_clip.text")
                                        .withTexture(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "modonomicon/clip/shell_clip.png"))
                                        .withDuration(13.0F),

                                BookImagePageModel.create()
                                        .withId("shell_waterlogged")
                                        .withTitle("block.gemcolony.shell_waterlogged.title")
                                        .withText("book.gemcolony.shell_waterlogged.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "modonomicon/image/shell_waterlogged.png"))
                        )
        );

        Item[] chromas = {
                ModItems.WHITE_CHROMA.get(),
                ModItems.LIGHT_GRAY_CHROMA.get(),
                ModItems.GRAY_CHROMA.get(),
                ModItems.BLACK_CHROMA.get(),
                ModItems.BROWN_CHROMA.get(),
                ModItems.RED_CHROMA.get(),
                ModItems.ORANGE_CHROMA.get(),
                ModItems.YELLOW_CHROMA.get(),
                ModItems.LIME_CHROMA.get(),
                ModItems.GREEN_CHROMA.get(),
                ModItems.CYAN_CHROMA.get(),
                ModItems.LIGHT_BLUE_CHROMA.get(),
                ModItems.BLUE_CHROMA.get(),
                ModItems.PURPLE_CHROMA.get(),
                ModItems.MAGENTA_CHROMA.get(),
                ModItems.PINK_CHROMA.get()
        };

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/pearl"), "entity.gemcolony.pearl")
                        .withIcon(ModItems.WHITE_PEARL_GEM)
                        .withLocation(3, -6)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/shell")))
                        .withPages(
                                Stream.concat(
                                        Stream.of(
                                                BookSpotlightPageModel.create()
                                                        .withId("pearl_spotlight")
                                                        .withTitle("book.gemcolony.pearl.title")
                                                        .withText("book.gemcolony.pearl.text")
                                                        .withItem(Ingredient.of(GemDefinitions.PEARL.items().toArray(Item[]::new)))),

                                        IntStream.range(0, chromas.length)
                                                .mapToObj(variant -> GemPreviewPageModel.create()
                                                        .withId("pearl_entity_" + variant)
                                                        .withEntityId("gemcolony:pearl")
                                                        .withEntityName(GemVariantUtil.getById(PearlVariants.class, variant).getName())
                                                        .withVariant(variant)
                                                        .withChroma(chromas[variant])))
                                        .toArray(BookPageModel<?>[]::new)
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/peridot"), "entity.gemcolony.peridot")
                        .withIcon(ModItems.PERIDOT_GEM)
                        .withLocation(2, -6)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/injector")))
                        .withPages(
                                BookSpotlightPageModel.create()
                                        .withId("peridot_spotlight")
                                        .withTitle("book.gemcolony.peridot.title")
                                        .withText("book.gemcolony.peridot.text")
                                        .withItem(Ingredient.of(ModItems.PERIDOT_GEM)),
                                GemPreviewPageModel.create()
                                        .withId("peridot_entity")
                                        .withEntityId("gemcolony:peridot")
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/quartz"), "entity.gemcolony.quartz")
                        .withIcon(ModItems.MILKY_QUARTZ_GEM)
                        .withLocation(0, -7)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/injector")))
                        .withPages(
                                Stream.concat(
                                                Stream.of(
                                                        BookSpotlightPageModel.create()
                                                                .withId("quartz_spotlight")
                                                                .withTitle("book.gemcolony.quartz.title")
                                                                .withText("book.gemcolony.quartz.text")
                                                                .withItem(Ingredient.of(GemDefinitions.QUARTZ.items().toArray(Item[]::new)))),

                                                IntStream.range(0, chromas.length)
                                                        .mapToObj(variant -> GemPreviewPageModel.create()
                                                                .withId("quartz_entity_" + variant)
                                                                .withEntityId("gemcolony:quartz")
                                                                .withEntityName(GemVariantUtil.getById(QuartzVariants.class, variant).getName())
                                                                .withVariant(variant)
                                                                .withChroma(chromas[variant])))
                                        .toArray(BookPageModel<?>[]::new)
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/jasper"), "entity.gemcolony.jasper")
                        .withIcon(ModItems.SPIDERWEB_JASPER_GEM)
                        .withLocation(1, -8)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gems/injector")))
                        .withPages(
                                Stream.concat(
                                                Stream.of(
                                                        BookSpotlightPageModel.create()
                                                                .withId("jasper_spotlight")
                                                                .withTitle("book.gemcolony.jasper.title")
                                                                .withText("book.gemcolony.jasper.text")
                                                                .withItem(Ingredient.of(GemDefinitions.JASPER.items().toArray(Item[]::new)))),

                                                IntStream.range(0, chromas.length)
                                                        .mapToObj(variant -> GemPreviewPageModel.create()
                                                                .withId("jasper_entity_" + variant)
                                                                .withEntityId("gemcolony:jasper")
                                                                .withEntityName(GemVariantUtil.getById(JasperVariants.class, variant).getName())
                                                                .withVariant(variant)
                                                                .withChroma(chromas[variant])))
                                        .toArray(BookPageModel<?>[]::new)
                        )
        );

    }
}

