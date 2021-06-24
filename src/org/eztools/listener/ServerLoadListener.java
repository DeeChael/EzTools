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
            Command commandEzT = EzT.BUKKIT_COMMAND_MAP.getCommand("ezt");
            Command commandEzTItem = EzT.BUKKIT_COMMAND_MAP.getCommand("ezt-item");
            commandEzT.unregister(EzT.BUKKIT_COMMAND_MAP);
            commandEzTItem.unregister(EzT.BUKKIT_COMMAND_MAP);
            EzT.BUKKIT_COMMAND_MAP.register("ezt", commandEzT);
            EzT.BUKKIT_COMMAND_MAP.register("ezt", commandEzTItem);
        }
    }

}
