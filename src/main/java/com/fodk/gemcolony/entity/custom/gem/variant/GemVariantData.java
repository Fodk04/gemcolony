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
    private final Color darkVisor;
    private final Color lightVisor;

    private final String name;
    private final Item gemItem;
    private final int colorId;

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
            int colorId
    ) {
        this.darkSkin = darkSkin;
        this.lightSkin = lightSkin;
        this.darkOutfit = darkOutfit;
        this.lightOutfit = lightOutfit;
        this.darkInsignia = darkInsignia;
        this.lightInsignia = lightInsignia;
        this.darkHair = darkHair;
        this.lightHair = lightHair;
        this.darkVisor = darkVisor;
        this.lightVisor = lightVisor;
        this.name = name;
        this.gemItem = gemItem;
        this.colorId = colorId;
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

    public int getColorId() {
        return colorId;
    }
}
