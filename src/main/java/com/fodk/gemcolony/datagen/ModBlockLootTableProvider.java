package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.block.custom.ChromaCrop;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

    @Override
    protected void generate() {
        //simple block drop
        dropSelf(ModBlocks.DRAINED_STONE.get());
        dropSelf(ModBlocks.DESTABILIZER_WALL_GENERATOR.get());

        dropSelf(ModBlocks.CHROMA_BLOCK.get());
        dropSelf(ModBlocks.CHROMA_STAIRS.get());
        dropSelf(ModBlocks.CHROMA_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.CHROMA_BUTTON.get());
        dropSelf(ModBlocks.CHROMA_FENCE.get());
        dropSelf(ModBlocks.CHROMA_FENCE_GATE.get());
        dropSelf(ModBlocks.CHROMA_WALL.get());
        dropSelf(ModBlocks.CHROMA_TRAPDOOR.get());

        //slab drops
        add(ModBlocks.CHROMA_SLAB.get(), this::createSlabItemTable);

        //door drops
        add(ModBlocks.CHROMA_DOOR.get(), this::createDoorTable);

        //ore drops
        add(ModBlocks.WHITE_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.WHITE_CHROMA_DEPOSIT.get(), ModItems.WHITE_CHROMA.get(), 1, 4));
        add(ModBlocks.LIGHT_GRAY_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.LIGHT_GRAY_CHROMA_DEPOSIT.get(), ModItems.LIGHT_GRAY_CHROMA.get(), 1, 4));
        add(ModBlocks.GRAY_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.GRAY_CHROMA_DEPOSIT.get(), ModItems.GRAY_CHROMA.get(), 1, 4));
        add(ModBlocks.BLACK_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.BLACK_CHROMA_DEPOSIT.get(), ModItems.BLACK_CHROMA.get(), 1, 4));
        add(ModBlocks.BROWN_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.BROWN_CHROMA_DEPOSIT.get(), ModItems.BROWN_CHROMA.get(), 1, 4));
        add(ModBlocks.RED_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.RED_CHROMA_DEPOSIT.get(), ModItems.RED_CHROMA.get(), 1, 4));
        add(ModBlocks.ORANGE_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.ORANGE_CHROMA_DEPOSIT.get(), ModItems.ORANGE_CHROMA.get(), 1, 4));
        add(ModBlocks.YELLOW_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.YELLOW_CHROMA_DEPOSIT.get(), ModItems.YELLOW_CHROMA.get(), 1, 4));
        add(ModBlocks.LIME_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.LIME_CHROMA_DEPOSIT.get(), ModItems.LIME_CHROMA.get(), 1, 4));
        add(ModBlocks.GREEN_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.GREEN_CHROMA_DEPOSIT.get(), ModItems.GREEN_CHROMA.get(), 1, 4));
        add(ModBlocks.CYAN_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.CYAN_CHROMA_DEPOSIT.get(), ModItems.CYAN_CHROMA.get(), 1, 4));
        add(ModBlocks.LIGHT_BLUE_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.LIGHT_BLUE_CHROMA_DEPOSIT.get(), ModItems.LIGHT_BLUE_CHROMA.get(), 1, 4));
        add(ModBlocks.BLUE_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.BLUE_CHROMA_DEPOSIT.get(), ModItems.BLUE_CHROMA.get(), 1, 4));
        add(ModBlocks.PURPLE_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.PURPLE_CHROMA_DEPOSIT.get(), ModItems.PURPLE_CHROMA.get(), 1, 4));
        add(ModBlocks.MAGENTA_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.MAGENTA_CHROMA_DEPOSIT.get(), ModItems.MAGENTA_CHROMA.get(), 1, 4));
        add(ModBlocks.PINK_CHROMA_DEPOSIT.get(), createCopperOreLikeDrops(ModBlocks.PINK_CHROMA_DEPOSIT.get(), ModItems.PINK_CHROMA.get(), 1, 4));

        //crop like drop
        add(ModBlocks.CHROMA_CROP.get(), createChromaCropDrops(ModBlocks.CHROMA_CROP.get(), ModItems.CHROMA_SEED.get()));

        //bush drop
        add(ModBlocks.STRAWBERRY_BUSH.get(), block -> this.applyExplosionDecay(block, LootTable.lootTable().withPool(
                LootPool.lootPool().when(
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.STRAWBERRY_BUSH.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3)))
                        .add(LootItem.lootTableItem(ModItems.STRAWBERRY))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
        ).withPool(LootPool.lootPool().when(
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.STRAWBERRY_BUSH.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2))
                        ).add(LootItem.lootTableItem(ModItems.STRAWBERRY))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
        )));
    }

    protected LootTable.Builder createChromaCropDrops(Block block, Item item){
        return LootTable.lootTable()
                .withPool(this.applyExplosionDecay(
                block,
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(
                                LootItem.lootTableItem(item)
                                        .apply(
                                                SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))
                                                        .when(
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ChromaCrop.AGE, 3))
                                                        )
                                        )
                                        .apply(
                                                ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))
                                                        .when(
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ChromaCrop.AGE, 3))
                                                        )
                                        )
                        )
        ));
    }

    protected LootTable.Builder createCopperOreLikeDrops(Block block, Item item, float minDrops, float maxDrops) {

        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks(){
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
