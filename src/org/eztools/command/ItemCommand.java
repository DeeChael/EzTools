package org.eztools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
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
import org.gedstudio.library.bukkit.configuration.JsonConfiguration;
import org.gedstudio.library.bukkit.enchant.GEnchantment;

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
            list.add("save");
            list.add("load");
            list.add("name");
            list.add("lore");
            list.add("enchant");
            list.add("attribute");
            list.add("unbreakable");
        } else if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("name")) {
                list.add(EzTools.getLanguageCommand().getString("eztools.args_2.item.name.<itemName>"));
            } else if (args[0].equalsIgnoreCase("enchant")) {
                if (args.length == 2) {
                    for (GEnchantment enchantment : GEnchantment.values()) {
                        String name = enchantment.name();
                        list.add(StringUtils.lowerCase(name));
                    }
                } else if (args.length == 3) {
                    list.add(EzTools.getLanguageCommand().getString("eztools.args_3.item.enchant.<enchantLevel>"));
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
                        list.add(EzTools.getLanguageCommand().getString("eztools.args_5.item.attribute.<attributeAmount>"));
                    } else {
                        list.add(" ");
                    }
                } else {
                    list.add(" ");
                }
            } else if (args[0].equalsIgnoreCase("save")) {
                list.add(EzTools.getLanguageCommand().getString("eztools.args_2.item.save.<localName>"));
            } else if (args[0].equalsIgnoreCase("load")) {
                for (String string : EzTools.getConfigHandler().getItemStacksStringList()) {
                    list.add(string);
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
                        ((Player) s).playSound(((Player) s).getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT, 1f, 1f);
                        EzTools.getGuiHandler().openInventory((Player) s, GuiHandler.InventoryType.ITEM_MAIN, ((Player) s).getInventory().getItemInMainHand());
                    } else if (args[0].equalsIgnoreCase("unbreakable")) {
                        ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                        ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                        itemMetaOfItemInPlayerMainHand.setUnbreakable(!itemMetaOfItemInPlayerMainHand.isUnbreakable());
                        itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.unbreakable.switch")));
                    } else if (args[0].equalsIgnoreCase("lore")) {
                        ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                        ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                        itemMetaOfItemInPlayerMainHand.setLore(new ArrayList<>());
                        itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.lore.clear")));
                    } else {
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                    }
                } else if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("name")) {
                        String itemName = "";
                        for (int i = 1; i < args.length; i++) {
                            String string = args[i];
                            itemName += EzTools.replaceColorCode((string + " "));
                        }
                        itemName = itemName.substring(0, itemName.length() - 1);
                        ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                        ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                        itemMetaOfItemInPlayerMainHand.setDisplayName(itemName);
                        itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.name.success")).replace("%item_name%", itemName));
                    } else if (args[0].equalsIgnoreCase("lore")) {
                        ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                        ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                        String loreJson = "";
                        for (int i = 1; i < args.length; i++) {
                            String string = args[i];
                            loreJson += EzTools.replaceColorCode((string + " "));
                        }
                        loreJson = loreJson.substring(0, loreJson.length() - 1);
                        List<String> lore = new ArrayList<>();
                        for(JsonConfiguration json : JsonConfiguration.fromJsonArray(loreJson)) {
                            lore.add(json.getString("text"));
                        }
                        itemMetaOfItemInPlayerMainHand.setLore(lore);
                        itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.lore.success")));
                    } else if (args[0].equals("save")) {
                        if (args.length == 2) {
                            String key = args[1];
                            EzTools.getConfigHandler().setItemStack(key, ((Player) s).getInventory().getItemInMainHand());
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.save.success").replace("%local_name%", key)));
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                        }
                    } else if (args[0].equals("load")) {
                        if (args.length == 2) {
                            String key = args[1];
                            if (EzTools.getConfigHandler().containsItemStack(key)) {
                                ItemStack got = EzTools.getConfigHandler().getItemStack(key);
                                ((Player) s).getInventory().addItem(got);
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.load.success").replace("%local_name%", key)));
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.load.not_exist").replace("%local_name%", key)));
                            }
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                        }
                    } else if (args[0].equalsIgnoreCase("enchant")) {
                        if (args.length == 3) {
                            GEnchantment enchantment = GEnchantment.valueOf(StringUtils.upperCase(args[1]));
                            if (!(enchantment == null)) {
                                ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                                int level = 1;
                                try {
                                    level = Integer.valueOf(args[2]);
                                } catch (NumberFormatException e) {
                                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.integer")));
                                    return true;
                                }
                                if (level != 0) {
                                    ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                                    itemMetaOfItemInPlayerMainHand.addEnchant(enchantment.getHandle(), level, true);
                                    itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.enchant.success")).replace("%enchant_name%", args[1]).replace("%level%", level + ""));
                                } else {
                                    ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                                    itemMetaOfItemInPlayerMainHand.removeEnchant(enchantment.getHandle());
                                    itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.enchant.remove")).replace("%enchant_name%", args[1]));
                                }
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                            }
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
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
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.attribute.remove")).replace("%attribute%", args[1]).replace("%slot%", args[2]));
                                    } else {
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                                    }
                                } else {
                                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                                }
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                            }
                        } else if (args.length == 5) {
                            Attribute attribute = Attribute.valueOf(StringUtils.upperCase(args[1]));
                            if (attribute != null) {
                                EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(StringUtils.upperCase(args[2]));
                                if (equipmentSlot != null) {
                                    double number = 1;
                                    try {
                                        number = Double.valueOf(args[4]);
                                    } catch (NumberFormatException e) {
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.integer")));
                                        return true;
                                    }
                                    if (args[3].equalsIgnoreCase("add")) {
                                        double num = 0;
                                        double addedInt = 0.0;
                                        double finalInt = 0.0;
                                        if (attribute.equals(Attribute.GENERIC_KNOCKBACK_RESISTANCE)) {
                                            double doub = number / 10;
                                            addedInt = doub;
                                            ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                                            ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                                            assert itemMetaOfItemInPlayerMainHand != null;
                                            if (itemMetaOfItemInPlayerMainHand.hasAttributeModifiers()) {
                                                if (itemMetaOfItemInPlayerMainHand.getAttributeModifiers(attribute) != null) {
                                                    if (itemMetaOfItemInPlayerMainHand.getAttributeModifiers(attribute).size() != 0) {
                                                        for (AttributeModifier attributeModifier : itemMetaOfItemInPlayerMainHand.getAttributeModifiers(attribute)) {
                                                            num += attributeModifier.getAmount();
                                                        }
                                                    }
                                                }
                                                itemMetaOfItemInPlayerMainHand.removeAttributeModifier(attribute);
                                            }
                                            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "eztools", (doub + num), AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
                                            itemMetaOfItemInPlayerMainHand.addAttributeModifier(attribute, attributeModifier);
                                            itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                                            finalInt = doub + num;
                                        } else {
                                            addedInt = number;
                                            ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                                            ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                                            for (AttributeModifier attributeModifier : itemMetaOfItemInPlayerMainHand.getAttributeModifiers(attribute)) {
                                                num += attributeModifier.getAmount();
                                            }
                                            itemMetaOfItemInPlayerMainHand.removeAttributeModifier(attribute);
                                            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "eztools", (number + num), AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
                                            itemMetaOfItemInPlayerMainHand.addAttributeModifier(attribute, attributeModifier);
                                            itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                                            finalInt = number + num;
                                        }
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.attribute.add").replace("%attribute%", args[1]).replace("%slot%", args[2]).replace("%final%", finalInt + "").replace("%added%", addedInt + "")));
                                    } else if (args[3].equalsIgnoreCase("set")) {
                                        double finalInt = 0.0;
                                        if (attribute.equals(Attribute.GENERIC_KNOCKBACK_RESISTANCE)) {
                                            double doub = number / 10;
                                            finalInt = doub;
                                            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "eztools", doub, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
                                            ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                                            ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                                            itemMetaOfItemInPlayerMainHand.removeAttributeModifier(attribute);
                                            itemMetaOfItemInPlayerMainHand.addAttributeModifier(attribute, attributeModifier);
                                            itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                                        } else {
                                            finalInt = number;
                                            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "eztools", number, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
                                            ItemStack itemInPlayerMainHand = ((Player) s).getInventory().getItemInMainHand();
                                            ItemMeta itemMetaOfItemInPlayerMainHand = itemInPlayerMainHand.getItemMeta();
                                            itemMetaOfItemInPlayerMainHand.removeAttributeModifier(attribute);
                                            itemMetaOfItemInPlayerMainHand.addAttributeModifier(attribute, attributeModifier);
                                            itemInPlayerMainHand.setItemMeta(itemMetaOfItemInPlayerMainHand);
                                        }
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.attribute.set")).replace("%attribute%", args[1]).replace("%slot%", args[2]).replace("%final%", finalInt + ""));
                                    } else {
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                                    }
                                } else {
                                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                                }
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                            }
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                        }
                    }
                } else {
                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                }
            } else {
                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.hold_item")));
            }
        } else {
            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.must_be_a_player")));
        }
        return true;
    }

}
