package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModExtraLootProvider implements LootTableSubProvider {

    public static final ResourceKey<LootTable> WHITE_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/white_chroma"));
    public static final ResourceKey<LootTable> LIGHT_GRAY_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/light_gray_chroma"));
    public static final ResourceKey<LootTable> GRAY_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/gray_chroma"));
    public static final ResourceKey<LootTable> BLACK_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/black_chroma"));
    public static final ResourceKey<LootTable> BROWN_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/brown_chroma"));
    public static final ResourceKey<LootTable> RED_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/red_chroma"));
    public static final ResourceKey<LootTable> ORANGE_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/orange_chroma"));
    public static final ResourceKey<LootTable> YELLOW_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/yellow_chroma"));
    public static final ResourceKey<LootTable> LIME_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/lime_chroma"));
    public static final ResourceKey<LootTable> GREEN_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/green_chroma"));
    public static final ResourceKey<LootTable> CYAN_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/cyan_chroma"));
    public static final ResourceKey<LootTable> LIGHT_BLUE_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/light_blue_chroma"));
    public static final ResourceKey<LootTable> BLUE_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/blue_chroma"));
    public static final ResourceKey<LootTable> PURPLE_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/purple_chroma"));
    public static final ResourceKey<LootTable> MAGENTA_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/magenta_chroma"));
    public static final ResourceKey<LootTable> PINK_CHROMA = ResourceKey.create(Registries.LOOT_TABLE,
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "extra/glm/pink_chroma"));


    public ModExtraLootProvider(HolderLookup.Provider provider) {

    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(WHITE_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.WHITE_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(LIGHT_GRAY_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.LIGHT_GRAY_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(GRAY_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.GRAY_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(BLACK_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.BLACK_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(BROWN_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.BROWN_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(RED_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.RED_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(ORANGE_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.ORANGE_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(YELLOW_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.YELLOW_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(LIME_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.LIME_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(GREEN_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.GREEN_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(CYAN_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.CYAN_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(LIGHT_BLUE_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.LIGHT_BLUE_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(BLUE_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.BLUE_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(PURPLE_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.PURPLE_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(MAGENTA_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.MAGENTA_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
        output.accept(PINK_CHROMA, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.7f))
                        .add(LootItem.lootTableItem(ModItems.PINK_CHROMA))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))));
    }
}
