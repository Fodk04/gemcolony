package com.fodk.gemcolony.entity.custom.gem.variant;

import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public abstract class VariantGemEntity extends GemEntity {

    public VariantGemEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Nullable
    public GemVariantData getVariantData() {
        Identifier variantId = getVariantId();

        if (variantId == null) {
            return null;
        }

        if (!GemVariantRegistry.getVariantIds(getType()).contains(variantId)) {
            return null;
        }

        return GemVariantRegistry.get(variantId);
    }

    @Override
    public Item getGemItem() {
        GemVariantData data = getVariantData();
        return data == null ? ItemStack.EMPTY.getItem() : data.getGemItem();
    }

    @Override
    public String getGemTypeName() {
        GemVariantData data = getVariantData();
        return data == null ? getNameWithoutVariant() : data.getName() + " " + getNameWithoutVariant();
    }

    public Identifier getRandomVariantId() {
        List<Identifier> variants = GemVariantRegistry.getVariantIds(getType());

        if (variants.isEmpty()) {
            throw new IllegalStateException("No variants registered for entity: " + getType());
        }

        return variants.get(random.nextInt(variants.size()));
    }

    public Identifier getVariantFromChroma(Item chromaItem) {
        List<Identifier> matchingVariants = GemVariantRegistry.getVariantIds(getType())
                .stream()
                .filter(id -> {
                    GemVariantData data = GemVariantRegistry.get(id);
                    return data != null && data.getChromaItem() == chromaItem;
                })
                .toList();

        if (matchingVariants.isEmpty()) {
            return getRandomVariantId();
        }

        return matchingVariants.get(random.nextInt(matchingVariants.size()));
    }

    @Override
    public Color generateGemColor() {
        return getVariantData().getSkinColor(random.nextFloat());
    }

    @Override
    public Color generateOutfitColor() {
        return getVariantData().getOutfitColor(random.nextFloat());
    }

    @Override
    public Color generateInsigniaColor() {
        return getVariantData().getInsigniaColor(random.nextFloat());
    }

    @Override
    public Color generateHairColor() {
        return getVariantData().getHairColor(random.nextFloat());
    }

    @Override
    public Color generateVisorColor() {
        return getVariantData().getVisorColor(random.nextFloat());
    }

    @Override
    public Color generateMarkingsColor() {
        GemVariantData data = getVariantData();
        return data == null ? Color.BLACK : data.getMarkingsColor(random.nextFloat());
    }
}
