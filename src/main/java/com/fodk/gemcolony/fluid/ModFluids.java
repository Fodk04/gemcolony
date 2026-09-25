package com.fodk.gemcolony.fluid;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, GemColony.MOD_ID);

    // Blue Essence

    public static final DeferredHolder<Fluid, EssenceFluid.Flowing> FLOWING_BLUE_ESSENCE =
            FLUIDS.register(
                    "flowing_blue_essence",
                    () -> new EssenceFluid.Flowing(
                            blueEssenceProperties()
                    )
            );

    public static final DeferredHolder<Fluid, EssenceFluid.Source> BLUE_ESSENCE =
            FLUIDS.register(
                    "blue_essence",
                    () -> new EssenceFluid.Source(
                            blueEssenceProperties()
                    )
            );

    private static BaseFlowingFluid.Properties blueEssenceProperties() {
        return new BaseFlowingFluid.Properties(
                ModFluidTypes.BLUE_ESSENCE,
                BLUE_ESSENCE,
                FLOWING_BLUE_ESSENCE
        ).block(() -> ModBlocks.BLUE_ESSENCE.get()).bucket(() -> ModItems.BLUE_ESSENCE_BUCKET.get());
    }

    // Yellow Essence

    public static final DeferredHolder<Fluid, EssenceFluid.Flowing> FLOWING_YELLOW_ESSENCE =
            FLUIDS.register(
                    "flowing_yellow_essence",
                    () -> new EssenceFluid.Flowing(
                            yellowEssenceProperties()
                    )
            );

    public static final DeferredHolder<Fluid, EssenceFluid.Source> YELLOW_ESSENCE =
            FLUIDS.register(
                    "yellow_essence",
                    () -> new EssenceFluid.Source(
                            yellowEssenceProperties()
                    )
            );

    private static BaseFlowingFluid.Properties yellowEssenceProperties() {
        return new BaseFlowingFluid.Properties(
                ModFluidTypes.YELLOW_ESSENCE,
                YELLOW_ESSENCE,
                FLOWING_YELLOW_ESSENCE
        ).block(() -> ModBlocks.YELLOW_ESSENCE.get()).bucket(() -> ModItems.YELLOW_ESSENCE_BUCKET.get());
    }

    // White Essence

    public static final DeferredHolder<Fluid, EssenceFluid.Flowing> FLOWING_WHITE_ESSENCE =
            FLUIDS.register(
                    "flowing_white_essence",
                    () -> new EssenceFluid.Flowing(
                            whiteEssenceProperties()
                    )
            );

    public static final DeferredHolder<Fluid, EssenceFluid.Source> WHITE_ESSENCE =
            FLUIDS.register(
                    "white_essence",
                    () -> new EssenceFluid.Source(
                            whiteEssenceProperties()
                    )
            );

    private static BaseFlowingFluid.Properties whiteEssenceProperties() {
        return new BaseFlowingFluid.Properties(
                ModFluidTypes.WHITE_ESSENCE,
                WHITE_ESSENCE,
                FLOWING_WHITE_ESSENCE
        ).block(() -> ModBlocks.WHITE_ESSENCE.get()).bucket(() -> ModItems.WHITE_ESSENCE_BUCKET.get());
    }

    // Pink Essence

    public static final DeferredHolder<Fluid, EssenceFluid.Flowing> FLOWING_PINK_ESSENCE =
            FLUIDS.register(
                    "flowing_pink_essence",
                    () -> new EssenceFluid.Flowing(
                            pinkEssenceProperties()
                    )
            );

    public static final DeferredHolder<Fluid, EssenceFluid.Source> PINK_ESSENCE =
            FLUIDS.register(
                    "pink_essence",
                    () -> new EssenceFluid.Source(
                            pinkEssenceProperties()
                    )
            );

    private static BaseFlowingFluid.Properties pinkEssenceProperties() {
        return new BaseFlowingFluid.Properties(
                ModFluidTypes.PINK_ESSENCE,
                PINK_ESSENCE,
                FLOWING_PINK_ESSENCE
        ).block(() -> ModBlocks.PINK_ESSENCE.get()).bucket(() -> ModItems.PINK_ESSENCE_BUCKET.get());
    }

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
