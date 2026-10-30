package com.fodk.gemcolony.modonomicon;

import com.fodk.gemcolony.GemColony;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookEntityPage;
import com.klikli_dev.modonomicon.data.BookPageType;
import com.klikli_dev.modonomicon.registry.BookPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public class GemPreviewPage extends BookEntityPage {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gem_preview");

    public static final MapCodec<GemPreviewPage> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BookTextHolder.CODEC.fieldOf("entity_name")
                            .forGetter(GemPreviewPage::getEntityName),
                    BookTextHolder.CODEC.fieldOf("text")
                            .forGetter(GemPreviewPage::getText),
                    Codec.STRING.fieldOf("entity_id")
                            .forGetter(GemPreviewPage::getEntityId),
                    Codec.FLOAT.optionalFieldOf("scale", 1.0f)
                            .forGetter(GemPreviewPage::getScale),
                    Codec.FLOAT.optionalFieldOf("offset", 0f)
                            .forGetter(GemPreviewPage::getOffset),
                    Codec.BOOL.optionalFieldOf("rotate", true)
                            .forGetter(GemPreviewPage::doesRotate),
                    Codec.FLOAT.optionalFieldOf("default_rotation", -45f)
                            .forGetter(GemPreviewPage::getDefaultRotation),
                    Codec.INT.optionalFieldOf("variant", 0)
                            .forGetter(GemPreviewPage::getVariant),
                    Codec.STRING.fieldOf("id")
                            .forGetter(GemPreviewPage::getId),
                    BookCondition.CODEC.optionalFieldOf("condition", new BookNoneCondition())
                            .forGetter(GemPreviewPage::getCondition))
                    .apply(instance, GemPreviewPage::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GemPreviewPage> STREAM_CODEC =
            StreamCodec.composite(
                    BookTextHolder.STREAM_CODEC, GemPreviewPage::getEntityName,
                    BookTextHolder.STREAM_CODEC, GemPreviewPage::getText,
                    ByteBufCodecs.STRING_UTF8, GemPreviewPage::getEntityId,
                    ByteBufCodecs.FLOAT, GemPreviewPage::getScale,
                    ByteBufCodecs.FLOAT, GemPreviewPage::getOffset,
                    ByteBufCodecs.BOOL, GemPreviewPage::doesRotate,
                    ByteBufCodecs.FLOAT, GemPreviewPage::getDefaultRotation,
                    ByteBufCodecs.VAR_INT, GemPreviewPage::getVariant,
                    ByteBufCodecs.STRING_UTF8, GemPreviewPage::getId,
                    BookCondition.STREAM_CODEC, GemPreviewPage::getCondition,
                    GemPreviewPage::new
            );

    public static final BookPageType<GemPreviewPage> TYPE = BookPageTypeRegistry.register(ID, CODEC, STREAM_CODEC);

    private final int variant;
    public int getVariant() {
        return this.variant;
    }

    public GemPreviewPage(BookTextHolder entityName, BookTextHolder text, String entityId,
                          float scale, float offset, boolean rotate, float defaultRotation,
                          int variant, String id, BookCondition condition) {
        super(entityName, text, entityId, scale, offset, rotate, defaultRotation, id, condition);
        this.variant = variant;
    }

    @Override
    public BookPageType<?> type() {
        return TYPE;
    }
}
