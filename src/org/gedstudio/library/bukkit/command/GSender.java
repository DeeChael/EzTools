package org.gedstudio.library.bukkit.command;

import org.gedstudio.library.bukkit.chat.GText;

import java.util.List;

public interface GSender {

    boolean isPlayer();

    String getName();

    void sendMessage(GText gText);

    void sendMessage(GText... gTexts);

    void sendMessage(List<GText> gTexts);

}
