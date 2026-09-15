package com.fodk.gemcolony.entity.custom.gem.placement;

import net.minecraft.util.RandomSource;

public enum GemPlacement {
    FOREHEAD,
    BACKHEAD,
    TOP_HEAD,
    EYE_R,
    EYE_L,
    CHEEK_R,
    CHEEK_L,
    EAR_R,
    EAR_L,
    NOSE,
    SHOULDER_R,
    SHOULDER_L,
    ARM_R,
    ARM_L,
    HAND_R,
    HAND_L,
    BACK,
    CHEST,
    NAVEL,
    THIGH_R,
    THIGH_L,
    LEG_R,
    LEG_L;

    public static GemPlacement getRandomGemPlacement() {
        GemPlacement[] placements = values();

        return placements[RandomSource.create().nextInt(placements.length)];
    }
}
