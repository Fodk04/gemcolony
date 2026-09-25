package com.fodk.gemcolony.item;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.fluid.ModFluids;
import com.fodk.gemcolony.food.ModFoods;
import com.fodk.gemcolony.item.custom.ChromaItem;
import com.fodk.gemcolony.item.custom.EssenceBottle;
import com.fodk.gemcolony.item.custom.EssenceType;
import com.fodk.gemcolony.item.custom.GemItem;
import com.fodk.gemcolony.sound.ModSounds;
import com.fodk.gemcolony.util.ColorUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(GemColony.MOD_ID);

    //CHROMAS
    public static final DeferredItem<Item> WHITE_CHROMA = ITEMS.registerItem("white_chroma",
            properties -> new ChromaItem(properties, ColorUtil.WHITE.getColorIndex()));
    public static final DeferredItem<Item> LIGHT_GRAY_CHROMA = ITEMS.registerItem("light_gray_chroma",
            properties -> new ChromaItem(properties, ColorUtil.LIGHT_GRAY.getColorIndex()));
    public static final DeferredItem<Item> GRAY_CHROMA = ITEMS.registerItem("gray_chroma",
            properties -> new ChromaItem(properties, ColorUtil.GRAY.getColorIndex()));
    public static final DeferredItem<Item> BLACK_CHROMA = ITEMS.registerItem("black_chroma",
            properties -> new ChromaItem(properties, ColorUtil.BLACK.getColorIndex()));
    public static final DeferredItem<Item> BROWN_CHROMA = ITEMS.registerItem("brown_chroma",
            properties -> new ChromaItem(properties, ColorUtil.BROWN.getColorIndex()));
    public static final DeferredItem<Item> RED_CHROMA = ITEMS.registerItem("red_chroma",
            properties -> new ChromaItem(properties, ColorUtil.RED.getColorIndex()));
    public static final DeferredItem<Item> ORANGE_CHROMA = ITEMS.registerItem("orange_chroma",
            properties -> new ChromaItem(properties, ColorUtil.ORANGE.getColorIndex()));
    public static final DeferredItem<Item> YELLOW_CHROMA = ITEMS.registerItem("yellow_chroma",
            properties -> new ChromaItem(properties, ColorUtil.YELLOW.getColorIndex()));
    public static final DeferredItem<Item> LIME_CHROMA = ITEMS.registerItem("lime_chroma",
            properties -> new ChromaItem(properties, ColorUtil.LIME.getColorIndex()));
    public static final DeferredItem<Item> GREEN_CHROMA = ITEMS.registerItem("green_chroma",
            properties -> new ChromaItem(properties, ColorUtil.GREEN.getColorIndex()));
    public static final DeferredItem<Item> CYAN_CHROMA = ITEMS.registerItem("cyan_chroma",
            properties -> new ChromaItem(properties, ColorUtil.CYAN.getColorIndex()));
    public static final DeferredItem<Item> LIGHT_BLUE_CHROMA = ITEMS.registerItem("light_blue_chroma",
            properties -> new ChromaItem(properties, ColorUtil.LIGHT_BLUE.getColorIndex()));
    public static final DeferredItem<Item> BLUE_CHROMA = ITEMS.registerItem("blue_chroma",
            properties -> new ChromaItem(properties, ColorUtil.BLUE.getColorIndex()));
    public static final DeferredItem<Item> PURPLE_CHROMA = ITEMS.registerItem("purple_chroma",
            properties -> new ChromaItem(properties, ColorUtil.PURPLE.getColorIndex()));
    public static final DeferredItem<Item> MAGENTA_CHROMA = ITEMS.registerItem("magenta_chroma",
            properties -> new ChromaItem(properties, ColorUtil.MAGENTA.getColorIndex()));
    public static final DeferredItem<Item> PINK_CHROMA = ITEMS.registerItem("pink_chroma",
            properties -> new ChromaItem(properties, ColorUtil.PINK.getColorIndex()));

    public static final DeferredItem<Item> STRAWBERRY = ITEMS.registerItem("strawberry",
            properties -> new BlockItem(ModBlocks.STRAWBERRY_BUSH.get(), properties.food(ModFoods.STRAWBERRY)));

    public static final DeferredItem<Item> GEM_SHARDS = ITEMS.registerItem("gem_shards",
            properties -> new Item(properties.stacksTo(16)){
                @Override
                public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
                    builder.accept(Component.translatable("tooltip.gemcolony.gem_shards.tooltip"));
                    super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> GEM_SEED = ITEMS.registerItem("gem_seed",
            properties -> new Item(properties.stacksTo(16)));

    //TOOLS AND WEAPONS
    public static final DeferredItem<Item> CHROMA_SWORD = ITEMS.registerItem("chroma_sword",
            properties -> new Item(properties.sword(ModToolTiers.CHROMA, 3, -2.4f)));
    public static final DeferredItem<Item> CHROMA_PICKAXE = ITEMS.registerItem("chroma_pickaxe",
            properties -> new Item(properties.pickaxe(ModToolTiers.CHROMA, 1, -2.8f)));
    public static final DeferredItem<Item> CHROMA_SHOVEL = ITEMS.registerItem("chroma_shovel",
            properties -> new ShovelItem(ModToolTiers.CHROMA, 1.5f, -3f, properties));
    public static final DeferredItem<Item> CHROMA_AXE = ITEMS.registerItem("chroma_axe",
            properties -> new AxeItem(ModToolTiers.CHROMA, 6f, -3.2f, properties));
    public static final DeferredItem<Item> CHROMA_HOE = ITEMS.registerItem("chroma_hoe",
            properties -> new HoeItem(ModToolTiers.CHROMA, 0, -3f, properties));
    public static final DeferredItem<Item> CHROMA_SPEAR = ITEMS.registerItem("chroma_spear",
            properties -> new Item(properties.spear(ModToolTiers.CHROMA, 0.95f, 0.7f, 0.7f, 3.5f, 13f, 8.5f, 5.1f, 13.37f, 4.67f)));

    //ARMOR
    public static final DeferredItem<Item> CHROMA_HELMET = ITEMS.registerItem("chroma_helmet",
            properties -> new Item(properties.humanoidArmor(ModArmorMaterials.CHROMA_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> CHROMA_CHESTPLATE = ITEMS.registerItem("chroma_chestplate",
            properties -> new Item(properties.humanoidArmor(ModArmorMaterials.CHROMA_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> CHROMA_LEGGINGS = ITEMS.registerItem("chroma_leggings",
            properties -> new Item(properties.humanoidArmor(ModArmorMaterials.CHROMA_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> CHROMA_BOOTS = ITEMS.registerItem("chroma_boots",
            properties -> new Item(properties.humanoidArmor(ModArmorMaterials.CHROMA_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> CHROMA_HORSE_ARMOR = ITEMS.registerItem("chroma_horse_armor",
            properties -> new Item(properties.horseArmor(ModArmorMaterials.CHROMA_ARMOR_MATERIAL)));

    //CUSTOM
    //ESSENCE BOTTLES
    public static final DeferredItem<Item> PINK_ESSENCE_BOTTLE = ITEMS.registerItem("pink_essence_bottle",
            properties -> new EssenceBottle(properties.stacksTo(1), EssenceType.PINK));
    public static final DeferredItem<Item> BLUE_ESSENCE_BOTTLE = ITEMS.registerItem("blue_essence_bottle",
            properties -> new EssenceBottle(properties.stacksTo(1), EssenceType.BLUE));
    public static final DeferredItem<Item> YELLOW_ESSENCE_BOTTLE = ITEMS.registerItem("yellow_essence_bottle",
            properties -> new EssenceBottle(properties.stacksTo(1), EssenceType.YELLOW));
    public static final DeferredItem<Item> WHITE_ESSENCE_BOTTLE = ITEMS.registerItem("white_essence_bottle",
            properties -> new EssenceBottle(properties.stacksTo(1), EssenceType.WHITE));

    //GEMS
    public static final DeferredItem<Item> PEBBLE_GEM = ITEMS.registerItem("pebble_gem",
            properties -> new GemItem(properties, "Pebble", ModEntities.PEBBLE.get()));

    public static final DeferredItem<Item> PERIDOT_GEM = ITEMS.registerItem("peridot_gem",
            properties -> new GemItem(properties, "Peridot", ModEntities.PERIDOT.get()));

    public static final DeferredItem<Item> RUBY_GEM = ITEMS.registerItem("ruby_gem",
            properties -> new GemItem(properties, "Ruby", ModEntities.PERIDOT.get()));
    public static final DeferredItem<Item> SAPPHIRE_GEM = ITEMS.registerItem("sapphire_gem",
            properties -> new GemItem(properties, "Sapphire", ModEntities.PERIDOT.get()));

    public static final DeferredItem<Item> CHROMA_BOW = ITEMS.registerItem("chroma_bow",
            properties -> new BowItem(properties.durability(600)));

    public static final DeferredItem<Item> CHROMA_SEED = ITEMS.registerItem("chroma_seed",
            properties -> new BlockItem(ModBlocks.CHROMA_CROP.get(), properties));

    public static final DeferredItem<Item> LOVE_LIKE_YOU_MUSIC_DISC = ITEMS.registerItem("love_like_you_music_disc",
            properties -> new Item(properties.jukeboxPlayable(ModSounds.LOVE_LIKE_YOU_KEY).rarity(Rarity.EPIC).stacksTo(1)));
    public static final DeferredItem<Item> ITS_OVER_ISNT_IT_MUSIC_DISC = ITEMS.registerItem("its_over_isnt_it_music_disc",
            properties -> new Item(properties.jukeboxPlayable(ModSounds.ITS_OVER_ISNT_IT_KEY).rarity(Rarity.RARE).stacksTo(1)));

    //Buckets
    public static final DeferredItem<BucketItem> BLUE_ESSENCE_BUCKET = ITEMS.registerItem("blue_essence_bucket",
            props -> new BucketItem(ModFluids.BLUE_ESSENCE.get(), props.stacksTo(1)));
    public static final DeferredItem<BucketItem> YELLOW_ESSENCE_BUCKET = ITEMS.registerItem("yellow_essence_bucket",
            props -> new BucketItem(ModFluids.YELLOW_ESSENCE.get(), props.stacksTo(1)));
    public static final DeferredItem<BucketItem> WHITE_ESSENCE_BUCKET = ITEMS.registerItem("white_essence_bucket",
            props -> new BucketItem(ModFluids.WHITE_ESSENCE.get(), props.stacksTo(1)));
    public static final DeferredItem<BucketItem> PINK_ESSENCE_BUCKET = ITEMS.registerItem("pink_essence_bucket",
            props -> new BucketItem(ModFluids.PINK_ESSENCE.get(), props.stacksTo(1)));

    //get resource key for item tags
    public static ResourceKey<Item> getRK(DeferredItem deferredItem){
        return BuiltInRegistries.ITEM.getResourceKey(deferredItem.asItem()).get();
    }


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
