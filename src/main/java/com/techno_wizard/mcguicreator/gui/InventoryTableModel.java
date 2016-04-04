package com.techno_wizard.mcguicreator.gui;

import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;

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
        return itemStacks[columnIndex][rowIndex].getInventoryIcon();
    }

    public ItemStack getItemStackAt(int x, int y) {
        return itemStacks[x][y];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ImageIcon.class;
    }
}
