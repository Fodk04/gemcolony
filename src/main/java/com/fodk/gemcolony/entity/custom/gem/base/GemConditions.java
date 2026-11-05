package com.fodk.gemcolony.entity.custom.gem.base;

import net.minecraft.resources.Identifier;

public record GemConditions(
        Identifier id,
        float optimalTemperature,
        float optimalHumidity,
        float temperatureSigma,
        float humiditySigma,
        float weight
) {
}
