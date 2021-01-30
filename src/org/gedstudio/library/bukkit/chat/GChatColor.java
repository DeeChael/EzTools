package org.gedstudio.library.bukkit.chat;

import org.gedstudio.library.bukkit.GedLibrary;
import org.gedstudio.library.bukkit.color.HexColor;
import org.gedstudio.library.bukkit.color.RGBColor;
import net.md_5.bungee.api.ChatColor;

public class GChatColor {

    public final static GChatColor RED = new GChatColor('c', "red");
    public final static GChatColor DARK_RED = new GChatColor('4', "dark_red");
    public final static GChatColor BLACK = new GChatColor('0', "black");
    public final static GChatColor WHITE = new GChatColor('f', "white");
    public final static GChatColor YELLOW = new GChatColor('e', "yellow");
    public final static GChatColor ORANGE = new GChatColor('6', "orange");
    public final static GChatColor GRAY = new GChatColor('8', "gray");
    public final static GChatColor LIGHT_GRAY = new GChatColor('7', "light_gray");
    public final static GChatColor GREEN = new GChatColor('2', "green");
    public final static GChatColor LIGHT_BLUE = new GChatColor('9', "light_blue");
    public final static GChatColor BLUE = new GChatColor('1', "blue");
    public final static GChatColor PURPLE = new GChatColor('5', "purple");
    public final static GChatColor PINK = new GChatColor('d', "pink");
    public final static GChatColor LIME = new GChatColor('a', "lime");
    public final static GChatColor AQUA = new GChatColor('b', "aqua");
    public final static GChatColor DARK_AQUA = new GChatColor('3', "dark_aqua");
    public final static GChatColor UNKNOWN = new GChatColor('k', "unknown");
    public final static GChatColor BOLD = new GChatColor('l', "bold");
    public final static GChatColor STRIKETHROUGH = new GChatColor('m', "strikethrough");
    public final static GChatColor UNDERLINE = new GChatColor('n', "underline");
    public final static GChatColor ITALIC = new GChatColor('o', "italic");
    public final static GChatColor RESET = new GChatColor('r', "reset");

    private final static GChatColor HEX = new GChatColor('x', "hex");

    private final String toString;

    private final char key;
    private final String name;

    GChatColor(char key, String name) {
        this.key = key;
        this.name = name;
        this.toString = "§" + key;
    }

    public GChatColor(HexColor hexColor) {
        this.key = 'x';
        this.name = "hex";
        this.toString = hexColor.toBungee().toString();
    }

    public GChatColor(RGBColor rgbColor) {
        this.key = 'x';
        this.name = "hex";
        this.toString = rgbColor.toBungee().toString();
    }

    public String name() {
        return this.name;
    }

    public GChatColor[] values() {
        return new GChatColor[] { RED, DARK_RED, BLACK, WHITE, YELLOW, ORANGE, GRAY, LIGHT_GRAY, GREEN, LIGHT_BLUE, BLUE, PURPLE, PINK, LIME, AQUA, DARK_AQUA, UNKNOWN, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET };
    }

    public GChatColor valueOf(String value) {
        for (GChatColor chatColor : values()) {
            if (value.equals(chatColor.name)) {
                return chatColor;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.toString;
    }

    public static String translate(char prefix, String textToTranslate) {
        String text = textToTranslate;
        for (char c : ChatColor.ALL_CODES.toCharArray()) {
            text = text.replace(prefix + c + "", '§' + c + "");
        }
        if (GedLibrary.getInstance().getNmsVersion().equalsIgnoreCase("v1_16_R1") || GedLibrary.getInstance().getNmsVersion().equalsIgnoreCase("v1_16_R2") || GedLibrary.getInstance().getNmsVersion().equalsIgnoreCase("v1_16_R3")) {
            if (text.contains("&#")) {
                String[] strings = text.split("&#");
                for (int i = 1; i < strings.length; i ++) {
                    String string = strings[i];
                    String msg = string;
                    if ((msg.length() >= 6)) {
                        text = text.replace("&#" + msg.substring(0, 6), new HexColor(msg.substring(0, 6)).toBungee().toString());
                    }
                }
            }
        }
        return text;
    }

}
