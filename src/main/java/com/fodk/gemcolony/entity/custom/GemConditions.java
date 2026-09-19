package com.fodk.gemcolony.entity.custom;

public record GemConditions(
        String gemId,
        float optimalTemperature,
        float optimalHumidity,
        float temperatureSigma,
        float humiditySigma,
        float weight
) {
}
