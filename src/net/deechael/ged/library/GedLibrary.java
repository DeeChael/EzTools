package net.deechael.ged.library;

//GedLibrary
import net.deechael.ged.library.entity.GPlayer;
import net.deechael.ged.library.exception.PlayerNotExistException;

//Bukkit API
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
//import org.bukkit.plugin.java.JavaPlugin; Removed by EzTools

//Java
import java.util.Collection;
import java.util.HashSet;

public final class GedLibrary /* extends JavaPlugin */ /* Removed by EzTools */ {

    private static GedLibrary instance;

    //@Override Removed by EzTools
    public static /* static added by EzTools */ void onEnable() {
        //instance = this; Removed by EzTools
        Bukkit.getServer().getConsoleSender().sendMessage("§aEnabled GedLibrary");
    }

    /* Removed by EzTools
    public static GedLibrary getInstance() {
        return instance;
    }
    */

    public Collection<GPlayer> getOnlinePlayers() {
        Collection<GPlayer> players = new HashSet<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                players.add(new GPlayer(player));
            } catch (PlayerNotExistException e) {
                e.printStackTrace();
            }
        }
        return players;
    }

}
