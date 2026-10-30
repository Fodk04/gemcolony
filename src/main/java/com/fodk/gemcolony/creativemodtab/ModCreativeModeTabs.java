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
                        output.accept(ModItems.GEM_SEED);

                        output.accept(ModItems.PINK_ESSENCE_BOTTLE);
                        output.accept(ModItems.BLUE_ESSENCE_BOTTLE);
                        output.accept(ModItems.YELLOW_ESSENCE_BOTTLE);
                        output.accept(ModItems.WHITE_ESSENCE_BOTTLE);

                        output.accept(ModItems.PINK_ESSENCE_BUCKET);
                        output.accept(ModItems.BLUE_ESSENCE_BUCKET);
                        output.accept(ModItems.YELLOW_ESSENCE_BUCKET);
                        output.accept(ModItems.WHITE_ESSENCE_BUCKET);

                        output.accept(ModItems.FUSE);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> GEM_BLOCKS_TAB = CREATIVE_MODE_TABS.register("gem_blocks_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.WHITE_CHROMA_BLOCK.get()))
                    .title(Component.translatable("creativetab.gemcolony.gem_blocks"))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gem_ingredients_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.DRAINED_STONE);

                        // CHROMA DEPOSITS
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

                        // WHITE CHROMA
                        output.accept(ModBlocks.WHITE_CHROMA_BLOCK);
                        output.accept(ModBlocks.WHITE_CHROMA_STAIRS);
                        output.accept(ModBlocks.WHITE_CHROMA_SLAB);
                        output.accept(ModBlocks.WHITE_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.WHITE_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.WHITE_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.WHITE_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.WHITE_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.WHITE_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.WHITE_CHROMA_PILLAR);

                        // LIGHT GRAY CHROMA
                        output.accept(ModBlocks.LIGHT_GRAY_CHROMA_BLOCK);
                        output.accept(ModBlocks.LIGHT_GRAY_CHROMA_STAIRS);
                        output.accept(ModBlocks.LIGHT_GRAY_CHROMA_SLAB);
                        output.accept(ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.LIGHT_GRAY_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.LIGHT_GRAY_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.LIGHT_GRAY_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.LIGHT_GRAY_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.LIGHT_GRAY_CHROMA_PILLAR);

                        // GRAY CHROMA
                        output.accept(ModBlocks.GRAY_CHROMA_BLOCK);
                        output.accept(ModBlocks.GRAY_CHROMA_STAIRS);
                        output.accept(ModBlocks.GRAY_CHROMA_SLAB);
                        output.accept(ModBlocks.GRAY_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.GRAY_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.GRAY_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.GRAY_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.GRAY_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.GRAY_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.GRAY_CHROMA_PILLAR);

                        // BLACK CHROMA
                        output.accept(ModBlocks.BLACK_CHROMA_BLOCK);
                        output.accept(ModBlocks.BLACK_CHROMA_STAIRS);
                        output.accept(ModBlocks.BLACK_CHROMA_SLAB);
                        output.accept(ModBlocks.BLACK_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.BLACK_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.BLACK_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.BLACK_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.BLACK_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.BLACK_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.BLACK_CHROMA_PILLAR);

                        // BROWN CHROMA
                        output.accept(ModBlocks.BROWN_CHROMA_BLOCK);
                        output.accept(ModBlocks.BROWN_CHROMA_STAIRS);
                        output.accept(ModBlocks.BROWN_CHROMA_SLAB);
                        output.accept(ModBlocks.BROWN_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.BROWN_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.BROWN_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.BROWN_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.BROWN_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.BROWN_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.BROWN_CHROMA_PILLAR);

                        // RED CHROMA
                        output.accept(ModBlocks.RED_CHROMA_BLOCK);
                        output.accept(ModBlocks.RED_CHROMA_STAIRS);
                        output.accept(ModBlocks.RED_CHROMA_SLAB);
                        output.accept(ModBlocks.RED_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.RED_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.RED_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.RED_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.RED_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.RED_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.RED_CHROMA_PILLAR);

                        // ORANGE CHROMA
                        output.accept(ModBlocks.ORANGE_CHROMA_BLOCK);
                        output.accept(ModBlocks.ORANGE_CHROMA_STAIRS);
                        output.accept(ModBlocks.ORANGE_CHROMA_SLAB);
                        output.accept(ModBlocks.ORANGE_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.ORANGE_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.ORANGE_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.ORANGE_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.ORANGE_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.ORANGE_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.ORANGE_CHROMA_PILLAR);

                        // YELLOW CHROMA
                        output.accept(ModBlocks.YELLOW_CHROMA_BLOCK);
                        output.accept(ModBlocks.YELLOW_CHROMA_STAIRS);
                        output.accept(ModBlocks.YELLOW_CHROMA_SLAB);
                        output.accept(ModBlocks.YELLOW_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.YELLOW_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.YELLOW_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.YELLOW_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.YELLOW_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.YELLOW_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.YELLOW_CHROMA_PILLAR);

                        // LIME CHROMA
                        output.accept(ModBlocks.LIME_CHROMA_BLOCK);
                        output.accept(ModBlocks.LIME_CHROMA_STAIRS);
                        output.accept(ModBlocks.LIME_CHROMA_SLAB);
                        output.accept(ModBlocks.LIME_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.LIME_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.LIME_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.LIME_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.LIME_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.LIME_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.LIME_CHROMA_PILLAR);

                        // GREEN CHROMA
                        output.accept(ModBlocks.GREEN_CHROMA_BLOCK);
                        output.accept(ModBlocks.GREEN_CHROMA_STAIRS);
                        output.accept(ModBlocks.GREEN_CHROMA_SLAB);
                        output.accept(ModBlocks.GREEN_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.GREEN_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.GREEN_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.GREEN_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.GREEN_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.GREEN_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.GREEN_CHROMA_PILLAR);

                        // CYAN CHROMA
                        output.accept(ModBlocks.CYAN_CHROMA_BLOCK);
                        output.accept(ModBlocks.CYAN_CHROMA_STAIRS);
                        output.accept(ModBlocks.CYAN_CHROMA_SLAB);
                        output.accept(ModBlocks.CYAN_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.CYAN_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.CYAN_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.CYAN_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.CYAN_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.CYAN_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.CYAN_CHROMA_PILLAR);

                        // LIGHT BLUE CHROMA
                        output.accept(ModBlocks.LIGHT_BLUE_CHROMA_BLOCK);
                        output.accept(ModBlocks.LIGHT_BLUE_CHROMA_STAIRS);
                        output.accept(ModBlocks.LIGHT_BLUE_CHROMA_SLAB);
                        output.accept(ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.LIGHT_BLUE_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.LIGHT_BLUE_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.LIGHT_BLUE_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.LIGHT_BLUE_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.LIGHT_BLUE_CHROMA_PILLAR);

                        // BLUE CHROMA
                        output.accept(ModBlocks.BLUE_CHROMA_BLOCK);
                        output.accept(ModBlocks.BLUE_CHROMA_STAIRS);
                        output.accept(ModBlocks.BLUE_CHROMA_SLAB);
                        output.accept(ModBlocks.BLUE_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.BLUE_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.BLUE_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.BLUE_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.BLUE_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.BLUE_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.BLUE_CHROMA_PILLAR);

                        // PURPLE CHROMA
                        output.accept(ModBlocks.PURPLE_CHROMA_BLOCK);
                        output.accept(ModBlocks.PURPLE_CHROMA_STAIRS);
                        output.accept(ModBlocks.PURPLE_CHROMA_SLAB);
                        output.accept(ModBlocks.PURPLE_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.PURPLE_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.PURPLE_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.PURPLE_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.PURPLE_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.PURPLE_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.PURPLE_CHROMA_PILLAR);

                        // MAGENTA CHROMA
                        output.accept(ModBlocks.MAGENTA_CHROMA_BLOCK);
                        output.accept(ModBlocks.MAGENTA_CHROMA_STAIRS);
                        output.accept(ModBlocks.MAGENTA_CHROMA_SLAB);
                        output.accept(ModBlocks.MAGENTA_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.MAGENTA_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.MAGENTA_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.MAGENTA_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.MAGENTA_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.MAGENTA_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.MAGENTA_CHROMA_PILLAR);

                        // PINK CHROMA
                        output.accept(ModBlocks.PINK_CHROMA_BLOCK);
                        output.accept(ModBlocks.PINK_CHROMA_STAIRS);
                        output.accept(ModBlocks.PINK_CHROMA_SLAB);
                        output.accept(ModBlocks.PINK_POLISHED_CHROMA_BLOCK);
                        output.accept(ModBlocks.PINK_POLISHED_CHROMA_STAIRS);
                        output.accept(ModBlocks.PINK_POLISHED_CHROMA_SLAB);
                        output.accept(ModBlocks.PINK_SMOOTH_CHROMA_BLOCK);
                        output.accept(ModBlocks.PINK_SMOOTH_CHROMA_STAIRS);
                        output.accept(ModBlocks.PINK_SMOOTH_CHROMA_SLAB);
                        output.accept(ModBlocks.PINK_CHROMA_PILLAR);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> GEMSTONES_TAB = CREATIVE_MODE_TABS.register("gemstones_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.PEBBLE_GEM.get()))
                    .title(Component.translatable("creativetab.gemcolony.gemstones"))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gem_blocks_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.PEBBLE_GEM);
                        output.accept(ModItems.SHALE_GEM);
                        output.accept(ModItems.MICA_GEM);
                        output.accept(ModItems.NACRE_GEM);

                        output.accept(ModItems.PERIDOT_GEM);

                        output.accept(ModItems.MILKY_QUARTZ_GEM);
                        output.accept(ModItems.PHANTOM_QUARTZ_GEM);
                        output.accept(ModItems.FLINT_QUARTZ_GEM);
                        output.accept(ModItems.ONYX_QUARTZ_GEM);
                        output.accept(ModItems.SMOKY_QUARTZ_GEM);
                        output.accept(ModItems.CARNELIAN_QUARTZ_GEM);
                        output.accept(ModItems.CHERT_QUARTZ_GEM);
                        output.accept(ModItems.CITRINE_QUARTZ_GEM);
                        output.accept(ModItems.PRASEOLITE_QUARTZ_GEM);
                        output.accept(ModItems.AVENTURINE_QUARTZ_GEM);
                        output.accept(ModItems.ANGEL_AURA_QUARTZ_GEM);
                        output.accept(ModItems.DUMORTIERITE_QUARTZ_GEM);
                        output.accept(ModItems.BLUE_QUARTZ_GEM);
                        output.accept(ModItems.AMETHYST_QUARTZ_GEM);
                        output.accept(ModItems.CHERRY_QUARTZ_GEM);
                        output.accept(ModItems.ROSE_QUARTZ_GEM);

                        output.accept(ModItems.SPIDERWEB_JASPER_GEM);
                        output.accept(ModItems.PORCELAIN_JASPER_GEM);
                        output.accept(ModItems.ZEBRA_JASPER_GEM);
                        output.accept(ModItems.CHRYSANTHEMUM_JASPER_GEM);
                        output.accept(ModItems.SNAKESKIN_JASPER_GEM);
                        output.accept(ModItems.BIGGS_JASPER_GEM);
                        output.accept(ModItems.NOREENA_JASPER_GEM);
                        output.accept(ModItems.HONEY_JASPER_GEM);
                        output.accept(ModItems.MORRISONITE_JASPER_GEM);
                        output.accept(ModItems.RHYOLITE_JASPER_GEM);
                        output.accept(ModItems.KAMBABA_JASPER_GEM);
                        output.accept(ModItems.OCEAN_JASPER_GEM);
                        output.accept(ModItems.SEA_SEDIMENT_JASPER_GEM);
                        output.accept(ModItems.ROYAL_PLUME_JASPER_GEM);
                        output.accept(ModItems.MOOKITE_JASPER_GEM);
                        output.accept(ModItems.PEACH_PETAL_JASPER_GEM);

                        output.accept(ModItems.WHITE_PEARL_GEM);
                        output.accept(ModItems.SPECTER_PEARL_GEM);
                        output.accept(ModItems.SILVER_PEARL_GEM);
                        output.accept(ModItems.ACKEE_PEARL_GEM);
                        output.accept(ModItems.CHOCOLATE_PEARL_GEM);
                        output.accept(ModItems.CHERRY_PEARL_GEM);
                        output.accept(ModItems.APRICOT_PEARL_GEM);
                        output.accept(ModItems.YELLOW_PEARL_GEM);
                        output.accept(ModItems.OLIVE_PEARL_GEM);
                        output.accept(ModItems.FOREST_PEARL_GEM);
                        output.accept(ModItems.SKY_PEARL_GEM);
                        output.accept(ModItems.BLUE_PEARL_GEM);
                        output.accept(ModItems.OCEAN_PEARL_GEM);
                        output.accept(ModItems.PLUM_PEARL_GEM);
                        output.accept(ModItems.FUCHSIA_PEARL_GEM);
                        output.accept(ModItems.PINK_PEARL_GEM);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> GEM_TOOLS_TAB = CREATIVE_MODE_TABS.register("gem_tools_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.CHROMA_SWORD.get()))
                    .title(Component.translatable("creativetab.gemcolony.gem_tools"))
                    .withTabsBefore(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gemstones_tab"))
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
