package com.fodk.gemcolony.util;

import com.fodk.gemcolony.entity.custom.gem.variant.GemVariant;

import java.util.Random;

public class GemVariantUtil {

    public static <T extends Enum<T> & GemVariant> T getById(Class<T> enumClass, int id) {
        return enumClass.getEnumConstants()[id];
    }

    public static <T extends Enum<T> & GemVariant> int getVariantFromChromaColor(Class<T> enumClass, int chromaColor) {
        T[] variants = enumClass.getEnumConstants();

        for (int i = 0; i < variants.length; i++) {
            if (variants[i].getColorId() == chromaColor) {
                return i;
            }
        }

        return new Random().nextInt(variants.length);
    }
}
