package net.deechael.plugin.bukkit.anvilapi.nms.v1_13_R2;

import net.minecraft.server.v1_13_R2.*;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FakeAnvil extends ContainerAnvil implements net.deechael.plugin.bukkit.anvilapi.inventory.FakeAnvil {

    public FakeAnvil(String title, Player player) {
        super(((CraftPlayer) player).getHandle().inventory, ((CraftPlayer) player).getHandle().world, new BlockPosition(0, 0, 0), ((CraftPlayer) player).getHandle());
        this.checkReachable = false;
        this.a(title);
    }

    @Override
    public void d() {
        super.d();
        this.levelCost = 0;
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
