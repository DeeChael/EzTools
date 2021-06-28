package org.eztools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.eztools.api.command.CommandManager;
import org.eztools.api.config.Language;
import org.eztools.api.item.ItemSaver;
import org.eztools.api.item.TransferTool;
import org.eztools.api.storage.Storage;
import org.eztools.impl.CommandManagerImpl;
import org.eztools.impl.ItemSaverImpl;
import org.eztools.impl.TransferToolImpl;
import org.eztools.listener.ServerLoadListener;
import org.eztools.storage.JsonStorage;
import org.eztools.storage.MysqlStorage;
import org.eztools.storage.YamlStorage;
import org.eztools.utils.ReflectionAPI;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public final class EzT extends JavaPlugin {

    private static EzT INSTANCE;

    public final static CommandMap BUKKIT_COMMAND_MAP;

    private final CommandManager commandManager = new CommandManagerImpl();

    private final TransferTool transferTool = new TransferToolImpl();

    private static Language USING_LANGUAGE;

    private static Storage ITEM_STORAGE;

    private static ItemSaver itemSaver;

    static {
        CommandMap commandMap = null;
        final Class<?> craftServerClass = Bukkit.getServer().getClass();
        try {
            commandMap = (CommandMap) craftServerClass.getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        BUKKIT_COMMAND_MAP = commandMap;
    }

    /*
     *  I'm too lazy to code nms with reflection
     */
    @Override
    public void onEnable() {
        INSTANCE = this;
        this.getServer().getPluginManager().registerEvents(new ServerLoadListener(), this);
        EzT.reload();
        try {
            Class<?> clazz = Class.forName("lib.ezt.nms." + ReflectionAPI.getServerVersion() + ".EzT");
            Constructor<?> constructor = clazz.getConstructor(EzT.class);
            Object ezTLib = constructor.newInstance(this);
            clazz.getDeclaredMethod("load").invoke(ezTLib);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
            this.getLogger().severe(EzT.getUsingLanguage().get("ezt.loading.failed.loadLibrary"));
        }
        commandManager.register();
        this.getServer().getConsoleSender().sendMessage("§b" + EzT.getUsingLanguage().get("ezt.loading.succeeded"));
    }

    @Override
    public void onDisable() {
        if (ITEM_STORAGE instanceof MysqlStorage) {
            try {
                ((MysqlStorage) ITEM_STORAGE).close();
            } catch (SQLException ignored) {
            }
        }
    }

    public static Language getUsingLanguage() {
        return USING_LANGUAGE;
    }

    public static FileConfiguration getConfiguration() {
        return getInstance().getConfig();
    }

    public static ItemSaver getItemSaver() {
        return itemSaver;
    }

    public static Storage getItemStorage() {
        return ITEM_STORAGE;
    }

    public static EzT getInstance() {
        return INSTANCE;
    }

    public static TransferTool getTransferTool() {
        return getInstance().transferTool();
    }

    private TransferTool transferTool() {
        return transferTool;
    }

    public static boolean reload() {
        try {
            if (!(new File("plugins/EzT/config.yml")).exists()) {
                new File("plugins/EzT/config.yml").createNewFile();
            }
            getInstance().reloadConfig();
            getConfiguration().set("plugin_tips", "language supports zh_cn and en_us, storage support yaml, json, sqlite and mysql!");

            if (!getConfiguration().contains("setting.language")) getConfiguration().set("setting.language", "en_us");
            if (!getConfiguration().contains("setting.storage")) getConfiguration().set("setting.storage", "yaml");
            getInstance().saveConfig();
            getInstance().reloadConfig();
            switch (getConfiguration().getString("setting.storage", "yaml")) {
                case "json":
                    ITEM_STORAGE = new JsonStorage(new File("plugins/EzT/storage/items.json"));
                case "yaml":
                default:
                    ITEM_STORAGE = new YamlStorage(new File("plugins/EzT/storage/items.yaml"));
            }
            itemSaver = new ItemSaverImpl(ITEM_STORAGE);
            loadLanguage();
            return true;
        } catch (IOException ignored) {
        }
        return false;
    }

    private static void loadLanguage() throws IOException {
        USING_LANGUAGE = new Language(getInstance(), getConfiguration().getString("setting.language", "en_us"));
        //Plugin loading message
        USING_LANGUAGE.setDefault("ezt.loading.failed.loadLibrary", "Could not find the library class! Failed to load EzTools!");
        USING_LANGUAGE.setDefault("ezt.loading.succeeded", "EzTools Enabled");
        //Plugin Command message
        //Command - EzT
        USING_LANGUAGE.setDefault("ezt.command.line", "&b==========================");
        USING_LANGUAGE.setDefault("ezt.command.ezt.help.message", "§a/ezt-item - The command to edit item stacks\n" +
                "§a/ezt-entity - The command to edit entities except player\n" +
                "§a/ezt-player - The command to edit players\n" +
                "§a/ezt-nbt - The command to edit some special nbt\n" +
                "§a/ezt-gui - The command to open EzTools GUI to edit items or entities");
        USING_LANGUAGE.setDefault("ezt.command.ezt.info.message", "&aEzTools developed by DeeChael(DiC)");
        USING_LANGUAGE.setDefault("ezt.command.ezt.reload.success", "&aReloaded EzTools configuration files successfully");
        //Command - EzT-Item
        USING_LANGUAGE.setDefault("ezt.command.ezt-item.name.success", "&aEdited the name of item to &e{MainHandDisplayName}");
        USING_LANGUAGE.setDefault("ezt.command.ezt-item.lore.success", "&aEdited the lore of item");
        USING_LANGUAGE.setDefault("ezt.command.ezt-item.enchant.success", "&aEdited the enchantments of item successfully");
        USING_LANGUAGE.setDefault("ezt.command.ezt-item.attribute.add.success", "&aAdd a attribute modifier successfully");
        USING_LANGUAGE.setDefault("ezt.command.ezt-item.attribute.set.success", "&aSet a attribute of item successfully");
        USING_LANGUAGE.setDefault("ezt.command.ezt-item.attribute.remove.success", "&aRemove a attribute of item successfully");
        USING_LANGUAGE.setDefault("ezt.command.ezt-item.attribute.get.success", "&aThe attribute amount is &e{amount}");
        USING_LANGUAGE.setDefault("ezt.command.ezt-item.unbreakable.player.success", "&aThe unbreakable status of the item stack in your main hand has been modified");
        USING_LANGUAGE.setDefault("ezt.command.ezt-item.unbreakable.single.success", "&aYou modified the unbreakable status of the item stack in &e{target}&a's main hand");
        USING_LANGUAGE.setDefault("ezt.command.ezt-item.unbreakable.multi.success", "&aYou modified the main hand item stack unbreakable status of {target}");
        USING_LANGUAGE.save();
    }

}
