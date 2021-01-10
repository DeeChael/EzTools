package org.eztools.listener;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
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
                ItemStack itemStack = EzTools.getEditingItem().get(e.getView().getPlayer());
                e.getView().getPlayer().closeInventory();
                EzTools.getGuiHandler().openInventory((Player) e.getView().getPlayer(), GuiHandler.InventoryType.ITEM_MAIN, itemStack);
            }
        } else if (e.getView().getTitle().equalsIgnoreCase(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.lore.title")))) {
            //Item Lore
            e.setCancelled(true);
            if (e.getRawSlot() == 49) {
                //Item Lore -> Item Lore New Line
                EzTools.getGuiHandler().openInventory((Player) e.getView().getPlayer(), GuiHandler.InventoryType.ITEM_LORE_NEW_LINE, EzTools.getEditingItem().get(e.getView().getPlayer()));
            } else if (
                    e.getRawSlot() >= 9
                            && e.getRawSlot() <= 43
                            && e.getRawSlot() != 17
                            && e.getRawSlot() != 18
                            && e.getRawSlot() != 26
                            && e.getRawSlot() != 27
                            && e.getRawSlot() != 35
                            && e.getRawSlot() != 36
                    && e.getClick().equals(ClickType.RIGHT)
            ) {
                EzTools.getItemHandler().removeLore(EzTools.getEditingItem().get(e.getView().getPlayer()), this.translateLoreInt(e.getRawSlot()));
                e.getView().getPlayer().sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.gui.lore_new_line.remove")));
                ((Player) e.getView().getPlayer()).playSound(e.getView().getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.AMBIENT, 1f, 1f);
                ItemStack itemStack = EzTools.getEditingItem().get(e.getView().getPlayer());
                e.getView().getPlayer().closeInventory();
                EzTools.getGuiHandler().openInventory((Player) e.getView().getPlayer(), GuiHandler.InventoryType.ITEM_LORE, itemStack);
            }
        } else if (e.getView().getTitle().equalsIgnoreCase(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.lore_new_line.title")))) {
            //Item Lore New Line
            e.setCancelled(true);
            if (e.getRawSlot() == 2) {
                String loreString = EzTools.replaceColorCode(e.getView().getTopInventory().getItem(2).getItemMeta().getDisplayName());
                EzTools.getItemHandler().addLore(EzTools.getEditingItem().get(e.getView().getPlayer()), loreString);
                e.getView().getPlayer().sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("item.gui.lore_new_line.success")));
                ((Player) e.getView().getPlayer()).playSound(e.getView().getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.AMBIENT, 1f, 1f);
                ItemStack itemStack = EzTools.getEditingItem().get(e.getView().getPlayer());
                e.getView().getPlayer().closeInventory();
                EzTools.getGuiHandler().openInventory((Player) e.getView().getPlayer(), GuiHandler.InventoryType.ITEM_LORE, itemStack);
            }
        } else if (e.getView().getTitle().equalsIgnoreCase("Developer Tool")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (
                e.getView().getTitle().equalsIgnoreCase(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.main.title")))
                || e.getView().getTitle().equalsIgnoreCase(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.rename.title")))
                || e.getView().getTitle().equalsIgnoreCase(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.lore.title")))
                || e.getView().getTitle().equalsIgnoreCase(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.lore_new_line.title")))
        ) {
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

    private int translateLoreInt(int inInventory) {
        switch (inInventory) {
            case 10:
            default:
                return 0;
            case 11:
                return 1;
            case 12:
                return 2;
            case 13:
                return 3;
            case 14:
                return 4;
            case 15:
                return 5;
            case 16:
                return 6;
            case 19:
                return 7;
            case 20:
                return 8;
            case 21:
                return 9;
            case 22:
                return 10;
            case 23:
                return 11;
            case 24:
                return 12;
            case 25:
                return 13;
            case 28:
                return 14;
            case 29:
                return 15;
            case 30:
                return 16;
            case 31:
                return 17;
            case 32:
                return 18;
            case 33:
                return 19;
            case 34:
                return 20;
            case 37:
                return 21;
            case 38:
                return 22;
            case 39:
                return 23;
            case 40:
                return 24;
            case 41:
                return 25;
            case 42:
                return 26;
            case 43:
                return 27;
        }
    }

}
