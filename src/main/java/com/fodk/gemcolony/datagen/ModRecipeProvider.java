package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

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

    @Override
    protected void buildRecipes() {
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHROMA_BLOCK.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .define('C', ModItems.WHITE_CHROMA)
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .group("chroma")
                .save(output, "gemcolony:chroma_block_from_chroma");

        shapeless(RecipeCategory.MISC, ModItems.WHITE_CHROMA.get(), 9)
                .requires(ModBlocks.CHROMA_BLOCK)
                .unlockedBy(getHasName(ModBlocks.CHROMA_BLOCK.get()), has(ModBlocks.CHROMA_BLOCK))
                .group("chroma")
                .save(output, "gemcolony:chroma_from_chroma_block");

        List<ItemLike> CHROMA_SMELTABLES = List.of(ModBlocks.DRAINED_STONE);

        oreSmelting(CHROMA_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.WHITE_CHROMA.get(), 0.5f, 200, "chroma");
        oreBlasting(CHROMA_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.WHITE_CHROMA.get(), 0.5f, 100, "chroma");

        stairBuilder(ModBlocks.CHROMA_STAIRS.get(), Ingredient.of(ModBlocks.CHROMA_BLOCK))
                .unlockedBy(getHasName(ModBlocks.CHROMA_BLOCK.get()), has(ModBlocks.CHROMA_BLOCK))
                .group("chroma")
                .save(output);

        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHROMA_SLAB.get(), ModBlocks.CHROMA_BLOCK.get());

        buttonBuilder(ModBlocks.CHROMA_BUTTON.get(), Ingredient.of(ModItems.WHITE_CHROMA.get()))
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .group("chroma")
                .save(output);

        pressurePlate(ModBlocks.CHROMA_PRESSURE_PLATE.get(), ModItems.WHITE_CHROMA.get());

        fenceBuilder(ModBlocks.CHROMA_FENCE.get(), Ingredient.of(ModBlocks.CHROMA_BLOCK.get()))
                .unlockedBy(getHasName(ModBlocks.CHROMA_BLOCK.get()), has(ModBlocks.CHROMA_BLOCK))
                .group("chroma")
                .save(output);

        fenceGateBuilder(ModBlocks.CHROMA_FENCE_GATE.get(), Ingredient.of(ModBlocks.CHROMA_BLOCK.get()))
                .unlockedBy(getHasName(ModBlocks.CHROMA_BLOCK.get()), has(ModBlocks.CHROMA_BLOCK))
                .group("chroma")
                .save(output);

        wall(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHROMA_WALL.get(), ModBlocks.CHROMA_BLOCK.get());

        doorBuilder(ModBlocks.CHROMA_DOOR.get(), Ingredient.of(ModItems.WHITE_CHROMA.get()))
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .group("chroma")
                .save(output);

        trapdoorBuilder(ModBlocks.CHROMA_TRAPDOOR.get(), Ingredient.of(ModItems.WHITE_CHROMA.get()))
                .unlockedBy(getHasName(ModItems.WHITE_CHROMA.get()), has(ModItems.WHITE_CHROMA))
                .group("chroma")
                .save(output);

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
