package org.eztools.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.eztools.EzTools;

public class EntityEventListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (EzTools.getSelectedEntities().containsValue(e.getEntity())) {
            Player player = null;
            for (Player p : EzTools.getSelectedEntities().keySet()) {
                if (EzTools.getSelectedEntities().get(p).equals(e.getEntity())) {
                    player = p;
                }
            }
            EzTools.getSelectedEntities().remove(player);
            player.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.select.dead")));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (EzTools.getSelectedEntities().containsKey(e.getPlayer())) {
            EzTools.getSelectedEntities().remove(e.getPlayer());
        }
        if (EzTools.getEditingItem().containsKey(e.getPlayer())) {
            EzTools.getEditingItem().remove(e.getPlayer());
        }
    }

}
