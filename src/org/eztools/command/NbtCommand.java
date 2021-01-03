package org.eztools.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.eztools.EzTools;

public class NbtCommand extends Command {

    public NbtCommand() {
        super("nbt");
        this.setPermission("eztools.command.nbt");
        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public boolean execute(CommandSender s, String Label, String[] args) {
        if (s instanceof Player) {
            if (args[0].equalsIgnoreCase("skull")) {
                if (args.length == 2) {
                    if (((Player) s).getInventory().getItemInMainHand().getType().equals(Material.PLAYER_HEAD)) {
                        ItemStack skull = ((Player) s).getInventory().getItemInMainHand();
                        SkullMeta meta = (SkullMeta) skull.getItemMeta();
                        meta.setOwningPlayer(Bukkit.getOfflinePlayer(args[1]));
                        skull.setItemMeta(meta);
                    } else {
                        s.sendMessage("§c请拿着头颅使用此指令");
                    }
                } else {
                    s.sendMessage("§c错误的指令用法");
                }
            }
        } else {
            s.sendMessage("§c你必须是个玩家!");
        }
        return true;
    }

}
