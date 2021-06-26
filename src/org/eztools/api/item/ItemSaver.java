package org.eztools.api.item;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ItemSaver {

    boolean has(String name);

    void save(String name, ItemStack itemStack);

    ItemStack get(String name);

    List<String> listNames();

    ItemStack remove(String name);

    boolean removeAll();

}
