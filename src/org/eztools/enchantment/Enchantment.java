package org.eztools.enchantment;

public enum Enchantment {

    PROTECTION(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL),
    FIRE_PROTECTION(org.bukkit.enchantments.Enchantment.PROTECTION_FIRE),
    FEATHER_FALLING(org.bukkit.enchantments.Enchantment.PROTECTION_FALL),
    BLAST_PROTECTION(org.bukkit.enchantments.Enchantment.PROTECTION_EXPLOSIONS),
    PROJECTILE_PROTECTION(org.bukkit.enchantments.Enchantment.PROTECTION_PROJECTILE),
    RESPIRATION(org.bukkit.enchantments.Enchantment.OXYGEN),
    AQUA_AFFINITY(org.bukkit.enchantments.Enchantment.WATER_WORKER),
    THORNS(org.bukkit.enchantments.Enchantment.THORNS),
    DEPTH_STRIDER(org.bukkit.enchantments.Enchantment.DEPTH_STRIDER),
    FROST_WALKER(org.bukkit.enchantments.Enchantment.FROST_WALKER),
    BINDING_CURSE(org.bukkit.enchantments.Enchantment.BINDING_CURSE),
    SHARPNESS(org.bukkit.enchantments.Enchantment.DAMAGE_ALL),
    SMITE(org.bukkit.enchantments.Enchantment.DAMAGE_UNDEAD),
    BANE_OF_ARTHROPODS(org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS),
    KNOCKBACK(org.bukkit.enchantments.Enchantment.KNOCKBACK),
    FIRE_ASPECT(org.bukkit.enchantments.Enchantment.FIRE_ASPECT),
    LOOTING(org.bukkit.enchantments.Enchantment.LOOT_BONUS_MOBS),
    SWEEPING(org.bukkit.enchantments.Enchantment.SWEEPING_EDGE),
    EFFICIENCY(org.bukkit.enchantments.Enchantment.DIG_SPEED),
    SILK_TOUCH(org.bukkit.enchantments.Enchantment.SILK_TOUCH),
    UNBREAKING(org.bukkit.enchantments.Enchantment.DURABILITY),
    FORTUNE(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS),
    POWER(org.bukkit.enchantments.Enchantment.ARROW_DAMAGE),
    PUNCH(org.bukkit.enchantments.Enchantment.ARROW_KNOCKBACK),
    FLAME(org.bukkit.enchantments.Enchantment.ARROW_FIRE),
    INFINITY(org.bukkit.enchantments.Enchantment.ARROW_INFINITE),
    LUCK_OF_THE_SEA(org.bukkit.enchantments.Enchantment.LUCK),
    LURE(org.bukkit.enchantments.Enchantment.LURE),
    LOYALTY(org.bukkit.enchantments.Enchantment.LOYALTY),
    IMPALING(org.bukkit.enchantments.Enchantment.IMPALING),
    RIPTIDE(org.bukkit.enchantments.Enchantment.RIPTIDE),
    CHANNELING(org.bukkit.enchantments.Enchantment.CHANNELING),
    MULTISHOT(org.bukkit.enchantments.Enchantment.MULTISHOT),
    QUICK_CHARGE(org.bukkit.enchantments.Enchantment.QUICK_CHARGE),
    PIERCING(org.bukkit.enchantments.Enchantment.PIERCING),
    MENDING(org.bukkit.enchantments.Enchantment.MENDING),
    VANISHING_CURSE(org.bukkit.enchantments.Enchantment.VANISHING_CURSE),
    SOUL_SPEED(org.bukkit.enchantments.Enchantment.SOUL_SPEED);

    private org.bukkit.enchantments.Enchantment enchantment;

