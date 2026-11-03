package com.fodk.gemcolony.modonomicon;

import com.fodk.gemcolony.GemColony;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookMultiblockPage;
import com.klikli_dev.modonomicon.book.page.BookPage;
import com.klikli_dev.modonomicon.data.BookPageType;
import com.klikli_dev.modonomicon.registry.BookPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public class FluidMultiblockPage extends BookMultiblockPage {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "fluid_multiblock");

    public static final MapCodec<FluidMultiblockPage> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BookTextHolder.CODEC.fieldOf("multiblock_name")
                            .forGetter(FluidMultiblockPage::getMultiblockName),
                    BookTextHolder.CODEC.fieldOf("text")
                            .forGetter(FluidMultiblockPage::getText),
                    Identifier.CODEC.fieldOf("multiblock")
                            .forGetter(FluidMultiblockPage::getMultiblockId),
                    Codec.BOOL.optionalFieldOf("show_visualize_button", false)
                            .forGetter(FluidMultiblockPage::showVisualizeButton),
                    Codec.STRING.fieldOf("id")
                            .forGetter(BookPage::getId),
                    BookCondition.CODEC.optionalFieldOf(
                            "condition", new BookNoneCondition()
                    ).forGetter(BookPage::getCondition))
                    .apply(instance, FluidMultiblockPage::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidMultiblockPage> STREAM_CODEC =
            StreamCodec.composite(
                    BookTextHolder.STREAM_CODEC, FluidMultiblockPage::getMultiblockName,
                    BookTextHolder.STREAM_CODEC, FluidMultiblockPage::getText,
                    Identifier.STREAM_CODEC, FluidMultiblockPage::getMultiblockId,
                    ByteBufCodecs.BOOL, FluidMultiblockPage::showVisualizeButton,
                    ByteBufCodecs.STRING_UTF8, BookPage::getId,
                    BookCondition.STREAM_CODEC, BookPage::getCondition,
                    FluidMultiblockPage::new
            );

    public static final BookPageType<FluidMultiblockPage> TYPE =
            BookPageTypeRegistry.register(ID, CODEC, STREAM_CODEC);

    public FluidMultiblockPage(
            BookTextHolder multiblockName,
            BookTextHolder text,
            Identifier multiblockId,
            boolean showVisualizeButton,
            String id,
            BookCondition condition
    ) {
        super(multiblockName, text, multiblockId, showVisualizeButton, id, condition);
    }

    @Override
    public BookPageType<?> type() {
        return TYPE;
    }
}

