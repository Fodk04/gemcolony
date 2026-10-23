package com.fodk.gemcolony.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.List;

public class ConstructionProgressData extends SavedData {

    public static final Codec<ConstructionProgressData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    ConstructionProgress.CODEC.listOf()
                            .fieldOf("constructions")
                            .forGetter(data -> data.constructions))
                    .apply(instance, ConstructionProgressData::new));

    public static final SavedDataType<ConstructionProgressData> TYPE =
            new SavedDataType<>(
                    Identifier.fromNamespaceAndPath("gemcolony", "construction_progress"),
                    ConstructionProgressData::new,
                    CODEC
            );

    private final List<ConstructionProgress> constructions;

    public ConstructionProgressData() {
        this(new ArrayList<>());
    }

    private ConstructionProgressData(List<ConstructionProgress> constructions) {
        this.constructions = new ArrayList<>(constructions);
    }

    public static ConstructionProgressData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public int getProgress(ResourceKey<Level> dimension, BlockPos pos) {
        return constructions.stream()
                .filter(entry -> entry.dimension().equals(dimension) && entry.pos().equals(pos))
                .mapToInt(ConstructionProgress::progress)
                .findFirst()
                .orElse(0);
    }

    public void setProgress(ResourceKey<Level> dimension, BlockPos pos, int progress) {
        constructions.removeIf(entry -> entry.dimension().equals(dimension) && entry.pos().equals(pos));

        if (progress > 0) {
            constructions.add(new ConstructionProgress(dimension, pos.immutable(), Math.min(progress, 200)));
        }

        setDirty();
    }

    public void clearProgress(ResourceKey<Level> dimension, BlockPos pos) {
        constructions.removeIf(entry -> entry.dimension().equals(dimension) && entry.pos().equals(pos));
        setDirty();
    }

    public record ConstructionProgress(ResourceKey<Level> dimension, BlockPos pos, int progress) {
        public static final Codec<ConstructionProgress> CODEC =
                RecordCodecBuilder.create(instance -> instance.group(
                        ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(ConstructionProgress::dimension),
                        BlockPos.CODEC.fieldOf("pos").forGetter(ConstructionProgress::pos),
                        Codec.INT.fieldOf("progress").forGetter(ConstructionProgress::progress))
                        .apply(instance, ConstructionProgress::new));
    }
}

