package net.deechael.plugin.bukkit.anvilapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.EzTools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class DAnvil {

    private Player player;
    private DAnvilClickEventListener handler;
    private static Class<?> BlockPosition;
    private static Class<?> PacketPlayOutOpenWindow;
    private static Class<?> ContainerAnvil;
    private static Class<?> ChatMessage;
    private static Class<?> EntityHuman;
    private HashMap<DAnvilSlot, ItemStack> items = new HashMap<DAnvilSlot, ItemStack>();
    private Inventory inv;
    private Listener listener;

    private void loadClasses() {
        BlockPosition = NetMinecraftServerManager.getInstance().getNMSClass("BlockPosition");
        PacketPlayOutOpenWindow = NetMinecraftServerManager.getInstance().getNMSClass("PacketPlayOutOpenWindow");
        ContainerAnvil = NetMinecraftServerManager.getInstance().getNMSClass("ContainerAnvil");
        EntityHuman = NetMinecraftServerManager.getInstance().getNMSClass("EntityHuman");
        ChatMessage = NetMinecraftServerManager.getInstance().getNMSClass("ChatMessage");
    }

    public DAnvil(final Player player, final DAnvilClickEventListener handler) {
        loadClasses();
        this.player = player;
        this.handler = handler;

        this.listener = new Listener() {
            @EventHandler
            public void onInventoryClick(InventoryClickEvent event) {
                if (event.getWhoClicked() instanceof Player) {
                    if (event.getInventory().equals(inv)) {
                        event.setCancelled(true);
                        ItemStack item = event.getCurrentItem();
                        int slot = event.getRawSlot();
                        String name = " ";
                        if (item != null) {
                            if (item.hasItemMeta()) {
                                ItemMeta meta = item.getItemMeta();

                                if (meta.hasDisplayName()) {
                                    name = meta.getDisplayName();
                                }
                            }
                        }
                        DAnvilClickEvent clickEvent = new DAnvilClickEvent(DAnvilSlot.bySlot(slot), name, items.get(slot), player);
                        handler.onAnvilClick(clickEvent);
                        event.setCancelled(clickEvent.isCancelled());
                        if (clickEvent.getWillClose()) {
                            event.getWhoClicked().closeInventory();
                        }
                        if (clickEvent.getWillDestroy()) {
                            destroy();
                        }
                    }
                }
            }

            @EventHandler
            public void onInventoryClose(InventoryCloseEvent event) {
                if (event.getPlayer() instanceof Player) {
                    Inventory inv = event.getInventory();
                    player.setLevel(player.getLevel() - 1);
                    if (inv.equals(DAnvil.this.inv)) {
                        inv.clear();
                        destroy();
                    }
                }
            }

            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event) {
                if (event.getPlayer().equals(getPlayer())) {
                    player.setLevel(player.getLevel() - 1);
                    destroy();
                }
            }
        };
        Bukkit.getPluginManager().registerEvents(listener, EzTools.getEzTools());
    }

    public Player getPlayer() {
        return player;
    }

    public void setSlot(DAnvilSlot slot, ItemStack item) {
        items.put(slot, item);
    }

    public void open() {
        player.setLevel(player.getLevel() + 1);

        try {
            Object p = NetMinecraftServerManager.getInstance().getHandle(player);

            Object container = ContainerAnvil.getConstructor(NetMinecraftServerManager.getInstance().getNMSClass("PlayerInventory"), NetMinecraftServerManager.getInstance().getNMSClass("World"), BlockPosition, EntityHuman).newInstance(NetMinecraftServerManager.getInstance().getPlayerField(player, "inventory"), NetMinecraftServerManager.getInstance().getPlayerField(player, "world"), BlockPosition.getConstructor(int.class, int.class, int.class).newInstance(0, 0, 0), p);
            NetMinecraftServerManager.getInstance().getField(NetMinecraftServerManager.getInstance().getNMSClass("Container"), "checkReachable").set(container, false);

            Object bukkitView = NetMinecraftServerManager.getInstance().invokeMethod("getBukkitView", container);
            inv = (Inventory) NetMinecraftServerManager.getInstance().invokeMethod("getTopInventory", bukkitView);

            for (DAnvilSlot slot : items.keySet()) {
                inv.setItem(slot.getSlot(), items.get(slot));
            }

            int c = (int) NetMinecraftServerManager.getInstance().invokeMethod("nextContainerCounter", p);

            Constructor<?> chatMessageConstructor = ChatMessage.getConstructor(String.class, Object[].class);
            Object playerConnection = NetMinecraftServerManager.getInstance().getPlayerField(player, "playerConnection");
            Object packet = PacketPlayOutOpenWindow.getConstructor(int.class, String.class, NetMinecraftServerManager.getInstance().getNMSClass("IChatBaseComponent"), int.class).newInstance(c, "minecraft:anvil", chatMessageConstructor.newInstance("Repairing", new Object[]{}), 0);

            Method sendPacket = NetMinecraftServerManager.getInstance().getMethod("sendPacket", playerConnection.getClass(), PacketPlayOutOpenWindow);
            sendPacket.invoke(playerConnection, packet);

            Field activeContainerField = NetMinecraftServerManager.getInstance().getField(EntityHuman, "activeContainer");
            if (activeContainerField != null) {
                activeContainerField.set(p, container);

                NetMinecraftServerManager.getInstance().getField(NetMinecraftServerManager.getInstance().getNMSClass("Container"), "windowId").set(activeContainerField.get(p), c);

                NetMinecraftServerManager.getInstance().getMethod("addSlotListener", activeContainerField.get(p).getClass(), p.getClass()).invoke(activeContainerField.get(p), p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        player = null;
        handler = null;
        items = null;
        HandlerList.unregisterAll(listener);
        listener = null;
    }

}
