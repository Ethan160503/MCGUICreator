package com.techno_wizard.mcguicreator.gui.inventory;

/**
 * Created by Zombie_Striker on 4/11/2016.
 */
public class Enchantment {

    int powerLavel;
    String enchantmentName;
    boolean isUnsafe;
    EnchantmentType type;

    public Enchantment(EnchantmentType e,int power){
        this.enchantmentName = e.getBukkitName();
        this.powerLavel = power;
        this.isUnsafe = e.isUnsafe(power);
        this.type = e;
    }
    public EnchantmentType getType(){return type;}

    public enum EnchantmentType{
        //TODO:Sort by alphabetical order.
        
        //Armour
        PROTECTION(10),PROTECTION_FIRE(10),PROTECTION_FALL(10),PROTECTION_EXPLOSION(10),PROTECTION_PROJECTILE(10),RESPIRATION(10),AQUA_AFINITY(10),THORNS(10),DEPTH_STRIDER(10),FROSTWALKER(10),
        //Weapons
        SHARPNESS(10),SMITE(10),BANE_OF_ARTHROPODS(10),KNOCKBACK(10),FIRE_ASPECT(10),LOOTING(10),
        //Tools
        EFFICIENCY(10),SLINK_TOUCH(10),UNBREACKING(10),FORTUNE(10),
        //Bows
        POWER(10),PUNCH(10),FLAME(10),INFINITY(10),
        //Fishingrods
        LUCK_OF_THE_SEA(10),LURE(10),
        //All
        MENDING(10);

        int maxPower;

        EnchantmentType(int maxPower){this.maxPower = maxPower;}
        public String getBukkitName(){
            return this.toString();
        }
        public int getMaxPower(){
            return maxPower;
        }
        public boolean isUnsafe(int powerLevel){
            return this.maxPower < powerLevel;
        }
        public static EnchantmentType getEnchantmentByName(String name){
            for (EnchantmentType e : EnchantmentType.values()){
                if(e.getBukkitName().equals(name))
                    return e;
            }
            return null;
        }
    }
    public String getBukkitName(){
        return this.enchantmentName;
    }
    public int getPowerLavel(){
        return this.powerLavel;
    }
    public boolean isUnsafe(){
        return this.isUnsafe;
    }

    public String getDisplay(){
        return this.enchantmentName+" : "+ ItemUtil.getRomanNumerals(powerLavel);
    }
}
