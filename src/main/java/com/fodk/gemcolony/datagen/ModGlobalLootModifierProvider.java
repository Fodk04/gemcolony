package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.GemColony;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, GemColony.MOD_ID);
    }

    @Override
    protected void start() {
        add("white_chroma_from_ancient_city",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/ancient_city")).build()
                }, 1000, ModExtraLootProvider.WHITE_CHROMA));
        add("light_gray_chroma_from_dungeon",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/simple_dungeon")).build()
                }, 1000, ModExtraLootProvider.LIGHT_GRAY_CHROMA));
        add("gray_chroma_from_mineshaft",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/abandoned_mineshaft")).build()
                }, 1000, ModExtraLootProvider.GRAY_CHROMA));
        add("black_chroma_from_bastion",
                new AddTableLootModifier(new LootItemCondition[]{
                        AnyOfCondition.anyOf(
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/bastion_bridge")),
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/bastion_hoglin_stable")),
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/bastion_other"))
                        ).build()

                }, 1000, ModExtraLootProvider.BLACK_CHROMA));
        add("brown_chroma_from_pillager_sites",
                new AddTableLootModifier(new LootItemCondition[]{
                        AnyOfCondition.anyOf(
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/pillager_outpost")),
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/woodland_mansion"))
                        ).build()

                }, 1000, ModExtraLootProvider.BROWN_CHROMA));
        add("red_chroma_from_nether_sites",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/nether_bridge")).build()
                }, 1000, ModExtraLootProvider.RED_CHROMA));
        add("orange_chroma_from_desert_pyramid",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/desert_pyramid")).build()
                }, 1000, ModExtraLootProvider.ORANGE_CHROMA));
        add("yellow_chroma_from_desert_pyramid",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/desert_pyramid")).build()
                }, 1000, ModExtraLootProvider.YELLOW_CHROMA));
        add("lime_chroma_from_jungle_temple",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/jungle_temple")).build()
                }, 1000, ModExtraLootProvider.LIME_CHROMA));
        add("green_chroma_from_jungle_temple",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/jungle_temple")).build()
                }, 1000, ModExtraLootProvider.GREEN_CHROMA));
        add("cyan_chroma_from_underwater_ruins",
                new AddTableLootModifier(new LootItemCondition[]{
                        AnyOfCondition.anyOf(
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/underwater_ruin_small")),
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/underwater_ruin_big"))
                        ).build()

                }, 1000, ModExtraLootProvider.CYAN_CHROMA));
        add("light_blue_chroma_from_water_treasures",
                new AddTableLootModifier(new LootItemCondition[]{
                        AnyOfCondition.anyOf(
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/shipwreck_treasure")),
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/buried_treasure"))
                        ).build()

                }, 1000, ModExtraLootProvider.LIGHT_BLUE_CHROMA));
        add("blue_chroma_from_igloo",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/igloo_chest")).build()
                }, 1000, ModExtraLootProvider.BLUE_CHROMA));
        add("purple_chroma_from_portal",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/ruined_portal")).build()
                }, 1000, ModExtraLootProvider.PURPLE_CHROMA));
        add("magenta_chroma_from_end",
                new AddTableLootModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/end_city_treasure")).build()
                }, 1000, ModExtraLootProvider.MAGENTA_CHROMA));
        add("pink_chroma_from_",
                new AddTableLootModifier(new LootItemCondition[]{
                        AnyOfCondition.anyOf(
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_temple")),
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_armorer")),
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_toolsmith"))
                        ).build()
                }, 1000, ModExtraLootProvider.PINK_CHROMA));
    }
}
