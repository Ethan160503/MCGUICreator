package com.techno_wizard.mcguicreator.management;

import com.techno_wizard.mcguicreator.gui.InventoryTableModel;
import com.techno_wizard.mcguicreator.gui.MainMenu;
import com.techno_wizard.mcguicreator.gui.inventory.Enchantment;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.Material;

import javax.swing.*;
import java.awt.*;
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
            boolean isBeingDragged=false;
            ItemStack beingDragged = null;
            long firstPressed=0;

            @Override
            public void mousePressed(MouseEvent e) {
                int clickedY = table.rowAtPoint(e.getPoint());//get mouse-selected row
                int clickedX = table.columnAtPoint(e.getPoint());//get mouse-selected col
                //Make sure it's not out of bounds
                if (clickedX >= getInventoryTableModel().getColumnCount())
                    clickedX = getInventoryTableModel().getColumnCount()-1;
                if(clickedY >=getInventoryTableModel().getRowCount())
                    clickedY =getInventoryTableModel().getRowCount()-1;
                if(clickedX<0||clickedY<0)
                    return;
                isBeingDragged = true;
                beingDragged = model.getItemStackAt(clickedX,clickedY);
                firstPressed = System.currentTimeMillis();
                 selectNewItemStack(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(isBeingDragged&&beingDragged!=null) {
                    if(System.currentTimeMillis()-firstPressed>1000) {
                        int clickedY = table.rowAtPoint(e.getPoint());//get mouse-selected row
                        int clickedX = table.columnAtPoint(e.getPoint());//get mouse-selected col
                        //Make sure it's not out of bounds
                        if (clickedX >= getInventoryTableModel().getColumnCount())
                            clickedX = getInventoryTableModel().getColumnCount()-1;
                        if(clickedY >=getInventoryTableModel().getRowCount())
                            clickedY =getInventoryTableModel().getRowCount()-1;
                        if(clickedX<0||clickedY<0)
                            return;
                        ItemStack activeItemStack = getActiveItemStack();
                        ItemStack slot = model.getItemStackAt(clickedX, clickedY);
                        Material m = slot.getMaterial();
                        String lore = slot.getLore()+"";
                        int amount = slot.getAmount();

                        //Update each slot
                        slot.update(activeItemStack.getMaterial(), activeItemStack.getLore(), activeItemStack.getAmount());
                        activeItemStack.update(m,lore,amount);
                        mainMenu.getEditorManager().saveItemStack(slot);
                        mainMenu.getEditorManager().saveItemStack(activeItemStack);
                     }
                    isBeingDragged = false;
                    beingDragged = null;
                }
                this.selectNewItemStack(e);
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
                if (selectedX == clickedX && selectedY == clickedY)
                    return;

                //Make sure it's not out of bounds
                if (clickedX >= getInventoryTableModel().getColumnCount())
                    clickedX = getInventoryTableModel().getColumnCount()-1;
                if(clickedY >=getInventoryTableModel().getRowCount())
                    clickedY =getInventoryTableModel().getRowCount()-1;
                if(clickedX<0||clickedY<0)
                    return;

                //Save the previous Itemstack
                mainMenu.getEditorManager().saveCurrentItemStack();

                //Set lastSlot equal to the current slot
                setActiveItemStack(clickedX, clickedY);

                //Load the new slot
                mainMenu.getEditorManager().loadStack(getActiveItemStack());

                //Adds hover-over components to the table.
                ItemStack is = getActiveItemStack();
                Component c = table.prepareRenderer(table.getCellRenderer(clickedY,clickedX), clickedY, clickedX);
                if (c instanceof JComponent) {
                    StringBuilder sb = new StringBuilder();
                    for(Enchantment ench : is.getEnchantments())
                       sb.append("<br>"+ench.getBukkitName()+" : "+ench.getPowerLavel());
                    table.setToolTipText("<html><body>"+is.getName()+"<br>----------------<br>"+is.getLore()+sb.toString()+"</body></html>");
                }
                table.add(c);
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
}
