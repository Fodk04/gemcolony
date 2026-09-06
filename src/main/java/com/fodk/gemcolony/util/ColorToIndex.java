package com.fodk.gemcolony.util;

import java.awt.*;

public enum ColorToIndex {
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

    ColorToIndex(int index, Color color, String name) {
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
        for(ColorToIndex value : values()){
            if(value.index == index){
                return value.color;
            }
        }

        return Color.WHITE;
    }

    public static int colorToInt(Color color)
    {
        int r = color.getRed();
        int b = color.getBlue();
        int g = color.getGreen();
        return (r << 16) | (g << 8) | b;
    }
}
