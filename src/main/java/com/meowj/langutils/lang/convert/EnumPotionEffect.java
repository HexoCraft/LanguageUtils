/*
 * Copyright (c) 2015 Jerrell Fang
 *
 * This project is Open Source and distributed under The MIT License (MIT)
 * (http://opensource.org/licenses/MIT)
 *
 * You should have received a copy of the The MIT License along with
 * this project.   If not, see <http://opensource.org/licenses/MIT>.
 */

package com.meowj.langutils.lang.convert;

import com.meowj.langutils.lang.LanguageHelper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Meow J on 7/6/2015.
 * <p>
 * A list of potion effects.
 *
 * @author Meow J
 */
public enum EnumPotionEffect {

    SPEED(PotionType.SPEED, "potion.moveSpeed.postfix"),
    SLOW(PotionType.SLOWNESS, "potion.moveSlowdown.postfix"),
    INCREASE_DAMAGE(PotionType.STRENGTH, "potion.damageBoost.postfix"),
    WEAKNESS(PotionType.WEAKNESS, "potion.weakness.postfix"),
    HEAL(PotionType.INSTANT_HEAL, "potion.heal.postfix"),
    HARM(PotionType.INSTANT_DAMAGE, "potion.harm.postfix"),
    JUMP(PotionType.JUMP, "potion.jump.postfix"),
    REGENERATION(PotionType.REGEN, "potion.regeneration.postfix"),
    FIRE_RESISTANCE(PotionType.FIRE_RESISTANCE, "potion.fireResistance.postfix"),
    WATER_BREATHING(PotionType.WATER_BREATHING, "potion.waterBreathing.postfix"),
    INVISIBILITY(PotionType.INVISIBILITY, "potion.invisibility.postfix"),
    NIGHT_VISION(PotionType.NIGHT_VISION, "potion.nightVision.postfix"),
    POISON(PotionType.POISON, "potion.poison.postfix");

    private static final Map<PotionType, EnumPotionEffect> lookup = new HashMap<PotionType, EnumPotionEffect>();

    static {
        for (EnumPotionEffect effect : EnumSet.allOf(EnumPotionEffect.class))
            lookup.put(effect.getPotionType(), effect);
    }

    private PotionType potionType;
    private String unlocalizedName;

    /**
     * Create an index of potion effects.
     */
    EnumPotionEffect(PotionType potionType, String unlocalizedName) {
        this.potionType = potionType;
        this.unlocalizedName = unlocalizedName;
    }

    /**
     * @param effectType The effect type.
     * @return The index of a potion based on effect.
     */
    public static EnumPotionEffect get(PotionType effectType) {
        return lookup.containsKey(effectType) ? lookup.get(effectType) : null;
    }

    /**
     * @param potion The potion to be processed.
     * @return The postfix of an potion(The actual name without Splash or what not).
     */
    public static String getPostfix(ItemStack potion) {
        Potion p = Potion.fromItemStack(potion);
        return get(p.getType()).getUnlocalizedName();
    }

    /**
     * @param potion The potion.
     * @param locale The language of the name.
     * @return The localized name of an potion.
     */
    public static String getLocalizedName(ItemStack potion, String locale) {
        switch (potion.getDurability()) {
            case 16:
                return LanguageHelper.translateToLocal("potion.prefix.awkward", locale) + " " + LanguageHelper.translateToLocal("item.potion.name", locale);
            case 32:
                return LanguageHelper.translateToLocal("potion.prefix.thick", locale) + " " + LanguageHelper.translateToLocal("item.potion.name", locale);
            case 64:
            case 8192:
                return LanguageHelper.translateToLocal("potion.prefix.mundane", locale) + " " + LanguageHelper.translateToLocal("item.potion.name", locale);
            // Fix Instant Health Potion problem(This problem has been fixed in the latest version of the Bukkit, but I'll still leave it here in case many people don't update their server very often. Indeed, I highly recommend updating the servers)
            case 16453:
                return LanguageHelper.translateToLocal("potion.prefix.grenade", locale) + " " + LanguageHelper.translateToLocal("potion.heal.postfix", locale);
            case 8261:
                return LanguageHelper.translateToLocal("potion.heal.postfix", locale);
            default:
                if (Potion.fromItemStack(potion).isSplash())
                    return LanguageHelper.translateToLocal("potion.prefix.grenade", locale) + " " + LanguageHelper.translateToLocal(getPostfix(potion), locale);
                else
                    return LanguageHelper.translateToLocal(getPostfix(potion), locale);
        }
    }

    /**
     * @return The type of the potion
     */
    public PotionType getPotionType() {
        return potionType;
    }

    /**
     * @return The unlocalized name of the potion
     */
    public String getUnlocalizedName() {
        return unlocalizedName;
    }
}