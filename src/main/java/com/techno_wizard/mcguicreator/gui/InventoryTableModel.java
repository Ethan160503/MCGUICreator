package com.techno_wizard.mcguicreator.gui;

import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.Material;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 * Table model representing a player inventory
 */
public class InventoryTableModel extends AbstractTableModel {
    private ItemStack[][] itemStacks;

    public InventoryTableModel() {
        itemStacks = new ItemStack[9][3];
    }

    @Override
    public int getRowCount() {
        return itemStacks[0].length;
    }

    public int getColumnCount() {
        return itemStacks.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return getItemStackAt(rowIndex, columnIndex).getInventoryIcon();
    }

    public ItemStack getItemStackAt(int column, int row) {
        //Since the itemstack might not exist yet, lets create a new Itemstack instance at that slot in order to prevent any possible NPEs
        if (itemStacks[column][row] == null)
            itemStacks[column][row] = new ItemStack(Material.AIR);
        return itemStacks[column][row];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ImageIcon.class;
    }
}
