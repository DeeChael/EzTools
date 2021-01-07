package org.eztools;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.enchantment.Enchantment;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConfigHandler {

    public final EzTools ezTools;

    public ConfigHandler(EzTools ezTools) {
        this.ezTools = ezTools;
    }


    public ItemStack getItemStack(String key) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/item.yml"));
        try {
            config.load(new File("plugins/EzTools/item.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        if (!config.contains(key)) return new ItemStack(Material.AIR);
        ItemStack itemStack = new ItemStack(Material.valueOf(config.getString(key + ".material")));
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (config.contains(key + ".display")) {
            itemMeta.setDisplayName(config.getString(key + ".display"));
        }
        if (config.contains(key + ".lore")) {
            itemMeta.setLore(config.getStringList(key + ".lore"));
        }
        itemMeta.setUnbreakable(config.getBoolean(key + ".unbreakable"));
        if (config.contains(key + ".attribute")) {
            ConfigurationSection cs1 = config.getConfigurationSection(key + ".attribute");
            if (cs1 != null) {
                for (String k : cs1.getKeys(false)) {
                    if (Attribute.valueOf(k) != null) {
                        ConfigurationSection cs2 = config.getConfigurationSection(key + ".attribute." + k);
                        Attribute attribute = Attribute.valueOf(k);
                        for (String ke : cs2.getKeys(false)) {
                            if (EquipmentSlot.valueOf(ke) != null) {
                                double amount = config.getDouble(key + ".attribute." + k + "." + ke + ".amount");
                                AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "eztools", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.valueOf(ke));
                                itemMeta.addAttributeModifier(attribute, attributeModifier);
                            }
                        }
                    }
                }
            }
        }
        if (config.contains(key + ".enchantment")) {
            ConfigurationSection cs3 = config.getConfigurationSection(key + ".enchantment");
            if (cs3 != null) {
                for (String k : cs3.getKeys(false)) {
                    if (Enchantment.valueOf(k) != null) {
                        org.bukkit.enchantments.Enchantment enchantment = Enchantment.valueOf(k).getEnchantment();
                        int level = config.getInt(key + ".enchantment." + k + ".level");
                        itemMeta.addEnchant(enchantment, level, true);
                    }
                }
            }
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void setItemStack(String key, ItemStack itemStack) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/item.yml"));
        try {
            config.load(new File("plugins/EzTools/item.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        config.set(key + ".material", itemStack.getType().name());
        if (itemStack.getItemMeta().hasDisplayName()) {
            config.set(key + ".display", itemStack.getItemMeta().getDisplayName());
        }
        if (itemStack.getItemMeta().hasLore()) {
            config.set(key + ".lore", itemStack.getItemMeta().getLore());
        }
        config.set(key + ".unbreakable", itemStack.getItemMeta().isUnbreakable());
        if (itemStack.getItemMeta().hasAttributeModifiers()) {
            for (Attribute attribute : itemStack.getItemMeta().getAttributeModifiers().keys()) {
                Map<EquipmentSlot, Double> map = new HashMap<>();
                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    map.put(equipmentSlot, 0.0);
                }
                for (AttributeModifier attributeModifier : itemStack.getItemMeta().getAttributeModifiers(attribute)) {
                    map.put(attributeModifier.getSlot(), attributeModifier.getAmount() + map.get(attributeModifier.getSlot()));
                }
                for (EquipmentSlot equipmentSlot : map.keySet()) {
                    if (map.get(equipmentSlot) != 0.0) {
                        config.set(key + ".attribute." + attribute.name() + "." + equipmentSlot.name() + ".amount", map.get(equipmentSlot));
                    }
                }
            }
        }
        if (itemStack.getItemMeta().hasEnchants()) {
            for (org.bukkit.enchantments.Enchantment enchantment : itemStack.getItemMeta().getEnchants().keySet()) {
                Enchantment ench = Enchantment.fromEnchantment(enchantment);
                int level = itemStack.getItemMeta().getEnchantLevel(enchantment);
                config.set(key + ".enchantment." + ench.name() + ".level", level);
            }
        }
        try {
            config.save(new File("plugins/EzTools/item.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean containsItemStack(String key) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/item.yml"));
        try {
            config.load(new File("plugins/EzTools/item.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        for (String string : config.getKeys(false)) {
            if (string.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getItemStacksStringList() {
        List<String> list = new ArrayList<>();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/item.yml"));
        try {
            config.load(new File("plugins/EzTools/item.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        for (String key : config.getKeys(false)) {
            list.add(key);
        }
        return list;
    }

    public List<ItemStack> getItemStacks() {
        List<ItemStack> list = new ArrayList<>();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/item.yml"));
        try {
            config.load(new File("plugins/EzTools/item.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        for (String key : config.getKeys(false)) {
            list.add(this.getItemStack(key));
        }
        return list;
    }

}
