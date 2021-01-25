package org.eztools.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.eztools.EzTools;

public class PlayerCommand extends Command {

    public PlayerCommand() {
        super("player");
        this.setPermission("eztools.command.player");
        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public boolean execute(CommandSender s, String Label, String[] args) {

        return true;
    }

}
