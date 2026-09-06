package com.fodk.gemcolony.block;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.custom.ChromaCrop;
import com.fodk.gemcolony.block.custom.ChromaDeposit;
import com.fodk.gemcolony.block.custom.DestabilizerWallGenerator;
import com.fodk.gemcolony.block.custom.StrawberryBushBlock;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.util.ColorToIndex;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(GemColony.MOD_ID);

    public static final DeferredBlock<Block> CHROMA_BLOCK = registerBlock("chroma_block",
            properties -> new Block(properties
                    .strength(2)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_STAIRS = registerBlock("chroma_stairs",
            properties -> new StairBlock(CHROMA_BLOCK.get().defaultBlockState(), properties
                    .strength(2)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_SLAB = registerBlock("chroma_slab",
            properties -> new SlabBlock(properties
                    .strength(2)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_PRESSURE_PLATE = registerBlock("chroma_pressure_plate",
            properties -> new PressurePlateBlock(BlockSetType.GOLD, properties
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(0.5F)
                    .requiresCorrectToolForDrops()
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_BUTTON = registerBlock("chroma_button",
            properties -> new ButtonBlock(BlockSetType.GOLD, 30, properties
                    .noCollision()
                    .strength(0.5F)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_FENCE = registerBlock("chroma_fence",
            properties -> new FenceBlock(properties
                    .strength(2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_FENCE_GATE = registerBlock("chroma_fence_gate",
            properties -> new FenceGateBlock(WoodType.ACACIA, properties
                    .strength(2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_WALL = registerBlock("chroma_wall",
            properties -> new WallBlock(properties
                    .strength(2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
            ));

    public static final DeferredBlock<Block> CHROMA_DOOR = registerBlock("chroma_door",
            properties -> new DoorBlock(BlockSetType.GOLD, properties
                    .strength(2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .noOcclusion()
            ));

    public static final DeferredBlock<Block> CHROMA_TRAPDOOR = registerBlock("chroma_trapdoor",
            properties -> new TrapDoorBlock(BlockSetType.GOLD, properties
                    .strength(2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .noOcclusion()
            ));

    public static final DeferredBlock<Block> DRAINED_STONE = registerBlock("drained_stone",
            properties -> new Block(properties
                    .strength(2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
            ));

    public static final DeferredBlock<Block> DESTABILIZER_WALL_GENERATOR = registerBlock("destabilizer_wall_generator",
            properties -> new DestabilizerWallGenerator(properties
                    .strength(1)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.GLASS)
                    .lightLevel(state -> state.getValue(DestabilizerWallGenerator.ACTIVE) ? 10 : 0)
            ), Component.translatable("tooltip.gemcolony.destabilizer_wall_generator.tooltip"));

    //CHROMA DEPOSITS
    public static final DeferredBlock<Block> WHITE_CHROMA_DEPOSIT = registerBlock("white_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.WHITE.getColorIndex()));

    public static final DeferredBlock<Block> LIGHT_GRAY_CHROMA_DEPOSIT = registerBlock("light_gray_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.LIGHT_GRAY.getColorIndex()));

    public static final DeferredBlock<Block> GRAY_CHROMA_DEPOSIT = registerBlock("gray_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.GRAY.getColorIndex()));

    public static final DeferredBlock<Block> BLACK_CHROMA_DEPOSIT = registerBlock("black_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.BLACK.getColorIndex()));

    public static final DeferredBlock<Block> BROWN_CHROMA_DEPOSIT = registerBlock("brown_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.BROWN.getColorIndex()));

    public static final DeferredBlock<Block> RED_CHROMA_DEPOSIT = registerBlock("red_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.RED.getColorIndex()));

    public static final DeferredBlock<Block> ORANGE_CHROMA_DEPOSIT = registerBlock("orange_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.ORANGE.getColorIndex()));

    public static final DeferredBlock<Block> YELLOW_CHROMA_DEPOSIT = registerBlock("yellow_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.YELLOW.getColorIndex()));

    public static final DeferredBlock<Block> LIME_CHROMA_DEPOSIT = registerBlock("lime_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.LIME.getColorIndex()));

    public static final DeferredBlock<Block> GREEN_CHROMA_DEPOSIT = registerBlock("green_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.GREEN.getColorIndex()));

    public static final DeferredBlock<Block> CYAN_CHROMA_DEPOSIT = registerBlock("cyan_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.CYAN.getColorIndex()));

    public static final DeferredBlock<Block> LIGHT_BLUE_CHROMA_DEPOSIT = registerBlock("light_blue_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.LIGHT_BLUE.getColorIndex()));

    public static final DeferredBlock<Block> BLUE_CHROMA_DEPOSIT = registerBlock("blue_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.BLUE.getColorIndex()));

    public static final DeferredBlock<Block> PURPLE_CHROMA_DEPOSIT = registerBlock("purple_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.PURPLE.getColorIndex()));

    public static final DeferredBlock<Block> MAGENTA_CHROMA_DEPOSIT = registerBlock("magenta_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.MAGENTA.getColorIndex()));

    public static final DeferredBlock<Block> PINK_CHROMA_DEPOSIT = registerBlock("pink_chroma_deposit",
            properties -> new ChromaDeposit(properties, ColorToIndex.PINK.getColorIndex()));

    //chroma crop
    public static final DeferredBlock<Block> CHROMA_CROP = BLOCKS.registerBlock("chroma_crop",
            properties -> new ChromaCrop(properties));

    public static final DeferredBlock<Block> STRAWBERRY_BUSH = BLOCKS.registerBlock("strawberry_bush",
            properties -> new StrawberryBushBlock(properties));

    //get resource key for tags
    public static ResourceKey<Block> getRK(DeferredBlock deferredBlock){
        return BuiltInRegistries.BLOCK.getResourceKey((Block) deferredBlock.get()).get();
    }


    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block, Component... components){
        ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()){
            @Override
            public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
                for(var component : components){
                    builder.accept(component);
                }
                super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
            }
        });
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function){
        DeferredBlock<T> blockToReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, blockToReturn);
        return blockToReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function, Component... components){
        DeferredBlock<T> blockToReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, blockToReturn, components);
        return blockToReturn;
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
