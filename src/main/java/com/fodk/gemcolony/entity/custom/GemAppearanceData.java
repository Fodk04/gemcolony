package com.fodk.gemcolony.entity.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record GemAppearanceData(
        String name,
        int color, int outfit, int outfitColor,
        int insignia, int insigniaColor,
        int hairstyle, int hairColor, int gemPlacement,
        int variant, int wings, int markings,
        int visor, int visorColor
) {
    public static final Codec<GemAppearanceData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(GemAppearanceData::name),
            Codec.INT.fieldOf("color").forGetter(GemAppearanceData::color),
            Codec.INT.fieldOf("outfit").forGetter(GemAppearanceData::outfit),
            Codec.INT.fieldOf("outfitColor").forGetter(GemAppearanceData::outfitColor),
            Codec.INT.fieldOf("insignia").forGetter(GemAppearanceData::insignia),
            Codec.INT.fieldOf("insigniaColor").forGetter(GemAppearanceData::insigniaColor),
            Codec.INT.fieldOf("hairstyle").forGetter(GemAppearanceData::hairstyle),
            Codec.INT.fieldOf("hairColor").forGetter(GemAppearanceData::hairColor),
            Codec.INT.fieldOf("gemPlacement").forGetter(GemAppearanceData::gemPlacement),
            Codec.INT.fieldOf("variant").forGetter(GemAppearanceData::variant),
            Codec.INT.fieldOf("wings").forGetter(GemAppearanceData::wings),
            Codec.INT.fieldOf("markings").forGetter(GemAppearanceData::markings),
            Codec.INT.fieldOf("visor").forGetter(GemAppearanceData::visor),
            Codec.INT.fieldOf("visorColor").forGetter(GemAppearanceData::visorColor)
    ).apply(instance, GemAppearanceData::new));

    public static final StreamCodec<ByteBuf, GemAppearanceData> STREAM_CODEC =
            StreamCodec.of(
                    (buf, data) -> {
                        ByteBufCodecs.STRING_UTF8.encode(buf, data.name());

                        ByteBufCodecs.VAR_INT.encode(buf, data.color());
                        ByteBufCodecs.VAR_INT.encode(buf, data.outfit());
                        ByteBufCodecs.VAR_INT.encode(buf, data.outfitColor());

                        ByteBufCodecs.VAR_INT.encode(buf, data.insignia());
                        ByteBufCodecs.VAR_INT.encode(buf, data.insigniaColor());

                        ByteBufCodecs.VAR_INT.encode(buf, data.hairstyle());
                        ByteBufCodecs.VAR_INT.encode(buf, data.hairColor());

                        ByteBufCodecs.VAR_INT.encode(buf, data.gemPlacement());

                        ByteBufCodecs.VAR_INT.encode(buf, data.variant());
                        ByteBufCodecs.VAR_INT.encode(buf, data.wings());
                        ByteBufCodecs.VAR_INT.encode(buf, data.markings());

                        ByteBufCodecs.VAR_INT.encode(buf, data.visor());
                        ByteBufCodecs.VAR_INT.encode(buf, data.visorColor());
                    },
                    buf -> new GemAppearanceData(
                            ByteBufCodecs.STRING_UTF8.decode(buf),

                            ByteBufCodecs.VAR_INT.decode(buf),
                            ByteBufCodecs.VAR_INT.decode(buf),
                            ByteBufCodecs.VAR_INT.decode(buf),

                            ByteBufCodecs.VAR_INT.decode(buf),
                            ByteBufCodecs.VAR_INT.decode(buf),

                            ByteBufCodecs.VAR_INT.decode(buf),
                            ByteBufCodecs.VAR_INT.decode(buf),

                            ByteBufCodecs.VAR_INT.decode(buf),

                            ByteBufCodecs.VAR_INT.decode(buf),
                            ByteBufCodecs.VAR_INT.decode(buf),
                            ByteBufCodecs.VAR_INT.decode(buf),

                            ByteBufCodecs.VAR_INT.decode(buf),
                            ByteBufCodecs.VAR_INT.decode(buf)
                    )
            );
}
