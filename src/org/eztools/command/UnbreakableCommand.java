package org.eztools.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.EzTools;

import java.util.ArrayList;
import java.util.List;

public class UnbreakableCommand extends Command {

    public UnbreakableCommand() {
        super("unbreakable");
        this.setPermission("eztools.command.unbreakable");
        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public List<String> tabComplete(CommandSender s, String label, String[] args) {
        List<String> list = new ArrayList<>();
        list.add(" ");
        return list;
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (s instanceof Player) {
            ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
            if (itemInPlayerMainHand != null) {
                ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                itemMetaOfItemInPlayerMainHand.setUnbreakable(!itemMetaOfItemInPlayerMainHand.isUnbreakable());
                itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                s.sendMessage("§a已切换物品是否不可损坏");
            } else {
                s.sendMessage("§c主手持物品 输入/unbreakable 来切换物品是否不可损坏");
            }
        } else {
            s.sendMessage("§c你必须是个玩家!");
        }
        return true;
    }

}
