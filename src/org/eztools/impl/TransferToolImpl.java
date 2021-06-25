package org.eztools.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.eztools.EzT;
import org.eztools.api.item.TransferTool;

public final class TransferToolImpl implements TransferTool {

    @Override
    public ItemStack toItemStack(String jsonObjectString) {
        return this.toItemStack(new JsonParser().parse("{}").getAsJsonObject());
    }

    @Override
    public ItemStack toItemStack(JsonObject jsonObject) {
        return new ItemStack(Material.AIR);
    }

    @Override
    public String toJsonObjectString(ItemStack itemStack) {
        return new Gson().toJson(toJsonObject(itemStack));
    }

    @Override
    public JsonObject toJsonObject(ItemStack itemStack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("callback", EzT.getUsingLanguage().get("ezt.loading.failed.loadLibrary"));
        return jsonObject;
    }

}
