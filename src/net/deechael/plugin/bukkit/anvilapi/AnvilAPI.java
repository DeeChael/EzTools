package net.deechael.plugin.bukkit.anvilapi;

import net.deechael.plugin.bukkit.anvilapi.inventory.DAnvil;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
//import org.bukkit.plugin.java.JavaPlugin; //Removed by EzTools

public final class AnvilAPI /* extends JavaPlugin */ /* Removed by EzTools */ {

    private static AnvilAPI anvilAPI;

    //@Override //Removed by EzTools
    public void onEnable() {
        anvilAPI = this;
        logger().sendMessage("§aDeeChael's AnvilAPI has been enabled");
    }

    //Added by EzTools
    public static void enable() {
        new AnvilAPI().onEnable();
    }

    public void reload() {
        //Nothing now
    }

    public static AnvilAPI getAnvilAPI() {
        return anvilAPI;
    }

    public DAnvil getAnvil(String title, Player player) {
        DAnvil dAnvil = null;
        if (getNMS().equalsIgnoreCase("v1_13_R1")) {
            dAnvil = new net.deechael.plugin.bukkit.anvilapi.nms.v1_13_R1.DAnvil(title, player);
        } else if (getNMS().equalsIgnoreCase("v1_13_R2")) {
            dAnvil = new net.deechael.plugin.bukkit.anvilapi.nms.v1_13_R2.DAnvil(title, player);
        } else if (getNMS().equalsIgnoreCase("v1_14_R1")) {
            dAnvil = new net.deechael.plugin.bukkit.anvilapi.nms.v1_14_R1.DAnvil(title, player);
        } else if (getNMS().equalsIgnoreCase("v1_15_R1")) {
            dAnvil = new net.deechael.plugin.bukkit.anvilapi.nms.v1_15_R1.DAnvil(title, player);
        } else if (getNMS().equalsIgnoreCase("v1_16_R1")) {
            dAnvil = new net.deechael.plugin.bukkit.anvilapi.nms.v1_16_R1.DAnvil(title, player);
        } else if (getNMS().equalsIgnoreCase("v1_16_R2")) {
            dAnvil = new net.deechael.plugin.bukkit.anvilapi.nms.v1_16_R2.DAnvil(title, player);
        } else if (getNMS().equalsIgnoreCase("v1_16_R3")) {
            dAnvil = new net.deechael.plugin.bukkit.anvilapi.nms.v1_16_R3.DAnvil(title, player);
        }
        return dAnvil;
    }

    public Inventory castToBukkit(DAnvil dAnvil) {
        return dAnvil.getFakeAnvil().castToBukkit();
    }

    public ConsoleCommandSender logger() {
        //Replaced by EzTools
        /* return this.getServer().getConsoleSender(); */
        return Bukkit.getServer().getConsoleSender();
    }

    private String getNMS() {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        return version.substring(version.lastIndexOf('.') + 1);
    }

}
