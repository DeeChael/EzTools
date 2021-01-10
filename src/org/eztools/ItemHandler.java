package org.eztools;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemHandler {

    private final EzTools ezTools;

    public ItemHandler(EzTools ezTools) {
        this.ezTools = ezTools;
    }

    public void addEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment.getEnchantment(), level, true);
        itemStack.setItemMeta(itemMeta);
    }

    public void setAttribute(ItemStack itemStack, Attribute attribute, EquipmentSlot equipmentSlot, int number) {
        AttributeModifier attributeModifier;
        if (attribute.equals(Attribute.GENERIC_KNOCKBACK_RESISTANCE)) {
            attributeModifier = new AttributeModifier(UUID.randomUUID(), "eztools", (number / 10), AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
        } else {
            attributeModifier = new AttributeModifier(UUID.randomUUID(), "eztools", number, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.removeAttributeModifier(attribute);
        itemMeta.addAttributeModifier(attribute, attributeModifier);
        itemStack.setItemMeta(itemMeta);
    }

    public void setName(ItemStack itemStack, String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
    }

    public void addLore(ItemStack itemStack, String string) {
        List<String> lore = itemStack.getItemMeta().hasLore() ? itemStack.getItemMeta().getLore() : new ArrayList<>();
        lore.add(string);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    public void removeLore(ItemStack itemStack, int i) {
        List<String> lore = itemStack.getItemMeta().getLore();
        lore.remove(i);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

}
