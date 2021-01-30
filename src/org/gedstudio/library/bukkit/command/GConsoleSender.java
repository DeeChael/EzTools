package org.gedstudio.library.bukkit.command;

import org.bukkit.entity.Player;
import org.gedstudio.library.bukkit.chat.GText;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GConsoleSender implements GSender {

    private CommandSender commandSender;

    public GConsoleSender(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Override
    public boolean isPlayer() {
        return this.commandSender instanceof Player;
    }

    @Override
    public String getName() {
        return this.commandSender.getName();
    }

    @Override
    public void sendMessage(GText gText) {
        this.commandSender.sendMessage(gText.getText());
    }

    @Override
    public void sendMessage(GText... gTexts) {
        for (GText gText : gTexts) {
            this.commandSender.sendMessage(gText.getText());
        }
    }

    @Override
    public void sendMessage(List<GText> gTexts) {
        for (GText gText : gTexts) {
            this.commandSender.sendMessage(gText.getText());
        }
    }

    public CommandSender getHandle() {
        return commandSender;
    }

}
