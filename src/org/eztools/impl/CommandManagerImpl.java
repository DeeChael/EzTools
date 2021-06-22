package org.eztools.impl;

import org.bukkit.command.Command;
import org.eztools.EzT;
import org.eztools.api.command.CommandManager;
import org.eztools.commands.CommandEzT;
import org.eztools.commands.CommandEzTName;

import java.util.ArrayList;
import java.util.List;

public class CommandManagerImpl implements CommandManager {

    private static final List<Command> commands = new ArrayList<>();

    @Override
    public void register() {
        CommandEzT ezT = new CommandEzT();
        CommandEzTName ezTName = new CommandEzTName();
        commands.add(ezT);
        commands.add(ezTName);
        EzT.BUKKIT_COMMAND_MAP.register("ezt", ezT);
        EzT.BUKKIT_COMMAND_MAP.register("ezt-name", ezTName);
    }

    public static void unregister() {
        for (Command command : commands) {
            command.unregister(EzT.BUKKIT_COMMAND_MAP);
        }
    }

}
