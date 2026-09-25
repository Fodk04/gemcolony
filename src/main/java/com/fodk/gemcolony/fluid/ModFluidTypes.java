package com.fodk.gemcolony.fluid;

import com.fodk.gemcolony.GemColony;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector4f;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class ModFluidTypes {

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, GemColony.MOD_ID);

    public static final DeferredHolder<FluidType, FluidType> BLUE_ESSENCE =
            FLUID_TYPES.register(
                    "blue_essence",
                    () -> new FluidType(
                            FluidType.Properties.create()
                                    .canSwim(true)
                                    .canPushEntity(true)
                                    .canDrown(true)
                                    .canExtinguish(true)
                                    .supportsBoating(true)
                                    .canHydrate(true)
                                    .isWaterLike(true)
                    )
            );

    public static final DeferredHolder<FluidType, FluidType> YELLOW_ESSENCE =
            FLUID_TYPES.register(
                    "yellow_essence",
                    () -> new FluidType(
                            FluidType.Properties.create()
                                    .canSwim(true)
                                    .canPushEntity(true)
                                    .canDrown(true)
                                    .canExtinguish(true)
                                    .supportsBoating(true)
                                    .isWaterLike(true)
                                    .canHydrate(true)
                    )
            );

    public static final DeferredHolder<FluidType, FluidType> WHITE_ESSENCE =
            FLUID_TYPES.register(
                    "white_essence",
                    () -> new FluidType(
                            FluidType.Properties.create()
                                    .canSwim(true)
                                    .canPushEntity(true)
                                    .canDrown(true)
                                    .canExtinguish(true)
                                    .supportsBoating(true)
                                    .isWaterLike(true)
                                    .canHydrate(true)
                    )
            );

    public static final DeferredHolder<FluidType, FluidType> PINK_ESSENCE =
            FLUID_TYPES.register(
                    "pink_essence",
                    () -> new FluidType(
                            FluidType.Properties.create()
                                    .canSwim(true)
                                    .canPushEntity(true)
                                    .canDrown(true)
                                    .canExtinguish(true)
                                    .supportsBoating(true)
                                    .isWaterLike(true)
                                    .canHydrate(true)
                    )
            );

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
