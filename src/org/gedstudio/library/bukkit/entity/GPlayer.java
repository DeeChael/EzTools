package org.gedstudio.library.bukkit.entity;

import org.gedstudio.library.bukkit.GLocation;
import org.gedstudio.library.bukkit.chat.GText;
import org.gedstudio.library.bukkit.command.GSender;
import org.gedstudio.library.bukkit.exception.PlayerNotExistException;
import org.gedstudio.library.bukkit.inventory.GInventory;
import org.gedstudio.library.bukkit.inventory.GItem;
import org.gedstudio.library.bukkit.scoreboard.GScoreBoard;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.gedstudio.library.bukkit.skin.Skin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface GPlayer extends GSender {

    void setScoreboard(GScoreBoard scoreboard);

    void resetScoreboard();

    boolean hasScoreboard();

    GScoreBoard getScoreboard();

    CommandSender getSender();

    void setDisplayName(String name);

    String getDisplayName();

    void setPlayerListName(String name);

    String getPlayerListName();

    GItem getItemInMainHand();

    GItem getItemInOffHand();

    GItem getHelmet();

    GItem getChestplate();

    GItem getLeggings();

    GItem getBoots();

    void setItemInMainHand(GItem item);

    void setItemInOffHand(GItem item);

    void setHelmet(GItem item);

    void setChestplate(GItem item);

    void setLeggings(GItem item);

    void setBoots(GItem item);

    void addItem(GItem... items);

    void setItem(int slot, GItem item);

    GItem getItem(int slot);

    void openInventory(GInventory inventory);

    void closeInventory();

    GInventory getOpenInventory();

    void teleport(GLocation location);

    void teleport(GPlayer player);

    GLocation getLocation();

    UUID getUniqueId();

    void setSkin(Skin skin);

    Player getHandle();

}
