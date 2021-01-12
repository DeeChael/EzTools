package org.eztools.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.eztools.EzTools;
import org.eztools.util.JsonConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NbtCommand extends Command {

    public NbtCommand() {
        super("nbt");
        this.setPermission("eztools.command.nbt");
        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public List<String> tabComplete(CommandSender s, String Label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("skull");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("skull")) {
                list.add(EzTools.getLanguageCommand().getString("eztools.args_2.nbt.skull.<playerName>"));
            } else {
                list.add(" ");
            }
        } else {
            list.add(" ");
        }
        return list;
    }

    @Override
    public boolean execute(CommandSender s, String Label, String[] args) {
        if (s instanceof Player) {
            if (args[0].equalsIgnoreCase("skull")) {
                if (args.length == 2) {
                    if (((Player) s).getInventory().getItemInMainHand().getType().equals(Material.PLAYER_HEAD)) {
                        ItemStack skull = ((Player) s).getInventory().getItemInMainHand();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SkullMeta meta = (SkullMeta) skull.getItemMeta();
                                JsonConfiguration jsonConfiguration = new JsonConfiguration("https://api.mojang.com/users/profiles/minecraft/" + args[1]);
                                UUID uuid = UUID.fromString(jsonConfiguration.getString("id"));
                                meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
                                skull.setItemMeta(meta);
                            }
                        }.runTaskAsynchronously(EzTools.getEzTools());
                    } else {
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.hold_skull")));
                    }
                } else {
                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                }
            }
        } else {
            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.must_be_a_player")));
        }
        return true;
    }

}
