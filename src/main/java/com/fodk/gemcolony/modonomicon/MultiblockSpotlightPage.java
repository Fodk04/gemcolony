package com.fodk.gemcolony.modonomicon;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.construction.ConstructionRequirement;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.page.BookSpotlightPage;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import java.util.List;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookPage;
import com.klikli_dev.modonomicon.data.BookPageType;
import com.klikli_dev.modonomicon.registry.BookPageTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class MultiblockSpotlightPage extends BookSpotlightPage {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "multiblock_spotlight");

    public static final Codec<ConstructionRequirement> REQUIREMENT_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("count").forGetter(ConstructionRequirement::count),
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(ConstructionRequirement::item))
                    .apply(instance, ConstructionRequirement::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConstructionRequirement> REQUIREMENT_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, ConstructionRequirement::count,
                    ByteBufCodecs.registry(Registries.ITEM), ConstructionRequirement::item,
                    ConstructionRequirement::new
            );

    public static final MapCodec<MultiblockSpotlightPage> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BookTextHolder.CODEC.fieldOf("title")
                            .forGetter(MultiblockSpotlightPage::getTitle),
                    BookTextHolder.CODEC.fieldOf("text")
                            .forGetter(MultiblockSpotlightPage::getText),
                    BookSpotlightPage.ITEM_CODEC.fieldOf("item")
                            .forGetter(BookSpotlightPage::getItem),
                    REQUIREMENT_CODEC.listOf().fieldOf("requirements")
                            .forGetter(MultiblockSpotlightPage::getRequirements),
                    Codec.STRING.fieldOf("id")
                            .forGetter(BookPage::getId),
                    BookCondition.CODEC.optionalFieldOf(
                            "condition", new BookNoneCondition()
                    ).forGetter(BookPage::getCondition)
            ).apply(instance, MultiblockSpotlightPage::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MultiblockSpotlightPage> STREAM_CODEC =
            StreamCodec.composite(
                    BookTextHolder.STREAM_CODEC, MultiblockSpotlightPage::getTitle,
                    BookTextHolder.STREAM_CODEC, MultiblockSpotlightPage::getText,
                    BookSpotlightPage.ITEM_STREAM_CODEC, BookSpotlightPage::getItem,
                    REQUIREMENT_STREAM_CODEC.apply(ByteBufCodecs.list()), MultiblockSpotlightPage::getRequirements,
                    ByteBufCodecs.STRING_UTF8, BookPage::getId,
                    BookCondition.STREAM_CODEC, BookPage::getCondition,
                    MultiblockSpotlightPage::new
            );

    public static final BookPageType<MultiblockSpotlightPage> TYPE =
            BookPageTypeRegistry.register(ID, CODEC, STREAM_CODEC);

    private final List<ConstructionRequirement> requirements;

    public MultiblockSpotlightPage(
            BookTextHolder title,
            BookTextHolder text,
            Either<ItemStackTemplate, Ingredient> item,
            List<ConstructionRequirement> requirements,
            String id,
            BookCondition condition
    ) {
        super(title, text, item, id, condition);
        this.requirements = List.copyOf(requirements);
    }

    public List<ConstructionRequirement> getRequirements() {
        return this.requirements;
    }

    @Override
    public BookPageType<?> type() {
        return TYPE;
    }
}
