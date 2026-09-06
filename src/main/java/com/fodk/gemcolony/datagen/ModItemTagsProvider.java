package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, GemColony.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.CHROMAS)
                .add(ModItems.getRK(ModItems.WHITE_CHROMA))
                .add(ModItems.getRK(ModItems.LIGHT_GRAY_CHROMA))
                .add(ModItems.getRK(ModItems.GRAY_CHROMA))
                .add(ModItems.getRK(ModItems.BLACK_CHROMA))
                .add(ModItems.getRK(ModItems.BROWN_CHROMA))
                .add(ModItems.getRK(ModItems.RED_CHROMA))
                .add(ModItems.getRK(ModItems.ORANGE_CHROMA))
                .add(ModItems.getRK(ModItems.YELLOW_CHROMA))
                .add(ModItems.getRK(ModItems.LIME_CHROMA))
                .add(ModItems.getRK(ModItems.GREEN_CHROMA))
                .add(ModItems.getRK(ModItems.CYAN_CHROMA))
                .add(ModItems.getRK(ModItems.LIGHT_BLUE_CHROMA))
                .add(ModItems.getRK(ModItems.BLUE_CHROMA))
                .add(ModItems.getRK(ModItems.PURPLE_CHROMA))
                .add(ModItems.getRK(ModItems.MAGENTA_CHROMA))
                .add(ModItems.getRK(ModItems.PINK_CHROMA));

        tag(ModTags.Items.CHROMA_REPAIRABLE).addTag(ModTags.Items.CHROMAS);

        tag(ItemTags.SWORDS).add(ModItems.getRK(ModItems.CHROMA_SWORD));
        tag(ItemTags.PICKAXES).add(ModItems.getRK(ModItems.CHROMA_PICKAXE));
        tag(ItemTags.SHOVELS).add(ModItems.getRK(ModItems.CHROMA_SHOVEL));
        tag(ItemTags.AXES).add(ModItems.getRK(ModItems.CHROMA_AXE));
        tag(ItemTags.HOES).add(ModItems.getRK(ModItems.CHROMA_HOE));
        tag(ItemTags.SPEARS).add(ModItems.getRK(ModItems.CHROMA_SPEAR));

        tag(ItemTags.HEAD_ARMOR).add(ModItems.getRK(ModItems.CHROMA_HELMET));
        tag(ItemTags.CHEST_ARMOR).add(ModItems.getRK(ModItems.CHROMA_CHESTPLATE));
        tag(ItemTags.LEG_ARMOR).add(ModItems.getRK(ModItems.CHROMA_LEGGINGS));
        tag(ItemTags.FOOT_ARMOR).add(ModItems.getRK(ModItems.CHROMA_BOOTS));

        //MUSIC DISC
        tag(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(ModItems.getRK(ModItems.LOVE_LIKE_YOU_MUSIC_DISC))
                .add(ModItems.getRK(ModItems.ITS_OVER_ISNT_IT_MUSIC_DISC));
    }
}
