package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registeries, RecipeOutput output) {
            return new ModRecipeProvider(registeries, output);
        }

        @Override
        public String getName() {
            return "Gem Colony Recipes";
        }
    }


    private record ChromaRecipeSet(
            DeferredItem<Item> chroma,
            DeferredBlock<Block> block,
            DeferredBlock<Block> polishedBlock,
            DeferredBlock<StairBlock> stairs,
            DeferredBlock<SlabBlock> slab,
            DeferredBlock<StairBlock> polishedStairs,
            DeferredBlock<SlabBlock> polishedSlab
    ) {}

    private void registerChromaRecipes() {
        List<ChromaRecipeSet> colors = List.of(
                new ChromaRecipeSet(ModItems.WHITE_CHROMA, ModBlocks.WHITE_CHROMA_BLOCK, ModBlocks.WHITE_POLISHED_CHROMA_BLOCK, ModBlocks.WHITE_CHROMA_STAIRS, ModBlocks.WHITE_CHROMA_SLAB, ModBlocks.WHITE_POLISHED_CHROMA_STAIRS, ModBlocks.WHITE_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.LIGHT_GRAY_CHROMA, ModBlocks.LIGHT_GRAY_CHROMA_BLOCK, ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_BLOCK, ModBlocks.LIGHT_GRAY_CHROMA_STAIRS, ModBlocks.LIGHT_GRAY_CHROMA_SLAB, ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_STAIRS, ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.GRAY_CHROMA, ModBlocks.GRAY_CHROMA_BLOCK, ModBlocks.GRAY_POLISHED_CHROMA_BLOCK, ModBlocks.GRAY_CHROMA_STAIRS, ModBlocks.GRAY_CHROMA_SLAB, ModBlocks.GRAY_POLISHED_CHROMA_STAIRS, ModBlocks.GRAY_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.BLACK_CHROMA, ModBlocks.BLACK_CHROMA_BLOCK, ModBlocks.BLACK_POLISHED_CHROMA_BLOCK, ModBlocks.BLACK_CHROMA_STAIRS, ModBlocks.BLACK_CHROMA_SLAB, ModBlocks.BLACK_POLISHED_CHROMA_STAIRS, ModBlocks.BLACK_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.BROWN_CHROMA, ModBlocks.BROWN_CHROMA_BLOCK, ModBlocks.BROWN_POLISHED_CHROMA_BLOCK, ModBlocks.BROWN_CHROMA_STAIRS, ModBlocks.BROWN_CHROMA_SLAB, ModBlocks.BROWN_POLISHED_CHROMA_STAIRS, ModBlocks.BROWN_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.RED_CHROMA, ModBlocks.RED_CHROMA_BLOCK, ModBlocks.RED_POLISHED_CHROMA_BLOCK, ModBlocks.RED_CHROMA_STAIRS, ModBlocks.RED_CHROMA_SLAB, ModBlocks.RED_POLISHED_CHROMA_STAIRS, ModBlocks.RED_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.ORANGE_CHROMA, ModBlocks.ORANGE_CHROMA_BLOCK, ModBlocks.ORANGE_POLISHED_CHROMA_BLOCK, ModBlocks.ORANGE_CHROMA_STAIRS, ModBlocks.ORANGE_CHROMA_SLAB, ModBlocks.ORANGE_POLISHED_CHROMA_STAIRS, ModBlocks.ORANGE_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.YELLOW_CHROMA, ModBlocks.YELLOW_CHROMA_BLOCK, ModBlocks.YELLOW_POLISHED_CHROMA_BLOCK, ModBlocks.YELLOW_CHROMA_STAIRS, ModBlocks.YELLOW_CHROMA_SLAB, ModBlocks.YELLOW_POLISHED_CHROMA_STAIRS, ModBlocks.YELLOW_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.LIME_CHROMA, ModBlocks.LIME_CHROMA_BLOCK, ModBlocks.LIME_POLISHED_CHROMA_BLOCK, ModBlocks.LIME_CHROMA_STAIRS, ModBlocks.LIME_CHROMA_SLAB, ModBlocks.LIME_POLISHED_CHROMA_STAIRS, ModBlocks.LIME_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.GREEN_CHROMA, ModBlocks.GREEN_CHROMA_BLOCK, ModBlocks.GREEN_POLISHED_CHROMA_BLOCK, ModBlocks.GREEN_CHROMA_STAIRS, ModBlocks.GREEN_CHROMA_SLAB, ModBlocks.GREEN_POLISHED_CHROMA_STAIRS, ModBlocks.GREEN_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.CYAN_CHROMA, ModBlocks.CYAN_CHROMA_BLOCK, ModBlocks.CYAN_POLISHED_CHROMA_BLOCK, ModBlocks.CYAN_CHROMA_STAIRS, ModBlocks.CYAN_CHROMA_SLAB, ModBlocks.CYAN_POLISHED_CHROMA_STAIRS, ModBlocks.CYAN_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.LIGHT_BLUE_CHROMA, ModBlocks.LIGHT_BLUE_CHROMA_BLOCK, ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_BLOCK, ModBlocks.LIGHT_BLUE_CHROMA_STAIRS, ModBlocks.LIGHT_BLUE_CHROMA_SLAB, ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_STAIRS, ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.BLUE_CHROMA, ModBlocks.BLUE_CHROMA_BLOCK, ModBlocks.BLUE_POLISHED_CHROMA_BLOCK, ModBlocks.BLUE_CHROMA_STAIRS, ModBlocks.BLUE_CHROMA_SLAB, ModBlocks.BLUE_POLISHED_CHROMA_STAIRS, ModBlocks.BLUE_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.PURPLE_CHROMA, ModBlocks.PURPLE_CHROMA_BLOCK, ModBlocks.PURPLE_POLISHED_CHROMA_BLOCK, ModBlocks.PURPLE_CHROMA_STAIRS, ModBlocks.PURPLE_CHROMA_SLAB, ModBlocks.PURPLE_POLISHED_CHROMA_STAIRS, ModBlocks.PURPLE_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.MAGENTA_CHROMA, ModBlocks.MAGENTA_CHROMA_BLOCK, ModBlocks.MAGENTA_POLISHED_CHROMA_BLOCK, ModBlocks.MAGENTA_CHROMA_STAIRS, ModBlocks.MAGENTA_CHROMA_SLAB, ModBlocks.MAGENTA_POLISHED_CHROMA_STAIRS, ModBlocks.MAGENTA_POLISHED_CHROMA_SLAB),
                new ChromaRecipeSet(ModItems.PINK_CHROMA, ModBlocks.PINK_CHROMA_BLOCK, ModBlocks.PINK_POLISHED_CHROMA_BLOCK, ModBlocks.PINK_CHROMA_STAIRS, ModBlocks.PINK_CHROMA_SLAB, ModBlocks.PINK_POLISHED_CHROMA_STAIRS, ModBlocks.PINK_POLISHED_CHROMA_SLAB)
        );

        for (ChromaRecipeSet color : colors) {
            // 4 Chromas -> 1 Chroma block
            shaped(RecipeCategory.BUILDING_BLOCKS, color.block().get())
                    .pattern("CC")
                    .pattern("CC")
                    .define('C', color.chroma().get())
                    .unlockedBy(getHasName(color.chroma().get()), has(color.chroma().get()))
                    .group("chroma")
                    .save(output);

            // 4 Chroma blocks -> 4 polished Chroma blocks
            shaped(RecipeCategory.BUILDING_BLOCKS, color.polishedBlock().get(), 4)
                    .pattern("BB")
                    .pattern("BB")
                    .define('B', color.block().get())
                    .unlockedBy(getHasName(color.block().get()), has(color.block().get()))
                    .group("chroma")
                    .save(output);

            // 6 blocks -> 4 stairs
            registerStairsRecipe(color.block(), color.stairs());

            // 3 blocks -> 6 slabs
            registerSlabRecipe(color.block(), color.slab());

            // Polished Chroma stairs and slabs
            registerStairsRecipe(color.polishedBlock(), color.polishedStairs());
            registerSlabRecipe(color.polishedBlock(), color.polishedSlab());
        }
    }

    private void registerStairsRecipe(DeferredBlock<? extends Block> ingredient, DeferredBlock<StairBlock> result) {
        shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 4)
                .pattern("B  ")
                .pattern("BB ")
                .pattern("BBB")
                .define('B', ingredient.get())
                .unlockedBy(getHasName(ingredient.get()), has(ingredient.get()))
                .group("chroma")
                .save(output);
    }

    private void registerSlabRecipe(DeferredBlock<? extends Block> ingredient, DeferredBlock<SlabBlock> result) {
        shaped(RecipeCategory.BUILDING_BLOCKS, result.get(), 6)
                .pattern("BBB")
                .define('B', ingredient.get())
                .unlockedBy(getHasName(ingredient.get()), has(ingredient.get()))
                .group("chroma")
                .save(output);
    }


    private record SmoothChromaRecipeSet(
            DeferredBlock<Block> block,
            DeferredBlock<Block> smoothBlock,
            DeferredBlock<StairBlock> smoothStairs,
            DeferredBlock<SlabBlock> smoothSlab
    ) {}

    private void registerSmoothChromaRecipes() {
        List<SmoothChromaRecipeSet> colors = List.of(
                new SmoothChromaRecipeSet(ModBlocks.WHITE_CHROMA_BLOCK, ModBlocks.WHITE_SMOOTH_CHROMA_BLOCK, ModBlocks.WHITE_SMOOTH_CHROMA_STAIRS, ModBlocks.WHITE_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.LIGHT_GRAY_CHROMA_BLOCK, ModBlocks.LIGHT_GRAY_SMOOTH_CHROMA_BLOCK, ModBlocks.LIGHT_GRAY_SMOOTH_CHROMA_STAIRS, ModBlocks.LIGHT_GRAY_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.GRAY_CHROMA_BLOCK, ModBlocks.GRAY_SMOOTH_CHROMA_BLOCK, ModBlocks.GRAY_SMOOTH_CHROMA_STAIRS, ModBlocks.GRAY_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.BLACK_CHROMA_BLOCK, ModBlocks.BLACK_SMOOTH_CHROMA_BLOCK, ModBlocks.BLACK_SMOOTH_CHROMA_STAIRS, ModBlocks.BLACK_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.BROWN_CHROMA_BLOCK, ModBlocks.BROWN_SMOOTH_CHROMA_BLOCK, ModBlocks.BROWN_SMOOTH_CHROMA_STAIRS, ModBlocks.BROWN_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.RED_CHROMA_BLOCK, ModBlocks.RED_SMOOTH_CHROMA_BLOCK, ModBlocks.RED_SMOOTH_CHROMA_STAIRS, ModBlocks.RED_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.ORANGE_CHROMA_BLOCK, ModBlocks.ORANGE_SMOOTH_CHROMA_BLOCK, ModBlocks.ORANGE_SMOOTH_CHROMA_STAIRS, ModBlocks.ORANGE_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.YELLOW_CHROMA_BLOCK, ModBlocks.YELLOW_SMOOTH_CHROMA_BLOCK, ModBlocks.YELLOW_SMOOTH_CHROMA_STAIRS, ModBlocks.YELLOW_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.LIME_CHROMA_BLOCK, ModBlocks.LIME_SMOOTH_CHROMA_BLOCK, ModBlocks.LIME_SMOOTH_CHROMA_STAIRS, ModBlocks.LIME_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.GREEN_CHROMA_BLOCK, ModBlocks.GREEN_SMOOTH_CHROMA_BLOCK, ModBlocks.GREEN_SMOOTH_CHROMA_STAIRS, ModBlocks.GREEN_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.CYAN_CHROMA_BLOCK, ModBlocks.CYAN_SMOOTH_CHROMA_BLOCK, ModBlocks.CYAN_SMOOTH_CHROMA_STAIRS, ModBlocks.CYAN_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.LIGHT_BLUE_CHROMA_BLOCK, ModBlocks.LIGHT_BLUE_SMOOTH_CHROMA_BLOCK, ModBlocks.LIGHT_BLUE_SMOOTH_CHROMA_STAIRS, ModBlocks.LIGHT_BLUE_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.BLUE_CHROMA_BLOCK, ModBlocks.BLUE_SMOOTH_CHROMA_BLOCK, ModBlocks.BLUE_SMOOTH_CHROMA_STAIRS, ModBlocks.BLUE_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.PURPLE_CHROMA_BLOCK, ModBlocks.PURPLE_SMOOTH_CHROMA_BLOCK, ModBlocks.PURPLE_SMOOTH_CHROMA_STAIRS, ModBlocks.PURPLE_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.MAGENTA_CHROMA_BLOCK, ModBlocks.MAGENTA_SMOOTH_CHROMA_BLOCK, ModBlocks.MAGENTA_SMOOTH_CHROMA_STAIRS, ModBlocks.MAGENTA_SMOOTH_CHROMA_SLAB),
                new SmoothChromaRecipeSet(ModBlocks.PINK_CHROMA_BLOCK, ModBlocks.PINK_SMOOTH_CHROMA_BLOCK, ModBlocks.PINK_SMOOTH_CHROMA_STAIRS, ModBlocks.PINK_SMOOTH_CHROMA_SLAB)
        );

        for (SmoothChromaRecipeSet color : colors) {
            List<ItemLike> smeltables = List.of(color.block().get());

            oreSmelting(
                    smeltables,
                    RecipeCategory.BUILDING_BLOCKS,
                    CookingBookCategory.BLOCKS,
                    color.smoothBlock().get(),
                    0.1f,
                    200,
                    "chroma"
            );

            oreBlasting(
                    smeltables,
                    RecipeCategory.BUILDING_BLOCKS,
                    CookingBookCategory.BLOCKS,
                    color.smoothBlock().get(),
                    0.1f,
                    100,
                    "chroma"
            );

            registerStairsRecipe(color.smoothBlock(), color.smoothStairs());
            registerSlabRecipe(color.smoothBlock(), color.smoothSlab());
        }
    }

    private void registerChromaPillarRecipes() {
        registerChromaPillarRecipe(ModBlocks.WHITE_POLISHED_CHROMA_BLOCK, ModBlocks.WHITE_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_BLOCK, ModBlocks.LIGHT_GRAY_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.GRAY_POLISHED_CHROMA_BLOCK, ModBlocks.GRAY_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.BLACK_POLISHED_CHROMA_BLOCK, ModBlocks.BLACK_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.BROWN_POLISHED_CHROMA_BLOCK, ModBlocks.BROWN_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.RED_POLISHED_CHROMA_BLOCK, ModBlocks.RED_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.ORANGE_POLISHED_CHROMA_BLOCK, ModBlocks.ORANGE_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.YELLOW_POLISHED_CHROMA_BLOCK, ModBlocks.YELLOW_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.LIME_POLISHED_CHROMA_BLOCK, ModBlocks.LIME_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.GREEN_POLISHED_CHROMA_BLOCK, ModBlocks.GREEN_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.CYAN_POLISHED_CHROMA_BLOCK, ModBlocks.CYAN_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_BLOCK, ModBlocks.LIGHT_BLUE_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.BLUE_POLISHED_CHROMA_BLOCK, ModBlocks.BLUE_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.PURPLE_POLISHED_CHROMA_BLOCK, ModBlocks.PURPLE_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.MAGENTA_POLISHED_CHROMA_BLOCK, ModBlocks.MAGENTA_CHROMA_PILLAR);
        registerChromaPillarRecipe(ModBlocks.PINK_POLISHED_CHROMA_BLOCK, ModBlocks.PINK_CHROMA_PILLAR);
    }

    private void registerChromaPillarRecipe(DeferredBlock<? extends Block> ingredient, DeferredBlock<? extends Block> result) {
        ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, result.get(), 2)
                .pattern("P")
                .pattern("P")
                .define('P', ingredient.get())
                .unlockedBy("has_polished_chroma", has(ingredient.get()))
                .save(output);
    }

    @Override
    protected void buildRecipes() {

        // CHROMA BLOCKS
        registerSmoothChromaRecipes();
        registerChromaRecipes();
        registerChromaPillarRecipes();
//        buttonBuilder(ModBlocks.CHROMA_BUTTON.get(), Ingredient.of(ModItems.WHITE_CHROMA.get()))
//                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
//                .group("chroma")
//                .save(output);
//
//        pressurePlate(ModBlocks.CHROMA_PRESSURE_PLATE.get(), ModItems.WHITE_CHROMA.get());
//
//        doorBuilder(ModBlocks.CHROMA_DOOR.get(), Ingredient.of(ModItems.WHITE_CHROMA.get()))
//                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
//                .group("chroma")
//                .save(output);
//
//        trapdoorBuilder(ModBlocks.CHROMA_TRAPDOOR.get(), Ingredient.of(ModItems.WHITE_CHROMA.get()))
//                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
//                .group("chroma")
//                .save(output);

        //TOOLS
        shaped(RecipeCategory.COMBAT, ModItems.CHROMA_SWORD.get())
                .pattern("C")
                .pattern("C")
                .pattern("S")
                .define('C', ModItems.WHITE_CHROMA)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .group("chroma")
                .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.CHROMA_PICKAXE.get())
                .pattern("CCC")
                .pattern(" S ")
                .pattern(" S ")
                .define('C', ModItems.WHITE_CHROMA)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .group("chroma")
                .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.CHROMA_SHOVEL.get())
                .pattern("C")
                .pattern("S")
                .pattern("S")
                .define('C', ModItems.WHITE_CHROMA)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .group("chroma")
                .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.CHROMA_AXE.get())
                .pattern("CC")
                .pattern("SC")
                .pattern("S ")
                .define('C', ModItems.WHITE_CHROMA)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .group("chroma")
                .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.CHROMA_HOE.get())
                .pattern("CC")
                .pattern("S ")
                .pattern("S ")
                .define('C', ModItems.WHITE_CHROMA)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .group("chroma")
                .save(output);

        shaped(RecipeCategory.COMBAT, ModItems.CHROMA_SPEAR.get())
                .pattern("  C")
                .pattern(" S ")
                .pattern("S  ")
                .define('C', ModItems.WHITE_CHROMA)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .group("chroma")
                .save(output);

        shaped(RecipeCategory.COMBAT, ModItems.CHROMA_BOW.get())
                .pattern(" CS")
                .pattern("C S")
                .pattern(" CS")
                .define('C', ModItems.WHITE_CHROMA)
                .define('S', Items.STRING)
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .group("chroma")
                .save(output);

        //armor
        shaped(RecipeCategory.COMBAT, ModItems.CHROMA_HELMET.get())
                .pattern("CCC")
                .pattern("C C")
                .define('C', ModItems.WHITE_CHROMA)
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .group("chroma")
                .save(output);

        shaped(RecipeCategory.COMBAT, ModItems.CHROMA_CHESTPLATE.get())
                .pattern("C C")
                .pattern("CCC")
                .pattern("CCC")
                .define('C', ModItems.WHITE_CHROMA)
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .group("chroma")
                .save(output);

        shaped(RecipeCategory.COMBAT, ModItems.CHROMA_LEGGINGS.get())
                .pattern("CCC")
                .pattern("C C")
                .pattern("C C")
                .define('C', ModItems.WHITE_CHROMA)
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .group("chroma")
                .save(output);

        shaped(RecipeCategory.COMBAT, ModItems.CHROMA_BOOTS.get())
                .pattern("C C")
                .pattern("C C")
                .define('C', ModItems.WHITE_CHROMA)
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .group("chroma")
                .save(output);
    }

    protected <T extends AbstractCookingRecipe> void oreCooking(AbstractCookingRecipe.Factory<T> factory, List<ItemLike> smeltables, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, ItemLike result, float experience, int cookingTime, String group, String fromDesc) {
        for(ItemLike item : smeltables) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(item), craftingCategory, cookingCategory, result, experience, cookingTime, factory).group(group).unlockedBy(getHasName(item), this.has(item))
                    .save(output, GemColony.MOD_ID + ":"+ getItemName(result) + fromDesc + "_" + getItemName(item));
        }

    }
}
