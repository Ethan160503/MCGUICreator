package com.techno_wizard.mcguicreator.management;

import com.techno_wizard.mcguicreator.gui.InventoryTableModel;
import com.techno_wizard.mcguicreator.gui.MainMenu;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.Material;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Ethan on 4/6/2016.
 */
public class InventoryManager {
    MainMenu mainMenu;
    JTable table;
    InventoryTableModel model;

    public InventoryManager(MainMenu mainMenu) {
        this.table = table;
        // assert the table model is correct
        if(!(table.getModel() instanceof InventoryTableModel)) throw new RuntimeException(
                new ClassCastException("Table model is not an inventory table model"));
        this.model = (InventoryTableModel) table.getModel();

        initSlots();
    }

    /**
     * Inits the slots for the inventory
     */
    public void initSlots(){

        MouseListener tableClickListener = new MouseAdapter() {
            int lastXClick = 0;
            int lastYClick = 0;

            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());//get mouse-selected row
                int col = table.columnAtPoint(e.getPoint());//get mouse-selected col

                //Make sure the user does not click on the same slot twice
                if(lastXClick==col&&lastYClick==row)
                    return;

                //Make sure it's not out of bounds
                if(col >= 9 || row >=6)
                    return;

                //Save the previous Itemstack
                if(lastXClick >=0 && lastYClick>=0) {
                    ItemStack oldItemstack = model.getActiveItemstack();
                    oldItemstack.setName(stackNameEditor.getText());
                    for (Material m : Material.values()) {
                        if (m.getName().equals(stackType.getSelectedItem())) {
                            oldItemstack.setMaterial(m);
                            break;
                        }
                    }
                    /*todo this is really only a temp fix. We'd need to go back to the unformatted text, and this simply
                    removes them
                     */

                    oldItemstack.setLore(editorPane1.getText().replaceAll("\\<[^>]*>",""));
                }

                //Set lastSlot equal to the current slot
                lastYClick =row;
                lastXClick =col;

                //Load the new slot
                ItemStack nextItemStack = inventoryTableModel.getItemStackAt(row, col);
                stackNameEditor.setText(nextItemStack.getName());
                for(int i = 0 ; i<stackType.getItemCount();i++){
                    if((stackType.getItemAt(i)).equals(nextItemStack.getMaterial().getName())){
                        stackType.setSelectedIndex(i);
                        break;
                    }
                }
                editorPane1.setText(nextItemStack.getLore());
                inventoryTableModel.setActiveItemStack(row,col);
            }
        };
        inventoryTable.addMouseListener(tableClickListener);

        stackType.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Load the new slot
                ItemStack is = inventoryTableModel.getActiveItemstack();
                for (Material mm : Material.values()) {
                    if (mm.getName().equals(stackType.getSelectedItem())) {
                        is.setMaterial(mm);
                        break;
                    }
                }
                int row = inventoryTableModel.getActiveItemStackRow();
                int column = inventoryTableModel.getActiveItemStackColumb();
                inventoryTableModel.setValueAt(inventoryTableModel.getValueAt(row, column), row, column);
                inventoryTableModel.fireTableCellUpdated(row, column);
            }
        });
    }
}
