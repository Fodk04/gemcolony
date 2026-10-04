package com.fodk.gemcolony.entity.custom.gem.variant;

import net.minecraft.world.item.Item;

import java.awt.*;

public interface GemVariant {

    GemVariantData getData();

    default Color getSkinColor(float t) {
        return getData().getSkinColor(t);
    }

    default Color getOutfitColor(float t) {
        return getData().getOutfitColor(t);
    }

    default Color getInsigniaColor(float t) {
        return getData().getInsigniaColor(t);
    }

    default Color getHairColor(float t) {
        return getData().getHairColor(t);
    }

    default Color getVisorColor(float t) {
        return getData().getVisorColor(t);
    }

    default String getName() {
        return getData().getName();
    }

    default Item getGemItem() {
        return getData().getGemItem();
    }

    default int getColorId() {
        return getData().getColorId();
    }
}
