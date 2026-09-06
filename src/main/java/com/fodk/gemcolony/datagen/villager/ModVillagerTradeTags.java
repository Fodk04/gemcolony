package com.fodk.gemcolony.datagen.villager;

import com.fodk.gemcolony.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VillagerTradesTagsProvider;
import net.minecraft.tags.TagEntry;

import java.util.concurrent.CompletableFuture;

public class ModVillagerTradeTags extends VillagerTradesTagsProvider {

    public ModVillagerTradeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {

        getOrCreateRawBuilder(ModTags.Trades.GEMOLOGIST_LEVEL_1)
                .add(TagEntry.element(ModVillagerTrades.GEMOLOGIST_1_EMERALD_CHROMA.identifier()))
                .add(TagEntry.element(ModVillagerTrades.GEMOLOGIST_1_EMERALD_DRAINED_STONE.identifier()));
        getOrCreateRawBuilder(ModTags.Trades.GEMOLOGIST_LEVEL_2)
                .add(TagEntry.element(ModVillagerTrades.GEMOLOGIST_2_EMERALD_DESTABILIZER.identifier()))
                .add(TagEntry.element(ModVillagerTrades.GEMOLOGIST_2_ESSENCE_EMERALD.identifier()));

    }
}