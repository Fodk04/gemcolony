package com.fodk.gemcolony.util;

import java.awt.*;

public enum ColorUtil {
    WHITE(0, Color.WHITE, "white"),
    LIGHT_GRAY(1, Color.LIGHT_GRAY, "light_gray"),
    GRAY(2, Color.GRAY, "gray"),
    BLACK(3, Color.BLACK, "black"),
    BROWN(4, new Color(93, 48, 31), "brown"),
    RED(5, Color.RED, "red"),
    ORANGE(6, Color.ORANGE, "orange"),
    YELLOW(7, Color.YELLOW, "yellow"),
    LIME(8, new Color(88, 178, 0), "lime"),
    GREEN(9, Color.GREEN, "green"),
    CYAN(10, Color.CYAN, "cyan"),
    LIGHT_BLUE(11, new Color(66, 152, 178), "light_blue"),
    BLUE(12, Color.BLUE, "blue"),
    PURPLE(13, new Color(110, 0, 110), "purple"),
    MAGENTA(14, Color.MAGENTA, "magenta"),
    PINK(15, Color.PINK, "pink");

    private int index;
    private Color color;
    private String name;

    ColorUtil(int index, Color color, String name) {
        this.index = index;
        this.color = color;
        this.name = name;
    }

    public int getColorIndex(){
        return index;
    }

    public String getColorName(){
        return name;
    }

    public static Color colorFromIndex(int index){
        for(ColorUtil value : values()){
            if(value.index == index){
                return value.color;
            }
        }

        return Color.WHITE;
    }

    public static int colorToInt(Color color)
    {
        int a = color.getAlpha();
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static Color lerpColor(Color light, Color dark, float t){
        float r = (t * (float)light.getRed() + (1f - t) * (float)dark.getRed()) / 255f;
        float g = (t * (float)light.getGreen() + (1f - t) * (float)dark.getGreen()) / 255f;
        float b = (t * (float)light.getBlue() + (1f - t) * (float)dark.getBlue()) / 255f;
        return new Color(r, g, b);
    }

    public static Color colorFromInt(int color){
        int a = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        return new Color(r, g, b, a);
    }

    public static int mixColorInts(int colorInt1, int colorInt2, float weight){
        Color color1 = colorFromInt(colorInt1);
        Color color2 = colorFromInt(colorInt2);

        float averageR = color1.getRed() * (1f - weight) + color2.getRed() * weight;
        float averageG = color1.getGreen() * (1f - weight) + color2.getGreen() * weight;
        float averageB = color1.getBlue() * (1f - weight) + color2.getBlue() * weight;

        return colorToInt(new Color((int)averageR, (int)averageG, (int)averageB));
    }

    public static int multiplyColors(int colorInt1, int colorInt2){
        Color color1 = colorFromInt(colorInt1);
        Color color2 = colorFromInt(colorInt2);

        float r = (float)color1.getRed() / 255f * (float)color2.getRed() / 255f;
        float g = (float)color1.getGreen() / 255f * (float)color2.getGreen() / 255f;
        float b = (float)color1.getBlue() / 255f * (float)color2.getBlue() / 255f;

        return colorToInt(new Color(r, g, b));
    }

    public static int getContrastingTextColor(int color) {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        float brightness =
                0.299f * red +
                        0.587f * green +
                        0.114f * blue;

        return brightness < 128
                ? 0x80FFFFFF
                : 0x80000000;
    }
}
