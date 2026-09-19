package com.fodk.gemcolony.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record GemAnalysisResult(
        String gemId,
        float score
) {

    public static final Codec<GemAnalysisResult> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("gemId").forGetter(GemAnalysisResult::gemId),
                    Codec.FLOAT.fieldOf("score").forGetter(GemAnalysisResult::score)

            ).apply(instance, GemAnalysisResult::new));


    public static final StreamCodec<ByteBuf, GemAnalysisResult> STREAM_CODEC =
            StreamCodec.of(
                    (buf, result) -> {
                        ByteBufCodecs.STRING_UTF8.encode(
                                buf,
                                result.gemId()
                        );

                        ByteBufCodecs.FLOAT.encode(
                                buf,
                                result.score()
                        );
                    },

                    buf -> new GemAnalysisResult(
                            ByteBufCodecs.STRING_UTF8.decode(buf),
                            ByteBufCodecs.FLOAT.decode(buf)
                    )
            );
}
