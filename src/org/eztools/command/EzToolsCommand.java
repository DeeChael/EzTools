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
            list.add("developer");
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
                s.sendMessage("§bPlugin name: §dEzTools");
                s.sendMessage("§bAuthors: §dSpigotMC-DeeChael,");
                s.sendMessage("§d      McBBS-DeeChael");
                s.sendMessage("§bVersion: §d1.4.3");
                s.sendMessage("§a==============================");
            } else if (args[0].equalsIgnoreCase("reload")) {
                EzTools.reload();
                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("eztools.reload")));
            } else if (args[0].equalsIgnoreCase("developer")) {
                if (s instanceof Player) {
                    Inventory inventory = Bukkit.createInventory(null, 54, "Developer Tool");
                    for (int i = 0; i < inventory.getSize(); i++) {
                        ItemStack itemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                        ItemMeta meta = itemStack.getItemMeta();
                        meta.setDisplayName("Slot: " + i);
                        itemStack.setItemMeta(meta);
                        inventory.setItem(i, itemStack);
                    }
                    ((Player) s).openInventory(inventory);
                } else {
                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.must_be_a_player")));
                }
            } else {
                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
            }
        } else {
            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
        }
        return true;
    }

}
