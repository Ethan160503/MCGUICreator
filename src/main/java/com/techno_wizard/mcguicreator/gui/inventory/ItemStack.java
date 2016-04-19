package com.techno_wizard.mcguicreator.gui.inventory;

import com.techno_wizard.mcguicreator.gui.events.AutoGenerateType;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ethan on 4/3/2016.
 */
public class ItemStack implements Serializable{
    private ImageIcon icon;
    private Material material;
    private String name="";
    private String lore;
    private int amount = 1;
    private boolean isEnchanted;
    private List<Enchantment> enchantments = new ArrayList<>();
    private String notes;
    private AutoGenerateType autoGenerateType = AutoGenerateType.NONE;

    public ItemStack(Material material) {
        this.material = material;
        this.icon = material.getImage();
    }
    public ItemStack(ItemStack is) {
        this.material = is.getMaterial();
        this.amount = is.getAmount();
        this.lore = is.getLore();
        this.icon = is.getIcon();
        this.name = is.getName();
        this.notes = is.getNotes();
        this.isEnchanted = is.isEnchanted();
        this.autoGenerateType = is.getAutoGenerateType();
    }


    public Material getMaterial() {
        return material;
    }
    public AutoGenerateType getAutoGenerateType(){
        return autoGenerateType;
    }
    public void setAutoGenerateType(AutoGenerateType autoGenerateType){
        this.autoGenerateType = autoGenerateType;
    }

    public void addEnchantment(Enchantment e){
        this.enchantments.add(e);
    }
    public void removeEnchantment(Enchantment e){
        this.enchantments.remove(e);

    }
    public void removeEnchantmentType(Enchantment.EnchantmentType e){
        Iterator<Enchantment> iterator = this.enchantments.listIterator();
        for(Enchantment e2 = iterator.next();iterator.hasNext();e2=iterator.next()){
            if(e.getBukkitName().equals(e2.getBukkitName()))
                this.enchantments.remove(e2);
        }
    }
    public List<Enchantment> getEnchantments(){
        return this.enchantments;
    }
    public void setEnchantments(List<Enchantment> e){
        this.enchantments = e;
    }

    public void setMaterial(Material material) {
        this.material = material;
        this.icon = ItemUtil.resizeIcon(material.getImage());
        this.icon = ItemUtil.addAmount(this.icon,this.amount);
    }

    /**
     * Use this to update all the values of an itemstack. This is what we should use to update the itemstack and
     * icon whenever a value is changed.
     * @param lore
     * @param amount
     */
    public void update(Material m,String lore,int amount){
        this.setAmount(amount);
        this.setLore(lore);
        this.setMaterial(m);
    }
    /**
     * Use this to update all the values of an itemstack. This is what we should use to update the itemstack and
     * icon whenever a value is changed.
     * @param materialName
     * @param lore
     * @param amount
     */
    public void update(String materialName,String lore,int amount){
        this.setAmount(amount);
        this.setLore(lore);
        this.setMaterial(Material.getMaterialByName(materialName));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public List<String> getLoreAsList() {
        if (lore == null)
            return new ArrayList<String>();
        List<String> loreList = Arrays.asList(lore.split("\n"));
        return loreList;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isEnchanted() {
        return isEnchanted;
    }

    public void setEnchanted(boolean enchanted) {
        isEnchanted = enchanted;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public ImageIcon getInventoryIcon() {
        //TODO process (shrink and add number/enchantment indicator?)
        return icon;
    }
}
