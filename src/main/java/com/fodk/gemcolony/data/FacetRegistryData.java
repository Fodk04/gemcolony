package com.fodk.gemcolony.data;

import com.fodk.gemcolony.GemColony;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.List;

public class FacetRegistryData extends SavedData {

    public static final Codec<FacetRegistryData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            FacetRegion.CODEC.listOf().fieldOf("regions").forGetter(data -> data.regions))
            .apply(instance, FacetRegistryData::new));

    public static final SavedDataType<FacetRegistryData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "facet_registry"),
            FacetRegistryData::new,
            CODEC
    );

    private final List<FacetRegion> regions;

    public FacetRegistryData() {
        this(new ArrayList<>());
    }

    private FacetRegistryData(List<FacetRegion> regions) {
        this.regions = new ArrayList<>(regions);
    }

    public static FacetRegistryData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public record FacetAssignment(int facetNumber, int cutGemCountInRegion, int cabochonGemCountInRegion) {}

    public FacetAssignment assignGem(ResourceKey<Level> dimension, int regionX, int regionZ, boolean isCut) {
        for (int i = 0; i < regions.size(); i++) {
            FacetRegion region = regions.get(i);
            if (region.dimension().equals(dimension) && region.regionX() == regionX && region.regionZ() == regionZ) {
                FacetRegion updated = region.withIncrementedCount(isCut);
                regions.set(i, updated);
                setDirty();
                return new FacetAssignment(i + 1, updated.cutGemCount(), updated.cabochonGemCount());
            }
        }
        if(isCut){
            regions.add(new FacetRegion(dimension, regionX, regionZ, 1, 0));
            setDirty();
            return new FacetAssignment(regions.size(), 1, 0);
        }else{
            regions.add(new FacetRegion(dimension, regionX, regionZ, 0, 1));
            setDirty();
            return new FacetAssignment(regions.size(), 0, 1);
        }
    }
}
