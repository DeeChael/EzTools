package net.deechael.plugin.bukkit.anvilapi.inventory;

import org.bukkit.inventory.ItemStack;

public interface DAnvil {

    String getTitle();

    void setItemStack(int i, ItemStack itemStack);

    FakeAnvil getFakeAnvil();

    void openInventory();

}
