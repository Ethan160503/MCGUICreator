package com.techno_wizard.mcguicreator.gui;

import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.Material;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.Serializable;

/**
 * Table model representing a player inventory
 */
public class InventoryTableModel extends AbstractTableModel implements Serializable {
    private static final long serialVersionUID = 1;

    private String inventoryName = "Custom Inventory";
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

    /**
     * Use this to set the inventory size.
     *
     * @param rows
     */
    public void setInventorySize(int rows) {
        ItemStack[][] oldArray = itemStacks;
        ItemStack[][] newArray = new ItemStack[getColumnCount()][rows];
        for (int y = 0; y < oldArray[0].length; y++) {
            for (int x = 0; x < oldArray.length; x++) {
                //Make sure the rows exist.
                if (y >= rows)
                    continue;

                newArray[x][y] = oldArray[x][y];
            }
        }
        itemStacks = newArray;
    }


    public void setInventoryName(String name) {
        this.inventoryName = name;
    }

    public String getInventoryName() {
        return this.inventoryName;
    }

    /**
     * returns the object at ROW, COLUMN
     * NOTE: The row and columns are reversed.
     *
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getItemStackAt(columnIndex, rowIndex).getInventoryIcon();
    }

    public ItemStack getItemStackAt(int column, int row) {
        //Since the itemstack might not exist yet, lets create a new Itemstack instance at that slot in order to prevent any possible NPEs
        if (column < 0 || row < 0 || column >= this.getColumnCount() || row >= this.getRowCount())
            return null;

        if (itemStacks[column][row] == null)
            itemStacks[column][row] = new ItemStack(Material.AIR);
        return itemStacks[column][row];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ImageIcon.class;
    }

    public boolean rowContainsItemstacks(int row) {
        for (int x = 0; x < 9; x++) {
            if (getItemStackAt(x, row).getMaterial() != Material.AIR)
                return true;
        }
        return false;
    }

    public ItemStack[][] getItemstacks() {
        return this.itemStacks;
    }

    public void setItemStacks(ItemStack[][] is) {
        this.itemStacks = is;
    }
}
