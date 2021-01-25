package org.eztools;

import net.deechael.ged.library.configuration.JsonConfiguration;
import net.deechael.ged.library.enchant.GEnchantment;
import net.deechael.plugin.bukkit.anvilapi.AnvilAPI;
import net.deechael.plugin.bukkit.anvilapi.Slot;
import net.deechael.plugin.bukkit.anvilapi.inventory.DAnvil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

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
            for (JsonConfiguration jsonConfiguration : EzTools.getLanguageGui().getJsonObject("item.main.button.unbreakable").getJsonArray("lore")) {
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
            paperMeta.setDisplayName(this.getDisplay("item.rename.input_left"));
            paper.setItemMeta(paperMeta);

            DAnvil anvil = AnvilAPI.getAnvilAPI().getAnvil(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.rename.title")), player);
            anvil.setItemStack(Slot.INPUT_LEFT, paper);
            anvil.openInventory();

            if (!EzTools.getEditingItem().containsKey(player)) {
                EzTools.getEditingItem().put(player, itemStack);
            }
        } else if (type == InventoryType.ITEM_LORE) {
            this.loreMenu(player, itemStack, 1);
        } else if (type == InventoryType.ITEM_LORE_NEW_LINE) {
            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta paperMeta = paper.getItemMeta();
            paperMeta.setDisplayName(this.getDisplay("item.rename.input_left"));
            paper.setItemMeta(paperMeta);

            DAnvil anvil = AnvilAPI.getAnvilAPI().getAnvil(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.lore_new_line.title")), player);
            anvil.setItemStack(Slot.INPUT_LEFT, paper);
            anvil.openInventory();

            if (!EzTools.getEditingItem().containsKey(player)) {
                EzTools.getEditingItem().put(player, itemStack);
            }
        } else if (type == InventoryType.ITEM_ENCHANT) {
            this.enchantMenu(player, itemStack, 1);
        } else if (type == InventoryType.ITEM_ATTRIBUTE) {
            Inventory inventory = Bukkit.createInventory(null, 27, EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.attribute_before.title")));
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, background);
            }
            //Main Hand
            ItemStack mainHandItem = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
            mainHandItemMeta.setDisplayName(EquipmentSlot.HAND.name());
            mainHandItemMeta.setLore(Arrays.asList(EquipmentSlot.HAND.name()));
            mainHandItem.setItemMeta(mainHandItemMeta);
            //Off Hand
            ItemStack offHandItem = new ItemStack(Material.SHIELD);
            ItemMeta offHandItemMeta = offHandItem.getItemMeta();
            offHandItemMeta.setDisplayName(EquipmentSlot.OFF_HAND.name());
            offHandItemMeta.setLore(Arrays.asList(EquipmentSlot.OFF_HAND.name()));
            offHandItem.setItemMeta(offHandItemMeta);
            //Helmet
            ItemStack helmetItem = new ItemStack(Material.DIAMOND_HELMET);
            ItemMeta helmetMeta = helmetItem.getItemMeta();
            helmetMeta.setDisplayName(EquipmentSlot.HEAD.name());
            helmetMeta.setLore(Arrays.asList(EquipmentSlot.HEAD.name()));
            helmetItem.setItemMeta(helmetMeta);
            //Chestplate
            ItemStack chestplateItem = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemMeta chestplateMeta = chestplateItem.getItemMeta();
            chestplateMeta.setDisplayName(EquipmentSlot.CHEST.name());
            chestplateMeta.setLore(Arrays.asList(EquipmentSlot.CHEST.name()));
            chestplateItem.setItemMeta(chestplateMeta);
            //Leggings
            ItemStack leggingsItem = new ItemStack(Material.DIAMOND_LEGGINGS);
            ItemMeta leggingsMeta = leggingsItem.getItemMeta();
            leggingsMeta.setDisplayName(EquipmentSlot.LEGS.name());
            leggingsMeta.setLore(Arrays.asList(EquipmentSlot.LEGS.name()));
            leggingsItem.setItemMeta(leggingsMeta);
            //Boots
            ItemStack bootsItem = new ItemStack(Material.DIAMOND_BOOTS);
            ItemMeta bootsMeta = bootsItem.getItemMeta();
            bootsMeta.setDisplayName(EquipmentSlot.FEET.name());
            bootsMeta.setLore(Arrays.asList(EquipmentSlot.FEET.name()));
            bootsItem.setItemMeta(bootsMeta);

            inventory.setItem(10, mainHandItem);
            inventory.setItem(11, offHandItem);
            inventory.setItem(12, helmetItem);
            inventory.setItem(14, chestplateItem);
            inventory.setItem(15, leggingsItem);
            inventory.setItem(16, bootsItem);

            player.openInventory(inventory);
            if (!EzTools.getEditingItem().containsKey(player)) {
                EzTools.getEditingItem().put(player, itemStack);
            }
        }
    }

    public void editLore(Player player, ItemStack itemStack, int i) {
        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        paperMeta.setDisplayName(itemStack.getItemMeta().getDisplayName());
        paper.setItemMeta(paperMeta);

        DAnvil anvil = AnvilAPI.getAnvilAPI().getAnvil(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.lore_edit.title")), player);
        anvil.setItemStack(Slot.INPUT_LEFT, paper);
        anvil.openInventory();

        EzTools.getEditingLore().put(player, i);
        if (!EzTools.getEditingItem().containsKey(player)) {
            EzTools.getEditingItem().put(player, itemStack);
        }
    }

    public void loreMenu(Player player, ItemStack itemStack, int page) {
        if (page <= 0) {
            page = 1;
        }

        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.setDisplayName(" ");
        background.setItemMeta(backgroundMeta);

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
                for (int i = 0; i < itemStack.getItemMeta().getLore().size(); i++) {
                    String string = itemStack.getItemMeta().getLore().get(i);
                    ItemStack paper = new ItemStack(Material.PAPER);
                    ItemMeta paperMeta = paper.getItemMeta();
                    paperMeta.setDisplayName(string);
                    List<String> lore = new ArrayList<>();
                    lore.add("" + i);
                    for (String s : this.getLore("item.lore.lore_item")) {
                        lore.add(s);
                    }
                    paperMeta.setLore(lore);
                    paper.setItemMeta(paperMeta);
                    inventory.addItem(paper);
                }
            } else {
                int total = 1;
                if ((itemStack.getItemMeta().getLore().size() % 28) > 0) {
                    total = (itemStack.getItemMeta().getLore().size() / 28) + 1;
                } else {
                    total = itemStack.getItemMeta().getLore().size() / 28;
                }
                ItemStack previousPage = new ItemStack(Material.ARROW);
                ItemMeta previousPageMeta = previousPage.getItemMeta();
                previousPageMeta.setDisplayName(this.getDisplay("item.button.previous"));
                List<String> previousPageLore = new ArrayList<>();
                for (JsonConfiguration jsonConfiguration : EzTools.getLanguageGui().getJsonObject("item.button.previous").getJsonArray("lore")) {
                    previousPageLore.add(EzTools.replaceColorCode(jsonConfiguration.getString("text").replace("%now_page%", page + "").replace("%total_page%", total + "")));
                }
                previousPageMeta.setLore(previousPageLore);
                previousPage.setItemMeta(previousPageMeta);

                ItemStack nextPage = new ItemStack(Material.ARROW);
                ItemMeta nextPageMeta = nextPage.getItemMeta();
                nextPageMeta.setDisplayName(this.getDisplay("item.button.next"));
                List<String> nextPageLore = new ArrayList<>();
                for (JsonConfiguration jsonConfiguration : EzTools.getLanguageGui().getJsonObject("item.button.next").getJsonArray("lore")) {
                    nextPageLore.add(EzTools.replaceColorCode(jsonConfiguration.getString("text").replace("%now_page%", page + "").replace("%total_page%", total + "")));
                }
                nextPageMeta.setLore(nextPageLore);
                nextPage.setItemMeta(nextPageMeta);

                inventory.setItem(45, previousPage);
                inventory.setItem(53, nextPage);

                if (itemStack.getItemMeta().getLore().size() >= (page * 28)) {
                    for (int o = ((page - 1) * 28); o < (page * 28); o++) {
                        String string = itemStack.getItemMeta().getLore().get(o);
                        ItemStack paper = new ItemStack(Material.PAPER);
                        ItemMeta paperMeta = paper.getItemMeta();
                        paperMeta.setDisplayName(string);
                        List<String> lore = new ArrayList<>();
                        lore.add("" + o);
                        for (String s : this.getLore("item.lore.lore_item")) {
                            lore.add(s);
                        }
                        paperMeta.setLore(lore);
                        paper.setItemMeta(paperMeta);
                        inventory.addItem(paper);
                    }
                } else {
                    for (int o = ((page - 1) * 28); o < itemStack.getItemMeta().getLore().size(); o++) {
                        String string = itemStack.getItemMeta().getLore().get(o);
                        ItemStack paper = new ItemStack(Material.PAPER);
                        ItemMeta paperMeta = paper.getItemMeta();
                        paperMeta.setDisplayName(string);
                        List<String> lore = new ArrayList<>();
                        lore.add("" + o);
                        for (String s : this.getLore("item.lore.lore_item")) {
                            lore.add(s);
                        }
                        paperMeta.setLore(lore);
                        paper.setItemMeta(paperMeta);
                        inventory.addItem(paper);
                    }
                }
            }
        }
        player.openInventory(inventory);
        if (!EzTools.getEditingItem().containsKey(player)) {
            EzTools.getEditingItem().put(player, itemStack);
        }
        EzTools.getEditingPage().put(player, page);
    }

    public void enchantMenu(Player player, ItemStack itemStack, int page) {
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.setDisplayName(" ");
        background.setItemMeta(backgroundMeta);

        int total = 1;
        if ((GEnchantment.values().length % 28) > 0) {
            total = (GEnchantment.values().length / 28) + 1;
        } else {
            total = GEnchantment.values().length / 28;
        }
        ItemStack previousPage = new ItemStack(Material.ARROW);
        ItemMeta previousPageMeta = previousPage.getItemMeta();
        previousPageMeta.setDisplayName(this.getDisplay("item.button.previous"));
        List<String> previousPageLore = new ArrayList<>();
        for (JsonConfiguration jsonConfiguration : EzTools.getLanguageGui().getJsonObject("item.button.previous").getJsonArray("lore")) {
            previousPageLore.add(EzTools.replaceColorCode(jsonConfiguration.getString("text").replace("%now_page%", page + "").replace("%total_page%", total + "")));
        }
        previousPageMeta.setLore(previousPageLore);
        previousPage.setItemMeta(previousPageMeta);

        ItemStack nextPage = new ItemStack(Material.ARROW);
        ItemMeta nextPageMeta = nextPage.getItemMeta();
        nextPageMeta.setDisplayName(this.getDisplay("item.button.next"));
        List<String> nextPageLore = new ArrayList<>();
        for (JsonConfiguration jsonConfiguration : EzTools.getLanguageGui().getJsonObject("item.button.next").getJsonArray("lore")) {
            nextPageLore.add(EzTools.replaceColorCode(jsonConfiguration.getString("text").replace("%now_page%", page + "").replace("%total_page%", total + "")));
        }
        nextPageMeta.setLore(nextPageLore);
        nextPage.setItemMeta(nextPageMeta);

        Inventory inventory = Bukkit.createInventory(null, 54, EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.enchant.title")));

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

        inventory.setItem(45, previousPage);
        inventory.setItem(53, nextPage);

        Map<GEnchantment, Integer> map = new HashMap<>();
        for (GEnchantment enchantment : GEnchantment.values()) {
            map.put(enchantment, 0);
        }
        if (itemStack.getItemMeta().hasEnchants()) {
            for (Enchantment enchantment : itemStack.getItemMeta().getEnchants().keySet()) {
                map.put(GEnchantment.valueOf(enchantment), itemStack.getItemMeta().getEnchantLevel(enchantment));
            }
        }

        if (GEnchantment.values().length >= (page * 28)) {
            for (int o = ((page - 1) * 28); o < (page * 28); o++) {
                ItemStack book = new ItemStack(Material.BOOK);
                ItemMeta bookMeta = book.getItemMeta();
                bookMeta.setDisplayName(GEnchantment.values()[o].name());
                List<String> lore = new ArrayList<>();
                lore.add(GEnchantment.values()[o].name());
                for (String s : this.getLore("item.enchant.enchantment_item")) {
                    lore.add(s.replace("%level%", ((int) map.get(GEnchantment.values()[o]) + "")));
                }
                bookMeta.addEnchant(GEnchantment.values()[o].getHandle(), map.get(GEnchantment.values()[o]), true);
                bookMeta.setLore(lore);
                book.setItemMeta(bookMeta);
                inventory.addItem(book);
            }
        } else {
            for (int o = ((page - 1) * 28); o < GEnchantment.values().length; o++) {
                ItemStack book = new ItemStack(Material.BOOK);
                ItemMeta bookMeta = book.getItemMeta();
                bookMeta.setDisplayName(GEnchantment.values()[o].name());
                List<String> lore = new ArrayList<>();
                lore.add(GEnchantment.values()[o].name());
                for (String s : this.getLore("item.enchant.enchantment_item")) {
                    lore.add(s.replace("%level%", ((int) map.get(GEnchantment.values()[o]) + "")));
                }
                bookMeta.addEnchant(GEnchantment.values()[o].getHandle(), map.get(GEnchantment.values()[o]), true);
                bookMeta.setLore(lore);
                book.setItemMeta(bookMeta);
                inventory.addItem(book);
            }
        }

        player.openInventory(inventory);

        if (!EzTools.getEditingItem().containsKey(player)) {
            EzTools.getEditingItem().put(player, itemStack);
        }
        EzTools.getEditingPage().put(player, page);
    }

    public void editEnchant(Player player, ItemStack itemStack, GEnchantment enchantment) {
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.setDisplayName(" ");
        background.setItemMeta(backgroundMeta);

        //So many buttons
        ItemStack up1 = new ItemStack(Material.ARROW);
        ItemMeta up1Meta = up1.getItemMeta();
        up1Meta.setDisplayName(this.getDisplay("item.edit_enchantment.button.level.up_1"));
        up1Meta.setLore(this.getLore("item.edit_enchantment.button.level.up_1"));
        up1.setItemMeta(up1Meta);

        ItemStack up10 = new ItemStack(Material.ARROW);
        ItemMeta up10Meta = up10.getItemMeta();
        up10Meta.setDisplayName(this.getDisplay("item.edit_enchantment.button.level.up_10"));
        up10Meta.setLore(this.getLore("item.edit_enchantment.button.level.up_10"));
        up10.setItemMeta(up10Meta);

        ItemStack up100 = new ItemStack(Material.ARROW);
        ItemMeta up100Meta = up100.getItemMeta();
        up100Meta.setDisplayName(this.getDisplay("item.edit_enchantment.button.level.up_100"));
        up100Meta.setLore(this.getLore("item.edit_enchantment.button.level.up_100"));
        up100.setItemMeta(up100Meta);

        ItemStack up1k = new ItemStack(Material.ARROW);
        ItemMeta up1kMeta = up1k.getItemMeta();
        up1kMeta.setDisplayName(this.getDisplay("item.edit_enchantment.button.level.up_1k"));
        up1kMeta.setLore(this.getLore("item.edit_enchantment.button.level.up_1k"));
        up1k.setItemMeta(up1kMeta);

        ItemStack up10k = new ItemStack(Material.ARROW);
        ItemMeta up10kMeta = up10k.getItemMeta();
        up10kMeta.setDisplayName(this.getDisplay("item.edit_enchantment.button.level.up_10k"));
        up10kMeta.setLore(this.getLore("item.edit_enchantment.button.level.up_10k"));
        up10k.setItemMeta(up10kMeta);

        ItemStack up100k = new ItemStack(Material.ARROW);
        ItemMeta up100kMeta = up100k.getItemMeta();
        up100kMeta.setDisplayName(this.getDisplay("item.edit_enchantment.button.level.up_100k"));
        up100kMeta.setLore(this.getLore("item.edit_enchantment.button.level.up_100k"));
        up100k.setItemMeta(up100kMeta);

        ItemStack down1 = new ItemStack(Material.ARROW);
        ItemMeta down1Meta = down1.getItemMeta();
        down1Meta.setDisplayName(this.getDisplay("item.edit_enchantment.button.level.down_1"));
        down1Meta.setLore(this.getLore("item.edit_enchantment.button.level.down_1"));
        down1.setItemMeta(down1Meta);

        ItemStack down10 = new ItemStack(Material.ARROW);
        ItemMeta down10Meta = down10.getItemMeta();
        down10Meta.setDisplayName(this.getDisplay("item.edit_enchantment.button.level.down_10"));
        down10Meta.setLore(this.getLore("item.edit_enchantment.button.level.down_10"));
        down10.setItemMeta(down10Meta);

        ItemStack down100 = new ItemStack(Material.ARROW);
        ItemMeta down100Meta = down100.getItemMeta();
        down100Meta.setDisplayName(this.getDisplay("item.edit_enchantment.button.level.down_100"));
        down100Meta.setLore(this.getLore("item.edit_enchantment.button.level.down_100"));
        down100.setItemMeta(down100Meta);

        ItemStack down1k = new ItemStack(Material.ARROW);
        ItemMeta down1kMeta = down1k.getItemMeta();
        down1kMeta.setDisplayName(this.getDisplay("item.edit_enchantment.button.level.down_1k"));
        down1kMeta.setLore(this.getLore("item.edit_enchantment.button.level.down_1k"));
        down1k.setItemMeta(down1kMeta);

        ItemStack down10k = new ItemStack(Material.ARROW);
        ItemMeta down10kMeta = down10k.getItemMeta();
        down10kMeta.setDisplayName(this.getDisplay("item.edit_enchantment.button.level.down_10k"));
        down10kMeta.setLore(this.getLore("item.edit_enchantment.button.level.down_10k"));
        down10k.setItemMeta(down10kMeta);

        ItemStack down100k = new ItemStack(Material.ARROW);
        ItemMeta down100kMeta = down100k.getItemMeta();
        down100kMeta.setDisplayName(this.getDisplay("item.edit_enchantment.button.level.down_100k"));
        down100kMeta.setLore(this.getLore("item.edit_enchantment.button.level.down_100k"));
        down100k.setItemMeta(down100kMeta);

        ItemStack apply = new ItemStack(Material.DIAMOND);
        ItemMeta applyMeta = apply.getItemMeta();
        applyMeta.setDisplayName(this.getDisplay("item.edit_enchantment.button.apply"));
        applyMeta.setLore(this.getLore("item.edit_enchantment.button.apply"));
        apply.setItemMeta(applyMeta);

        //Inventory
        Inventory inventory = Bukkit.createInventory(null, 36, EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.edit_enchantment.title")));
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, background);
        }
        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta bookMeta = book.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(enchantment.name());
        if (itemStack.getItemMeta().hasEnchant(enchantment.getHandle())) {
            bookMeta.addEnchant(enchantment.getHandle(), itemStack.getItemMeta().getEnchantLevel(enchantment.getHandle()), true);
            lore.add("" + itemStack.getItemMeta().getEnchantLevel(enchantment.getHandle()));
        } else {
            lore.add("" + 0);
        }
        bookMeta.setDisplayName(enchantment.name());
        bookMeta.setLore(lore);
        book.setItemMeta(bookMeta);

        inventory.setItem(10, down1);
        inventory.setItem(11, down10);
        inventory.setItem(12, down100);
        inventory.setItem(13, book);
        inventory.setItem(14, up1);
        inventory.setItem(15, up10);
        inventory.setItem(16, up100);
        inventory.setItem(19, down1k);
        inventory.setItem(20, down10k);
        inventory.setItem(21, down100k);
        inventory.setItem(22, apply);
        inventory.setItem(23, up1k);
        inventory.setItem(24, up10k);
        inventory.setItem(25, up100k);

        player.openInventory(inventory);

        if (!EzTools.getEditingItem().containsKey(player)) {
            EzTools.getEditingItem().put(player, itemStack);
        }
    }

    public boolean hasNextPageEnchantment(int page) {
        int total = 1;
        if ((GEnchantment.values().length % 28) > 0) {
            total = (GEnchantment.values().length / 28) + 1;
        } else {
            total = GEnchantment.values().length / 28;
        }
        if (page == total) {
            return false;
        }
        return true;
    }

    public void attributeMenu(Player player, ItemStack itemStack, EquipmentSlot equipmentSlot) {
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.setDisplayName(" ");
        background.setItemMeta(backgroundMeta);

        Inventory inventory = Bukkit.createInventory(null, 54, EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.attribute.title")));

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

        for (Attribute attribute : Attribute.values()) {
            ItemStack beacon = new ItemStack(Material.BEACON);
            ItemMeta beaconMeta = beacon.getItemMeta();
            beaconMeta.setDisplayName(StringUtils.lowerCase(attribute.name()));
            List<String> lore = new ArrayList<>();
            lore.add(attribute.name());
            lore.add(equipmentSlot.name());
            double amount = 0.0;
            if (itemStack.getItemMeta().hasAttributeModifiers()) {
                if (itemStack.getItemMeta().getAttributeModifiers(attribute) != null) {
                    if (itemStack.getItemMeta().getAttributeModifiers().size() > 0) {
                        for (AttributeModifier attributeModifier : itemStack.getItemMeta().getAttributeModifiers(attribute)) {
                            if (attributeModifier.getSlot().equals(equipmentSlot)) {
                                amount += attributeModifier.getAmount();
                            }
                        }
                    }
                }
            }
            for (String string : this.getLore("item.attribute.attribute_item")) {
                lore.add(string.replace("%amount%", amount + ""));
            }
            beaconMeta.setLore(lore);
            beacon.setItemMeta(beaconMeta);
            inventory.addItem(beacon);
        }

        player.openInventory(inventory);
        if (!EzTools.getEditingItem().containsKey(player)) {
            EzTools.getEditingItem().put(player, itemStack);
        }
        EzTools.getEditingEquipmentSlot().put(player, equipmentSlot);
    }

    public void editAttribute(Player player, ItemStack itemStack, Attribute attribute, EquipmentSlot equipmentSlot) {
        DAnvil anvil = AnvilAPI.getAnvilAPI().getAnvil(EzTools.replaceColorCode(EzTools.getLanguageGui().getString("item.attribute_edit.title")), player);

        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        paperMeta.setDisplayName(this.getDisplay("item.attribute_edit.input_left"));
        paper.setItemMeta(paperMeta);

        anvil.setItemStack(Slot.INPUT_LEFT, paper);

        anvil.openInventory();
        if (!EzTools.getEditingItem().containsKey(player)) {
            EzTools.getEditingItem().put(player, itemStack);
        }
        EzTools.getEditingAttribute().put(player, attribute);
        EzTools.getEditingEquipmentSlot().put(player, equipmentSlot);
    }

    private String getDisplay(String key) {
        return EzTools.replaceColorCode(EzTools.getLanguageGui().getJsonObject(key).getString("display"));
    }

    private List<String> getLore(String key) {
        List<String> list = new ArrayList<>();
        for (JsonConfiguration jsonConfiguration : EzTools.getLanguageGui().getJsonObject(key).getJsonArray("lore")) {
            list.add(EzTools.replaceColorCode(jsonConfiguration.getString("text")));
        }
        return list;
    }

    public boolean hasNextPage(ItemStack itemStack, int page) {
        int total = 1;
        if ((itemStack.getItemMeta().getLore().size() % 28) > 0) {
            total = (itemStack.getItemMeta().getLore().size() / 28) + 1;
        } else {
            total = itemStack.getItemMeta().getLore().size() / 28;
        }
        if (page == total) {
            return false;
        }
        return true;
    }

    public static enum InventoryType {

        ITEM_MAIN,
        ITEM_RENAME,
        ITEM_LORE,
        ITEM_LORE_NEW_LINE,
        ITEM_ENCHANT,
        ITEM_ATTRIBUTE,
        ENTITY_MAIN,
        ENTITY_DISPLAY,
        ENTITY_ITEM,
        ENTITY_CHANCE,
        ENTITY_ATTRIBUTE,
        ENTITY_SUMMON;

    }

}
