package com.fodk.gemcolony.modonomicon;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.tags.ModTags;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.stream.Stream;

public class GettingStartedCategory extends CategoryProvider {

    public GettingStartedCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    public String categoryId() {
        return "getting_started";
    }

    @Override
    protected String categoryName() {
        return "Getting Started";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Items.WRITABLE_BOOK);
    }

    @Override
    protected void generateEntries() {
        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "getting_started/welcome"), "book.gemcolony.welcome")
                        .withIcon(Items.WRITABLE_BOOK)
                        .withLocation(0, 0)
                        .withPage(
                                BookTextPageModel.create()
                                        .withTitle("book.gemcolony.welcome.title")
                                        .withText("book.gemcolony.welcome.text")
                                        .withId("welcome_intro")
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "getting_started/chroma"), "book.gemcolony.chroma")
                        .withIcon(ModItems.BLUE_CHROMA)
                        .withLocation(2, 0)
                        .withPages(
                                BookSpotlightPageModel.create()
                                        .withId("chroma_items")
                                        .withTitle("book.gemcolony.chroma.items.title")
                                        .withText("book.gemcolony.chroma.items.text")
                                        .withItem(Ingredient.of(this.registries().lookupOrThrow(Registries.ITEM).getOrThrow(ModTags.Items.CHROMAS))),

                                BookSpotlightPageModel.create()
                                        .withId("chroma_deposits")
                                        .withTitle("book.gemcolony.chroma.deposits.title")
                                        .withText("book.gemcolony.chroma.deposits.text")
                                        .withItem(Ingredient.of(
                                                Stream.of(
                                                                "white", "light_gray", "gray", "black",
                                                                "brown", "red", "orange", "yellow",
                                                                "lime", "green", "cyan", "light_blue",
                                                                "blue", "purple", "magenta", "pink"
                                                        )
                                                        .map(color -> this.registries()
                                                                .lookupOrThrow(Registries.ITEM)
                                                                .getOrThrow(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, color + "_chroma_deposit")))
                                                                .value())
                                        ))
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "getting_started/chroma_biomes"), "book.gemcolony.chroma_biomes")
                        .withIcon(ModBlocks.BLUE_CHROMA_DEPOSIT)
                        .withLocation(4, 0)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "getting_started/chroma")))
                        .withPages(
                                BookTextPageModel.create()
                                        .withTitle("book.gemcolony.chroma_biomes.title")
                                        .withText("book.gemcolony.chroma_biomes.text")
                                        .withId("chroma_biomes_intro"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.white_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.white.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/white_biome_showcase.png"))
                                        .withId("white_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.light_gray_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.light_gray.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/light_gray_biome_showcase.png"))
                                        .withId("light_gray_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.gray_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.gray.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/gray_biome_showcase.png"))
                                        .withId("gray_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.black_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.black.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/black_biome_showcase.png"))
                                        .withId("black_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.brown_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.brown.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/brown_biome_showcase.png"))
                                        .withId("brown_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.red_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.red.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/red_biome_showcase.png"))
                                        .withId("red_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.orange_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.orange.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/orange_biome_showcase.png"))
                                        .withId("orange_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.yellow_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.yellow.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/yellow_biome_showcase.png"))
                                        .withId("yellow_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.lime_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.lime.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/lime_biome_showcase.png"))
                                        .withId("lime_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.green_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.green.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/green_biome_showcase.png"))
                                        .withId("green_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.cyan_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.cyan.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/cyan_biome_showcase.png"))
                                        .withId("cyan_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.light_blue_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.light_blue.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/light_blue_biome_showcase.png"))
                                        .withId("light_blue_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.blue_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.blue.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/blue_biome_showcase.png"))
                                        .withId("blue_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.purple_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.purple.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/purple_biome_showcase.png"))
                                        .withId("purple_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.magenta_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.magenta.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/magenta_biome_showcase.png"))
                                        .withId("magenta_biome_showcase"),

                                BookImagePageModel.create()
                                        .withTitle("block.gemcolony.pink_chroma_deposit")
                                        .withText("book.gemcolony.chroma_biomes.pink.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/pink_biome_showcase.png"))
                                        .withId("pink_biome_showcase")
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "getting_started/chroma_farming"), "book.gemcolony.chroma_farming")
                        .withIcon(ModItems.CHROMA_SEED)
                        .withLocation(3, -2)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "getting_started/chroma")))
                        .withPages(
                                BookTextPageModel.create()
                                        .withTitle("book.gemcolony.chroma_farming.title")
                                        .withText("book.gemcolony.chroma_farming.text")
                                        .withId("chroma_farming_intro"),

                                BookCraftingRecipePageModel.create()
                                        .withId("chroma_seed_recipe")
                                        .withTitle1("book.gemcolony.chroma_farming.chroma_seed.recipe_title")
                                        .withText("book.gemcolony.chroma_farming.chroma_seed.recipe_text")
                                        .withRecipeId1("gemcolony:chroma_seed"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_white")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "white_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.white.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.white.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_light_gray")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "light_gray_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.light_gray.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.light_gray.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_gray")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gray_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.gray.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.gray.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_black")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "black_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.black.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.black.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_brown")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "brown_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.brown.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.brown.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_red")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "red_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.red.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.red.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_orange")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "orange_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.orange.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.orange.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_yellow")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "yellow_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.yellow.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.yellow.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_lime")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "lime_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.lime.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.lime.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_green")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "green_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.green.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.green.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_cyan")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "cyan_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.cyan.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.cyan.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_light_blue")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "light_blue_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.light_blue.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.light_blue.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_blue")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "blue_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.blue.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.blue.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_purple")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "purple_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.purple.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.purple.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_magenta")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "magenta_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.magenta.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.magenta.text"),

                                BookMultiblockPageModel.create()
                                        .withId("chroma_farming_pink")
                                        .withMultiblockId(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "pink_chroma_farming"))
                                        .withMultiblockName("book.gemcolony.chroma_farming.pink.multiblock_name")
                                        .withText("book.gemcolony.chroma_farming.pink.text")
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "getting_started/chroma_structures"), "book.gemcolony.chroma_structures")
                        .withIcon(Items.CHEST)
                        .withLocation(3, 2)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "getting_started/chroma")))
                        .withPages(
                                BookTextPageModel.create()
                                        .withId("chroma_structures_intro")
                                        .withTitle("book.gemcolony.chroma_structures.title")
                                        .withText("book.gemcolony.chroma_structures.text"),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_ancient_city")
                                        .withTitle("book.gemcolony.chroma_structures.ancient_city.title")
                                        .withText("book.gemcolony.chroma_structures.ancient_city.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/ancient_city_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_dungeon")
                                        .withTitle("book.gemcolony.chroma_structures.dungeon.title")
                                        .withText("book.gemcolony.chroma_structures.dungeon.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/dungeon_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_mineshaft")
                                        .withTitle("book.gemcolony.chroma_structures.mineshaft.title")
                                        .withText("book.gemcolony.chroma_structures.mineshaft.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/mineshaft_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_bastion")
                                        .withTitle("book.gemcolony.chroma_structures.bastion.title")
                                        .withText("book.gemcolony.chroma_structures.bastion.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/bastion_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_pillager_bases")
                                        .withTitle("book.gemcolony.chroma_structures.pillager_bases.title")
                                        .withText("book.gemcolony.chroma_structures.pillager_bases.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/pillager_bases_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_nether_fortress")
                                        .withTitle("book.gemcolony.chroma_structures.nether_fortress.title")
                                        .withText("book.gemcolony.chroma_structures.nether_fortress.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/nether_fortress_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_desert_pyramid")
                                        .withTitle("book.gemcolony.chroma_structures.desert_pyramid.title")
                                        .withText("book.gemcolony.chroma_structures.desert_pyramid.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/desert_pyramid_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_jungle_temple")
                                        .withTitle("book.gemcolony.chroma_structures.jungle_temple.title")
                                        .withText("book.gemcolony.chroma_structures.jungle_temple.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/jungle_temple_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_underwater_ruins")
                                        .withTitle("book.gemcolony.chroma_structures.underwater_ruins.title")
                                        .withText("book.gemcolony.chroma_structures.underwater_ruins.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/underwater_ruins_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_treasures")
                                        .withTitle("book.gemcolony.chroma_structures.treasures.title")
                                        .withText("book.gemcolony.chroma_structures.treasures.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/treasures_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_igloo")
                                        .withTitle("book.gemcolony.chroma_structures.igloo.title")
                                        .withText("book.gemcolony.chroma_structures.igloo.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/igloo_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_ruined_portal")
                                        .withTitle("book.gemcolony.chroma_structures.ruined_portal.title")
                                        .withText("book.gemcolony.chroma_structures.ruined_portal.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/ruined_portal_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_end_city")
                                        .withTitle("book.gemcolony.chroma_structures.end_city.title")
                                        .withText("book.gemcolony.chroma_structures.end_city.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/end_city_chroma.png")),

                                BookImagePageModel.create()
                                        .withId("chroma_structure_village")
                                        .withTitle("book.gemcolony.chroma_structures.village.title")
                                        .withText("book.gemcolony.chroma_structures.village.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/village_chroma.png"))
                        )
        );


        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "getting_started/essence"), "book.gemcolony.essence")
                        .withIcon(ModItems.BLUE_ESSENCE_BUCKET)
                        .withLocation(-2, 0)
                        .withPages(
                                BookSpotlightPageModel.create()
                                        .withId("essence_buckets")
                                        .withTitle("book.gemcolony.essence.buckets.title")
                                        .withText("book.gemcolony.essence.buckets.text")
                                        .withItem(Ingredient.of(this.registries()
                                                .lookupOrThrow(Registries.ITEM)
                                                .getOrThrow(ModTags.Items.ESSENCE_BUCKETS))),

                                BookSpotlightPageModel.create()
                                        .withId("essence_bottles")
                                        .withTitle("book.gemcolony.essence.bottles.title")
                                        .withText("book.gemcolony.essence.bottles.text")
                                        .withItem(Ingredient.of(this.registries()
                                                .lookupOrThrow(Registries.ITEM)
                                                .getOrThrow(ModTags.Items.ESSENCE_BOTTLES)))
                        )
        );

        this.add(
                BookEntryModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "getting_started/essence_structures"), "book.gemcolony.essence_structures")
                        .withIcon(Items.CHEST)
                        .withLocation(-4, 0)
                        .withParent(BookEntryParentModel.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "getting_started/essence")))
                        .withPages(
                                BookTextPageModel.create()
                                        .withId("essence_structures_intro")
                                        .withTitle("book.gemcolony.essence_structures.title")
                                        .withText("book.gemcolony.essence_structures.text"),

                                BookImagePageModel.create()
                                        .withId("essence_structure_yellow")
                                        .withTitle("book.gemcolony.essence_structures.yellow.title")
                                        .withText("book.gemcolony.essence_structures.yellow.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/yellow_essence_structure.png")),

                                BookImagePageModel.create()
                                        .withId("essence_structure_blue")
                                        .withTitle("book.gemcolony.essence_structures.blue.title")
                                        .withText("book.gemcolony.essence_structures.blue.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/blue_essence_structure.png")),

                                BookImagePageModel.create()
                                        .withId("essence_structure_pink")
                                        .withTitle("book.gemcolony.essence_structures.pink.title")
                                        .withText("book.gemcolony.essence_structures.pink.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/pink_essence_structure.png")),

                                BookImagePageModel.create()
                                        .withId("essence_structure_white")
                                        .withTitle("book.gemcolony.essence_structures.white.title")
                                        .withText("book.gemcolony.essence_structures.white.text")
                                        .withImages(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/guide/white_essence_structure.png"))
                        )
        );

    }
}

