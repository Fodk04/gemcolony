package com.fodk.gemcolony.entity.custom.gem.variant;

import com.fodk.gemcolony.util.ColorUtil;
import net.minecraft.world.item.Item;

import java.awt.*;

public class GemVariantData {

    private final Color darkSkin;
    private final Color lightSkin;
    private final Color darkOutfit;
    private final Color lightOutfit;
    private final Color darkInsignia;
    private final Color lightInsignia;
    private final Color darkHair;
    private final Color lightHair;
    private final Color darkMarkings;
    private final Color lightMarkings;
    private final Color darkVisor;
    private final Color lightVisor;

    private final String name;
    private final Item gemItem;
    private final Item chromaItem;

    public GemVariantData(
            Color darkSkin,
            Color lightSkin,
            Color darkOutfit,
            Color lightOutfit,
            Color darkInsignia,
            Color lightInsignia,
            Color darkHair,
            Color lightHair,
            Color darkVisor,
            Color lightVisor,
            String name,
            Item gemItem,
            Item chromaItem
    ) {
        this(
                darkSkin, lightSkin,
                darkOutfit, lightOutfit,
                darkInsignia, lightInsignia,
                darkHair, lightHair,
                null, null,
                darkVisor, lightVisor,
                name, gemItem, chromaItem
        );
    }

    private GemVariantData(
            Color darkSkin,
            Color lightSkin,
            Color darkOutfit,
            Color lightOutfit,
            Color darkInsignia,
            Color lightInsignia,
            Color darkHair,
            Color lightHair,
            Color darkMarkings,
            Color lightMarkings,
            Color darkVisor,
            Color lightVisor,
            String name,
            Item gemItem,
            Item chromaItem
    ) {
        this.darkSkin = darkSkin;
        this.lightSkin = lightSkin;
        this.darkOutfit = darkOutfit;
        this.lightOutfit = lightOutfit;
        this.darkInsignia = darkInsignia;
        this.lightInsignia = lightInsignia;
        this.darkHair = darkHair;
        this.lightHair = lightHair;
        this.darkMarkings = darkMarkings;
        this.lightMarkings = lightMarkings;
        this.darkVisor = darkVisor;
        this.lightVisor = lightVisor;
        this.name = name;
        this.gemItem = gemItem;
        this.chromaItem = chromaItem;
    }

    public Color getSkinColor(float t) {
        return ColorUtil.lerpColor(lightSkin, darkSkin, t);
    }

    public Color getOutfitColor(float t) {
        return ColorUtil.lerpColor(lightOutfit, darkOutfit, t);
    }

    public Color getInsigniaColor(float t) {
        return ColorUtil.lerpColor(lightInsignia, darkInsignia, t);
    }

    public Color getHairColor(float t) {
        return ColorUtil.lerpColor(lightHair, darkHair, t);
    }

    public Color getVisorColor(float t) {
        return ColorUtil.lerpColor(lightVisor, darkVisor, t);
    }

    public String getName() {
        return name;
    }

    public Item getGemItem() {
        return gemItem;
    }

    public Item getChromaItem() {
        return chromaItem;
    }

    public boolean hasMarkings() {
        return darkMarkings != null && lightMarkings != null;
    }

    public Color getMarkingsColor(float t) {
        if (!hasMarkings()) {
            return Color.BLACK;
        }

        return ColorUtil.lerpColor(lightMarkings, darkMarkings, t);
    }

    public GemVariantData withMarkings(Color darkMarkings, Color lightMarkings) {
        return new GemVariantData(
                darkSkin, lightSkin,
                darkOutfit, lightOutfit,
                darkInsignia, lightInsignia,
                darkHair, lightHair,
                darkMarkings, lightMarkings,
                darkVisor, lightVisor,
                name, gemItem, chromaItem
        );
    }
}
