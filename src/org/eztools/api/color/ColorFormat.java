package org.eztools.api.color;

import net.md_5.bungee.api.ChatColor;
import org.eztools.utils.ReflectionAPI;

public final class ColorFormat {

    public static String translate(String textToTranslate) {
        String text = textToTranslate;
        for (char c : ChatColor.ALL_CODES.toCharArray()) {
            text = text.replace("&" + c, "§" + c);
        }
        if (ReflectionAPI.getVersion() >= 13) {
            if (text.contains("&#")) {
                for (String msg : text.split("&#")) {
                    if ((msg.length() >= 6)) {
                        text = text.replace("&#" + msg.substring(0, 6), ChatColor.of("#" + msg.substring(0, 6)).toString());
                    }
                }
            }
        }
        return text;
    }

    public static String transfer(String textToTransfer) {
        String text = textToTransfer;
        for (char c : ChatColor.ALL_CODES.toCharArray()) {
            text = text.replace("§" + c, "&" + c);
        }
        return text;
    }

}
