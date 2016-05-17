package com.techno_wizard.mcguicreator.management;

import com.techno_wizard.mcguicreator.gui.InventoryTableModel;
import com.techno_wizard.mcguicreator.gui.MainMenu;
import com.techno_wizard.mcguicreator.gui.events.AutoGenerateType;
import com.techno_wizard.mcguicreator.gui.inventory.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

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


    /**
     * Inits the slots for the inventory
     */
    public void initSlots() {

        MouseListener tableClickListener = new MouseAdapter() {
            boolean isBeingDragged=false;
            ItemStack beingDragged = null;
            long firstPressed=0;

            @Override
            public void mousePressed(MouseEvent e) {
                int clickedY = table.rowAtPoint(e.getPoint());//get mouse-selected row
                int clickedX = table.columnAtPoint(e.getPoint());//get mouse-selected col
                if(outOfBounds(clickedX,clickedY))
                    return;
                selectNewItemStack(e);
                isBeingDragged = true;
                beingDragged = getActiveItemStack();
                firstPressed = System.currentTimeMillis();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(isBeingDragged&&beingDragged!=null) {
                    int clickedY = table.rowAtPoint(e.getPoint());//get mouse-selected row
                    int clickedX = table.columnAtPoint(e.getPoint());//get mouse-selected col;

                    if(outOfBounds(clickedX,clickedY))
                        return;
                    if(System.currentTimeMillis()-firstPressed>100) {

                        ItemStack slot = beingDragged;

                        mainMenu.getEditorManager().saveCurrentItemStack();

                        setActiveItemStack(clickedX, clickedY);
                        mainMenu.getEditorManager().loadStack(getActiveItemStack());

                        ItemStack activeItemStack = getActiveItemStack();

                        Material m = activeItemStack.getMaterial();
                        String lore = activeItemStack.getLore();
                        int amount = activeItemStack.getAmount();
                        String name = activeItemStack.getName();
                        List<Enchantment> ench = new ArrayList<>(activeItemStack.getEnchantments());
                        String notes = activeItemStack.getNotes();
                        AutoGenerateType autogen = activeItemStack.getAutoGenerateType();
                        boolean close = activeItemStack.getCloseInvOnClick();

                        //Update each slot
                        activeItemStack.update(slot.getMaterial(), slot.getLore(), slot.getAmount(),slot.getName(),slot.getEnchantments(),slot.getNotes(),slot.getAutoGenerateType(),slot.getCloseInvOnClick());
                        slot.update(m,lore,amount,name,ench,notes,autogen,close);
                        mainMenu.getEditorManager().loadStack(getActiveItemStack());

                    }else{
                        selectNewItemStack(e);
                    }
                    isBeingDragged = false;
                    beingDragged = null;

                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(!isBeingDragged)
                    selectNewItemStack(e);
            }

            public void selectNewItemStack(MouseEvent e){
                int clickedY = table.rowAtPoint(e.getPoint());//get mouse-selected row
                int clickedX = table.columnAtPoint(e.getPoint());//get mouse-selected col

                //Make sure the user does not click on the same slot twice
                if (selectedX == clickedX && selectedY == clickedY||outOfBounds(clickedX,clickedY))
                    return;

                //Save the previous Itemstack
                mainMenu.getEditorManager().saveCurrentItemStack();

                //Set lastSlot equal to the current slot
                setActiveItemStack(clickedX, clickedY);

                //Load the new slot
                mainMenu.getEditorManager().loadStack(getActiveItemStack());

            }
        };

        table.addMouseListener(tableClickListener);
    }

    public void updateActiveItemStackIcon(Material material) {
        ItemStack is = getActiveItemStack();
        is.setMaterial(material);

        int row = selectedY;
        int column = selectedX;
        table.setValueAt(table.getValueAt(row, column), row, column);
        //todo check if this actually works. IOB is making it impossible to test
        model.fireTableCellUpdated(row, column);
        //todo debug statement
    }

    public void setActiveItemStack(int x, int y) {
        selectedX = x;
        selectedY = y;
    }

    public ItemStack getActiveItemStack(){
        return model.getItemStackAt(selectedX, selectedY);
    }

    public void onColorPress() {

    }
    public InventoryTableModel getInventoryTableModel(){
        return this.model;
    }

    private boolean outOfBounds(int clickedX,int clickedY){

        //Make sure it's not out of bounds
        if (clickedX >= getInventoryTableModel().getColumnCount()||clickedY >=getInventoryTableModel().getRowCount()||clickedX<0||clickedY<0)
            return true;
        return false;
    }

    public void transferData(InventoryTableModel itm){
        this.getInventoryTableModel().setInventoryName(itm.getInventoryName());
        this.getInventoryTableModel().setItemStacks(itm.getItemstacks());
        mainMenu.getEditorManager().updateInventorySize(itm.getRowCount());
    }
}
