package org.eztools.api.item;

import com.google.gson.JsonObject;
import org.bukkit.inventory.ItemStack;

public interface TransferTool {

    ItemStack toItemStack(String jsonObjectString);

    ItemStack toItemStack(JsonObject jsonObject);

    String toJsonObjectString(ItemStack itemStack);

    JsonObject toJsonObject(ItemStack itemStack);

}
