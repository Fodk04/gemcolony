package com.fodk.gemcolony.datagen.villager;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.villager.ModVillagers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.tags.TagEntry;

import java.util.concurrent.CompletableFuture;

public class ModPOITags  extends PoiTypeTagsProvider {
    public ModPOITags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, GemColony.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .add(TagEntry.element(ModVillagers.GEMOLOGIST_POI.unwrapKey().get().identifier()));
    }
}
