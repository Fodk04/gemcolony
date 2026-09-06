package com.fodk.gemcolony.datagen;


import com.fodk.gemcolony.GemColony;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.tags.TagEntry;

import java.util.concurrent.CompletableFuture;

public class ModPaintingTagsProvider extends PaintingVariantTagsProvider {

    public ModPaintingTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, GemColony.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(PaintingVariantTags.PLACEABLE)
                .add(TagEntry.optionalElement(ModPaintings.RUBY_SAPPHIRE_KEY.identifier()));
    }
}