    Enchantment(org.bukkit.enchantments.Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    public org.bukkit.enchantments.Enchantment getEnchantment() {
        return this.enchantment;
    }

    public static Enchantment fromEnchantment(org.bukkit.enchantments.Enchantment enchantment) {
        if (org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL.equals(enchantment)) {
            return PROTECTION;
        } else if (org.bukkit.enchantments.Enchantment.PROTECTION_FIRE.equals(enchantment)) {
            return FIRE_PROTECTION;
        } else if (org.bukkit.enchantments.Enchantment.PROTECTION_FALL.equals(enchantment)) {
            return FEATHER_FALLING;
        } else if (org.bukkit.enchantments.Enchantment.PROTECTION_EXPLOSIONS.equals(enchantment)) {
            return BLAST_PROTECTION;
        } else if (org.bukkit.enchantments.Enchantment.PROTECTION_PROJECTILE.equals(enchantment)) {
            return PROJECTILE_PROTECTION;
        } else if (org.bukkit.enchantments.Enchantment.OXYGEN.equals(enchantment)) {
            return RESPIRATION;
        } else if (org.bukkit.enchantments.Enchantment.WATER_WORKER.equals(enchantment)) {
            return AQUA_AFFINITY;
        } else if (org.bukkit.enchantments.Enchantment.THORNS.equals(enchantment)) {
            return THORNS;
        } else if (org.bukkit.enchantments.Enchantment.DEPTH_STRIDER.equals(enchantment)) {
            return DEPTH_STRIDER;
        } else if (org.bukkit.enchantments.Enchantment.FROST_WALKER.equals(enchantment)) {
            return FROST_WALKER;
        } else if (org.bukkit.enchantments.Enchantment.BINDING_CURSE.equals(enchantment)) {
            return BINDING_CURSE;
        } else if (org.bukkit.enchantments.Enchantment.DAMAGE_ALL.equals(enchantment)) {
            return SHARPNESS;
        } else if (org.bukkit.enchantments.Enchantment.DAMAGE_UNDEAD.equals(enchantment)) {
            return SMITE;
        } else if (org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS.equals(enchantment)) {
            return BANE_OF_ARTHROPODS;
        } else if (org.bukkit.enchantments.Enchantment.KNOCKBACK.equals(enchantment)) {
            return KNOCKBACK;
        } else if (org.bukkit.enchantments.Enchantment.FIRE_ASPECT.equals(enchantment)) {
            return FIRE_ASPECT;
        } else if (org.bukkit.enchantments.Enchantment.LOOT_BONUS_MOBS.equals(enchantment)) {
            return LOOTING;
        } else if (org.bukkit.enchantments.Enchantment.SWEEPING_EDGE.equals(enchantment)) {
            return SWEEPING;
        } else if (org.bukkit.enchantments.Enchantment.DIG_SPEED.equals(enchantment)) {
            return EFFICIENCY;
        } else if (org.bukkit.enchantments.Enchantment.SILK_TOUCH.equals(enchantment)) {
            return SILK_TOUCH;
        } else if (org.bukkit.enchantments.Enchantment.DURABILITY.equals(enchantment)) {
            return UNBREAKING;
        } else if (org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS.equals(enchantment)) {
            return FORTUNE;
        } else if (org.bukkit.enchantments.Enchantment.ARROW_DAMAGE.equals(enchantment)) {
            return POWER;
        } else if (org.bukkit.enchantments.Enchantment.ARROW_KNOCKBACK.equals(enchantment)) {
            return PUNCH;
        } else if (org.bukkit.enchantments.Enchantment.ARROW_FIRE.equals(enchantment)) {
            return FLAME;
        } else if (org.bukkit.enchantments.Enchantment.ARROW_INFINITE.equals(enchantment)) {
            return INFINITY;
        } else if (org.bukkit.enchantments.Enchantment.LUCK.equals(enchantment)) {
            return LUCK_OF_THE_SEA;
        } else if (org.bukkit.enchantments.Enchantment.LURE.equals(enchantment)) {
            return LURE;
        } else if (org.bukkit.enchantments.Enchantment.LOYALTY.equals(enchantment)) {
            return LOYALTY;
        } else if (org.bukkit.enchantments.Enchantment.IMPALING.equals(enchantment)) {
            return IMPALING;
        } else if (org.bukkit.enchantments.Enchantment.RIPTIDE.equals(enchantment)) {
            return RIPTIDE;
        } else if (org.bukkit.enchantments.Enchantment.CHANNELING.equals(enchantment)) {
            return CHANNELING;
        } else if (org.bukkit.enchantments.Enchantment.MULTISHOT.equals(enchantment)) {
            return MULTISHOT;
        } else if (org.bukkit.enchantments.Enchantment.QUICK_CHARGE.equals(enchantment)) {
            return QUICK_CHARGE;
        } else if (org.bukkit.enchantments.Enchantment.PIERCING.equals(enchantment)) {
            return PIERCING;
        } else if (org.bukkit.enchantments.Enchantment.MENDING.equals(enchantment)) {
            return MENDING;
        } else if (org.bukkit.enchantments.Enchantment.VANISHING_CURSE.equals(enchantment)) {
            return VANISHING_CURSE;
        } else if (org.bukkit.enchantments.Enchantment.SOUL_SPEED.equals(enchantment)) {
            return SOUL_SPEED;
        } else {
            return PROTECTION;
        }
    }

}
