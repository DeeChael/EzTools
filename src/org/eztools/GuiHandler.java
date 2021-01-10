package org.eztools;

import net.deechael.plugin.bukkit.anvilapi.AnvilAPI;
import net.deechael.plugin.bukkit.anvilapi.Slot;
import net.deechael.plugin.bukkit.anvilapi.inventory.DAnvil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.util.JsonConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            nameTagMeta.setDisplayName(this.getDisplay("item.main.button.rename"));
            nameTagMeta.setLore(this.getLore("item.main.button.rename"));
            nameTag.setItemMeta(nameTagMeta);

            ItemStack book = new ItemStack(Material.BOOK);
            ItemMeta bookMeta = book.getItemMeta();
            bookMeta.setDisplayName(this.getDisplay("item.main.button.enchant"));
            bookMeta.setLore(this.getLore("item.main.button.enchant"));
            book.setItemMeta(bookMeta);

            ItemStack stick = new ItemStack(Material.STICK);
            ItemMeta stickMeta = stick.getItemMeta();
            stickMeta.setDisplayName(this.getDisplay("item.main.button.attribute"));
            stickMeta.setLore(this.getLore("item.main.button.attribute"));
            stick.setItemMeta(stickMeta);

            Inventory inventory = Bukkit.createInventory(null, 45, EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.main.title")));
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, background);
            }

            ItemStack barrier = new ItemStack(Material.BARRIER);
            ItemMeta barrierMeta = barrier.getItemMeta();
            barrierMeta.setDisplayName(this.getDisplay("item.main.button.unbreakable"));
            List<String> barrierLore = new ArrayList<>();
            for (JsonConfiguration jsonConfiguration : EzTools.getLanguageGui().getJsonObject("item.main.button.unbreakable").getJsonObjectsInJsonArray("lore")) {
                barrierLore.add(EzTools.replaceColorCode(jsonConfiguration.getString("text")).replace("%unbreakable%", StringUtils.upperCase(String.valueOf(itemStack.getItemMeta().isUnbreakable()))));
            }
            barrierMeta.setLore(barrierLore);
            barrier.setItemMeta(barrierMeta);

            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta paperMeta = paper.getItemMeta();
            paperMeta.setDisplayName(this.getDisplay("item.main.button.lore"));
            paperMeta.setLore(this.getLore("item.main.button.lore"));
            paper.setItemMeta(paperMeta);

            inventory.setItem(13, itemStack);

            inventory.setItem(28, nameTag);
            inventory.setItem(30, paper);
            inventory.setItem(31, book);
            inventory.setItem(32, stick);
            inventory.setItem(34, barrier);

            player.openInventory(inventory);
            if (!EzTools.getEditingItem().containsKey(player)) {
                EzTools.getEditingItem().put(player, itemStack);
            }
        } else if (type == InventoryType.ITEM_RENAME) {
            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta paperMeta = paper.getItemMeta();
            paperMeta.setDisplayName(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.rename.input_left.display")));
            paper.setItemMeta(paperMeta);

            DAnvil anvil = AnvilAPI.getAnvilAPI().getAnvil(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.rename.title")), player);
            anvil.setItemStack(Slot.INPUT_LEFT, paper);
            anvil.openInventory();

            if (!EzTools.getEditingItem().containsKey(player)) {
                EzTools.getEditingItem().put(player, itemStack);
            }
        } else if (type == InventoryType.ITEM_LORE) {
            ItemStack diamond = new ItemStack(Material.DIAMOND);
            ItemMeta diamondMeta = diamond.getItemMeta();
            diamondMeta.setDisplayName(this.getDisplay("item.lore.button.new"));
            diamondMeta.setLore(this.getLore("item.lore.button.new"));
            diamond.setItemMeta(diamondMeta);

            Inventory inventory = Bukkit.createInventory(null, 54, EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.lore.title")));

            //Paint a circle
            inventory.setItem(0, background);
            inventory.setItem(1, background);
            inventory.setItem(2, background);
            inventory.setItem(3, background);
            inventory.setItem(4, background);
            inventory.setItem(5, background);
            inventory.setItem(6, background);
            inventory.setItem(7, background);
            inventory.setItem(8, background);
            inventory.setItem(9, background);
            inventory.setItem(17, background);
            inventory.setItem(18, background);
            inventory.setItem(26, background);
            inventory.setItem(27, background);
            inventory.setItem(35, background);
            inventory.setItem(36, background);
            inventory.setItem(44, background);
            inventory.setItem(45, background);
            inventory.setItem(46, background);
            inventory.setItem(47, background);
            inventory.setItem(48, background);
            inventory.setItem(49, background);
            inventory.setItem(50, background);
            inventory.setItem(51, background);
            inventory.setItem(52, background);
            inventory.setItem(53, background);

            inventory.setItem(49, diamond);

            if (itemStack.getItemMeta().hasLore()) {
                if (itemStack.getItemMeta().getLore().size() <= 28) {
                    for (String string : itemStack.getItemMeta().getLore()) {
                        ItemStack paper = new ItemStack(Material.PAPER);
                        ItemMeta paperMeta = paper.getItemMeta();
                        paperMeta.setDisplayName(string);
                        paperMeta.setLore(Arrays.asList("EzTools Line Tips: " + itemStack.getItemMeta().getLore().indexOf(string)));
                        paper.setItemMeta(paperMeta);
                        inventory.addItem(paper);
                    }
                } else {
                    for (int o = 0; o < 28; o++) {
                        String string = itemStack.getItemMeta().getLore().get(o);
                        ItemStack paper = new ItemStack(Material.PAPER);
                        ItemMeta paperMeta = paper.getItemMeta();
                        paperMeta.setDisplayName(string);
                        paperMeta.setLore(Arrays.asList("EzTools Line Tips: " + o));
                        paper.setItemMeta(paperMeta);
                        inventory.addItem(paper);
                    }
                }
            }
            player.openInventory(inventory);
            if (!EzTools.getEditingItem().containsKey(player)) {
                EzTools.getEditingItem().put(player, itemStack);
            }
        } else if (type == InventoryType.ITEM_LORE_NEW_LINE) {
            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta paperMeta = paper.getItemMeta();
            paperMeta.setDisplayName(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.lore_new_line.input_left.display")));
            paper.setItemMeta(paperMeta);

            DAnvil anvil = AnvilAPI.getAnvilAPI().getAnvil(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.lore_new_line.title")), player);
            anvil.setItemStack(Slot.INPUT_LEFT, paper);
            anvil.openInventory();

            if (!EzTools.getEditingItem().containsKey(player)) {
                EzTools.getEditingItem().put(player, itemStack);
            }
        } else if (type == InventoryType.ITEM_ENCHANT) {

        } else if (type == InventoryType.ITEM_ATTRIBUTE) {

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

    public static enum InventoryType {

        ITEM_MAIN,
        ITEM_RENAME,
        ITEM_LORE,
        ITEM_LORE_NEW_LINE,
        ITEM_ENCHANT,
        ITEM_ATTRIBUTE,
        ITEM_LOAD,
        ENTITY_MAIN,
        ENTITY_DISPLAY,
        ENTITY_ITEM,
        ENTITY_CHANCE,
        ENTITY_ATTRIBUTE,
        ENTITY_SUMMON;

    }

}
