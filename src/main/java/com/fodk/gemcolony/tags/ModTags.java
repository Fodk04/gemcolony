package com.fodk.gemcolony.tags;

import com.fodk.gemcolony.GemColony;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks{
        public static final TagKey<Block> GEM_DRAINABLES = createTag("gem_drainables");
        public static final TagKey<Block> CHROMA_DEPOSITS = createTag("chroma_deposits");
        public static final TagKey<Block> CHROMA_PLANTABLE = createTag("chroma_plantable");

        public static final TagKey<Block> NEEDS_CHROMA_TOOL = createTag("needs_chroma_tool");
        public static final TagKey<Block> INCORRECT_FOR_CHROMA_TOOL = createTag("incorrect_for_chroma_tool");

        private static TagKey<Block> createTag(String name){
            return BlockTags.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name));
        }
    }

    public static class Items{
        public static final TagKey<Item> CHROMAS = createTag("chromas");

        public static final TagKey<Item> CHROMA_REPAIRABLE = createTag("chroma_repairable");

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name));
        }
    }

    public static class Trades {
        public static final TagKey<VillagerTrade> GEMOLOGIST_LEVEL_1 = createTag("gemologist/level_1");
        public static final TagKey<VillagerTrade> GEMOLOGIST_LEVEL_2 = createTag("gemologist/level_2");

        private static TagKey<VillagerTrade> createTag(String name) {
            return TagKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, name));
        }
    }
}
