package org.eztools.command;

//Apache Common Lang
import org.apache.commons.lang.StringUtils;

//Bukkit API
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

//EzTools
import org.bukkit.scheduler.BukkitRunnable;
import org.eztools.EzTools;

//Java
import java.util.ArrayList;
import java.util.List;

public class NbtCommand extends Command {

    public NbtCommand() {
        super("nbt");
        this.setPermission("eztools.command.nbt");
        EzTools.getCommandMap().register("eztools", this);
    }

    @Override
    public List<String> tabComplete(CommandSender s, String Label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("skull");
            list.add("potion");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("skull")) {
                list.add(EzTools.getLanguageCommand().getString("eztools.args_2.nbt.skull.<playerName>"));
            } else if (args[0].equalsIgnoreCase("potion")) {
                list.add("add");
                list.add("remove");
            } else {
                list.add(" ");
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("potion")) {
                if (args[1].equalsIgnoreCase("add")) {
                    for (PotionEffectType potionEffectType : PotionEffectType.values()) {
                        list.add(StringUtils.lowerCase(potionEffectType.getName()));
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    for (PotionEffectType potionEffectType : PotionEffectType.values()) {
                        list.add(StringUtils.lowerCase(potionEffectType.getName()));
                    }
                } else {
                    list.add(" ");
                }
            } else {
                list.add(" ");
            }
        } else if (args.length == 4) {
            if (args[0].equalsIgnoreCase("potion")) {
                if (args[1].equalsIgnoreCase("add")) {
                    list.add(EzTools.getLanguageCommand().getString("eztools.args_4.nbt.potion.<time>"));
                } else {
                    list.add(" ");
                }
            } else {
                list.add(" ");
            }
        } else if (args.length == 5) {
            if (args[0].equalsIgnoreCase("potion")) {
                if (args[1].equalsIgnoreCase("add")) {
                    list.add(EzTools.getLanguageCommand().getString("eztools.args_5.nbt.potion.<level>"));
                } else {
                    list.add(" ");
                }
            } else {
                list.add(" ");
            }
        } else if (args.length == 6) {
            if (args[0].equalsIgnoreCase("potion")) {
                if (args[1].equalsIgnoreCase("add")) {
                    list.add("true");
                    list.add("false");
                    list.add(EzTools.getLanguageCommand().getString("eztools.args_6.nbt.potion.particles"));
                } else {
                    list.add(" ");
                }
            } else {
                list.add(" ");
            }
        } else if (args.length == 7) {
            if (args[0].equalsIgnoreCase("potion")) {
                if (args[1].equalsIgnoreCase("add")) {
                    list.add("true");
                    list.add("false");
                    list.add(EzTools.getLanguageCommand().getString("eztools.args_7.nbt.potion.icon"));
                } else {
                    list.add(" ");
                }
            } else {
                list.add(" ");
            }
        } else {
            list.add(" ");
        }
        return list;
    }

    @Override
    public boolean execute(CommandSender s, String Label, String[] args) {
        if (s instanceof Player) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("skull")) {
                    if (args.length == 2) {
                        if (!((Player) s).getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                            if (((Player) s).getInventory().getItemInMainHand().getType().equals(Material.PLAYER_HEAD)) {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        ItemStack skull = ((Player) s).getInventory().getItemInMainHand();
                                        if (skull.getType().equals(Material.PLAYER_HEAD)) {
                                            SkullMeta meta = (SkullMeta) skull.getItemMeta();
                                            meta.setOwningPlayer(Bukkit.getOfflinePlayer(args[1]));
                                            skull.setItemMeta(meta);
                                        }
                                    }
                                }.runTaskAsynchronously(EzTools.getEzTools());
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.hold_skull")));
                            }
                        } else {
                            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                            SkullMeta meta = (SkullMeta) skull.getItemMeta();
                            meta.setOwningPlayer(Bukkit.getOfflinePlayer(args[1]));
                            skull.setItemMeta(meta);
                            ((Player) s).getInventory().addItem(skull);
                        }
                    } else {
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                    }
                } else if (args[0].equalsIgnoreCase("potion")) {
                    if (
                            ((Player) s).getInventory().getItemInMainHand().getType().equals(Material.POTION) ||
                                    ((Player) s).getInventory().getItemInMainHand().getType().equals(Material.SPLASH_POTION) ||
                                    ((Player) s).getInventory().getItemInMainHand().getType().equals(Material.LINGERING_POTION)
                    ) {
                        ItemStack itemPotion = ((Player) s).getInventory().getItemInMainHand();
                        PotionMeta potionMeta = (PotionMeta) itemPotion.getItemMeta();
                        if (args.length >= 3) {
                            if (args[1].equalsIgnoreCase("add")) {
                                args[2] = StringUtils.upperCase(args[2]);
                                if (args.length == 5) {
                                    PotionEffectType potionEffectType = PotionEffectType.getByName(args[2]);
                                    if (potionEffectType != null) {
                                        int time = 0;
                                        int level = 0;
                                        try {
                                            time = Integer.valueOf(args[3]) * 20;
                                            level = Integer.valueOf(args[4]) - 1;
                                        } catch (NumberFormatException e) {
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.integer")));
                                            return true;
                                        }
                                        boolean particles = true;
                                        boolean icon = true;
                                        if (time != 0) {
                                            PotionEffect potionEffect = new PotionEffect(potionEffectType, time, level, true, particles, icon);
                                            potionMeta.addCustomEffect(potionEffect, true);
                                            itemPotion.setItemMeta(potionMeta);
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("nbt.potion.add.success")));
                                        } else {
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.cannot_be_zero")));
                                        }
                                    } else {
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                                    }
                                } else if (args.length == 6) {
                                    PotionEffectType potionEffectType = PotionEffectType.getByName(args[2]);
                                    if (potionEffectType != null) {
                                        int time = 0;
                                        int level = 0;
                                        try {
                                            time = Integer.valueOf(args[3]) * 20;
                                            level = Integer.valueOf(args[4]) - 1;
                                        } catch (NumberFormatException e) {
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.integer")));
                                            return true;
                                        }
                                        boolean particles = true;
                                        boolean icon = true;
                                        try {
                                            particles = Boolean.valueOf(args[5]);
                                        } catch (Exception e) {
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.boolean")));
                                            return true;
                                        }
                                        if (time != 0) {
                                            PotionEffect potionEffect = new PotionEffect(potionEffectType, time, level, true, particles, icon);
                                            potionMeta.addCustomEffect(potionEffect, true);
                                            itemPotion.setItemMeta(potionMeta);
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("nbt.potion.add.success")));
                                        } else {
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.cannot_be_zero")));
                                        }
                                    } else {
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                                    }
                                } else if (args.length == 7) {
                                    PotionEffectType potionEffectType = PotionEffectType.getByName(args[2]);
                                    if (potionEffectType != null) {
                                        int time = 0;
                                        int level = 0;
                                        try {
                                            time = Integer.valueOf(args[3]) * 20;
                                            level = Integer.valueOf(args[4]) - 1;
                                        } catch (NumberFormatException e) {
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.integer")));
                                            return true;
                                        }
                                        boolean particles = true;
                                        boolean icon = true;
                                        try {
                                            particles = Boolean.valueOf(args[5]);
                                            icon = Boolean.valueOf(args[6]);
                                        } catch (Exception e) {
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.boolean")));
                                            return true;
                                        }
                                        if (time != 0) {
                                            PotionEffect potionEffect = new PotionEffect(potionEffectType, time, level, true, particles, icon);
                                            potionMeta.addCustomEffect(potionEffect, true);
                                            itemPotion.setItemMeta(potionMeta);
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("nbt.potion.add.success")));
                                        } else {
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.cannot_be_zero")));
                                        }
                                    } else {
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                                    }
                                } else {
                                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                                }
                            } else if (args[1].equalsIgnoreCase("remove")) {
                                args[2] = StringUtils.upperCase(args[2]);
                                if (args.length == 3) {
                                    PotionEffectType potionEffectType = PotionEffectType.getByName(args[2]);
                                    if (potionEffectType != null) {
                                        if (potionMeta.hasCustomEffect(potionEffectType)) {
                                            potionMeta.removeCustomEffect(potionEffectType);
                                            itemPotion.setItemMeta(potionMeta);
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("nbt.potion.remove.success")));
                                        } else {
                                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("nbt.potion.remove.not_exist")));
                                        }
                                    } else {
                                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                                    }
                                }
                            } else if (args[1].equalsIgnoreCase("gui")) {
                                s.sendMessage("Haven't finished yet");
                            } else {
                                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                            }
                        } else {
                            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                        }
                    } else {
                        s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.hold_potion")));
                    }
                } else {
                    s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
                }
            } else {
                s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.wrong_usage")));
            }
        } else {
            s.sendMessage(EzTools.replaceColorCode(EzTools.getLanguageMessage().getString("error.must_be_a_player")));
        }
        return true;
    }

}