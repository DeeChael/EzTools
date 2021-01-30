package org.gedstudio.library.bukkit;

import org.gedstudio.library.bukkit.entity.GPlayer;
import org.gedstudio.library.bukkit.exception.PlayerNotExistException;
import org.gedstudio.library.bukkit.inventory.GAnvil;
import org.gedstudio.library.bukkit.scoreboard.GScoreBoard;
import org.gedstudio.library.bukkit.skin.Skin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
//import org.bukkit.plugin.java.JavaPlugin; removed by DeeChael

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public final class GedLibrary /* extends JavaPlugin removed by EzTools*/ {

    private static GedLibrary instance;
    private static CommandMap commandMap;
    private static Map<String, Skin> skinCache;

    //@Override
    public static void enable() {
        instance = new GedLibrary();
        skinCache = new HashMap<>();
        final Class<?> craftServer = Bukkit.getServer().getClass();
        for (final Method method : craftServer.getDeclaredMethods()) {
            if (method.getName().equals("getCommandMap")) {
                try {
                    commandMap = (CommandMap) method.invoke(Bukkit.getServer(), new Object[0]);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        Bukkit.getServer().getConsoleSender().sendMessage("§aGedLibrary enabled");
        //Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static GedLibrary getInstance() {
        return instance;
    }

    public CommandMap getCommandMap() {
        return commandMap;
    }

    public Map<String, Skin> getSkinCache() {
        return this.skinCache;
    }

    public GAnvil createNewAnvil(String title, GPlayer player) {
        if (this.getNmsVersion().equalsIgnoreCase("v1_13_R1")) {
            return new org.gedstudio.library.bukkit.nms.v1_13_R1.inventory.GAnvil(title, player);
        } else if (this.getNmsVersion().equalsIgnoreCase("v1_13_R2")) {
            return new org.gedstudio.library.bukkit.nms.v1_13_R2.inventory.GAnvil(title, player);
        } else if (this.getNmsVersion().equalsIgnoreCase("v1_14_R1")) {
            return new org.gedstudio.library.bukkit.nms.v1_14_R1.inventory.GAnvil(title, player);
        } else if (this.getNmsVersion().equalsIgnoreCase("v1_15_R1")) {
            return new org.gedstudio.library.bukkit.nms.v1_15_R1.inventory.GAnvil(title, player);
        } else if (this.getNmsVersion().equalsIgnoreCase("v1_16_R1")) {
            return new org.gedstudio.library.bukkit.nms.v1_16_R1.inventory.GAnvil(title, player);
        } else if (this.getNmsVersion().equalsIgnoreCase("v1_16_R2")) {
            return new org.gedstudio.library.bukkit.nms.v1_16_R2.inventory.GAnvil(title, player);
        } else if (this.getNmsVersion().equalsIgnoreCase("v1_16_R3")) {
            return new org.gedstudio.library.bukkit.nms.v1_16_R3.inventory.GAnvil(title, player);
        } else {
            return null;
        }
    }

    public GPlayer getPlayer(Player player) {
        try {
            if (this.getNmsVersion().equalsIgnoreCase("v1_13_R1")) {
                return new org.gedstudio.library.bukkit.nms.v1_13_R1.entity.GPlayer(player);
            } else if (this.getNmsVersion().equalsIgnoreCase("v1_13_R2")) {
                return new org.gedstudio.library.bukkit.nms.v1_13_R2.entity.GPlayer(player);
            } else if (this.getNmsVersion().equalsIgnoreCase("v1_14_R1")) {
                return new org.gedstudio.library.bukkit.nms.v1_14_R1.entity.GPlayer(player);
            } else if (this.getNmsVersion().equalsIgnoreCase("v1_15_R1")) {
                return new org.gedstudio.library.bukkit.nms.v1_15_R1.entity.GPlayer(player);
            } else if (this.getNmsVersion().equalsIgnoreCase("v1_16_R1")) {
                return new org.gedstudio.library.bukkit.nms.v1_16_R1.entity.GPlayer(player);
            } else if (this.getNmsVersion().equalsIgnoreCase("v1_16_R2")) {
                return new org.gedstudio.library.bukkit.nms.v1_16_R2.entity.GPlayer(player);
            } else if (this.getNmsVersion().equalsIgnoreCase("v1_16_R3")) {
                return new org.gedstudio.library.bukkit.nms.v1_16_R3.entity.GPlayer(player);
            } else {
                return null;
            }
        } catch (PlayerNotExistException e) {
            e.printStackTrace();
            return null;
        }
    }

    public GPlayer getPlayer(String name) {
        try {
            if (this.getNmsVersion().equalsIgnoreCase("v1_13_R1")) {
                return new org.gedstudio.library.bukkit.nms.v1_13_R1.entity.GPlayer(name);
            } else if (this.getNmsVersion().equalsIgnoreCase("v1_13_R2")) {
                return new org.gedstudio.library.bukkit.nms.v1_13_R2.entity.GPlayer(name);
            } else if (this.getNmsVersion().equalsIgnoreCase("v1_14_R1")) {
                return new org.gedstudio.library.bukkit.nms.v1_14_R1.entity.GPlayer(name);
            } else if (this.getNmsVersion().equalsIgnoreCase("v1_15_R1")) {
                return new org.gedstudio.library.bukkit.nms.v1_15_R1.entity.GPlayer(name);
            } else if (this.getNmsVersion().equalsIgnoreCase("v1_16_R1")) {
                return new org.gedstudio.library.bukkit.nms.v1_16_R1.entity.GPlayer(name);
            } else if (this.getNmsVersion().equalsIgnoreCase("v1_16_R2")) {
                return new org.gedstudio.library.bukkit.nms.v1_16_R2.entity.GPlayer(name);
            } else if (this.getNmsVersion().equalsIgnoreCase("v1_16_R3")) {
                return new org.gedstudio.library.bukkit.nms.v1_16_R3.entity.GPlayer(name);
            } else {
                return null;
            }
        } catch (PlayerNotExistException e) {
            e.printStackTrace();
            return null;
        }
    }

    public GScoreBoard createNewScoreboard(String title, String... context) {
        if (this.getNmsVersion().equalsIgnoreCase("v1_13_R1")) {
            return new org.gedstudio.library.bukkit.nms.v1_13_R1.scoreboard.GScoreBoard(title, context);
        } else if (this.getNmsVersion().equalsIgnoreCase("v1_13_R2")) {
            return new org.gedstudio.library.bukkit.nms.v1_13_R2.scoreboard.GScoreBoard(title, context);
        } else if (this.getNmsVersion().equalsIgnoreCase("v1_14_R1")) {
            return new org.gedstudio.library.bukkit.nms.v1_14_R1.scoreboard.GScoreBoard(title, context);
        } else if (this.getNmsVersion().equalsIgnoreCase("v1_15_R1")) {
            return new org.gedstudio.library.bukkit.nms.v1_15_R1.scoreboard.GScoreBoard(title, context);
        } else if (this.getNmsVersion().equalsIgnoreCase("v1_16_R1")) {
            return new org.gedstudio.library.bukkit.nms.v1_16_R1.scoreboard.GScoreBoard(title, context);
        } else if (this.getNmsVersion().equalsIgnoreCase("v1_16_R2")) {
            return new org.gedstudio.library.bukkit.nms.v1_16_R2.scoreboard.GScoreBoard(title, context);
        } else if (this.getNmsVersion().equalsIgnoreCase("v1_16_R3")) {
            return new org.gedstudio.library.bukkit.nms.v1_16_R3.scoreboard.GScoreBoard(title, context);
        } else {
            return null;
        }
    }

    public Collection<GPlayer> getOnlinePlayers() {
        Collection<GPlayer> players = new HashSet<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(this.getPlayer(player.getName()));
        }
        return players;
    }

    public String getNmsVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1);
    }

}
