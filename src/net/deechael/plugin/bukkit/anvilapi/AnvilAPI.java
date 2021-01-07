package net.deechael.plugin.bukkit.anvilapi;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnvilAPI extends JavaPlugin {

    private static AnvilAPI anvilAPI;

    @Override
    public void onEnable() {
        anvilAPI = this;
        logger().sendMessage("§aDeeChael's AnvilAPI has been enabled");
    }

    public static AnvilAPI getAnvilAPI() {
        return anvilAPI;
    }

    public ConsoleCommandSender logger() {
        return this.getServer().getConsoleSender();
    }

}
