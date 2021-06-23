package lib.ezt.nms.v1_14_R1.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_14_R1.enchantments.CraftEnchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eztools.EzT;
import org.eztools.api.color.ColorFormat;
import org.eztools.utils.ReflectionAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CommandEzTItem {


    public static void register(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commandDispatcher) {
        //Main command
        LiteralArgumentBuilder<CommandListenerWrapper> command = CommandDispatcher
                .a("ezt-item")
                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item"));
        //Name command
        command.then(CommandDispatcher
                .a("name")
                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.name"))
                .then(CommandDispatcher
                        .a("content", ArgumentChat.a())
                        .executes((commandContext -> executeName(commandContext.getSource(), commandContext.getSource().h(), ArgumentChat.a(commandContext, "content"))))
                )
        );
        //Lore command
        command.then(CommandDispatcher
                .a("lore")
                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.lore"))
                .then(CommandDispatcher
                        .a("content", ArgumentNBTBase.a())
                        .executes((commandContext -> executeLore(commandContext.getSource(), commandContext.getSource().h(), ArgumentNBTBase.a(commandContext, "content"))))
                )
        );
        //Enchant command
        command.then(CommandDispatcher
                .a("enchant")
                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.enchant"))
                .then(CommandDispatcher
                        .a("enchantment", ArgumentEnchantment.a())
                        .executes((commandContext -> executeEnchant(commandContext.getSource(), commandContext.getSource().h(), ArgumentEnchantment.a(commandContext, "enchantment"), 1)))
                        .then(CommandDispatcher
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
        );
        //attribute command
        LiteralArgumentBuilder<CommandListenerWrapper> attributeCommand =  CommandDispatcher
                .a("attribute")
                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.attribute"));
        for (Attribute attribute : Attribute.values()) {
            LiteralArgumentBuilder<CommandListenerWrapper> subAttribute = CommandDispatcher
                    .a(attribute.name().toLowerCase());
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                subAttribute.then(CommandDispatcher
                        .a(equipmentSlot.name().toLowerCase())
                        .then(CommandDispatcher
                                .a("add")
                                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.attribute.add"))
                                .then(CommandDispatcher
                                        .a("amount", DoubleArgumentType.doubleArg())
                                        .executes((commandContext -> executeAttributeAdd(commandContext.getSource(), commandContext.getSource().h(), attribute, equipmentSlot, DoubleArgumentType.getDouble(commandContext, "amount"))))
                                )
                        )
                        .then(CommandDispatcher
                                .a("set")
                                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.attribute.set"))
                                .then(CommandDispatcher
                                        .a("amount", DoubleArgumentType.doubleArg())
                                        .executes((commandContext -> executeAttributeSet(commandContext.getSource(), commandContext.getSource().h(), attribute, equipmentSlot, DoubleArgumentType.getDouble(commandContext, "amount"))))
                                )
                        )
                        .then(CommandDispatcher
                                .a("remove")
                                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.attribute.remove"))
                                .executes((commandContext -> executeAttributeRemove(commandContext.getSource(), commandContext.getSource().h(), attribute, equipmentSlot)))
                        )
                        .then(CommandDispatcher
                                .a("get")
                                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.attribute.get"))
                                .executes((commandContext -> executeAttributeGet(commandContext.getSource(), commandContext.getSource().h(), attribute, equipmentSlot)))
                        )
                );
            }
            attributeCommand.then(subAttribute);
        }
        command.then(attributeCommand);
        command.then(CommandDispatcher
                .a("unbreakable")
                .requires((requirement) -> requirement.hasPermission(4, "ezt.command.ezt-item.unbreakable"))
                .executes(commandContext -> executeUnbreakable(commandContext.getSource(), Lists.newArrayList(commandContext.getSource().h())))
                .then(CommandDispatcher
                        .a("targets", ArgumentEntity.d())
                        .executes((commandContext) -> executeUnbreakable(commandContext.getSource(), ArgumentEntity.f(commandContext, "targets")))
                )
        );
        commandDispatcher.register(command);
    }

    private static int executeName(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, IChatBaseComponent iChatBaseComponent) {
        int i = 0;
        if (iChatBaseComponent != null) {
            ItemStack itemStack = ((Player) entityPlayer.getBukkitEntity()).getInventory().getItemInMainHand();
            if (itemStack.getType() != Material.AIR) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta != null) {
                    itemMeta.setDisplayName(ColorFormat.translate(iChatBaseComponent.getText()));
                    itemStack.setItemMeta(itemMeta);
                    commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.name.success").replace("{MainHandDisplayName}", ColorFormat.translate(iChatBaseComponent.getText()))), false);
                    i++;
                }
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
                if (itemMeta != null) {
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                    commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.lore.success")), false);
                    i++;
                }
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
                if (itemMeta != null) {
                    org.bukkit.enchantments.Enchantment enchant = org.bukkit.enchantments.Enchantment.getByName(new CraftEnchantment(enchantment).getName());
                    assert enchant != null;
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
        }
        return i;
    }

    private static int executeAttributeAdd(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, Attribute attribute, EquipmentSlot slot, double amount) {
        int i = 0;
        if (attribute != null && slot != null) {
            ItemStack itemStack = ((Player) entityPlayer.getBukkitEntity()).getInventory().getItemInMainHand();
            if (itemStack.getType() != Material.AIR) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta != null) {
                    if (ReflectionAPI.getVersion() >= 13) {
                        if (attribute == Attribute.GENERIC_KNOCKBACK_RESISTANCE) {
                            amount = amount / 10;
                        }
                    }
                    if (itemMeta.hasAttributeModifiers()) {
                        AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "ezt", amount, AttributeModifier.Operation.ADD_NUMBER, slot);
                        itemMeta.addAttributeModifier(attribute, attributeModifier);
                        itemStack.setItemMeta(itemMeta);
                        commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.attribute.set.success")), false);
                        i++;
                    }
                }
            }
        }
        return i;
    }

    private static int executeAttributeSet(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, Attribute attribute, EquipmentSlot slot, double amount) {
        int i = 0;
        if (attribute != null && slot != null) {
            ItemStack itemStack = ((Player) entityPlayer.getBukkitEntity()).getInventory().getItemInMainHand();
            if (itemStack.getType() != Material.AIR) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta != null) {
                    if (ReflectionAPI.getVersion() >= 13) {
                        if (attribute == Attribute.GENERIC_KNOCKBACK_RESISTANCE) {
                            amount = amount / 10;
                        }
                    }
                    double original = 0.0;
                    if (itemMeta.hasAttributeModifiers()) {
                        Collection<AttributeModifier> collection = itemMeta.getAttributeModifiers(attribute);
                        if (collection != null) {
                            if (collection.size() > 0) {
                                for (AttributeModifier attributeModifier : collection) {
                                    if (attributeModifier.getSlot() == slot) {
                                        if (attributeModifier.getOperation() == AttributeModifier.Operation.ADD_NUMBER || attributeModifier.getOperation() == AttributeModifier.Operation.ADD_SCALAR) {
                                            original += attributeModifier.getAmount();
                                        }
                                        itemMeta.removeAttributeModifier(attribute, attributeModifier);
                                    }
                                }
                            }
                        }
                        AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), "ezt", original + amount, AttributeModifier.Operation.ADD_NUMBER, slot);
                        itemMeta.addAttributeModifier(attribute, attributeModifier);
                        itemStack.setItemMeta(itemMeta);
                        commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.attribute.set.success")), false);
                        i++;
                    }
                }
            }
        }
        return i;
    }

    private static int executeAttributeRemove(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, Attribute attribute, EquipmentSlot slot) {
        int i = 0;
        if (attribute != null && slot != null) {
            ItemStack itemStack = ((Player) entityPlayer.getBukkitEntity()).getInventory().getItemInMainHand();
            if (itemStack.getType() != Material.AIR) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta != null) {
                    Collection<AttributeModifier> collection = itemMeta.getAttributeModifiers(attribute);
                    if (collection != null) {
                        if (collection.size() > 0) {
                            for (AttributeModifier attributeModifier : collection) {
                                if (attributeModifier.getSlot() == slot) {
                                    itemMeta.removeAttributeModifier(attribute, attributeModifier);
                                }
                            }
                        }
                    }
                }
                commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.attribute.set.success")), false);
                i++;
            }
        }
        return i;
    }

    private static int executeAttributeGet(CommandListenerWrapper commandListenerWrapper, EntityPlayer entityPlayer, Attribute attribute, EquipmentSlot slot) {
        int i = 0;
        if (attribute != null && slot != null) {
            ItemStack itemStack = ((Player) entityPlayer.getBukkitEntity()).getInventory().getItemInMainHand();
            if (itemStack.getType() != Material.AIR) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                double amount = 0.0;
                if (itemMeta != null) {
                    Collection<AttributeModifier> collection = itemMeta.getAttributeModifiers(attribute);
                    if (collection != null) {
                        if (collection.size() > 0) {
                            for (AttributeModifier attributeModifier : collection) {
                                if (attributeModifier.getSlot() == slot) {
                                    if (attributeModifier.getOperation() == AttributeModifier.Operation.ADD_NUMBER || attributeModifier.getOperation() == AttributeModifier.Operation.ADD_SCALAR) {
                                        amount += attributeModifier.getAmount();
                                    }
                                    itemMeta.removeAttributeModifier(attribute, attributeModifier);
                                }
                            }
                        }
                    }
                }
                commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.attribute.set.success").replace("{amount}", String.valueOf(amount))), false);
                i++;
            }
        }
        return i;
    }

    private static int executeUnbreakable(CommandListenerWrapper commandListenerWrapper, Collection<EntityPlayer> entityPlayers) {
        int i = 0;
        List<EntityPlayer> list = new ArrayList<>();
        if (entityPlayers != null) {
            list.addAll(entityPlayers);
        }
        if (list.size() > 0) {
            for (EntityPlayer entityPlayer : list) {
                ItemStack itemStack = ((Player) entityPlayer.getBukkitEntity()).getInventory().getItemInMainHand();
                if (itemStack.getType() != Material.AIR) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if (itemMeta != null) {
                        itemMeta.setUnbreakable(!itemMeta.isUnbreakable());
                        itemStack.setItemMeta(itemMeta);
                        i++;
                    }
                }
            }
        }
        if (i == 1) {
            list.get(0).getBukkitEntity().sendMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.unbreakable.player.success"));
            commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.unbreakable.single.success").replace("{target}", list.get(0).displayName)), false);
        } else if (i > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (EntityPlayer entityPlayer : entityPlayers) {
                stringBuilder.append(entityPlayer.displayName).append(", ");
                entityPlayer.getBukkitEntity().sendMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.unbreakable.player.success"));
            }
            String target = stringBuilder.substring(0, stringBuilder.length() - 2);
            commandListenerWrapper.sendMessage(new ChatMessage(EzT.LANGUAGE.getString("ezt.command.ezt-item.unbreakable.multi.success").replace("{target}", target)), false);
        }
        return i;
    }


}
