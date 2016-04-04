package com.techno_wizard.mcguicreator.gui.inventory;

import  java.util.*;

/**
 * Created by Zombie_Striker on 4/3/2016.
 */
public class Slot{

    public int x = 0;
    public int y = 0;
    ItemStack itemStack;

    private static List<Slot> slotData = new ArrayList<Slot>();
    private static Slot currentSlot = new Slot(0,0);

    public Slot(int x, int y){
        this.x = x;
        this.y = y;
        itemStack = new ItemStack(Material.AIR);
        Slot.slotData.add(this);
    }

    public void setItemStack(ItemStack is){
        itemStack = is;
    }
    public ItemStack  getItemStack(){
        return itemStack;
    }

    public static Slot getCurrentSlot(){
        return currentSlot;
    }
    public static Slot setCurrentSlot(Slot s) {
        return Slot.currentSlot = s;
    }

    public static List<Slot> getSlots(){
        return Slot.slotData;
    }

    public static Slot getSlotAt(int x, int y){
        for(Slot slot : Slot.slotData){
            if(slot.x==x && slot.y ==y)
                return slot;
        }
        return new Slot(x,y);
    }

}
