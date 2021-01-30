package org.gedstudio.library.bukkit.skin;

import org.bukkit.scheduler.BukkitRunnable;
import org.eztools.EzTools;
import org.gedstudio.library.bukkit.GedLibrary;
import org.gedstudio.library.bukkit.configuration.JsonConfiguration;

import java.io.Serializable;

public class Skin implements Serializable {

    private String name;
    private String uuid;
    private String value;
    private String signature;

    public Skin(String playerName) {
        this.name = playerName;
        if (GedLibrary.getInstance().getSkinCache().containsKey(playerName)) {
            Skin cache = GedLibrary.getInstance().getSkinCache().get(playerName);
            uuid = cache.getUuid();
            value = cache.getValue();
            signature = cache.getSignature();

        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    JsonConfiguration api1 = new JsonConfiguration("https://api.mojang.com/users/profiles/minecraft/" + playerName);
                    uuid = api1.getString("id");
                    JsonConfiguration api2 = new JsonConfiguration("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
                    value = api2.getString("value");
                    signature = api2.getString("signature");
                    GedLibrary.getInstance().getSkinCache().put(playerName, getHandle());
                }
            }.runTaskAsynchronously(EzTools.getEzTools());
        }
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }

    public Skin getHandle() {
        return this;
    }

}
