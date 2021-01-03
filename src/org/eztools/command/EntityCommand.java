package org.eztools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
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
            list.add("select");
            list.add("item");
            list.add("maxair");
            list.add("chance");
            list.add("attribute");
            list.add("name");
        } else if (args.length >= 2) {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("select")) {
                    list.add("cancel");
                } else if (args[0].equalsIgnoreCase("item")) {
                    for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                        list.add(StringUtils.lowerCase(equipmentSlot.name()));
                    }
                } else if (args[0].equalsIgnoreCase("maxair")) {
                    list.add("<最大氧气值>");
                } else if (args[0].equalsIgnoreCase("chance")) {
                    for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                        list.add(StringUtils.lowerCase(equipmentSlot.name()));
                    }
                } else if (args[0].equalsIgnoreCase("attribute")) {
                    for (Attribute attribute : Attribute.values()) {
                        list.add(StringUtils.lowerCase(attribute.name()));
                    }
                } else if (args[0].equalsIgnoreCase("name")) {
                    list.add("<实体名称>");
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
                    list.add("<掉落几率 百分比>");
                } else if (args[0].equalsIgnoreCase("attribute")) {
                    list.add("<属性数值>");
                } else if (args[0].equalsIgnoreCase("name")) {
                    list.add("<实体名称>");
                } else {
                    list.add(" ");
                }
            }
        } else {
            if (args[0].equalsIgnoreCase("name")) {
                list.add("<实体名称>");
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
                if (args[0].equalsIgnoreCase("select")) {
                    Entity entity = getNearestEntityInSight((Player) s);
                    if (entity != null) {
                        if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                            EzTools.getSelectedEntities().remove((Player) s);
                            s.sendMessage("§a已取消选择上一个已选择的实体");
                        }
                        EzTools.getSelectedEntities().put((Player) s, entity);
                        s.sendMessage("§a已选择实体！");
                    } else {
                        s.sendMessage("§c请看着一个实体使用此指令");
                    }
                } else if(args[0].equalsIgnoreCase("loot")) {
                    if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                        Entity entity = EzTools.getSelectedEntities().get((Player) s);
                        if (entity instanceof Mob) {
                            Mob mob = (Mob) entity;
                        } else {
                            s.sendMessage("§c你不能对你选中的实体进行操作");
                        }
                    } else {
                        s.sendMessage("§c请先选择实体");
                    }
                } else {
                    s.sendMessage("§c未知的指令用法");
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
                                        s.sendMessage("§a已将生物 §e" + mob.getName() + " §a的 §e" + args[1] + " §a栏位设置为 §r" + itemStack.getItemMeta().getDisplayName());
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
                                        s.sendMessage("§a已将生物 §e" + mob.getName() + " §a的 §e" + args[1] + " §a栏位设置为 §rAIR");
                                    }
                                } else {
                                    s.sendMessage("§c你不能对你选中的实体进行操作");
                                }
                            } else {
                                s.sendMessage("§c错误的栏位");
                            }
                        } else {
                            s.sendMessage("§c请先选择实体");
                        }
                    } else if (args[0].equalsIgnoreCase("select")) {
                        if (args[1].equalsIgnoreCase("cancel")) {
                            if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                                EzTools.getSelectedEntities().remove((Player) s);
                                s.sendMessage("§a已取消选择实体");
                            } else {
                                s.sendMessage("§c你没有选择实体");
                            }
                        } else {
                            s.sendMessage("§c未知的指令用法");
                        }
                    } else if (args[0].equalsIgnoreCase("maxair")) {
                        if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                            Entity entity = EzTools.getSelectedEntities().get((Player) s);
                            if (entity instanceof Mob) {
                                Mob mob = (Mob) entity;
                                int number = 1;
                                try {
                                    number = Integer.valueOf(args[1]);
                                } catch (NumberFormatException e) {
                                    s.sendMessage("§c输入的最大空气值不是一个正确的数字");
                                    return true;
                                }
                                mob.setMaximumAir(number);
                            } else {
                                s.sendMessage("§c你不能对你选中的实体进行操作");
                            }
                        } else {
                            s.sendMessage("§c请先选择实体");
                        }
                    } else if (args[0].equalsIgnoreCase("name")) {

                        if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                            Entity entity = EzTools.getSelectedEntities().get((Player) s);
                            if (entity instanceof Mob) {
                                Mob mob = (Mob) entity;
                                String entityName = "";
                                for (int i = 1 ; i < args.length ; i++) {
                                    String string = args[i];
                                    entityName += (string + " ").replace("&", "§");
                                }
                                entityName = entityName.substring(0, entityName.length() - 1);
                                mob.setCustomNameVisible(true);
                                mob.setCustomName(entityName);
                                s.sendMessage("§a已将实体的名称设置为 " + entityName);
                            } else {
                                s.sendMessage("§c你不能对你选中的实体进行操作");
                            }
                        } else {
                            s.sendMessage("§c请先选择实体");
                        }
                    } else {
                        s.sendMessage("§c未知的指令用法");
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
                                    s.sendMessage("§c输入的掉落几率不是一个正确的百分比");
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
                                    s.sendMessage("§a已将生物 §e" + mob.getName() + " §a的 §e" + args[1] + " §a栏位的物品掉落几率设置为 §e" + number + "%");
                                } else {
                                    s.sendMessage("§c你不能对你选中的实体进行操作");
                                }
                            } else {
                                s.sendMessage("§c错误的栏位");
                            }

                        } else {
                            s.sendMessage("§c请先选择实体");
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
                                        s.sendMessage("§c输入的属性数值不是一个正确的数字");
                                        return true;
                                    }
                                    mob.getAttribute(attribute).setBaseValue(number);
                                    s.sendMessage("§a已将生物的 §e" +  args[1] + " §a属性设置为 §e" + args[2]);
                                } else {
                                    s.sendMessage("§c请输入正确的属性名称");
                                }
                            } else {
                                s.sendMessage("§c你不能对你选中的实体进行操作");
                            }
                        } else {
                            s.sendMessage("§c请先选择实体");
                        }
                    } else if (args[0].equalsIgnoreCase("name")) {

                        if (EzTools.getSelectedEntities().containsKey((Player) s)) {
                            Entity entity = EzTools.getSelectedEntities().get((Player) s);
                            if (entity instanceof Mob) {
                                Mob mob = (Mob) entity;
                                String entityName = "";
                                for (int i = 1 ; i < args.length ; i++) {
                                    String string = args[i];
                                    entityName += (string + " ").replace("&", "§");
                                }
                                entityName = entityName.substring(0, entityName.length() - 1);
                                mob.setCustomNameVisible(true);
                                mob.setCustomName(entityName);
                                s.sendMessage("§a已将实体的名称设置为 " + entityName);
                            } else {
                                s.sendMessage("§c你不能对你选中的实体进行操作");
                            }
                        } else {
                            s.sendMessage("§c请先选择实体");
                        }
                    } else {
                        s.sendMessage("§c未知的指令用法");
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
                                    entityName += (string + " ").replace("&", "§");
                                }
                                entityName = entityName.substring(0, entityName.length() - 1);
                                mob.setCustomNameVisible(true);
                                mob.setCustomName(entityName);
                                s.sendMessage("§a已将实体的名称设置为 " + entityName);
                            } else {
                                s.sendMessage("§c你不能对你选中的实体进行操作");
                            }
                        } else {
                            s.sendMessage("§c请先选择实体");
                        }
                    } else {
                        s.sendMessage("§c未知的指令用法");
                    }
                }
            }
        } else {
            s.sendMessage("§c你必须是个玩家!");
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
