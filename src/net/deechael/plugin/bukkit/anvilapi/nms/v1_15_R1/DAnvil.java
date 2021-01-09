package net.deechael.plugin.bukkit.anvilapi.nms.v1_15_R1;

import net.deechael.plugin.bukkit.anvilapi.Slot;
import net.minecraft.server.v1_15_R1.ChatMessage;
import net.minecraft.server.v1_15_R1.Containers;
import net.minecraft.server.v1_15_R1.PacketPlayOutOpenWindow;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DAnvil implements net.deechael.plugin.bukkit.anvilapi.inventory.DAnvil {

    private String title;
    private final Player player;
    private FakeAnvil fakeAnvil;

    public DAnvil(String title, Player player) {
        this.title = title;
        this.player = player;
        this.fakeAnvil = new FakeAnvil(title, player);
    }

    @Override
    public void setItemStack(int slot, ItemStack itemStack) {
        //fakeAnvil.setItem(slot, CraftItemStack.asNMSCopy(itemStack));
        fakeAnvil.castToBukkit().setItem(slot, itemStack);
    }

    @Override
    public void openInventory() {
        //((CraftPlayer) player).getHandle().activeContainer = ((CraftPlayer) player).getHandle().defaultContainer;
        PacketPlayOutOpenWindow packetPlayOutOpenWindow = new PacketPlayOutOpenWindow(this.fakeAnvil.getContainerId(), Containers.ANVIL, new ChatMessage(this.title));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutOpenWindow);
        ((CraftPlayer) player).getHandle().activeContainer = this.fakeAnvil;
        this.fakeAnvil.addSlotListener(((CraftPlayer) player).getHandle());
    }

    @Override
    public FakeAnvil getFakeAnvil() {
        return fakeAnvil;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
