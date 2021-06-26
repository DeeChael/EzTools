package org.eztools.impl;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.eztools.EzT;
import org.eztools.api.item.ItemSaver;
import org.eztools.api.storage.Storage;

import java.util.List;

public final class ItemSaverImpl implements ItemSaver {

    private Storage storage;

    public ItemSaverImpl(Storage storage) {
        this.storage = storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }

    @Override
    public boolean has(String name) {
        return this.storage.has(name);
    }

    @Override
    public void save(String name, ItemStack itemStack) {
        if (!storage.has(name)) {
            this.storage.set(name, EzT.getTransferTool().toJsonObjectString(itemStack));
        }
    }

    @Override
    public ItemStack get(String name) {
        if (storage.has(name)) {
            return EzT.getTransferTool().toItemStack(storage.get(name));
        }
        return new ItemStack(Material.AIR);
    }

    @Override
    public List<String> listNames() {
        return storage.keys();
    }

    @Override
    public ItemStack remove(String name) {
        if (storage.has(name)) {
            return EzT.getTransferTool().toItemStack(storage.remove(name));
        }
        return new ItemStack(Material.AIR);
    }

    @Override
    public boolean removeAll() {
        if (storage.keys().size() > 0) {
            storage.removeAll();
            return true;
        }
        return false;
    }
}
