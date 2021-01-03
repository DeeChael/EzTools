package org.eztools.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.EzTools;

import java.util.ArrayList;
import java.util.List;

public class NameCommand extends Command {

    public NameCommand() {
        super("name");
        this.setPermission("eztools.command.name");
        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public List<String> tabComplete(CommandSender s, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length >= 1) {
            list.add("<物品名称>");
        } else {
            list.add(" ");
        }
        return list;
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (s instanceof Player) {
            if (args.length >= 1) {
                String itemName = "";
                for (String string : args) {
                    itemName += (string + " ").replace("&", "§");
                }
                itemName = itemName.substring(0, itemName.length() - 1);
                ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                itemMetaOfItemInPlayerMainHand.setDisplayName(itemName);
                itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                s.sendMessage("§a已将物品名称设置为 " + itemName);
            } else {
                s.sendMessage("§c主手持物品 输入/name <物品名称> 来更改物品名称");
            }
        } else {
            s.sendMessage("§c你必须是个玩家!");
        }
        return true;
    }

}
