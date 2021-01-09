package net.deechael.plugin.bukkit.anvilapi.nms.v1_14_R1;

import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FakeAnvil extends ContainerAnvil implements net.deechael.plugin.bukkit.anvilapi.inventory.FakeAnvil {

    public FakeAnvil(String title, Player player) {
        super(((CraftPlayer) player).getHandle().nextContainerCounter(), ((CraftPlayer) player).getHandle().inventory, ContainerAccess.at(((CraftWorld) player.getWorld()).getHandle(), new BlockPosition(0, 0, 0)));
        this.checkReachable = false;
        this.setTitle(new ChatMessage(title));
    }

    @Override
    public void e() {
        super.e();
        this.levelCost.a(0);
    }

    @Override
    public void b(EntityHuman entityhuman) {
    }

    @Override
    protected void a(EntityHuman entityhuman, World world, IInventory iinventory) {
    }

    public int getContainerId() {
        return windowId;
    }

    @Override
    public Inventory castToBukkit() {
        return this.getBukkitView().getTopInventory();
    }

}
