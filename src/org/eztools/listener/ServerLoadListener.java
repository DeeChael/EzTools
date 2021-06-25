package org.eztools.listener;

import org.bukkit.command.Command;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.eztools.EzT;

public final class ServerLoadListener implements Listener {

    @EventHandler
    public void onServerLoaded(ServerLoadEvent serverLoadEvent) {
        if (serverLoadEvent.getType() == ServerLoadEvent.LoadType.STARTUP || serverLoadEvent.getType() == ServerLoadEvent.LoadType.RELOAD) {
            Command commandEzT = EzT.BUKKIT_COMMAND_MAP.getCommand("minecraft:ezt");
            Command commandEzTItem = EzT.BUKKIT_COMMAND_MAP.getCommand("minecraft:ezt-item");
            Command commandEzTStorage = EzT.BUKKIT_COMMAND_MAP.getCommand("minecraft:ezt-storage");
            commandEzT.unregister(EzT.BUKKIT_COMMAND_MAP);
            commandEzTItem.unregister(EzT.BUKKIT_COMMAND_MAP);
            commandEzTStorage.unregister(EzT.BUKKIT_COMMAND_MAP);
            EzT.BUKKIT_COMMAND_MAP.register("ezt", commandEzT);
            EzT.BUKKIT_COMMAND_MAP.register("ezt", commandEzTItem);
            EzT.BUKKIT_COMMAND_MAP.register("ezt", commandEzTStorage);
        }
    }

}
