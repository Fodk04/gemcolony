package com.fodk.gemcolony.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record FacetRegion(ResourceKey<Level> dimension, int regionX, int regionZ, int gemCount) {

    public static final Codec<FacetRegion> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(FacetRegion::dimension),
            Codec.INT.fieldOf("regionX").forGetter(FacetRegion::regionX),
            Codec.INT.fieldOf("regionZ").forGetter(FacetRegion::regionZ),
            Codec.INT.fieldOf("gemCount").forGetter(FacetRegion::gemCount)
    ).apply(instance, FacetRegion::new));

    public FacetRegion withIncrementedCount() {
        return new FacetRegion(dimension, regionX, regionZ, gemCount + 1);
    }
}
