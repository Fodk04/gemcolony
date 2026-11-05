package com.fodk.gemcolony.datagen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
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

        tag(ModTags.Items.ESSENCE_BOTTLES)
                .add(ModItems.getRK(ModItems.BLUE_ESSENCE_BOTTLE))
                .add(ModItems.getRK(ModItems.YELLOW_ESSENCE_BOTTLE))
                .add(ModItems.getRK(ModItems.WHITE_ESSENCE_BOTTLE))
                .add(ModItems.getRK(ModItems.PINK_ESSENCE_BOTTLE));

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

        tag(Tags.Items.BUCKETS)
                .add(ModItems.getRK(ModItems.BLUE_ESSENCE_BUCKET))
                .add(ModItems.getRK(ModItems.YELLOW_ESSENCE_BUCKET))
                .add(ModItems.getRK(ModItems.WHITE_ESSENCE_BUCKET))
                .add(ModItems.getRK(ModItems.PINK_ESSENCE_BUCKET));

        tag(ModTags.Items.ESSENCE_BUCKETS)
                .add(ModItems.getRK(ModItems.BLUE_ESSENCE_BUCKET))
                .add(ModItems.getRK(ModItems.YELLOW_ESSENCE_BUCKET))
                .add(ModItems.getRK(ModItems.WHITE_ESSENCE_BUCKET))
                .add(ModItems.getRK(ModItems.PINK_ESSENCE_BUCKET));

        tag(ModTags.Items.MINERAL_SHARDS)
                .add(ModItems.getRK(ModItems.BLUE_MINERAL_SHARDS))
                .add(ModItems.getRK(ModItems.YELLOW_MINERAL_SHARDS))
                .add(ModItems.getRK(ModItems.WHITE_MINERAL_SHARDS))
                .add(ModItems.getRK(ModItems.PINK_MINERAL_SHARDS));

        tag(ModTags.Items.QUARTZ_GEMSTONES)
                .add(ModItems.getRK(ModItems.MILKY_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.PHANTOM_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.FLINT_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.ONYX_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.SMOKY_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.CARNELIAN_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.CHERT_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.CITRINE_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.PRASEOLITE_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.AVENTURINE_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.ANGEL_AURA_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.DUMORTIERITE_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.BLUE_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.AMETHYST_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.CHERRY_QUARTZ_GEM))
                .add(ModItems.getRK(ModItems.ROSE_QUARTZ_GEM));

        tag(ModTags.Items.JASPER_GEMSTONES)
                .add(ModItems.getRK(ModItems.SPIDERWEB_JASPER_GEM))
                .add(ModItems.getRK(ModItems.PORCELAIN_JASPER_GEM))
                .add(ModItems.getRK(ModItems.ZEBRA_JASPER_GEM))
                .add(ModItems.getRK(ModItems.CHRYSANTHEMUM_JASPER_GEM))
                .add(ModItems.getRK(ModItems.SNAKESKIN_JASPER_GEM))
                .add(ModItems.getRK(ModItems.BIGGS_JASPER_GEM))
                .add(ModItems.getRK(ModItems.NOREENA_JASPER_GEM))
                .add(ModItems.getRK(ModItems.HONEY_JASPER_GEM))
                .add(ModItems.getRK(ModItems.MORRISONITE_JASPER_GEM))
                .add(ModItems.getRK(ModItems.RHYOLITE_JASPER_GEM))
                .add(ModItems.getRK(ModItems.KAMBABA_JASPER_GEM))
                .add(ModItems.getRK(ModItems.OCEAN_JASPER_GEM))
                .add(ModItems.getRK(ModItems.SEA_SEDIMENT_JASPER_GEM))
                .add(ModItems.getRK(ModItems.ROYAL_PLUME_JASPER_GEM))
                .add(ModItems.getRK(ModItems.MOOKITE_JASPER_GEM))
                .add(ModItems.getRK(ModItems.PEACH_PETAL_JASPER_GEM));

        tag(ModTags.Items.PEARL_GEMSTONES)
                .add(ModItems.getRK(ModItems.WHITE_PEARL_GEM))
                .add(ModItems.getRK(ModItems.SPECTER_PEARL_GEM))
                .add(ModItems.getRK(ModItems.SILVER_PEARL_GEM))
                .add(ModItems.getRK(ModItems.ACKEE_PEARL_GEM))
                .add(ModItems.getRK(ModItems.CHOCOLATE_PEARL_GEM))
                .add(ModItems.getRK(ModItems.CHERRY_PEARL_GEM))
                .add(ModItems.getRK(ModItems.APRICOT_PEARL_GEM))
                .add(ModItems.getRK(ModItems.YELLOW_PEARL_GEM))
                .add(ModItems.getRK(ModItems.OLIVE_PEARL_GEM))
                .add(ModItems.getRK(ModItems.FOREST_PEARL_GEM))
                .add(ModItems.getRK(ModItems.SKY_PEARL_GEM))
                .add(ModItems.getRK(ModItems.BLUE_PEARL_GEM))
                .add(ModItems.getRK(ModItems.OCEAN_PEARL_GEM))
                .add(ModItems.getRK(ModItems.PLUM_PEARL_GEM))
                .add(ModItems.getRK(ModItems.FUCHSIA_PEARL_GEM))
                .add(ModItems.getRK(ModItems.PINK_PEARL_GEM));
    }
}
