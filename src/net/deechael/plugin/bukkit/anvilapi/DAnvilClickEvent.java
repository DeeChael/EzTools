package net.deechael.plugin.bukkit.anvilapi;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DAnvilClickEvent {

    private DAnvilSlot slot;

    private String name;
    private ItemStack itemStack;
    private Player player;

    private boolean close = false;
    private boolean destroy = false;
    private boolean isCancelled = false;

    public DAnvilClickEvent(DAnvilSlot slot, String name, ItemStack itemStack, Player player) {
        this.slot = slot;
        this.name = name;
        this.itemStack = itemStack;
        this.player = player;
    }

    public DAnvilSlot getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public boolean getWillClose() {
        return close;
    }

    public void setWillClose(boolean close) {
        this.close = close;
    }

    public boolean getWillDestroy() {
        return destroy;
    }

    public void setWillDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public Player getPlayer() {
        return player;
    }
}
