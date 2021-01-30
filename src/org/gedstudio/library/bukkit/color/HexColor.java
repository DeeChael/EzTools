package org.gedstudio.library.bukkit.color;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Color;

import java.io.Serializable;

public class HexColor implements Serializable {

    private final String hex;

    public HexColor(String hex) {
        if (hex.length() >= 6) {
            this.hex = hex.substring(0, 6);
        } else {
            for (int i = 0; i < (6 - hex.length()); i++) {
                hex += "0";
            }
            this.hex = hex;
        }
    }

    public String getColor() {
        return this.hex;
    }

    public RGBColor toRGB() {
        return new RGBColor(Integer.valueOf(hex, 16));
    }

    public Color toBukkit() {
        return Color.fromRGB(this.toRGB().getColor());
    }

    public ChatColor toBungee() {
        return ChatColor.of("#" + hex);
    }

}
