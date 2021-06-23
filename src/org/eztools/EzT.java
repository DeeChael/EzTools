package org.eztools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.eztools.api.command.CommandManager;
import org.eztools.api.config.Language;
import org.eztools.impl.CommandManagerImpl;
import org.eztools.utils.ReflectionAPI;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class EzT extends JavaPlugin {

    private static EzT INSTANCE;

    public final static CommandMap BUKKIT_COMMAND_MAP;

    private CommandManager commandManager = new CommandManagerImpl();

    public static Language LANGUAGE;

    static {
        CommandMap commandMap = null;
        final Class<?> craftServerClass = Bukkit.getServer().getClass();
        try {
            commandMap = (CommandMap) craftServerClass.getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
        }
        BUKKIT_COMMAND_MAP = commandMap;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        LANGUAGE = new Language(this, "en_us");
        //Plugin loading message
        LANGUAGE.setDefault("ezt.loading.failed.loadLibrary", "Could not find the library class! Failed to load EzTools!");
        LANGUAGE.setDefault("ezt.loading.succeeded", "EzTools Enabled");
        //Plugin Command message
        //Command - EzT
        LANGUAGE.setDefault("ezt.command.line", "&b==========================");
        LANGUAGE.setDefault("ezt.command.ezt.help.message", "§a/item - The command to edit item stacks\n" +
                "§a/entity - The command to edit entities except player\n" +
                "§a/player - The command to edit players\n" +
                "§a/nbt - The command to edit some special nbt\n" +
                "§a/ezgui - The command to open EzTools GUI to edit items or entities");
        LANGUAGE.setDefault("ezt.command.ezt.info.message", "&aEzTools developed by DeeChael(DiC)");
        //Command - EzT-Item
        LANGUAGE.setDefault("ezt.command.ezt-item.name.success", "&aEdited the name of item to &e{MainHandDisplayName}");
        LANGUAGE.setDefault("ezt.command.ezt-item.lore.success", "&aEdited the lore of item");
        LANGUAGE.setDefault("ezt.command.ezt-item.enchant.success", "&aEdited the enchantments of item successfully");
        LANGUAGE.setDefault("ezt.command.ezt-item.attribute.add.success", "&aAdd a attribute modifier successfully");
        LANGUAGE.setDefault("ezt.command.ezt-item.attribute.set.success", "&aSet a attribute of item successfully");
        LANGUAGE.setDefault("ezt.command.ezt-item.attribute.remove.success", "&aRemove a attribute of item successfully");
        LANGUAGE.setDefault("ezt.command.ezt-item.attribute.set.success", "&aThe attribute amount is &e{amount}");
        LANGUAGE.setDefault("ezt.command.ezt-item.unbreakable.player.success", "&aThe unbreakable status of the item stack in your main hand has been modified");
        LANGUAGE.setDefault("ezt.command.ezt-item.unbreakable.single.success", "&aYou modified the unbreakable status of the item stack in &e{target}&a's main hand");
        LANGUAGE.setDefault("ezt.command.ezt-item.unbreakable.multi.success", "&aYou modified the main hand item stack unbreakable status of {target}");
        LANGUAGE.save();
        try {
            Class<?> clazz = Class.forName("lib.ezt.nms." + ReflectionAPI.getServerVersion() + ".EzT");
            Constructor<?> constructor = clazz.getConstructor(EzT.class);
            Object ezTLib = constructor.newInstance(this);
            clazz.getDeclaredMethod("load").invoke(ezTLib);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
            this.getLogger().severe(EzT.LANGUAGE.getString("ezt.loading.failed.loadLibrary"));
        }
        commandManager.register();
        this.getServer().getConsoleSender().sendMessage("§b" + EzT.LANGUAGE.getString("ezt.loading.succeeded"));
    }

    public static EzT getInstance() {
        return INSTANCE;
    }

}
