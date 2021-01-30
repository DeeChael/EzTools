package org.gedstudio.library.bukkit.color;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Color;

import java.io.Serializable;

public class RGBColor implements Serializable {

    private final int color;

    private final int r;
    private final int g;
    private final int b;

    public RGBColor(int color) {
        this.color = color;
        this.r = (color & 0xff0000) >> 16;
        this.g = (color & 0x00ff00) >> 8;
        this.b = (color & 0x0000ff);
    }

    public RGBColor(int r, int g, int b) {
        this.color = (r * 65536) + (g * 256) + b;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getColor() {
        return color;
    }

    public int getB() {
        return b;
    }

    public int getG() {
        return g;
    }

    public int getR() {
        return r;
    }

    public HexColor toHex() {
        return new HexColor(Integer.toHexString(color).toLowerCase());
    }

    public Color toBukkit() {
        return Color.fromRGB(color);
    }

    public ChatColor toBungee() {
        return this.toHex().toBungee();
    }

}
