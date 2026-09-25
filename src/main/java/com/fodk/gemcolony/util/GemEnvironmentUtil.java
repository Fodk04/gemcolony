package com.fodk.gemcolony.util;

import com.fodk.gemcolony.data.GemAnalysisResult;
import com.fodk.gemcolony.data.GemConditionsRegistry;
import com.fodk.gemcolony.entity.custom.GemConditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public static List<GemAnalysisResult> getTopThreeGemResults(Level level, BlockPos center) {
        float temperatureTotal = 0.0F;
        float humidityTotal = 0.0F;
        int samples = 0;

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {

                BlockPos samplePos = center.offset(x, 0, z);

                temperatureTotal += getTemperature(level, samplePos);
                humidityTotal += getHumidity(level, samplePos);

                samples++;
            }
        }

        float temperature = temperatureTotal / samples;
        float humidity = humidityTotal / samples;

        List<GemAnalysisResult> results = new ArrayList<>();

        float totalScore = 0.0F;

        for (GemConditions conditions : GemConditionsRegistry.ALL) {

            float score = getGemScore(temperature, humidity, conditions);

            results.add(new GemAnalysisResult(conditions.gemId(), score));

            totalScore += score;
        }

        if (totalScore <= 0.0F) {
            return results.subList(0, Math.min(3, results.size()));
        }

        List<GemAnalysisResult> normalizedResults = new ArrayList<>();

        for (GemAnalysisResult result : results) {

            float percentage = result.score() / totalScore * 100.0F;

            normalizedResults.add(new GemAnalysisResult(result.gemId(), percentage));
        }

        normalizedResults.sort(Comparator.comparingDouble(GemAnalysisResult::score).reversed());

        return normalizedResults.subList(0, Math.min(3, normalizedResults.size()));
    }
}
