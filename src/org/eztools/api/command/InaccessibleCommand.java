package org.eztools.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.eztools.EzT;

public class InaccessibleCommand extends Command {

    protected InaccessibleCommand(String name) {
        super(name);
    }

    @Override
    public final boolean execute(CommandSender sender, String label, String[] args) {
        sender.sendMessage(EzT.LANGUAGE.getString("ezt.loading.failed.loadLibrary"));
        return true;
    }

}
