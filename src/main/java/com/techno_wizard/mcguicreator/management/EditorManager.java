package com.techno_wizard.mcguicreator.management;

import com.techno_wizard.mcguicreator.gui.MainMenu;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.Material;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Ethan on 4/6/2016.
 */
public class EditorManager {
    private MainMenu mainMenu;
    private JEditorPane stackNameEditor;
    private JCheckBox showFormattedTxtDetails;
    private JCheckBox showFormattedTxtLore;
    private JSpinner stackItemCountSpinner;
    private JComboBox materialComboBox;
    private JCheckBox enableEnchantCheckBox;
    private JTextField notesBox;
    private JEditorPane loreEditor;

    public EditorManager(MainMenu mainMenu, JEditorPane stackNameEditor, JCheckBox showFormattedTxtDetails,
                         JCheckBox showFormattedTxtLore, JSpinner stackItemCountSpinner, JComboBox materialBox,
                         JCheckBox enableEnchantCheckBox, JTextField notesBox, JEditorPane loreEditor) {
        this.mainMenu = mainMenu;
        this.stackNameEditor = stackNameEditor;
        this.showFormattedTxtDetails = showFormattedTxtDetails;
        this.showFormattedTxtLore = showFormattedTxtLore;
        this.stackItemCountSpinner = stackItemCountSpinner;
        this.materialComboBox = materialBox;
        this.enableEnchantCheckBox = enableEnchantCheckBox;
        this.notesBox = notesBox;
        this.loreEditor = loreEditor;
    }

    public void initEditors() {
        materialComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenu.getInvManager().updateActiveItemStackIcon((Material) materialComboBox.getSelectedItem());
            }
        });
    }

    public void loadStack(ItemStack stack) {
        stackNameEditor.setText(stack.getName());
        materialComboBox.setSelectedItem(stack.getMaterial());

        //todo render if showFormatted is true
        loreEditor.setText(stack.getLore());
        materialComboBox.setSelectedItem(stack.getMaterial());
        stackItemCountSpinner.setValue(stack.getAmount());
        enableEnchantCheckBox.setSelected(stack.isEnchanted());
        notesBox.setText(stack.getNotes());
    }

    public void saveCurrentItemStack() {
        ItemStack oldItemstack = mainMenu.getInvManager().getActiveItemStack();
        //todo revert to unformatted version first
        oldItemstack.setName(stackNameEditor.getText());
        oldItemstack.setMaterial((Material) materialComboBox.getSelectedItem());
        /*todo this is really only a temp fix. We'd need to go back to the unformatted text, and this simply
        removes them
         */
        oldItemstack.setLore(loreEditor.getText().replaceAll("\\<[^>]*>", ""));
        oldItemstack.setEnchanted(enableEnchantCheckBox.isSelected());
        oldItemstack.setAmount((Integer) stackItemCountSpinner.getValue());
        oldItemstack.setNotes(notesBox.getText());
    }

    /**
     * inits the materials
     */
    public void initMaterialList() {
        for (Material mat : Material.values()) {
            materialComboBox.addItem(mat);
        }
    }
}
