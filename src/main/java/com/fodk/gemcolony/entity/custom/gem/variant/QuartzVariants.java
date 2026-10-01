package com.fodk.gemcolony.entity.custom.gem.variant;

import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.util.ColorUtil;
import net.minecraft.world.item.Item;

import java.awt.*;
import java.util.Random;

public enum QuartzVariants {
    MILKY(new Color(218, 218, 218),
            new Color(240, 240, 240),
            new Color(137, 137, 137),
            new Color(170, 170, 170),
            new Color(76, 76, 76),
            new Color(126, 125, 125),
            new Color(194, 199, 218),
            new Color(238, 241, 255),
            new Color(199, 201, 205),
            new Color(255, 255, 255),
            "Milky", ModItems.MILKY_QUARTZ_GEM.get(), 0),
    PHANTOM(new Color(147, 147, 147),
            new Color(202, 202, 202),
            new Color(48, 48, 48),
            new Color(96, 96, 96),
            new Color(99, 99, 99),
            new Color(150, 150, 148),
            new Color(94, 94, 94),
            new Color(161, 160, 160),
            new Color(209, 209, 209),
            new Color(228, 228, 227),
            "Phantom", ModItems.PHANTOM_QUARTZ_GEM.get(), 1),
    FLINT(new Color(40, 63, 94),
            new Color(110, 139, 169),
            new Color(26, 34, 51),
            new Color(47, 62, 94),
            new Color(51, 56, 67),
            new Color(87, 95, 113),
            new Color(26, 34, 51),
            new Color(69, 84, 113),
            new Color(69, 84, 113),
            new Color(112, 134, 179),
            "Flint", ModItems.FLINT_QUARTZ_GEM.get(), 2),
    ONYX(new Color(28, 26, 29),
            new Color(49, 49, 49),
            new Color(25, 22, 15),
            new Color(60, 60, 60),
            new Color(48, 48, 48),
            new Color(83, 83, 83),
            new Color(98, 98, 98),
            new Color(143, 143, 143),
            new Color(128, 128, 128),
            new Color(161, 161, 161),
            "Onyx", ModItems.ONYX_QUARTZ_GEM.get(), 3),
    SMOKY(new Color(66, 49, 55),
            new Color(130, 100, 100),
            new Color(53, 39, 50),
            new Color(99, 74, 94),
            new Color(53, 35, 42),
            new Color(78, 48, 60),
            new Color(97, 72, 72),
            new Color(175, 124, 124),
            new Color(152, 105, 105),
            new Color(189, 114, 114),
            "Smoky", ModItems.SMOKY_QUARTZ_GEM.get(), 4),
    CARNELIAN(new Color(200, 45, 70),
            new Color(246, 11, 50),
            new Color(120, 10, 10),
            new Color(205, 19, 19),
            new Color(156, 1, 19),
            new Color(181, 16, 35),
            new Color(158, 8, 30),
            new Color(223, 22, 51),
            new Color(207, 20, 47),
            new Color(255, 52, 81),
            "Carnelian", ModItems.CARNELIAN_QUARTZ_GEM.get(), 5),
    CHERT(new Color(150, 110, 60),
            new Color(250, 160, 70),
            new Color(100, 60, 25),
            new Color(195, 106, 27),
            new Color(147, 76, 14),
            new Color(209, 109, 22),
            new Color(152, 94, 45),
            new Color(244, 148, 66),
            new Color(207, 95, 0),
            new Color(244, 114, 3),
            "Chert", ModItems.CHERT_QUARTZ_GEM.get(), 6),
    CITRINE(new Color(150, 130, 30),
            new Color(225, 225, 33),
            new Color(163, 143, 25),
            new Color(218, 191, 30),
            new Color(140, 117, 0),
            new Color(214, 179, 2),
            new Color(163, 144, 57),
            new Color(243, 214, 69),
            new Color(207, 174, 6),
            new Color(255, 218, 30),
            "Citrine", ModItems.CITRINE.get(), 7),
    PRASEOLITE(new Color(134, 175, 118),
            new Color(193, 234, 178),
            new Color(2, 124, 63),
            new Color(8, 188, 98),
            new Color(53, 184, 120),
            new Color(113, 251, 184),
            new Color(145, 204, 119),
            new Color(222, 251, 209),
            new Color(153, 200, 133),
            new Color(207, 251, 186),
            "Praseolite", ModItems.PRASEOLITE_QUARTZ_GEM.get(), 8),
    AVENTURINE(new Color(85, 136, 46),
            new Color(134, 191, 93),
            new Color(35, 66, 58),
            new Color(69, 113, 88),
            new Color(59, 124, 52),
            new Color(87, 181, 77),
            new Color(61, 104, 80),
            new Color(87, 147, 113),
            new Color(128, 220, 119),
            new Color(184, 250, 178),
            "Aventurine", ModItems.AVENTURINE_QUARTZ_GEM.get(), 9),
    ANGEL_AURA(new Color(46, 110, 133),
            new Color(184, 237, 255),
            new Color(127, 130, 199),
            new Color(207, 186, 253),
            new Color(95, 205, 156),
            new Color(143, 106, 183),
            new Color(186, 153, 50),
            new Color(250, 225, 140),
            new Color(92, 173, 202),
            new Color(135, 214, 243),
            "Angel Aura", ModItems.ANGEL_AURA_QUARTZ_GEM.get(), 10),
    DUMORTIERITE(new Color(118, 141, 185),
            new Color(154, 186, 224),
            new Color(71, 108, 149),
            new Color(103, 159, 221),
            new Color(51, 115, 186),
            new Color(80, 158, 244),
            new Color(57, 122, 193),
            new Color(121, 180, 246),
            new Color(121, 180, 246),
            new Color(169, 210, 255),
            "Dumortierite", ModItems.DUMORTIERITE_QUARTZ_GEM.get(), 11),
    BLUE(new Color(7, 50, 96),
            new Color(2, 79, 151),
            new Color(0, 43, 85),
            new Color(13, 57, 138),
            new Color(10, 26, 50),
            new Color(18, 31, 112),
            new Color(0, 27, 138),
            new Color(1, 39, 200),
            new Color(15, 42, 147),
            new Color(0, 49, 241),
            "Blue", ModItems.BLUE_QUARTZ_GEM.get(), 12),
    AMETHYST(new Color(70, 10, 130),
            new Color(200, 80, 250),
            new Color(70, 0, 150),
            new Color(120, 30, 210),
            new Color(63, 0, 100),
            new Color(103, 0, 200),
            new Color(103, 0, 200),
            new Color(200, 80, 250),
            new Color(93, 0, 180),
            new Color(92, 0, 230),
            "Amethyst", ModItems.AMETHYST_QUARTZ_GEM.get(), 13),
    CHERRY(new Color(251, 105, 116),
            new Color(252, 128, 165),
            new Color(168, 51, 77),
            new Color(220, 70, 103),
            new Color(142, 55, 75),
            new Color(214, 69, 103),
            new Color(250, 146, 178),
            new Color(250, 55, 113),
            new Color(154, 97, 113),
            new Color(225, 141, 165),
            "Cherry", ModItems.CHERRY_QUARTZ_GEM.get(), 14),
    ROSE(new Color(253, 112, 164),
            new Color(253, 167, 216),
            new Color(170, 23, 78),
            new Color(253, 112, 164),
            new Color(190, 91, 127),
            new Color(230, 76, 133),
            new Color(188, 71, 115),
            new Color(254, 128, 175),
            new Color(255, 79, 142),
            new Color(230, 76, 133),
            "Rose", ModItems.ROSE_QUARTZ_GEM.get(), 15);

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

