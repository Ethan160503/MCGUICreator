package com.techno_wizard.mcguicreator.data_retention;

import com.techno_wizard.mcguicreator.gui.InventoryTableModel;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;

import java.io.Serializable;

/**
 * Created by ethan on 6/22/16.
 */
public class StoredInventory implements Serializable {
    private long serialVersionUID = 1L;

    protected ItemStack[][] stacks;
    protected String name;


    public void setInventoryModel(InventoryTableModel model) {
        stacks = model.getItemstacks();
        name = model.getInventoryName();
    }

    public InventoryTableModel loadData(InventoryTableModel model) {
        model.setInventoryName(name);
        model.setItemStacks(stacks);
        return model;
    }
}
