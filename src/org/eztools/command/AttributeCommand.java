package org.eztools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.EzTools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AttributeCommand extends Command {

    public AttributeCommand() {
        super("attribute");
        this.setPermission("eztools.command.attribute");
        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public List<String> tabComplete(CommandSender s, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            for (Attribute attribute : Attribute.values()) {
                String name = attribute.name();
                list.add(StringUtils.lowerCase(name));
            }
        } else if (args.length == 2) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                String name = equipmentSlot.name();
                list.add(StringUtils.lowerCase(name));
            }
        } else if (args.length == 3) {
            list.add("<属性数值>");
        } else {
            list.add(" ");
        }
        return list;
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (s instanceof Player) {
            if (args.length == 3) {
                Attribute attribute = Attribute.valueOf(StringUtils.upperCase(args[0]));
                if (attribute != null) {
                    EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(StringUtils.upperCase(args[1]));
                    if (equipmentSlot != null) {
                        int number = 1;
                        try {
                            number = Integer.valueOf(args[2]);
                        } catch (NumberFormatException e) {
                            s.sendMessage("§c输入的属性数值不是一个正确的数字");
                            return true;
                        }
                        if (attribute.equals(Attribute.GENERIC_KNOCKBACK_RESISTANCE)) {
                            double doub = number / 10;
                            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "eztools", doub, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
                            ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                            ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                            itemMetaOfItemInPlayerMainHand.removeAttributeModifier(attribute);
                            itemMetaOfItemInPlayerMainHand.addAttributeModifier(attribute, attributeModifier);
                            itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                        } else {
                            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "eztools", number, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
                            ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                            ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                            itemMetaOfItemInPlayerMainHand.removeAttributeModifier(attribute);
                            itemMetaOfItemInPlayerMainHand.addAttributeModifier(attribute, attributeModifier);
                            itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                        }
                        s.sendMessage("§a已添加属性 §e" + args[0] + " §a! 在栏位 §e" + args[1] + " §a时属性增加 §e" + number);
                    } else {
                        s.sendMessage("§c错误的栏位");
                    }
                } else {
                    s.sendMessage("§c错误的属性名称");
                }
            } else {
                s.sendMessage("§c主手持物品 输入/attribute <属性名称> <栏位> <属性数值> 来更改物品名称");
            }
        } else {
            s.sendMessage("§c你必须是个玩家!");
        }
        return true;
    }

}
