package org.eztools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.eztools.command.*;
import org.eztools.listener.EntityEventListener;
import org.eztools.listener.GuiListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class EzTools extends JavaPlugin {

    private static EzTools ezTools;
    private static CommandMap commandMap;
    private static GuiHandler guiHandler;
    private static ItemHandler itemHandler;

    private static Map<Player, Entity> selectedEntities;
    private static Map<Player, ItemStack> editingItem;

    @Override
    public void onEnable() {
        ezTools = this;
        guiHandler = new GuiHandler(this);
        itemHandler = new ItemHandler(this);
        selectedEntities = new HashMap<>();
        editingItem = new HashMap<>();
        final Class<?> c = Bukkit.getServer().getClass();
        for (final Method method : c.getDeclaredMethods()) {
            if (method.getName().equals("getCommandMap")) {
                try {
                    commandMap = (CommandMap) method.invoke(Bukkit.getServer(), new Object[0]);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        Bukkit.getPluginManager().registerEvents(new EntityEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
        new EntityCommand();
        new EzToolsCommand();
        new ItemCommand();
        new NbtCommand();
        this.getServer().getConsoleSender().sendMessage("§aEzTools已开启");
        this.getServer().getConsoleSender().sendMessage("§aEzTools - 中文版");
        this.getServer().getConsoleSender().sendMessage("§aEzTools作者: SpigotMC-DeeChael, McBBS-DeeChael (同一个人)");
        this.getServer().getConsoleSender().sendMessage("§aSpigot主页: https://www.spigotmc.org/members/deechael.883670/");
        this.getServer().getConsoleSender().sendMessage("§aMcBBS主页: https://www.mcbbs.net/?2536446");
    }

    public static EzTools getEzTools() {
        return ezTools;
    }

    public static GuiHandler getGuiHandler() {
        return guiHandler;
    }

    public static ItemHandler getItemHandler() {
        return itemHandler;
    }

    public static CommandMap getCommandMap() {
        return commandMap;
    }

    public static Map<Player, Entity> getSelectedEntities() {
        return selectedEntities;
    }

    public static Map<Player, ItemStack> getEditingItem() {
        return editingItem;
    }

    private static void unknownMethod() {
        System.out.println("不如死了算了。");
        System.out.println("可是还有那么多的事还没做完。");
        System.out.println("果然还是太理性了。");
    }

}