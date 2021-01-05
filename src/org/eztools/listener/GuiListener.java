package org.eztools.listener;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.EzTools;
import org.eztools.GuiHandler;

import java.util.Arrays;

public class GuiListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("§c§l物品编辑器")) {
            e.setCancelled(true);
            if (e.getRawSlot() == 28) {
                EzTools.getGuiHandler().openInventory((Player) e.getView().getPlayer(), GuiHandler.InventoryType.ITEM_RENAME, EzTools.getEditingItem().get(e.getView().getPlayer()));
            } else if (e.getRawSlot() == 30) {

            } else if (e.getRawSlot() == 32) {

            } else if (e.getRawSlot() == 34) {
                ItemStack itemStack = EzTools.getEditingItem().get(e.getView().getPlayer());
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setUnbreakable(!itemMeta.isUnbreakable());
                itemStack.setItemMeta(itemMeta);

                ItemStack barrier = new ItemStack(Material.BARRIER);
                ItemMeta barrierMeta = barrier.getItemMeta();
                barrierMeta.setDisplayName("§c§l不可破坏");
                barrierMeta.setLore(Arrays.asList("§r", "§a设置物品是否不可破坏", "§a当前状态: §e" + StringUtils.upperCase(String.valueOf(itemStack.getItemMeta().isUnbreakable())), "§e点击切换是否不可破坏"));
                barrier.setItemMeta(barrierMeta);

                e.getView().getTopInventory().setItem(34, barrier);
                e.getView().getTopInventory().setItem(13, EzTools.getEditingItem().get(e.getView().getPlayer()));
                ((Player) e.getView().getPlayer()).updateInventory();

                e.getView().getPlayer().sendMessage("§a已将物品的不可破坏设置为 §e" + StringUtils.upperCase(String.valueOf(itemStack.getItemMeta().isUnbreakable())));
                ((Player) e.getView().getPlayer()).playSound(e.getView().getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT, 1f, 1f);
            }
        } else if (e.getView().getTitle().equalsIgnoreCase("§c§l编辑名称")) {
            if (e.getRawSlot() == 0) {
                e.setCancelled(true);
            }
        } else if (e.getView().getTitle().equalsIgnoreCase("Developer Tool")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onRename(PrepareAnvilEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("§c§l编辑名称")) {
            EzTools.getItemHandler().setName(EzTools.getEditingItem().get(e.getView().getPlayer()), e.getResult().getItemMeta().getDisplayName());
            e.getView().getPlayer().closeInventory();
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("§c§l物品编辑器") || e.getView().getTitle().equalsIgnoreCase("§c§l编辑名称")) {
            EzTools.getEditingItem().remove(e.getPlayer());
        }
    }

}
