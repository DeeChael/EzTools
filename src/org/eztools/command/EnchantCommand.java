package org.eztools.command;

import net.deechael.ged.library.enchant.GEnchantment;

import org.apache.commons.lang.StringUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.eztools.EzTools;

import java.util.ArrayList;
import java.util.List;

public class EnchantCommand extends Command {

    public EnchantCommand() {
        super("enchant");
        this.setPermission("eztools.command.enchant");
        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public List<String> tabComplete(CommandSender s, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            for (GEnchantment enchantment : GEnchantment.values()) {
                String name = enchantment.name();
                list.add(StringUtils.lowerCase(name));
            }
        } else if (args.length == 2) {
            list.add("<附魔等级>");
        } else {
            list.add(" ");
        }
        return list;
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (s instanceof Player) {
            if (args.length == 2) {
                GEnchantment enchantment = GEnchantment.valueOf(StringUtils.upperCase(args[0]));
                if (!(enchantment == null)) {
                    ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                    int level = 1;
                    try {
                        level = Integer.valueOf(args[1]);
                    } catch (NumberFormatException e) {
                        s.sendMessage("§c输入的附魔等级不是一个正确的数字");
                        return true;
                    }
                    ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                    itemMetaOfItemInPlayerMainHand.addEnchant(enchantment.getHandle(), level, true);
                    itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                    s.sendMessage("§a已附魔 §e" + args[0] + " §a等级为 §e" + level);
                } else {
                    s.sendMessage("§c错误的附魔名称");
                }
            } else {
                s.sendMessage("§c主手持物品 输入/enchant <附魔名称> <附魔等级> 来添加附魔");
            }
        } else {
            s.sendMessage("§c你必须是个玩家!");
        }
        return true;
    }

}
