package org.eztools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.eztools.EzTools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityCommand extends Command {

    public EntityCommand() {
        super("entity");
        this.setPermission("eztools.command.entity");
        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public List<String> tabComplete(CommandSender s, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            //list.add("gui");
            list.add("select");
            list.add("save");
            list.add("summon");
            list.add("item");
            list.add("chance");
            list.add("attribute");
            list.add("name");
            list.add("glowing");
        } else if (args.length >= 2) {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("select")) {
                    list.add("cancel");
                } else if (args[0].equalsIgnoreCase("item")) {
                    for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                        list.add(StringUtils.lowerCase(equipmentSlot.name()));
                    }
                } else if (args[0].equalsIgnoreCase("chance")) {
                    for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                        list.add(StringUtils.lowerCase(equipmentSlot.name()));
                    }
                } else if (args[0].equalsIgnoreCase("attribute")) {
                    for (Attribute attribute : Attribute.values()) {
                        list.add(StringUtils.lowerCase(attribute.name()));
                    }
                } else if (args[0].equalsIgnoreCase("name")) {
                    list.add(EzTools.getLanguageCommand().getString("eztools.args_2.entity.name.<entityName>"));
                } else if (args[0].equalsIgnoreCase("save")) {
                    list.add(EzTools.getLanguageCommand().getString("eztools.args_2.entity.save.<localName>"));
                } else if (args[0].equalsIgnoreCase("summon")) {
                    for (String string : EzTools.getConfigHandler().getEntitiesStringList()) {
                        list.add(string);
                    }
                } else {
                    list.add(" ");
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("chance")) {
                    list.add("0");
                    list.add("25");
                    list.add("50");
                    list.add("75");
                    list.add("100");
                    list.add(EzTools.getLanguageCommand().getString("eztools.args_3.entity.chance.<dropChance>"));
                } else if (args[0].equalsIgnoreCase("attribute")) {
                    list.add(EzTools.getLanguageCommand().getString("eztools.args_3.entity.attribute.<attributeAmount>"));
                } else if (args[0].equalsIgnoreCase("name")) {
                    list.add(EzTools.getLanguageCommand().getString("eztools.args_2.entity.name.<entityName>"));
                } else {
                    list.add(" ");
                }
            }
        } else {
            if (args[0].equalsIgnoreCase("name")) {
                list.add(EzTools.getLanguageCommand().getString("eztools.args_2.entity.name.<entityName>"));
            }else {
                list.add(" ");
            }
        }
        return list;
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if (s instanceof Player) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("gui")) {
                    s.sendMessage("Haven't finished yet");
                } else if (args[0].equalsIgnoreCase("select")) {
                    Entity entity = getNearestEntityInSight((Player) s);
                    if (entity != null) {
                        if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                            EzTools.getSelectedEntities().remove((Player) s);
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.select.cancel_last")));
                        }
                        EzTools.getSelectedEntities().put((Player) s, entity);
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.select.success")));
                    } else {
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.look_at_an_entity")));
                    }
                } else if (args[0].equalsIgnoreCase("loot")) {
                    if (EzTools.getSelectedEntities().containsKey(s)) {
                        Entity entity = EzTools.getSelectedEntities().get(s);
                        if (entity instanceof Mob) {
                            Mob mob = (Mob) entity;
                            s.sendMessage("Haven't finished yet");
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.cannot_edit_select_entity")));
                        }
                    } else {
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.select_an_entity")));
                    }
                } else if (args[0].equalsIgnoreCase("glowing")) {
                    if (EzTools.getSelectedEntities().containsKey(s)) {
                        Entity entity = EzTools.getSelectedEntities().get(s);
                        if (entity instanceof Mob) {
                            Mob mob = (Mob) entity;
                            mob.setGlowing(!mob.isGlowing());
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.glowing.switch")));
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.cannot_edit_select_entity")));
                        }
                    } else {
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.select_an_entity")));
                    }
                } else {
                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                }
            } else if (args.length >= 2) {
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("item")) {
                        if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                            EquipmentSlot slot = EquipmentSlot.valueOf(StringUtils.upperCase(args[1]));
                            if (slot != null) {
                                Entity entity = EzTools.getSelectedEntities().get((Player) s);
                                if (entity instanceof Mob) {
                                    Mob mob = (Mob) entity;
                                    ItemStack itemStack = ((Player) s).getInventory().getItemInMainHand().clone();
                                    if (itemStack != null || itemStack.getType() != Material.AIR) {
                                        if (slot.equals(EquipmentSlot.HAND)) {
                                            mob.getEquipment().setItemInMainHand(itemStack);
                                        } else if (slot.equals(EquipmentSlot.OFF_HAND)) {
                                            mob.getEquipment().setItemInOffHand(itemStack);
                                        } else if (slot.equals(EquipmentSlot.HEAD)) {
                                            mob.getEquipment().setHelmet(itemStack);
                                        } else if (slot.equals(EquipmentSlot.CHEST)) {
                                            mob.getEquipment().setChestplate(itemStack);
                                        } else if (slot.equals(EquipmentSlot.LEGS)) {
                                            mob.getEquipment().setLeggings(itemStack);
                                        } else if (slot.equals(EquipmentSlot.FEET)) {
                                            mob.getEquipment().setBoots(itemStack);
                                        }
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.item.success")).replace("%entity_name%", mob.getName()).replace("%slot%", args[1]).replace("%item_name%", itemStack.getItemMeta().getDisplayName()));
                                    } else {
                                        ItemStack air = new ItemStack(Material.AIR);
                                        if (slot.equals(EquipmentSlot.HAND)) {
                                            mob.getEquipment().setItemInMainHand(air);
                                        } else if (slot.equals(EquipmentSlot.OFF_HAND)) {
                                            mob.getEquipment().setItemInOffHand(air);
                                        } else if (slot.equals(EquipmentSlot.HEAD)) {
                                            mob.getEquipment().setHelmet(air);
                                        } else if (slot.equals(EquipmentSlot.CHEST)) {
                                            mob.getEquipment().setChestplate(air);
                                        } else if (slot.equals(EquipmentSlot.LEGS)) {
                                            mob.getEquipment().setLeggings(air);
                                        } else if (slot.equals(EquipmentSlot.FEET)) {
                                            mob.getEquipment().setBoots(air);
                                        }
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.item.success")).replace("%entity_name%", mob.getName()).replace("%slot%", args[1]).replace("%item_name%", "§eAIR"));
                                    }
                                } else {
                                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.cannot_edit_select_entity")));
                                }
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                            }
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.select_an_entity")));
                        }
                    } else if (args[0].equalsIgnoreCase("save")) {
                        if (EzTools.getSelectedEntities().containsKey(s)) {
                            EzTools.getConfigHandler().saveEntity(EzTools.getSelectedEntities().get(s), args[1]);
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.save.success").replace("%local_name%", args[1])));
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.select_an_entity")));
                        }
                    } else if (args[0].equalsIgnoreCase("summon")) {
                        if (EzTools.getConfigHandler().containsEntity(args[1])) {
                            EzTools.getConfigHandler().summonEntity((Player) s, args[1]);
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.summon.success").replace("%local_name%", args[1])));
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.summon.not_exist").replace("%local_name%", args[1])));
                        }
                    } else if (args[0].equalsIgnoreCase("select")) {
                        if (args[1].equalsIgnoreCase("cancel")) {
                            if (EzTools.getSelectedEntities().containsKey(s)) {
                                EzTools.getSelectedEntities().remove(s);
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.select.cancel")));
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.select_an_entity")));
                            }
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                        }
                    } else if (args[0].equalsIgnoreCase("name")) {
                        if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                            Entity entity = EzTools.getSelectedEntities().get((Player) s);
                            if (entity instanceof Mob) {
                                Mob mob = (Mob) entity;
                                String entityName = "";
                                for (int i = 1 ; i < args.length ; i++) {
                                    String string = args[i];
                                    entityName += EzTools.replaceColorCode((string + " "));
                                }
                                entityName = entityName.substring(0, entityName.length() - 1);
                                mob.setCustomNameVisible(true);
                                mob.setCustomName(entityName);
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.name.success")).replace("%entity_name%", entityName));
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.cannot_edit_select_entity")));
                            }
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.select_an_entity")));
                        }
                    } else {
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                    }
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("chance")) {
                        if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                            EquipmentSlot slot = EquipmentSlot.valueOf(StringUtils.upperCase(args[1]));
                            if (slot != null) {
                                Entity entity = EzTools.getSelectedEntities().get((Player) s);
                                int number = 0;
                                try {
                                    number = Integer.valueOf(args[2]);
                                } catch (NumberFormatException e) {
                                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.integer")));
                                    return true;
                                }
                                if (number >= 100) {
                                    number = 100;
                                }
                                if (number <= 0) {
                                    number = 0;
                                }
                                float chance = number / 100;
                                if (entity instanceof Mob) {
                                    Mob mob = (Mob) entity;
                                    if (slot.equals(EquipmentSlot.HAND)) {
                                        mob.getEquipment().setItemInMainHandDropChance(chance);
                                    } else if (slot.equals(EquipmentSlot.OFF_HAND)) {
                                        mob.getEquipment().setItemInOffHandDropChance(chance);
                                    } else if (slot.equals(EquipmentSlot.HEAD)) {
                                        mob.getEquipment().setHelmetDropChance(chance);
                                    } else if (slot.equals(EquipmentSlot.CHEST)) {
                                        mob.getEquipment().setChestplateDropChance(chance);
                                    } else if (slot.equals(EquipmentSlot.LEGS)) {
                                        mob.getEquipment().setLeggingsDropChance(chance);
                                    } else if (slot.equals(EquipmentSlot.FEET)) {
                                        mob.getEquipment().setBootsDropChance(chance);
                                    }
                                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.chance.success")).replace("%entity_name%", mob.getName()).replace("%slot%", args[1]).replace("%chance%", number + ""));
                                } else {
                                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.cannot_edit_select_entity")));
                                }
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                            }
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.select_an_entity")));
                        }
                    } else if (args[0].equalsIgnoreCase("attribute")) {
                        if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                            Entity entity = EzTools.getSelectedEntities().get((Player) s);
                            if (entity instanceof Mob) {
                                Mob mob = (Mob) entity;
                                Attribute attribute = Attribute.valueOf(StringUtils.upperCase(args[1]));
                                if (attribute != null) {
                                    int number = 1;
                                    try {
                                        number = Integer.valueOf(args[2]);
                                    } catch (NumberFormatException e) {
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.integer")));
                                        return true;
                                    }
                                    mob.getAttribute(attribute).addModifier(new AttributeModifier(UUID.randomUUID(), "eztools", number, AttributeModifier.Operation.ADD_NUMBER));
                                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.attribute.success")).replace("%attribute%", args[1]).replace("%amount%", args[2]));
                                } else {
                                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                                }
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.cannot_edit_select_entity")));
                            }
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.select_an_entity")));
                        }
                    } else if (args[0].equalsIgnoreCase("name")) {

                        if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                            Entity entity = EzTools.getSelectedEntities().get((Player) s);
                            if (entity instanceof Mob) {
                                Mob mob = (Mob) entity;
                                String entityName = "";
                                for (int i = 1 ; i < args.length ; i++) {
                                    String string = args[i];
                                    entityName += EzTools.replaceColorCode((string + " "));
                                }
                                entityName = entityName.substring(0, entityName.length() - 1);
                                mob.setCustomNameVisible(true);
                                mob.setCustomName(entityName);
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.name.success")).replace("%entity_name%", entityName));
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.cannot_edit_select_entity")));
                            }
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.select_an_entity")));
                        }
                    } else {
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                    }
                } else {
                    if (args[0].equalsIgnoreCase("name")) {

                        if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                            Entity entity = EzTools.getSelectedEntities().get((Player) s);
                            if (entity instanceof Mob) {
                                Mob mob = (Mob) entity;
                                String entityName = "";
                                for (int i = 1 ; i < args.length ; i++) {
                                    String string = args[i];
                                    entityName += EzTools.replaceColorCode((string + " "));
                                }
                                entityName = entityName.substring(0, entityName.length() - 1);
                                mob.setCustomNameVisible(true);
                                mob.setCustomName(entityName);
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("entity.name.success")).replace("%entity_name%", entityName));
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.cannot_edit_select_entity")));
                            }
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.select_an_entity")));
                        }
                    } else {
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                    }
                }
            }
        } else {
            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.must_be_a_player")));
        }
        return true;
    }

    public static Entity getNearestEntityInSight(Player player) {
        int range = 5;
        ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
        ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight(null, range);
        ArrayList<Location> sight = new ArrayList<>();
        for (int i = 0; i < sightBlock.size(); i++) {
            sight.add(sightBlock.get(i).getLocation());
        }
        for (int i = 0; i < sight.size(); i++) {
            for (int k = 0; k < entities.size(); k++) {
                if (Math.abs(entities.get(k).getLocation().getX() - sight.get(i).getX()) < 1.3) {
                    if (Math.abs(entities.get(k).getLocation().getY() - sight.get(i).getY()) < 1.5) {
                        if (Math.abs(entities.get(k).getLocation().getZ() - sight.get(i).getZ()) < 1.3) {
                            if (entities.get(k).getType() != EntityType.PLAYER) {
                                return entities.get(k);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

}
