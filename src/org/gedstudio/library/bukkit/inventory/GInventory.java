package org.gedstudio.library.bukkit.inventory;

import org.bukkit.inventory.Inventory;

public interface GInventory {

    Inventory getHandle();

    void setItem(int slot, GItem item);

    void addItem(GItem... items);

    GItem getItem(int slot);

    boolean hasItem();

    boolean hasItem(int slot);

}
