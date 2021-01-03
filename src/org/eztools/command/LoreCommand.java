package org.eztools.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.EzTools;

import java.util.ArrayList;
import java.util.List;

public class LoreCommand extends Command {

    public LoreCommand() {
        super("lore");
        this.setPermission("eztools.command.lore");
        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public List<String> tabComplete(CommandSender s, String label, String[] args) {
        List<String> list = new ArrayList<>();
        list.add("<第 " + (args.length) + " 行介绍>");
        return list;
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (s instanceof Player) {
            if (args.length == 0) {
                ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                itemMetaOfItemInPlayerMainHand.setLore(new ArrayList<>());
                itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                s.sendMessage("§a已清除物品介绍");
            } else {
                ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                List<String> lore = new ArrayList<>();
                for (String line : args) {
                    lore.add(line.replace("&", "§").replace("^space^", "^space^"));
                }
                itemMetaOfItemInPlayerMainHand.setLore(lore);
                itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                s.sendMessage("§a已添加物品介绍");
            }
        } else {
            s.sendMessage("§c你必须是个玩家!");
        }
        return true;
    }
}
