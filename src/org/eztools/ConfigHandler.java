package org.eztools;

//Bukkit API
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

//new GedLibrary
import org.gedstudio.library.bukkit.enchant.GEnchantment;
import org.gedstudio.library.bukkit.inventory.GItem;

//Java
import java.io.*;
import java.util.*;

public class ConfigHandler {

    public final EzTools ezTools;

    public ConfigHandler(EzTools ezTools) {
        this.ezTools = ezTools;
    }


    public ItemStack getItemStack(String key) {
        File file = new File("plugins/EzTools/items/" + key + ".eztool");
        ItemStack itemStack = new ItemStack(Material.AIR);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            Object object = objectInputStream.readObject();
            if (object instanceof GItem) {
                itemStack = ((GItem) object).getHandle();
            }
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*
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
                        Enchantment enchantment = Enchantment.valueOf(k).getEnchantment();
                        int level = config.getInt(key + ".enchantment." + k + ".level");
                        itemMeta.addEnchant(enchantment, level, true);
                    }
                }
            }
        }
        itemStack.setItemMeta(itemMeta);
        */
        return itemStack;
    }

    public void setItemStack(String key, ItemStack itemStack) {
        File file = new File("plugins/EzTools/items/" + key + ".eztool");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(new GItem(itemStack));
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
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
            for (Enchantment enchantment : itemStack.getItemMeta().getEnchants().keySet()) {
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
        */
    }

    public boolean containsItemStack(String key) {
        File items = new File("plugins/EzTools/items");
        for (File item : items.listFiles()) {
            if (item.getName().equals(key + ".eztool")) {
                return true;
            }
        }
        /*
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
        */
        return false;
    }

    public List<String> getItemStacksStringList() {
        List<String> list = new ArrayList<>();
        File items = new File("plugins/EzTools/items");
        for (File item : items.listFiles()) {
            list.add(item.getName().replace(".eztool", ""));
        }
        /*
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/item.yml"));
        try {
            config.load(new File("plugins/EzTools/item.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        for (String key : config.getKeys(false)) {
            list.add(key);
        }
        */
        return list;
    }

    public List<ItemStack> getItemStacks() {
        List<ItemStack> list = new ArrayList<>();
        File items = new File("plugins/EzTools/items");
        for (File item : items.listFiles()) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(item));
                Object object = objectInputStream.readObject();
                if (object instanceof GItem) {
                    list.add(((GItem) object).getHandle());
                    objectInputStream.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        /*
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/item.yml"));
        try {
            config.load(new File("plugins/EzTools/item.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        for (String key : config.getKeys(false)) {
            list.add(this.getItemStack(key));
        }
        */
        return list;
    }

    public void summonEntity(Player player, String key) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/entity.yml"));
        try {
            config.load(new File("plugins/EzTools/entity.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        if (!config.contains(key)) return;
        Entity entity = player.getWorld().spawnEntity(player.getLocation(), EntityType.valueOf(config.getString(key + ".type")));
        if (config.contains(key + ".display")) {
            entity.setCustomNameVisible(true);
            entity.setCustomName(config.getString(key + ".display"));
        }
        entity.setGlowing(config.getBoolean(key + ".glowing"));
        if (entity instanceof Mob) {
            Mob mob = (Mob) entity;
            if (config.contains(key + ".item")) {
                ConfigurationSection cs1 = config.getConfigurationSection(key + ".item");
                if (cs1 != null) {
                    for (String k : cs1.getKeys(false)) {
                        if (EquipmentSlot.valueOf(k) != null) {
                            EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(k);
                            mob.getEquipment().setItem(equipmentSlot, this.getEntityItemStack(key + ".item." + k + ".item"));
                            /*
                            switch (equipmentSlot) {
                                case HAND:
                                    mob.getEquipment().setItemInMainHand(this.getItemStack(key + ".item." + k + ".item"));
                                    break;
                                case OFF_HAND:
                                    mob.getEquipment().setItemInOffHand(this.getItemStack(key + ".item." + k + ".item"));
                                    break;
                                case HEAD:
                                    mob.getEquipment().setHelmet(this.getItemStack(key + ".item." + k + ".item"));
                                    break;
                                case CHEST:
                                    mob.getEquipment().setChestplate(this.getItemStack(key + ".item." + k + ".item"));
                                    break;
                                case LEGS:
                                    mob.getEquipment().setLeggings(this.getItemStack(key + ".item." + k + ".item"));
                                    break;
                                case FEET:
                                    mob.getEquipment().setBoots(this.getItemStack(key + ".item." + k + ".item"));
                                    break;
                            }
                            */
                        }
                    }
                }
            }
            if (config.contains(key + ".chance")) {
                ConfigurationSection cs2 = config.getConfigurationSection(key + ".chance");
                if (cs2 != null) {
                    for (String k : cs2.getKeys(false)) {
                        if (EquipmentSlot.valueOf(k) != null) {
                            EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(k);
                            switch (equipmentSlot) {
                                case HAND:
                                    mob.getEquipment().setItemInMainHandDropChance((float) config.getDouble(key + ".chance." + k + ".chance"));
                                    break;
                                case OFF_HAND:
                                    mob.getEquipment().setItemInOffHandDropChance((float) config.getDouble(key + ".chance." + k + ".chance"));
                                    break;
                                case HEAD:
                                    mob.getEquipment().setHelmetDropChance((float) config.getDouble(key + ".chance." + k + ".chance"));
                                    break;
                                case CHEST:
                                    mob.getEquipment().setChestplateDropChance((float) config.getDouble(key + ".chance." + k + ".chance"));
                                    break;
                                case LEGS:
                                    mob.getEquipment().setLeggingsDropChance((float) config.getDouble(key + ".chance." + k + ".chance"));
                                    break;
                                case FEET:
                                    mob.getEquipment().setBootsDropChance((float) config.getDouble(key + ".chance." + k + ".chance"));
                                    break;
                            }
                        }
                    }
                }
            }
            if (config.contains(key + ".attribute")) {
                ConfigurationSection cs3 = config.getConfigurationSection(key + ".attribute");
                if (cs3 != null) {
                    for (String k : cs3.getKeys(false)) {
                        if (Attribute.valueOf(k) != null) {
                            Attribute attribute = Attribute.valueOf(k);
                            mob.getAttribute(attribute).setBaseValue(config.getDouble(key + ".attribute." + k + ".amount"));
                        }
                    }
                }
            }
        }
    }

    private ItemStack getEntityItemStack(String key) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/entity.yml"));
        try {
            config.load(new File("plugins/EzTools/entity.yml"));
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
                    if (GEnchantment.valueOf(k) != null) {
                        Enchantment enchantment = GEnchantment.valueOf(k).getHandle();
                        int level = config.getInt(key + ".enchantment." + k + ".level");
                        itemMeta.addEnchant(enchantment, level, true);
                    }
                }
            }
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void saveEntity(Entity entity, String key) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/entity.yml"));
        try {
            config.load(new File("plugins/EzTools/entity.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        config.set(key + ".type", entity.getType().name());
        config.set(key + ".display", entity.getCustomName());
        config.set(key + ".glowing", entity.isGlowing());
        if (entity instanceof Mob) {
            Mob mob = (Mob) entity;
            //item & chance
            //Fuck you! Drop chance can't use LivingEntity#getEquipmentSlot().setItemDropChance(EquipmentSlot, Float);! Why don't bukkit api make this method?
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                if (!mob.getEquipment().getItem(equipmentSlot).getType().equals(Material.AIR)) {
                    //this.saveEntityItemStack(key + ".item." + equipmentSlot.name() + ".item", mob.getEquipment().getItem(equipmentSlot));
                    config.set(key + ".item." + equipmentSlot.name() + ".item" + ".material", mob.getEquipment().getItem(equipmentSlot).getType().name());
                    if (mob.getEquipment().getItem(equipmentSlot).getItemMeta().hasDisplayName()) {
                        config.set(key + ".item." + equipmentSlot.name() + ".item" + ".display", mob.getEquipment().getItem(equipmentSlot).getItemMeta().getDisplayName());
                    }
                    if (mob.getEquipment().getItem(equipmentSlot).getItemMeta().hasLore()) {
                        config.set(key + ".item." + equipmentSlot.name() + ".item" + ".lore", mob.getEquipment().getItem(equipmentSlot).getItemMeta().getLore());
                    }
                    config.set(key + ".item." + equipmentSlot.name() + ".item" + ".unbreakable", mob.getEquipment().getItem(equipmentSlot).getItemMeta().isUnbreakable());
                    if (mob.getEquipment().getItem(equipmentSlot).getItemMeta().hasAttributeModifiers()) {
                        for (Attribute attribute : mob.getEquipment().getItem(equipmentSlot).getItemMeta().getAttributeModifiers().keys()) {
                            Map<EquipmentSlot, Double> map = new HashMap<>();
                            for (EquipmentSlot e : EquipmentSlot.values()) {
                                map.put(e, 0.0);
                            }
                            for (AttributeModifier attributeModifier : mob.getEquipment().getItem(equipmentSlot).getItemMeta().getAttributeModifiers(attribute)) {
                                map.put(attributeModifier.getSlot(), attributeModifier.getAmount() + map.get(attributeModifier.getSlot()));
                            }
                            for (EquipmentSlot e : map.keySet()) {
                                if (map.get(e) != 0.0) {
                                    config.set(key + ".item." + equipmentSlot.name() + ".item" + ".attribute." + attribute.name() + "." + e.name() + ".amount", map.get(e));
                                }
                            }
                        }
                    }
                    if (mob.getEquipment().getItem(equipmentSlot).getItemMeta().hasEnchants()) {
                        for (Enchantment enchantment : mob.getEquipment().getItem(equipmentSlot).getItemMeta().getEnchants().keySet()) {
                            GEnchantment ench = GEnchantment.valueOf(enchantment);
                            int level = mob.getEquipment().getItem(equipmentSlot).getItemMeta().getEnchantLevel(enchantment);
                            config.set(key + ".item." + equipmentSlot.name() + ".item" + ".enchantment." + ench.name() + ".level", level);
                        }
                    }
                    switch (equipmentSlot) {
                        case HAND:
                            config.set(key + ".chance." + equipmentSlot.name() + ".chance", mob.getEquipment().getItemInMainHandDropChance());
                            break;
                        case OFF_HAND:
                            config.set(key + ".chance." + equipmentSlot.name() + ".chance", mob.getEquipment().getItemInOffHandDropChance());
                            break;
                        case HEAD:
                            config.set(key + ".chance." + equipmentSlot.name() + ".chance", mob.getEquipment().getHelmetDropChance());
                            break;
                        case CHEST:
                            config.set(key + ".chance." + equipmentSlot.name() + ".chance", mob.getEquipment().getChestplateDropChance());
                            break;
                        case LEGS:
                            config.set(key + ".chance." + equipmentSlot.name() + ".chance", mob.getEquipment().getLeggingsDropChance());
                            break;
                        case FEET:
                            config.set(key + ".chance." + equipmentSlot.name() + ".chance", mob.getEquipment().getBootsDropChance());
                            break;
                    }
                }
            }
            Map<Attribute, Double> attributeDoubleMap = new HashMap<>();
            for (Attribute attribute : Attribute.values()) {
                attributeDoubleMap.put(attribute, mob.getAttribute(attribute).getBaseValue());
            }
            for (Attribute attribute : attributeDoubleMap.keySet()) {
                if (attributeDoubleMap.get(attribute) != 0.0) {
                    config.set(key + ".attribute." + attribute.name() + ".amount", attributeDoubleMap.get(attribute));
                }
            }
        }
        try {
            config.save(new File("plugins/EzTools/entity.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveEntityItemStack(String key, ItemStack itemStack) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/entity.yml"));
        try {
            config.load(new File("plugins/EzTools/entity.yml"));
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
            for (Enchantment enchantment : itemStack.getItemMeta().getEnchants().keySet()) {
                GEnchantment ench = GEnchantment.valueOf(enchantment);
                int level = itemStack.getItemMeta().getEnchantLevel(enchantment);
                config.set(key + ".enchantment." + ench.name() + ".level", level);
            }
        }
        try {
            config.save(new File("plugins/EzTools/entity.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean containsEntity(String key) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/entityyml"));
        try {
            config.load(new File("plugins/EzTools/entity.yml"));
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

    public List<String> getEntitiesStringList() {
        List<String> list = new ArrayList<>();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/EzTools/entity.yml"));
        try {
            config.load(new File("plugins/EzTools/entity.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        for (String key : config.getKeys(false)) {
            list.add(key);
        }
        return list;
    }

}
