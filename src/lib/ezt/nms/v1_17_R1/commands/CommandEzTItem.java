package lib.ezt.nms.v1_17_R1.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.*;
import net.minecraft.core.IRegistry;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.enchantment.Enchantment;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_17_R1.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.EzT;
import org.eztools.api.color.ColorFormat;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class CommandEzTItem {

    private static final SuggestionProvider<CommandListenerWrapper> attributes = (commandContext, suggestionsBuilder) -> ICompletionProvider.a(IRegistry.al.keySet(), suggestionsBuilder);;

    public static void register(CommandDispatcher<CommandListenerWrapper> commandDispatcher) {
        commandDispatcher.register(
                net.minecraft.commands.CommandDispatcher
                        .a("ezt-item")
                        .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item"))
                        .then(net.minecraft.commands.CommandDispatcher
                                .a("name")
                                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.name"))
                                .then(net.minecraft.commands.CommandDispatcher
                                        .a("content", ArgumentChat.a())
                                        .executes((commandContext -> executeName(commandContext.getSource(), commandContext.getSource().h(), ArgumentChat.a(commandContext, "content"))))
                                )
                        )
                        .then(net.minecraft.commands.CommandDispatcher
                                .a("lore")
                                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.lore"))
                                .then(net.minecraft.commands.CommandDispatcher
                                        .a("content", ArgumentNBTBase.a())
                                        .executes((commandContext -> executeLore(commandContext.getSource(), commandContext.getSource().h(), ArgumentNBTBase.a(commandContext, "content"))))
                                )
                        )
                        .then(net.minecraft.commands.CommandDispatcher
                                .a("enchant")
                                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.enchant"))
                                .then(net.minecraft.commands.CommandDispatcher
                                        .a("enchantment", ArgumentEnchantment.a())
                                        .executes((commandContext -> executeEnchant(commandContext.getSource(), commandContext.getSource().h(), ArgumentEnchantment.a(commandContext, "enchantment"), 1)))
                                        .then(net.minecraft.commands.CommandDispatcher
                                                .a("level", IntegerArgumentType.integer(0, 32767))
                                                .suggests(((commandContext, suggestionsBuilder) -> {
                                                        Enchantment enchantment = ArgumentEnchantment.a(commandContext, "enchantment");
                                                        for (int i = 0; i < enchantment.getMaxLevel() + 1; i++) {
                                                            suggestionsBuilder.suggest(i);
                                                        }
                                                        return suggestionsBuilder.buildFuture();
                                                }))
                                                .executes((commandContext -> executeEnchant(commandContext.getSource(), commandContext.getSource().h(), ArgumentEnchantment.a(commandContext, "enchantment"), IntegerArgumentType.getInteger(commandContext, "level"))))
                                        )
                                )
                        )
                        .then(net.minecraft.commands.CommandDispatcher
                                .a("attribute", StringArgumentType.string())
                                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.attribute"))
                                .then(net.minecraft.commands.CommandDispatcher
                                        .a("attribute", ArgumentMinecraftKeyRegistered.a())
                                        .suggests(((commandContext, suggestionsBuilder) -> {
                                            for (Attribute attribute : Attribute.values()) {
                                                suggestionsBuilder.suggest(attribute.name().toLowerCase());
                                            }
                                            return suggestionsBuilder.buildFuture();
                                        }))
                                        .then(net.minecraft.commands.CommandDispatcher
                                                .a("slot", StringArgumentType.string())
                                                .suggests(((commandContext, suggestionsBuilder) -> {
                                                    for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                                                        suggestionsBuilder.suggest(equipmentSlot.name().toLowerCase());
                                                    }
                                                    return suggestionsBuilder.buildFuture();
                                                }))
                                                .then(net.minecraft.commands.CommandDispatcher
                                                        .a("add")
                                                        .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.attribute.add"))
                                                        .then(net.minecraft.commands.CommandDispatcher
                                                                .a("amount", DoubleArgumentType.doubleArg(-2048.0, 2048.0))
                                                                .executes((commandContext -> executeAttributeAdd(commandContext.getSource(), commandContext.getSource().h(), Attribute.valueOf(StringArgumentType.getString(commandContext, "attribute")), EquipmentSlot.valueOf(StringArgumentType.getString(commandContext, "slot")), DoubleArgumentType.getDouble(commandContext, "amount"))))
                                                        )
                                                )
                                                .then(net.minecraft.commands.CommandDispatcher
                                                        .a("set")
                                                        .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.attribute.set"))
                                                        .then(net.minecraft.commands.CommandDispatcher
                                                                .a("amount", DoubleArgumentType.doubleArg(-2048.0, 2048.0))
                                                                .executes((commandContext -> executeAttributeSet(commandContext.getSource(), commandContext.getSource().h(), Attribute.valueOf(StringArgumentType.getString(commandContext, "attribute")), EquipmentSlot.valueOf(StringArgumentType.getString(commandContext, "slot")), DoubleArgumentType.getDouble(commandContext, "amount"))))
                                                        )
                                                )
                                                .then(net.minecraft.commands.CommandDispatcher
                                                        .a("remove")
                                                        .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.attribute.remove"))
                                                        .then(net.minecraft.commands.CommandDispatcher
                                                                .a("amount", DoubleArgumentType.doubleArg(-2048.0, 2048.0))
                                                                .executes((commandContext -> executeAttributeRemove(commandContext.getSource(), commandContext.getSource().h(), Attribute.valueOf(StringArgumentType.getString(commandContext, "attribute")), EquipmentSlot.valueOf(StringArgumentType.getString(commandContext, "slot")))))
                                                        )
                                                )
                                                .then(net.minecraft.commands.CommandDispatcher
                                                        .a("get")
                                                        .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.attribute.get"))
                                                        .then(net.minecraft.commands.CommandDispatcher
                                                                .a("amount", DoubleArgumentType.doubleArg(-2048.0, 2048.0))
                                                                .executes((commandContext -> executeAttributeGet(commandContext.getSource(), commandContext.getSource().h(), Attribute.valueOf(StringArgumentType.getString(commandContext, "attribute")), EquipmentSlot.valueOf(StringArgumentType.getString(commandContext, "slot")))))
                                                        )
                                                )
                                        )
                                )
                        )
                        .then(net.minecraft.commands.CommandDispatcher
                                .a("unbreakable")
                                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.unbreakable"))
                                .executes(commandContext -> executeUnbreakable(commandContext.getSource(), Lists.newArrayList(commandContext.getSource().h())))
                                .then(net.minecraft.commands.CommandDispatcher
                                        .a("targets", ArgumentEntity.d())
                                        .executes((commandContext) -> executeUnbreakable(commandContext.getSource(), ArgumentEntity.f(commandContext, "targets")))
                                )
                        )
        );
    }

    private static int executeName(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, IChatBaseComponent iChatBaseComponent) {
        int i = 0;
        if (iChatBaseComponent != null) {
            ItemStack itemStack = ((Player) entityPlayer.getBukkitEntity()).getInventory().getItemInMainHand();
            if (itemStack.getType() != Material.AIR) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ColorFormat.translate(iChatBaseComponent.getText()));
                itemStack.setItemMeta(itemMeta);
                commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.name.success").replace("{MainHandDisplayName}", ColorFormat.translate(iChatBaseComponent.getText()))), false);
                i++;
            }
        }
        return i;
    }

    private static int executeLore(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, NBTBase nbtBase) {
        int i = 0;
        if (nbtBase instanceof NBTTagList) {
            NBTTagList nbtTagList = (NBTTagList) nbtBase;
            List<String> lore = new ArrayList<>();
            for (NBTBase nbtBase1 : nbtTagList) {
                if (nbtBase1 instanceof NBTTagString) {
                    lore.add(nbtBase1.asString());
                }
            }
            ItemStack itemStack = ((Player) entityPlayer.getBukkitEntity()).getInventory().getItemInMainHand();
            if (itemStack.getType() != Material.AIR) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.lore.success")), false);
                i++;
            }
        }
        return i;
    }

    private static int executeEnchant(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, Enchantment enchantment, int level) {
        int i = 0;
        if (enchantment != null) {
            ItemStack itemStack = ((Player) entityPlayer.getBukkitEntity()).getInventory().getItemInMainHand();
            if (itemStack.getType() != Material.AIR) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                org.bukkit.enchantments.Enchantment enchant = org.bukkit.enchantments.Enchantment.getByName(new CraftEnchantment(enchantment).getName());
                if (level == 0) {
                    if (itemMeta.hasEnchant(enchant)) {
                        itemMeta.removeEnchant(enchant);
                    }
                } else {
                    itemMeta.addEnchant(enchant, level, true);
                }
                itemStack.setItemMeta(itemMeta);
                commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.enchant.success")), false);
                i++;
            }
        }
        return i;
    }

    private static int executeAttributeAdd(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, Attribute attribute, EquipmentSlot slot, double amount) {
        int i = 0;
        return i;
    }

    private static int executeAttributeSet(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, Attribute attribute, EquipmentSlot slot, double amount) {
        int i = 0;
        return i;
    }

    private static int executeAttributeRemove(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, Attribute attribute, EquipmentSlot slot) {
        int i = 0;
        return i;
    }

    private static int executeAttributeGet(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, Attribute attribute, EquipmentSlot slot) {
        int i = 0;
        return i;
    }

    private static int executeUnbreakable(CommandListenerWrapper commandListenerWrapper, Collection<EntityPlayer> entityPlayers) {
        int i = 0;
        return i;
    }

}
