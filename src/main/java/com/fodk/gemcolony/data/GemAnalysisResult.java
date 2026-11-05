package com.fodk.gemcolony.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record GemAnalysisResult(
        Identifier id,
        float score
) {

    public static final Codec<GemAnalysisResult> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Identifier.CODEC.fieldOf("id").forGetter(GemAnalysisResult::id),
                    Codec.FLOAT.fieldOf("score").forGetter(GemAnalysisResult::score)

            ).apply(instance, GemAnalysisResult::new));


    public static final StreamCodec<ByteBuf, GemAnalysisResult> STREAM_CODEC =
            StreamCodec.of(
                    (buf, result) -> {
                        Identifier.STREAM_CODEC.encode(buf, result.id());
                        ByteBufCodecs.FLOAT.encode(buf, result.score());
                    },
                    buf -> new GemAnalysisResult(
                            Identifier.STREAM_CODEC.decode(buf),
                            ByteBufCodecs.FLOAT.decode(buf)
                    )
            );
}
