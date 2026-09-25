package com.fodk.gemcolony.entity.custom.savedata;

import com.fodk.gemcolony.data.GemAnalysisResult;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public record PeridotAnalysisData(
        boolean analysing,
        int analysisTicks,
        int analysisDuration,
        int centerX,
        int centerY,
        int centerZ,
        float temperatureTotal,
        float humidityTotal,
        int samples,
        List<GemAnalysisResult> results
) {

    public static final Codec<PeridotAnalysisData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.BOOL.fieldOf("analysing").forGetter(PeridotAnalysisData::analysing),
                    Codec.INT.fieldOf("analysisTicks").forGetter(PeridotAnalysisData::analysisTicks),
                    Codec.INT.fieldOf("analysisDuration").forGetter(PeridotAnalysisData::analysisDuration),
                    Codec.INT.fieldOf("centerX").forGetter(PeridotAnalysisData::centerX),
                    Codec.INT.fieldOf("centerY").forGetter(PeridotAnalysisData::centerY),
                    Codec.INT.fieldOf("centerZ").forGetter(PeridotAnalysisData::centerZ),
                    Codec.FLOAT.fieldOf("temperatureTotal").forGetter(PeridotAnalysisData::temperatureTotal),
                    Codec.FLOAT.fieldOf("humidityTotal").forGetter(PeridotAnalysisData::humidityTotal),
                    Codec.INT.fieldOf("samples").forGetter(PeridotAnalysisData::samples),
                    Codec.list(GemAnalysisResult.CODEC).fieldOf("results").forGetter(PeridotAnalysisData::results)

            ).apply(instance, PeridotAnalysisData::new));


    public static final StreamCodec<ByteBuf, PeridotAnalysisData> STREAM_CODEC =
            StreamCodec.of(
                    (buf, data) -> {
                        ByteBufCodecs.BOOL.encode(buf, data.analysing());

                        ByteBufCodecs.VAR_INT.encode(buf, data.analysisTicks());
                        ByteBufCodecs.VAR_INT.encode(buf, data.analysisDuration());

                        ByteBufCodecs.VAR_INT.encode(buf, data.centerX());
                        ByteBufCodecs.VAR_INT.encode(buf, data.centerY());
                        ByteBufCodecs.VAR_INT.encode(buf, data.centerZ());

                        ByteBufCodecs.FLOAT.encode(buf, data.temperatureTotal());
                        ByteBufCodecs.FLOAT.encode(buf, data.humidityTotal());

                        ByteBufCodecs.VAR_INT.encode(buf, data.samples());
                        ByteBufCodecs.collection(ArrayList::new, GemAnalysisResult.STREAM_CODEC).encode(buf, new ArrayList<>(data.results()));
                    },

                    buf -> new PeridotAnalysisData(
                            ByteBufCodecs.BOOL.decode(buf),

                            ByteBufCodecs.VAR_INT.decode(buf),
                            ByteBufCodecs.VAR_INT.decode(buf),

                            ByteBufCodecs.VAR_INT.decode(buf),
                            ByteBufCodecs.VAR_INT.decode(buf),
                            ByteBufCodecs.VAR_INT.decode(buf),

                            ByteBufCodecs.FLOAT.decode(buf),
                            ByteBufCodecs.FLOAT.decode(buf),

                            ByteBufCodecs.VAR_INT.decode(buf),
                            ByteBufCodecs.collection(ArrayList::new, GemAnalysisResult.STREAM_CODEC).decode(buf)
                    )
            );
}
