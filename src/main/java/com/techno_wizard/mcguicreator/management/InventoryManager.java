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
    int selectedX = 0, selectedY = 0;

    public InventoryManager(MainMenu mainMenue,JTable table) {
        this.table = table;
        this.mainMenu = mainMenue;
        // assert the table model is correct
        if (!(table.getModel() instanceof InventoryTableModel)) throw new RuntimeException(
                new ClassCastException("Table model is not an inventory table model"));
        this.model = (InventoryTableModel) table.getModel();

        initSlots();
    }

    //Get Selected Itemstack is exactly the same as getActiveItemStack, just with different names

    /**
     * Inits the slots for the inventory
     */
    public void initSlots() {

        MouseListener tableClickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int clickedY = table.rowAtPoint(e.getPoint());//get mouse-selected row
                int clickedX = table.columnAtPoint(e.getPoint());//get mouse-selected col

                //Make sure the user does not click on the same slot twice
                if (selectedX == clickedX && selectedY == clickedY)
                    return;

                //Make sure it's not out of bounds
                if (clickedX >= 9)
                    clickedX = 8;
                if(clickedY >=3)
                    clickedY =2;

                updateItemStackIcon(getActiveItemStack().getMaterial());

                //Save the previous Itemstack
                mainMenu.getEditorManager().saveCurrentItemStack();

                //Set lastSlot equal to the current slot
                setActiveItemStack(clickedX, clickedY);


                updateItemStackIcon(getActiveItemStack().getMaterial());

                //Load the new slot
                mainMenu.getEditorManager().loadStack(getActiveItemStack());
            }
        };

        table.addMouseListener(tableClickListener);
    }

    public void updateItemStackIcon(Material material) {
        ItemStack is = getActiveItemStack();
        is.setMaterial(material);

        int row = selectedY;
        int column = selectedX;
        table.setValueAt(table.getValueAt(row, column), row, column);
        model.fireTableCellUpdated(row, column);
    }

    public void setActiveItemStack(int x, int y) {
        selectedX = x;
        selectedY = y;
    }

    public ItemStack getActiveItemStack(){
        return model.getItemStackAt(selectedX, selectedY);
    }
}
