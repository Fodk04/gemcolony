package com.fodk.gemcolony.creativemodtab;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GemColony.MOD_ID);

    public static final Supplier<CreativeModeTab> GEM_INGREDIENTS_TAB = CREATIVE_MODE_TABS.register("gem_ingredients_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.WHITE_CHROMA.get()))
                    .title(Component.translatable("creativetab.gemcolony.gem_ingredients"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.WHITE_CHROMA);
                        output.accept(ModItems.LIGHT_GRAY_CHROMA);
                        output.accept(ModItems.GRAY_CHROMA);
                        output.accept(ModItems.BLACK_CHROMA);
                        output.accept(ModItems.BROWN_CHROMA);
                        output.accept(ModItems.RED_CHROMA);
                        output.accept(ModItems.ORANGE_CHROMA);
                        output.accept(ModItems.YELLOW_CHROMA);
                        output.accept(ModItems.LIME_CHROMA);
                        output.accept(ModItems.GREEN_CHROMA);
                        output.accept(ModItems.CYAN_CHROMA);
                        output.accept(ModItems.LIGHT_BLUE_CHROMA);
                        output.accept(ModItems.BLUE_CHROMA);
                        output.accept(ModItems.PURPLE_CHROMA);
                        output.accept(ModItems.MAGENTA_CHROMA);
                        output.accept(ModItems.PINK_CHROMA);

                        output.accept(ModItems.CHROMA_SEED);

                        output.accept(ModItems.GEM_SHARDS);

                        output.accept(ModItems.PINK_ESSENCE_BOTTLE);
                        output.accept(ModItems.BLUE_ESSENCE_BOTTLE);
                        output.accept(ModItems.YELLOW_ESSENCE_BOTTLE);
                        output.accept(ModItems.WHITE_ESSENCE_BOTTLE);

                        output.accept(ModItems.PEBBLE_GEM);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> GEM_BLOCKS_TAB = CREATIVE_MODE_TABS.register("gem_blocks_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.CHROMA_BLOCK.get()))
                    .title(Component.translatable("creativetab.gemcolony.gem_blocks"))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gem_ingredients_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.CHROMA_BLOCK);
                        output.accept(ModBlocks.CHROMA_STAIRS);
                        output.accept(ModBlocks.CHROMA_SLAB);
                        output.accept(ModBlocks.CHROMA_PRESSURE_PLATE);
                        output.accept(ModBlocks.CHROMA_BUTTON);
                        output.accept(ModBlocks.CHROMA_FENCE);
                        output.accept(ModBlocks.CHROMA_FENCE_GATE);
                        output.accept(ModBlocks.CHROMA_WALL);
                        output.accept(ModBlocks.CHROMA_DOOR);
                        output.accept(ModBlocks.CHROMA_TRAPDOOR);

                        output.accept(ModBlocks.DRAINED_STONE);
                        output.accept(ModBlocks.DESTABILIZER_WALL_GENERATOR);


                        output.accept(ModBlocks.WHITE_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.LIGHT_GRAY_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.GRAY_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.BLACK_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.BROWN_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.RED_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.ORANGE_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.YELLOW_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.LIME_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.GREEN_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.CYAN_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.LIGHT_BLUE_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.BLUE_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.PURPLE_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.MAGENTA_CHROMA_DEPOSIT);
                        output.accept(ModBlocks.PINK_CHROMA_DEPOSIT);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> GEM_FOODS_TAB = CREATIVE_MODE_TABS.register("gem_foods_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.STRAWBERRY.get()))
                    .title(Component.translatable("creativetab.gemcolony.gem_foods"))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gem_blocks_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.STRAWBERRY);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> GEM_TOOLS_TAB = CREATIVE_MODE_TABS.register("gem_tools_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.CHROMA_SWORD.get()))
                    .title(Component.translatable("creativetab.gemcolony.gem_tools"))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gem_foods_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.CHROMA_SWORD);
                        output.accept(ModItems.CHROMA_PICKAXE);
                        output.accept(ModItems.CHROMA_SHOVEL);
                        output.accept(ModItems.CHROMA_AXE);
                        output.accept(ModItems.CHROMA_HOE);
                        output.accept(ModItems.CHROMA_SPEAR);

                        output.accept(ModItems.CHROMA_BOW);

                        output.accept(ModItems.CHROMA_HELMET);
                        output.accept(ModItems.CHROMA_CHESTPLATE);
                        output.accept(ModItems.CHROMA_LEGGINGS);
                        output.accept(ModItems.CHROMA_BOOTS);

                        output.accept(ModItems.CHROMA_HORSE_ARMOR);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> GEM_SONGS_TAB = CREATIVE_MODE_TABS.register("gem_songs_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.LOVE_LIKE_YOU_MUSIC_DISC.get()))
                    .title(Component.translatable("creativetab.gemcolony.gem_songs"))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gem_tools_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.LOVE_LIKE_YOU_MUSIC_DISC);
                        output.accept(ModItems.ITS_OVER_ISNT_IT_MUSIC_DISC);
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
