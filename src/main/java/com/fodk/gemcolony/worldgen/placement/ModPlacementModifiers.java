package com.fodk.gemcolony.worldgen.placement;

import com.fodk.gemcolony.GemColony;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPlacementModifiers {

    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS =
            DeferredRegister.create(
                    Registries.PLACEMENT_MODIFIER_TYPE,
                    GemColony.MOD_ID
            );

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<SurfaceClusterPlacement>> NEARBY_SURFACE = PLACEMENT_MODIFIERS.register(
            "nearby_surface",
            () -> new PlacementModifierType<SurfaceClusterPlacement>() {
                @Override
                public MapCodec<SurfaceClusterPlacement> codec() {
                    return SurfaceClusterPlacement.CODEC;
                }
            }
    );

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<OceanFloorClusterPlacement>> OCEAN_FLOOR_CLUSTER = PLACEMENT_MODIFIERS.register(
            "ocean_floor_cluster",
            () -> new PlacementModifierType<OceanFloorClusterPlacement>() {
                @Override
                public MapCodec<OceanFloorClusterPlacement> codec() {
                    return OceanFloorClusterPlacement.CODEC;
                }
            }
    );

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<CaveSurfacePlacement>> CAVE_SURFACE = PLACEMENT_MODIFIERS.register(
            "cave_surface",
            () -> new PlacementModifierType<CaveSurfacePlacement>() {
                @Override
                public MapCodec<CaveSurfacePlacement> codec() {
                    return CaveSurfacePlacement.CODEC;
                }
            }
    );

    public static void register(IEventBus eventBus) {
        PLACEMENT_MODIFIERS.register(eventBus);
    }
}
