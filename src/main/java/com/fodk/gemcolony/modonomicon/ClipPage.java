package com.fodk.gemcolony.modonomicon;


import com.fodk.gemcolony.GemColony;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.ImageDisplayMode;
import com.klikli_dev.modonomicon.book.ImageScaleMode;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookImagePage;
import com.klikli_dev.modonomicon.data.BookPageType;
import com.klikli_dev.modonomicon.registry.BookPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public class ClipPage extends BookImagePage {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "clip");

    // Shared clip format
    public static final int FRAME_WIDTH = 350;
    public static final int FRAME_HEIGHT = 350;
    public static final int COLUMNS = 11;
    public static final int ROWS = 11;
    public static final int FPS = 6;

    public static final MapCodec<ClipPage> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BookTextHolder.CODEC.fieldOf("title")
                            .forGetter(ClipPage::getTitle),
                    BookTextHolder.CODEC.fieldOf("text")
                            .forGetter(ClipPage::getText),
                    Identifier.CODEC.fieldOf("texture")
                            .forGetter(ClipPage::getTexture),
                    Codec.FLOAT.fieldOf("duration")
                            .forGetter(ClipPage::getDuration),
                    Codec.BOOL.optionalFieldOf("border", true)
                            .forGetter(ClipPage::hasBorder),
                    Codec.STRING.fieldOf("id")
                            .forGetter(ClipPage::getId),
                    BookCondition.CODEC.optionalFieldOf(
                            "condition", new BookNoneCondition()
                    ).forGetter(ClipPage::getCondition)
            ).apply(instance, ClipPage::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClipPage> STREAM_CODEC =
            StreamCodec.composite(
                    BookTextHolder.STREAM_CODEC, ClipPage::getTitle,
                    BookTextHolder.STREAM_CODEC, ClipPage::getText,
                    Identifier.STREAM_CODEC, ClipPage::getTexture,
                    ByteBufCodecs.FLOAT, ClipPage::getDuration,
                    ByteBufCodecs.BOOL, ClipPage::hasBorder,
                    ByteBufCodecs.STRING_UTF8, ClipPage::getId,
                    BookCondition.STREAM_CODEC, ClipPage::getCondition,
                    ClipPage::new
            );

    public static final BookPageType<ClipPage> TYPE = BookPageTypeRegistry.register(ID, CODEC, STREAM_CODEC);

    private final Identifier texture;
    private final float duration;

    public ClipPage(BookTextHolder title, BookTextHolder text, Identifier texture, float duration, boolean border, String id, BookCondition condition) {
        super(title, text, new Identifier[]{texture}, border, false, ImageDisplayMode.DEFAULT, ImageScaleMode.SCALE_TO_FIT, id, condition);

        this.texture = texture;
        this.duration = duration;
    }

    public Identifier getTexture() {
        return this.texture;
    }

    public float getDuration() {
        return this.duration;
    }

    @Override
    public BookPageType<?> type() {
        return TYPE;
    }
}

