package com.fodk.gemcolony.block.entity.savedata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ConstructionSiteData(
        String assemblyId,
        int currentComponent,
        int currentStage
) {
    public static final Codec<ConstructionSiteData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("Assembly").forGetter(ConstructionSiteData::assemblyId),
                    Codec.INT.fieldOf("CurrentComponent").forGetter(ConstructionSiteData::currentComponent),
                    Codec.INT.fieldOf("CurrentStage").forGetter(ConstructionSiteData::currentStage)
            ).apply(instance, ConstructionSiteData::new));
}