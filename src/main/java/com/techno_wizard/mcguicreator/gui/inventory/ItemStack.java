package com.techno_wizard.mcguicreator.gui.inventory;

import com.techno_wizard.mcguicreator.gui.events.AutoGenerateType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ethan on 4/3/2016.
 */
public class ItemStack implements Serializable {
    private ImageIcon icon;
    private Material material;
    private String name = "";
    private String lore;
    private int amount = 1;
    private boolean isEnchanted;
    private List<Enchantment> enchantments = new ArrayList<>();
    private String notes;
    private AutoGenerateType autoGenerateType = AutoGenerateType.NONE;
    private boolean closeInvOnClick = false;

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


    public void updateIcon() {
        this.icon = material.getImage();
        if (enchantments.size() > 0)
            this.icon = ItemUtil.addEnchantments(icon);
        this.icon = ItemUtil.resizeIcon(icon);
        this.icon = ItemUtil.addAmount(this.icon, this.amount);
    }

    public Material getMaterial() {
        return material;
    }

    public AutoGenerateType getAutoGenerateType() {
        return autoGenerateType;
    }

    public void setAutoGenerateType(AutoGenerateType autoGenerateType) {
        this.autoGenerateType = autoGenerateType;
    }

    public void setCloseInvOnClick(boolean b) {
        closeInvOnClick = b;
    }

    public boolean getCloseInvOnClick() {
        return closeInvOnClick;
    }

    public void addEnchantment(Enchantment e) {
        this.enchantments.add(e);
    }

    public void removeEnchantment(Enchantment e, JList enchantmentList) {
        removeEnchantmentType(e.getType(), enchantmentList);
    }

    public void removeEnchantmentType(Enchantment.EnchantmentType e, JList enchantmentList) {
        List<Enchantment> ench = new ArrayList<>(this.enchantments);
        for (Enchantment e1 : ench) {
            if (e.getBukkitName().equals(e1.getType().getBukkitName()))
                this.enchantments.remove(e1);
        }
        for (int i = 0; i < ((DefaultListModel) enchantmentList.getModel()).size(); i++) {
            if (((String) (enchantmentList.getModel()).getElementAt(i)).startsWith(e.getBukkitName().split(" : ")[0]))
                ((DefaultListModel) enchantmentList.getModel()).remove(i);
        }
    }

    public List<Enchantment> getEnchantments() {
        return this.enchantments;
    }

    public void setEnchantments(List<Enchantment> e) {
        this.enchantments = e;
    }

    public void setMaterial(Material material) {
        this.material = material;
        updateIcon();
    }

    /**
     * Use this to update all the values of an itemstack. This is what we should use to update the itemstack and
     * icon whenever a value is changed.
     *
     * @param lore
     * @param amount
     */
    public void update(Material m, String lore, int amount, String name, List<Enchantment> ench, String notes, AutoGenerateType auto, boolean closeInvOnClick) {
        this.setAmount(amount);
        this.setLore(lore);
        this.setMaterial(m);
        this.setName(name);
        this.setEnchantments(ench);
        this.setNotes(notes);
        this.setAutoGenerateType(auto);
        this.setCloseInvOnClick(closeInvOnClick);
    }

    /**
     * Use this to update all the values of an itemstack. This is what we should use to update the itemstack and
     * icon whenever a value is changed.
     *
     * @param materialName
     * @param lore
     * @param amount
     */
    public void update(String materialName, String lore, int amount, String name, List<Enchantment> ench, String note, AutoGenerateType auto, boolean closeInvOnClick) {
        update(Material.getMaterialByName(materialName), lore, amount, name, ench, note, auto, closeInvOnClick);
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

    public boolean isCloseInvOnClick() {
        return closeInvOnClick;
    }
}
