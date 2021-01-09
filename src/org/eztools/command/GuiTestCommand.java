package org.eztools.command;

import net.deechael.plugin.bukkit.anvilapi.AnvilAPI;
import net.deechael.plugin.bukkit.anvilapi.Slot;
import net.deechael.plugin.bukkit.anvilapi.inventory.DAnvil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.eztools.EzTools;

import java.util.ArrayList;
import java.util.List;

public class GuiTestCommand extends Command {

    public GuiTestCommand() {
        super("guitest");
        this.setPermission("eztools.command.guitest");
        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public List<String> tabComplete(CommandSender s, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length >= 1) {
            list.add("<物品名称>");
        } else {
            list.add(" ");
        }
        return list;
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (s instanceof Player) {
            DAnvil anvil = AnvilAPI.getAnvilAPI().getAnvil("TestAnvil", (Player) s);
            anvil.setItemStack(Slot.INPUT_LEFT, new ItemStack(Material.PAPER));
            anvil.openInventory();
        } else {
            s.sendMessage("§c你必须是个玩家!");
        }
        return true;
    }

}
