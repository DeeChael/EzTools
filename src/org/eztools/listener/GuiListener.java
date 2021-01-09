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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.EzTools;
import org.eztools.GuiHandler;
import org.eztools.util.JsonConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.main.title")))) {
            //Item Main
            e.setCancelled(true);
            if (e.getRawSlot() == 28) {
                //Item Main -> Item Rename
                EzTools.getGuiHandler().openInventory((Player) e.getView().getPlayer(), GuiHandler.InventoryType.ITEM_RENAME, EzTools.getEditingItem().get(e.getView().getPlayer()));
            } else if (e.getRawSlot() == 30) {
                //Item Main -> Item Lore
                EzTools.getGuiHandler().openInventory((Player) e.getView().getPlayer(), GuiHandler.InventoryType.ITEM_LORE, EzTools.getEditingItem().get(e.getView().getPlayer()));
            } else if (e.getRawSlot() == 31) {
                //Item Main -> Item Enchant

            } else if (e.getRawSlot() == 32) {
                //Item Main -> Item Attribute

            } else if (e.getRawSlot() == 34) {
                //Item Main || Item Unbreakable
                ItemStack itemStack = EzTools.getEditingItem().get(e.getView().getPlayer());
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setUnbreakable(!itemMeta.isUnbreakable());
                itemStack.setItemMeta(itemMeta);

                ItemStack barrier = new ItemStack(Material.BARRIER);
                ItemMeta barrierMeta = barrier.getItemMeta();
                barrierMeta.setDisplayName(this.getDisplay("item.main.button.unbreakable"));
                List<String> barrierLore = new ArrayList<>();
                for (JsonConfiguration jsonConfiguration : EzTools.getLanguageGui().getJsonObject("item.main.button.unbreakable").getJsonObjectsInJsonArray("lore")) {
                    barrierLore.add(EzTools.replaceColorCode(jsonConfiguration.getString("text")).replace("%unbreakable%", StringUtils.upperCase(String.valueOf(itemStack.getItemMeta().isUnbreakable()))));
                }
                barrierMeta.setLore(barrierLore);
                barrier.setItemMeta(barrierMeta);

                e.getView().getTopInventory().setItem(34, barrier);
                e.getView().getTopInventory().setItem(13, EzTools.getEditingItem().get(e.getView().getPlayer()));
                ((Player) e.getView().getPlayer()).updateInventory();

                e.getView().getPlayer().sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.unbreakable.switch")));
                ((Player) e.getView().getPlayer()).playSound(e.getView().getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT, 1f, 1f);
            }
        } else if (e.getView().getTitle().equalsIgnoreCase(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.rename.title")))) {
            //Item Rename
            e.setCancelled(true);
            if (e.getRawSlot() == 2) {
                String itemName = EzTools.replaceColorCode(e.getView().getTopInventory().getItem(2).getItemMeta().getDisplayName());
                EzTools.getItemHandler().setName(EzTools.getEditingItem().get(e.getView().getPlayer()), itemName);
                e.getView().getPlayer().sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.name.success")).replace("%item_name%", itemName));
                ((Player) e.getView().getPlayer()).playSound(e.getView().getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT, 1f, 1f);
                e.getView().getPlayer().closeInventory();
                if (EzTools.getEditingItem().containsKey(e.getView().getPlayer())) EzTools.getEditingItem().remove(e.getView().getPlayer());
            }
        } else if (e.getView().getTitle().equalsIgnoreCase(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.lore.title")))) {
            e.setCancelled(true);
        } else if (e.getView().getTitle().equalsIgnoreCase("Developer Tool")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.main.title"))) || e.getView().getTitle().equalsIgnoreCase(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.rename.title")))) {
            EzTools.getEditingItem().remove(e.getPlayer());
        }
    }

    private String getDisplay(String key) {
        return EzTools.replaceColorCode(EzTools.getLanguageGui().getJsonObject(key).getString("display"));
    }

    private List<String> getLore(String key) {
        List<String> list = new ArrayList<>();
        for (JsonConfiguration jsonConfiguration : EzTools.getLanguageGui().getJsonObject(key).getJsonObjectsInJsonArray("lore")) {
            list.add(EzTools.replaceColorCode(jsonConfiguration.getString("text")));
        }
        return list;
    }

}
