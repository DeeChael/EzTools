package org.eztools.impl;

import org.bukkit.command.Command;
import org.eztools.EzT;
import org.eztools.api.command.CommandManager;
import org.eztools.commands.CommandEzT;
import org.eztools.commands.CommandEzTName;
import org.eztools.commands.CommandEzTStorage;

import java.util.ArrayList;
import java.util.List;

public final class CommandManagerImpl implements CommandManager {

    private static final List<Command> commands = new ArrayList<>();

    @Override
    public void register() {
        CommandEzT ezT = new CommandEzT();
        CommandEzTName ezTName = new CommandEzTName();
        CommandEzTStorage ezTStorage = new CommandEzTStorage();
        commands.add(ezT);
        commands.add(ezTName);
        commands.add(ezTStorage);
        EzT.BUKKIT_COMMAND_MAP.register("ezt", ezT);
        EzT.BUKKIT_COMMAND_MAP.register("ezt-name", ezTName);
        EzT.BUKKIT_COMMAND_MAP.register("ezt-storage", ezTStorage);
    }

    public static void unregister() {
        for (Command command : commands) {
            command.unregister(EzT.BUKKIT_COMMAND_MAP);
        }
    }

}
