package com.fodk.gemcolony.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record FacetRegion(ResourceKey<Level> dimension, int regionX, int regionZ, int cutGemCount, int cabochonGemCount) {

    public static final Codec<FacetRegion> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(FacetRegion::dimension),
            Codec.INT.fieldOf("regionX").forGetter(FacetRegion::regionX),
            Codec.INT.fieldOf("regionZ").forGetter(FacetRegion::regionZ),
            Codec.INT.fieldOf("cutGemCount").forGetter(FacetRegion::cutGemCount),
            Codec.INT.fieldOf("cabochonGemCount").forGetter(FacetRegion::cutGemCount))
            .apply(instance, FacetRegion::new));

    public FacetRegion withIncrementedCount(boolean isCut) {
        if(isCut){
            return new FacetRegion(dimension, regionX, regionZ, cutGemCount + 1, cabochonGemCount);
        }else{
            return new FacetRegion(dimension, regionX, regionZ, cutGemCount, cabochonGemCount + 1);
        }
    }

    public static String computeCutString(FacetRegistryData.FacetAssignment facetAssignment, boolean isCut) {
        if(isCut){
            int index0 = facetAssignment.cutGemCountInRegion() - 1;
            int letterIndex = index0 / 9;
            int number = (index0 % 9) + 1;
            char letter = (char) ('A' + letterIndex);
            return number + "X" + letter;
        }else{
            return String.valueOf(facetAssignment.cabochonGemCountInRegion());
        }
    }
}
