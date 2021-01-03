package org.eztools.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.EzTools;

import java.util.ArrayList;
import java.util.List;

public class EzToolsCommand extends Command {

    public EzToolsCommand() {
        super("eztools");
        this.setPermission("eztools.command.eztools");
        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public List<String> tabComplete(CommandSender s, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("info");
            list.add("reload");
            list.add("author_tool");
        } else {
            list.add(" ");
        }
        return list;
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("info")) {
                s.sendMessage("§a==============================");
                s.sendMessage("§b插件名: §dEzTools");
                s.sendMessage("§b作者: §dSpigotMC-DeeChael,");
                s.sendMessage("§d      McBBS-DeeChael");
                s.sendMessage("§b版本: §d1.0.0");
                s.sendMessage("§a==============================");
            } else if (args[0].equalsIgnoreCase("reload")) {
                s.sendMessage("§c抱歉呢~ 本插件暂时没有功能需要重载~");
            } else if (args[0].equalsIgnoreCase("author_tool")) {
                Inventory inventory = Bukkit.createInventory(null, 54, "开发者工具");
                for (int i = 0; i < inventory.getSize(); i++) {
                    ItemStack itemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                    ItemMeta meta = itemStack.getItemMeta();
                    meta.setDisplayName("栏位: " + i);
                    itemStack.setItemMeta(meta);
                    inventory.setItem(i, itemStack);
                }
                ((Player) s).openInventory(inventory);
            } else {
                s.sendMessage("§c未知的指令用法");
            }
        } else {
            s.sendMessage("§c未知的指令用法");
        }
        return true;
    }
}
