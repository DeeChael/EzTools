package org.eztools;

//Bukkit API
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

//EzTools
import org.eztools.command.*;
import org.eztools.listener.EntityEventListener;
import org.eztools.listener.GuiListener;

//new GedLibrary
import org.gedstudio.library.bukkit.GedLibrary;
import org.gedstudio.library.bukkit.chat.GChatColor;
import org.gedstudio.library.bukkit.configuration.JsonConfiguration;

//Java
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EzTools extends JavaPlugin {

    private static EzTools ezTools;
    private static CommandMap commandMap;
    private static GuiHandler guiHandler;
    private static ItemHandler itemHandler;
    private static ConfigHandler configHandler;

    private static JsonConfiguration lang_message;
    private static JsonConfiguration lang_command;
    private static JsonConfiguration lang_gui;

    private static List<Command> registerCommands;

    private static Map<Player, Entity> selectedEntities;
    private static Map<Player, ItemStack> editingItem;
    private static Map<Player, Integer> editingLore;
    private static Map<Player, Integer> editingPage;
    private static Map<Player, EquipmentSlot> editingEquipmentSlot;
    private static Map<Player, Attribute> editingAttribute;

    @Override
    public void onEnable() {
        //Start enable EzTools

        ezTools = this;
        guiHandler = new GuiHandler(      this);
        itemHandler = new ItemHandler(    this);
        configHandler = new ConfigHandler(this);
        registerCommands = new ArrayList<>();
        selectedEntities = new HashMap<>();
        editingItem = new HashMap<>();
        editingLore = new HashMap<>();
        editingPage = new HashMap<>();
        editingEquipmentSlot = new HashMap<>();
        editingAttribute = new HashMap<>();
        //Save Config
        if (!new File("plugins/EzTools/config.yml").exists()) {
            this.saveDefaultConfig();
            this.reloadConfig();
        }
        //Saver File Check
        //If item.yml/entity.yml is not exist it will create new files
        //Your local items and entities will save in these files
        if (!new File("plugins/EzTools/items").exists()) {
            new File("plugins/EzTools/items").mkdirs();
        }
        if (!new File("plugins/EzTools/entity.yml").exists()) {
            try {
                new File("plugins/EzTools/entity.yml").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Save Language File
        //Always replace to make sure new message will write into language file and it won't throw NullPointerException
        this.saveResource("message/zh_CN.json", true);
        this.saveResource("command/zh_CN.json", true);
        this.saveResource("gui/zh_CN.json", true);
        this.saveResource("message/en_US.json", true);
        this.saveResource("command/en_US.json", true);
        this.saveResource("gui/en_US.json", true);
        //Load Language
          //Load Message Language
        lang_message = new File("plugins/EzTools/message/" + this.getConfig().getString("Setting.Language") + ".json").exists() ? new JsonConfiguration(new File("plugins/EzTools/message/" + this.getConfig().getString("Setting.Language") + ".json")) : new JsonConfiguration(new File("plugins/EzTools/message/en_US.json"));
          //Load Command Language
        lang_command = new File("plugins/EzTools/command/" + this.getConfig().getString("Setting.Language") + ".json").exists() ? new JsonConfiguration(new File("plugins/EzTools/command/" + this.getConfig().getString("Setting.Language") + ".json")) : new JsonConfiguration(new File("plugins/EzTools/command/en_US.json"));
          //Load GUI Language
        lang_gui = new File(    "plugins/EzTools/gui/" + this.getConfig().getString(    "Setting.Language") + ".json").exists() ? new JsonConfiguration(new File("plugins/EzTools/gui/" + this.getConfig().getString(    "Setting.Language") + ".json")) : new JsonConfiguration(new File("plugins/EzTools/gui/en_US.json"));
        //Get CommandMap with method in CraftServer 'getCommandMap'
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
        //Register Events
        Bukkit.getPluginManager().registerEvents(new EntityEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuiListener(),         this);
        //Register Commands
        registerCommands.add(new EzToolsCommand());
        registerCommands.add(new ItemCommand());
        registerCommands.add(new EntityCommand());
        registerCommands.add(new NbtCommand());
        registerCommands.add(new PlayerCommand());

        //Finish enable EzTools

        //Enabled Message
        this.getServer().getConsoleSender().sendMessage("§b================================");
        this.getServer().getConsoleSender().sendMessage("§aEzTools has been enabled");
        this.getServer().getConsoleSender().sendMessage("§aEzTools - More language supported!");
        this.getServer().getConsoleSender().sendMessage("§aAuthors: DeeChael");
        this.getServer().getConsoleSender().sendMessage("§aMy Spigot Page: https://www.spigotmc.org/members/deechael.883670/");
        this.getServer().getConsoleSender().sendMessage("§aMy McBBS Page: https://www.mcbbs.net/?2536446");
        this.getServer().getConsoleSender().sendMessage("§aGithub Source-Code: https://github.com/DeeChael/EzTools");
        this.getServer().getConsoleSender().sendMessage("§b================================");

        //Start AnvilAPI...
        GedLibrary.enable();
    }

    public void reload() {
        getEzTools().reloadConfig();
        getEzTools().saveResource("message/zh_CN.json", true);
        getEzTools().saveResource("command/zh_CN.json", true);
        getEzTools().saveResource("gui/zh_CN.json",     true);
        getEzTools().saveResource("message/en_US.json", true);
        getEzTools().saveResource("command/en_US.json", true);
        getEzTools().saveResource("gui/en_US.json",     true);
        //Load Language
        //Load Message Language
        lang_message = new File("plugins/EzTools/message/" + EzTools.getEzTools().getConfig().getString("Setting.Language") + ".json").exists() ? new JsonConfiguration(new File("plugins/EzTools/message/" + EzTools.getEzTools().getConfig().getString("Setting.Language") + ".json")) : new JsonConfiguration(new File("plugins/EzTools/message/en_US.json"));
        //Load Command Language
        lang_command = new File("plugins/EzTools/command/" + EzTools.getEzTools().getConfig().getString("Setting.Language") + ".json").exists() ? new JsonConfiguration(new File("plugins/EzTools/command/" + EzTools.getEzTools().getConfig().getString("Setting.Language") + ".json")) : new JsonConfiguration(new File("plugins/EzTools/command/en_US.json"));
        //Load GUI Language
        lang_gui = new File(    "plugins/EzTools/gui/" + EzTools.getEzTools().getConfig().getString(    "Setting.Language") + ".json").exists() ? new JsonConfiguration(new File("plugins/EzTools/gui/" + EzTools.getEzTools().getConfig().getString(    "Setting.Language") + ".json")) : new JsonConfiguration(new File("plugins/EzTools/gui/en_US.json"));
    }

    public static JsonConfiguration getLanguageMessage() {
        return lang_message;
    }

    public static JsonConfiguration getLanguageCommand() {
        return lang_command;
    }

    public static JsonConfiguration getLanguageGui() {
        return lang_gui;
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

    public static ConfigHandler getConfigHandler() {
        return configHandler;
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

    public static Map<Player, Integer> getEditingLore() {
        return editingLore;
    }

    public static Map<Player, Integer> getEditingPage() {
        return editingPage;
    }

    public static Map<Player, EquipmentSlot> getEditingEquipmentSlot() {
        return editingEquipmentSlot;
    }

    public static Map<Player, Attribute> getEditingAttribute() {
        return editingAttribute;
    }

    public static String replaceColorCode(String string) {
        return GChatColor.translate('&', string);
        //return string.replace("&0", "§0").replace("&1", "§1").replace("&2", "§2").replace("&3", "§3").replace("&4", "§4").replace("&5", "§5").replace("&6", "§6").replace("&7", "§7").replace("&8", "§8".replace("&9", "§9").replace("&a", "§a").replace("&b", "§b").replace("&c", "§c").replace("&d", "§d").replace("&e", "§e").replace("&f", "§f").replace("&k", "§k").replace("&l", "§l").replace("&m", "§m").replace("&n", "§n").replace("&o", "§o").replace("&r", "§r");
    }

    private String getNMS() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1);
    }

}