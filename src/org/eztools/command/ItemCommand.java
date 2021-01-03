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
import org.eztools.GuiHandler;
import org.eztools.enchantment.Enchantment;
import org.eztools.util.JsonConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemCommand extends Command {

    public ItemCommand() {
        super("item");
        this.setPermission("eztools.command.item");

        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public List<String> tabComplete(CommandSender s, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("gui");
            list.add("name");
            list.add("lore");
            list.add("enchant");
            list.add("attribute");
            list.add("unbreakable");
        } else if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("name")) {
                list.add("<物品名称>");
            } else if (args[0].equalsIgnoreCase("enchant")) {
                if (args.length == 2) {
                    for (Enchantment enchantment : Enchantment.values()) {
                        String name = enchantment.name();
                        list.add(StringUtils.lowerCase(name));
                    }
                } else if (args.length == 3) {
                    list.add("<附魔等级>");
                } else {
                    list.add(" ");
                }
            } else if (args[0].equalsIgnoreCase("attribute")) {
                if (args.length == 2) {
                    for (Attribute attribute : Attribute.values()) {
                        String name = attribute.name();
                        list.add(StringUtils.lowerCase(name));
                    }
                } else if (args.length == 3) {
                    for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                        String name = equipmentSlot.name();
                        list.add(StringUtils.lowerCase(name));
                    }
                } else if (args.length == 4) {
                    list.add("add");
                    list.add("set");
                    list.add("remove");
                } else if (args.length == 5) {
                    if (!args[3].equalsIgnoreCase("remove")) {
                        list.add("<属性数值>");
                    } else {
                        list.add(" ");
                    }
                } else {
                    list.add(" ");
                }
            } else {
                list.add(" ");
            }
        } else {
            list.add(" ");
        }
        return list;
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (s instanceof Player) {
            if (((Player) s).getInventory().getItemInMainHand() != null) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("gui")) {
                        EzTools.getGuiHandler().openInventory((Player) s, GuiHandler.InventoryType.ITEM_MAIN, ((Player) s).getInventory().getItemInMainHand());
                    } else if (args[0].equalsIgnoreCase("unbreakable")) {
                        ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                        ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                        itemMetaOfItemInPlayerMainHand.setUnbreakable(!itemMetaOfItemInPlayerMainHand.isUnbreakable());
                        itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                        s.sendMessage("§a已切换物品是否不可损坏");
                    } else if (args[0].equalsIgnoreCase("lore")) {
                        ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                        ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                        itemMetaOfItemInPlayerMainHand.setLore(new ArrayList<>());
                        itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                        s.sendMessage("§a已清除物品介绍");
                    } else {
                        s.sendMessage("§c错误的指令用法");
                    }
                } else if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("name")) {
                        String itemName = "";
                        for (int i = 1; i < args.length; i++) {
                            String string = args[i];
                            itemName += (string + " ").replace("&", "§");
                        }
                        itemName = itemName.substring(0, itemName.length() - 1);
                        ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                        ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                        itemMetaOfItemInPlayerMainHand.setDisplayName(itemName);
                        itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                        s.sendMessage("§a已将物品名称设置为 " + itemName);
                    } else if (args[0].equalsIgnoreCase("lore")) {
                        ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                        ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                        String loreJson = "";
                        for (int i = 1; i < args.length; i++) {
                            String string = args[i];
                            loreJson += (string + " ").replace("&", "§");
                        }
                        loreJson = loreJson.substring(0, loreJson.length() - 1);
                        List<String> lore = new ArrayList<>();
                        for(JsonConfiguration json : JsonConfiguration.asJsonArray(loreJson)) {
                            lore.add(json.getString("text"));
                        }
                        itemMetaOfItemInPlayerMainHand.setLore(lore);
                        itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                        s.sendMessage("§a已添加物品介绍");
                    } else if (args[0].equalsIgnoreCase("enchant")) {
                        if (args.length == 3) {
                            Enchantment enchantment = Enchantment.valueOf(StringUtils.upperCase(args[1]));
                            if (!(enchantment == null)) {
                                ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                                int level = 1;
                                try {
                                    level = Integer.valueOf(args[2]);
                                } catch (NumberFormatException e) {
                                    s.sendMessage("§c输入的附魔等级不是一个正确的数字");
                                    return true;
                                }
                                if (level != 0) {
                                    ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                                    itemMetaOfItemInPlayerMainHand.addEnchant(enchantment.getEnchantment(), level, true);
                                    itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                                    s.sendMessage("§a已附魔 §e" + args[1] + " §a等级为 §e" + level);
                                } else {
                                    ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                                    itemMetaOfItemInPlayerMainHand.removeEnchant(enchantment.getEnchantment());
                                    itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                                    s.sendMessage("§a已删除附魔 §e" + args[1]);
                                }
                            } else {
                                s.sendMessage("§c错误的附魔名称");
                            }
                        } else {
                            s.sendMessage("§c主手持物品 输入/item enchant <附魔名称> <附魔等级> 来添加附魔");
                        }
                    } else if (args[0].equalsIgnoreCase("attribute")) {
                        if (args.length == 4) {
                            Attribute attribute = Attribute.valueOf(StringUtils.upperCase(args[1]));
                            if (attribute != null) {
                                EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(StringUtils.upperCase(args[2]));
                                if (equipmentSlot != null) {
                                    if (args[3].equalsIgnoreCase("remove")) {
                                        ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                                        ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                                        itemMetaOfItemInPlayerMainHand.removeAttributeModifier(attribute);
                                        itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                                        s.sendMessage("§a已删除属性 §e" + args[1] + "§a 在栏位 §e" + args[2] + " §a时的属性");
                                    } else {
                                        s.sendMessage("§c错误的指令用法");
                                    }
                                } else {
                                    s.sendMessage("§c错误的栏位");
                                }
                            } else {
                                s.sendMessage("§c错误的属性名称");
                            }
                        } else if (args.length == 5) {
                            Attribute attribute = Attribute.valueOf(StringUtils.upperCase(args[1]));
                            if (attribute != null) {
                                EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(StringUtils.upperCase(args[2]));
                                if (equipmentSlot != null) {
                                    int number = 1;
                                    try {
                                        number = Integer.valueOf(args[4]);
                                    } catch (NumberFormatException e) {
                                        s.sendMessage("§c输入的属性数值不是一个正确的数字");
                                        return true;
                                    }
                                    if (args[3].equalsIgnoreCase("add")) {
                                        double num = 0;
                                        if (attribute.equals(Attribute.GENERIC_KNOCKBACK_RESISTANCE)) {
                                            double doub = number / 10;
                                            ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                                            ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                                            for (AttributeModifier attributeModifier : itemMetaOfItemInPlayerMainHand.getAttributeModifiers(attribute)) {
                                                num += attributeModifier.getAmount();
                                            }
                                            itemMetaOfItemInPlayerMainHand.removeAttributeModifier(attribute);
                                            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "eztools", (doub + num), AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
                                            itemMetaOfItemInPlayerMainHand.addAttributeModifier(attribute, attributeModifier);
                                            itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                                        } else {
                                            ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                                            ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                                            for (AttributeModifier attributeModifier : itemMetaOfItemInPlayerMainHand.getAttributeModifiers(attribute)) {
                                                num += attributeModifier.getAmount();
                                            }
                                            itemMetaOfItemInPlayerMainHand.removeAttributeModifier(attribute);
                                            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "eztools", (number + num), AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
                                            itemMetaOfItemInPlayerMainHand.addAttributeModifier(attribute, attributeModifier);
                                            itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                                        }
                                        s.sendMessage("§a使属性 §e" + args[1] + "§a 在栏位 §e" + args[2] + " §a时属性的为 §e" + (num + number) + "§a(增加了 " + number + ")");
                                    } else if (args[3].equalsIgnoreCase("set")) {
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
                                        s.sendMessage("§a已设置属性 §e" + args[1] + "§a 在栏位 §e" + args[2] + " §a时属性增加 §e" + number);
                                    } else {
                                        s.sendMessage("§c错误的指令用法");
                                    }
                                } else {
                                    s.sendMessage("§c错误的栏位");
                                }
                            } else {
                                s.sendMessage("§c错误的属性名称");
                            }
                        } else {
                            s.sendMessage("§c主手持物品 输入/item attribute <属性名称> <栏位> <操作> <属性数值> 来更改物品名称");
                        }
                    }
                } else {
                    s.sendMessage("§c错误的指令用法");
                }
            } else {
                s.sendMessage("§c请手持物品使用此指令");
            }
        } else {
            s.sendMessage("§c你必须是个玩家!");
        }
        return true;
    }

}