    public Color getSkinColor(float t){
        return ColorUtil.lerpColor(lightSkin, darkSkin, t);
    }

    public Color getOutfitColor(float t){
        return ColorUtil.lerpColor(lightOutfit, darkOutfit, t);
    }

    public Color getInsigniaColor(float t){
        return ColorUtil.lerpColor(lightInsignia, darkInsignia, t);
    }

    public Color getHairColor(float t){
        return ColorUtil.lerpColor(lightHair, darkHair, t);
    }

    public Color getVisorColor(float t){
        return ColorUtil.lerpColor(lightVisor, darkVisor, t);
    }

    public String getName(){
        return name;
    }

    public Item getGemItem(){
        return gemItem;
    }

    QuartzVariants(Color darkSkin, Color lightSkin, Color darkOutfit, Color lightOutfit, Color darkInsignia, Color lightInsignia, Color darkHair, Color lightHair, Color darkVisor, Color lightVisor, String name, Item gemItem, int colorId) {
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

    public static QuartzVariants getById(int id){
        return QuartzVariants.values()[id];
    }

    public static int getVariantFromChromaColor(int colorId) {
        QuartzVariants[] variants = QuartzVariants.values();

        for (int i = 0; i < variants.length; i++) {
            if (variants[i].colorId == colorId) {
                return i;
            }
        }

        return new Random().nextInt(variants.length);
    }
}
