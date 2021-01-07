package org.eztools;

import net.deechael.plugin.bukkit.anvilapi.DAnvil;
import net.deechael.plugin.bukkit.anvilapi.DAnvilClickEvent;
import net.deechael.plugin.bukkit.anvilapi.DAnvilClickEventListener;
import net.deechael.plugin.bukkit.anvilapi.DAnvilSlot;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GuiHandler {

    private final EzTools ezTools;

    public GuiHandler(EzTools ezTools) {
        this.ezTools = ezTools;
    }

    public void openInventory(Player player, InventoryType type, ItemStack itemStack) {
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.setDisplayName(" ");
        background.setItemMeta(backgroundMeta);

        if (type == InventoryType.ITEM_MAIN) {

            ItemStack nameTag = new ItemStack(Material.NAME_TAG);
            ItemMeta nameTagMeta = nameTag.getItemMeta();
            nameTagMeta.setDisplayName("§b§l编辑显示");
            nameTagMeta.setLore(Arrays.asList("§r", "§a编辑物品名称和介绍", "§e点击选择编辑项"));
            nameTag.setItemMeta(nameTagMeta);

            ItemStack book = new ItemStack(Material.BOOK);
            ItemMeta bookMeta = book.getItemMeta();
            bookMeta.setDisplayName("§d§l编辑附魔");
            bookMeta.setLore(Arrays.asList("§r", "§a编辑物品的附魔", "§e点击编辑附魔"));
            book.setItemMeta(bookMeta);

            ItemStack stick = new ItemStack(Material.STICK);
            ItemMeta stickMeta = stick.getItemMeta();
            stickMeta.setDisplayName("§a§l编辑属性");
            stickMeta.setLore(Arrays.asList("§r", "§a编辑物品的属性", "§e点击编辑属性"));
            stick.setItemMeta(stickMeta);

            Inventory inventory = Bukkit.createInventory(null, 45, "§c§l物品编辑器");
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, background);
            }

            ItemStack barrier = new ItemStack(Material.BARRIER);
            ItemMeta barrierMeta = barrier.getItemMeta();
            barrierMeta.setDisplayName("§c§l不可破坏");
            barrierMeta.setLore(Arrays.asList("§r", "§a设置物品是否不可破坏", "§a当前状态: §e" + StringUtils.upperCase(String.valueOf(itemStack.getItemMeta().isUnbreakable())), "§e点击切换是否不可破坏"));
            barrier.setItemMeta(barrierMeta);

            inventory.setItem(13, itemStack);

            inventory.setItem(28, nameTag);
            inventory.setItem(30, book);
            inventory.setItem(32, stick);
            inventory.setItem(34, barrier);

            player.openInventory(inventory);
            if (!EzTools.getEditingItem().containsKey(player)) {
                EzTools.getEditingItem().put(player, itemStack);
            }
        } else if (type == InventoryType.ITEM_DISPLAY) {

        } else if (type == InventoryType.ITEM_RENAME) {
            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta paperMeta = paper.getItemMeta();
            paperMeta.setDisplayName("请输入要更改的物品名称");
            paper.setItemMeta(paperMeta);
            /*
            DAnvil dAnvil = new DAnvil(player, new DAnvilClickEventListener() {

                @Override
                public void onAnvilClick(DAnvilClickEvent e) {
                    e.setCancelled(true);
                    if (e.getSlot().equals(DAnvilSlot.OUTPUT)) {
                        String string = " ";
                        string = e.getItemStack().getItemMeta().getDisplayName();
                        ItemStack itemStack1 = EzTools.getEditingItem().get(e.getPlayer());
                        ItemMeta meta = itemStack1.getItemMeta();
                        meta.setDisplayName(string);
                        itemStack1.setItemMeta(meta);
                        e.setWillClose(true);
                        e.setWillDestroy(true);
                    }
                }
            });
            dAnvil.setSlot(DAnvilSlot.INPUT_LEFT, paper);
            dAnvil.open();
            */
            Inventory inventory = Bukkit.createInventory(null, org.bukkit.event.inventory.InventoryType.ANVIL, "§c§l编辑名称");
            inventory.setItem(0, paper);

            player.openInventory(inventory);
            if (!EzTools.getEditingItem().containsKey(player)) {
                EzTools.getEditingItem().put(player, itemStack);
            }
        } else if (type == InventoryType.ITEM_LORE) {

        } else if (type == InventoryType.ITEM_ENCHANT) {

        } else if (type == InventoryType.ITEM_ATTRIBUTE) {

        }

    }

    public static enum InventoryType {

        ITEM_MAIN, ITEM_DISPLAY, ITEM_RENAME, ITEM_LORE, ITEM_ENCHANT, ITEM_ATTRIBUTE;

    }

}
