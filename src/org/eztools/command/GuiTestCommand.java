package org.eztools.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.eztools.EzTools;
import org.gedstudio.library.bukkit.GedLibrary;
import org.gedstudio.library.bukkit.inventory.GAnvil;
import org.gedstudio.library.bukkit.inventory.GItem;

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
            GAnvil anvil = GedLibrary.getInstance().createNewAnvil("TestAnvil", GedLibrary.getInstance().getPlayer((Player) s));
            anvil.setItem(GAnvil.Slot.INPUT_LEFT, new GItem(new ItemStack(Material.PAPER)));
            anvil.openInventory();
        } else {
            s.sendMessage("§c你必须是个玩家!");
        }
        return true;
    }

}
