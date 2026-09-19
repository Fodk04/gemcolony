package com.fodk.gemcolony.util;

import com.fodk.gemcolony.entity.custom.GemConditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class GemEnvironmentUtil {

    public static float getTemperature(Level level, BlockPos pos) {
        return level.getBiome(pos).value().getBaseTemperature();
    }

    public static float getHumidity(Level level, BlockPos pos) {
        return level.getBiome(pos).value().getModifiedClimateSettings().downfall();
    }

    public static float gaussianScore(float value, float optimal, float sigma) {
        float difference = value - optimal;

        return (float) Math.exp(-(difference * difference) / (2.0f * sigma * sigma));
    }

    public static float getEnvironmentalScore(Level level, BlockPos pos, GemConditions conditions) {
        float temperature = getTemperature(level, pos);
        float humidity = getHumidity(level, pos);

        float temperatureScore = gaussianScore(
                temperature,
                conditions.optimalTemperature(),
                conditions.temperatureSigma()
        );

        float humidityScore = gaussianScore(
                humidity,
                conditions.optimalHumidity(),
                conditions.humiditySigma()
        );

        return temperatureScore * humidityScore;
    }

    public static float getGemScore(Level level, BlockPos pos, GemConditions conditions) {
        float environmentalScore = getEnvironmentalScore(level, pos, conditions);

        return environmentalScore * conditions.weight();
    }

    public static float getGemScore(float temperature, float humidity, GemConditions conditions) {
        float temperatureScore = gaussianScore(
                temperature,
                conditions.optimalTemperature(),
                conditions.temperatureSigma()
        );

        float humidityScore = gaussianScore(
                humidity,
                conditions.optimalHumidity(),
                conditions.humiditySigma()
        );

        return temperatureScore
                * humidityScore
                * conditions.weight();
    }
}
