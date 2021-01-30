package org.gedstudio.library.bukkit.scoreboard;

import org.bukkit.scoreboard.Scoreboard;
import org.gedstudio.library.bukkit.entity.GPlayer;

import java.util.List;

public interface GScoreBoard {

    String getTitle();

    List<GPlayer> getPlayers();

    List<String> getContext();

    void addPlayer(GPlayer player);

    void removePlayer(GPlayer player);

    Scoreboard getHandle();

}
