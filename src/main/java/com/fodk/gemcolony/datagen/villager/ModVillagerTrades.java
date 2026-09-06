package com.fodk.gemcolony.datagen.villager;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.List;
import java.util.Optional;

public class ModVillagerTrades {
    public static final ResourceKey<VillagerTrade> GEMOLOGIST_1_EMERALD_CHROMA = createKey("gemologist/1/emerald_chroma");
    public static final ResourceKey<VillagerTrade> GEMOLOGIST_1_EMERALD_DRAINED_STONE = createKey("gemologist/1/emerald_drained_stone");

    public static final ResourceKey<VillagerTrade> GEMOLOGIST_2_EMERALD_DESTABILIZER = createKey("gemologist/2/emerald_destabilizer");
    public static final ResourceKey<VillagerTrade> GEMOLOGIST_2_ESSENCE_EMERALD = createKey("gemologist/2/essence_emerald");


    public static void bootstrap(BootstrapContext<VillagerTrade> context) {
        var items = context.lookup(Registries.ITEM);
        var enchantments = context.lookup(Registries.ENCHANTMENT);

        context.register(GEMOLOGIST_1_EMERALD_CHROMA, new VillagerTrade(
                new TradeCost(Items.EMERALD, 12),
                new ItemStackTemplate(ModItems.WHITE_CHROMA, 5),
                8, 12, 0.05F, Optional.empty(), List.of()));
        context.register(GEMOLOGIST_1_EMERALD_DRAINED_STONE, new VillagerTrade(
                new TradeCost(Items.EMERALD, 16),
                new ItemStackTemplate(ModBlocks.DRAINED_STONE.asItem(), 16),
                8, 12, 0.05F, Optional.empty(), List.of()));

        context.register(GEMOLOGIST_2_EMERALD_DESTABILIZER, new VillagerTrade(
                new TradeCost(Items.EMERALD, 15),
                new ItemStackTemplate(ModBlocks.DESTABILIZER_WALL_GENERATOR.asItem(), 1),
                8, 12, 0.05F, Optional.empty(), List.of()));
        context.register(GEMOLOGIST_2_ESSENCE_EMERALD, new VillagerTrade(
                new TradeCost(ModItems.WHITE_ESSENCE_BOTTLE, 1),
                new ItemStackTemplate(Items.EMERALD, 6),
                8, 12, 0.05F, Optional.empty(), List.of()));
    }


    private static ResourceKey<VillagerTrade> createKey(String name) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name));
    }
}
