package com.techno_wizard.mcguicreator.management;

import com.techno_wizard.mcguicreator.gui.ChatColor;
import com.techno_wizard.mcguicreator.gui.MainMenu;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.Material;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ethan on 4/6/2016.
 */
public class EditorManager {
    public static final int STACK_EDITOR_TAB = 0;
    public static final int LORE_EDITOR_TAB = 1;
    public static final int INV_EDITOR_TAB = 2;

    private MainMenu mainMenu;
    private JEditorPane stackNameEditor;
    private JCheckBox showFormattedTxtDetails;
    private JCheckBox showFormattedTxtLore;
    private JSpinner stackItemCountSpinner;
    private JComboBox materialComboBox;
    private JCheckBox enableEnchantCheckBox;
    private JTextField notesBox;
    private JEditorPane loreEditor;
    private JCheckBox showFormattedTxtInv;
    private JEditorPane inventoryNameEditor;
    private JTabbedPane editorTabbedPane;

    private boolean textIsFormatted;

    private String stackNamePlain = "";
    private String inventoryNamePlain = "";
    private String lorePlain = "";

    public EditorManager(MainMenu mainMenu, JEditorPane stackNameEditor, JCheckBox showFormattedTxtDetails,
                         JCheckBox showFormattedTxtLore, JCheckBox showFormattedTxtInv, JSpinner stackItemCountSpinner, JComboBox materialBox,
                         JCheckBox enableEnchantCheckBox, JTextField notesBox, JEditorPane loreEditor,
                         JTabbedPane editorTabbedPane, JEditorPane inventoryNameEditor) {
        this.inventoryNameEditor = inventoryNameEditor;
        this.mainMenu = mainMenu;
        this.stackNameEditor = stackNameEditor;
        this.showFormattedTxtDetails = showFormattedTxtDetails;
        this.showFormattedTxtLore = showFormattedTxtLore;
        this.stackItemCountSpinner = stackItemCountSpinner;
        this.materialComboBox = materialBox;
        this.enableEnchantCheckBox = enableEnchantCheckBox;
        this.notesBox = notesBox;
        this.loreEditor = loreEditor;
        this.showFormattedTxtInv = showFormattedTxtInv;
        this.editorTabbedPane = editorTabbedPane;
        initEditors();
    }

    public void initEditors() {
        materialComboBox.addActionListener(e ->
                mainMenu.getInvManager().updateActiveItemStackIcon((Material) materialComboBox.getSelectedItem()));
        //TODO add text formatting switch
        ActionListener listener = e1 -> setTextIsFormatted(((JCheckBox) e1.getSource()).isSelected());
        showFormattedTxtLore.addActionListener(listener);
        showFormattedTxtDetails.addActionListener(listener);
        showFormattedTxtInv.addActionListener(listener);
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
        setTextIsFormatted(false);
        ItemStack oldItemstack = mainMenu.getInvManager().getActiveItemStack();
        //todo revert to unformatted version first
        oldItemstack.setName(stackNameEditor.getText());
        oldItemstack.setMaterial((Material) materialComboBox.getSelectedItem());
        /*todo this is really only a temp fix. We'd need to go back to the unformatted text, and this simply
        removes them
         */
        oldItemstack.setLore(loreEditor.getText());
        oldItemstack.setEnchanted(enableEnchantCheckBox.isSelected());
        oldItemstack.setAmount((Integer) stackItemCountSpinner.getValue());
        oldItemstack.setNotes(notesBox.getText());
    }

    /**
     * sets the selected editor view
     * @param textIsFormatted whether or not the text should be formatted
     */
    public void setTextIsFormatted(boolean textIsFormatted) {
        if((this.textIsFormatted && textIsFormatted) || (!this.textIsFormatted && !textIsFormatted)) return;

        if(!textIsFormatted) {
            // set text unformatted
            inventoryNameEditor.setEditable(true);
            inventoryNameEditor.setContentType("text/plain");
            inventoryNameEditor.setText(inventoryNamePlain);

            loreEditor.setEditable(true);
            loreEditor.setContentType("text/plain");
            loreEditor.setText(lorePlain);

            stackNameEditor.setEditable(true);
            stackNameEditor.setContentType("text/plain");
            stackNameEditor.setText(stackNamePlain);
            System.out.println("Inactive");
        } else {
            System.out.println("Active");
            inventoryNamePlain = inventoryNameEditor.getText();
            inventoryNameEditor.setContentType("text/html");
            inventoryNameEditor.setEditable(false);
            // convert

            lorePlain = loreEditor.getText();
            loreEditor.setContentType("text/html");
            loreEditor.setEditable(false);
            // convert

            stackNamePlain = stackNameEditor.getText();
            stackNameEditor.setContentType("text/html");
            stackNameEditor.setEditable(false);
            //convert
        }

        this.textIsFormatted = textIsFormatted;
        showFormattedTxtDetails.setSelected(textIsFormatted);
        showFormattedTxtInv.setSelected(textIsFormatted);
        showFormattedTxtLore.setSelected(textIsFormatted);
    }

    public void setButtonListener(ChatColor color, JButton button) {
        button.addActionListener(e -> onColorButtonPress(color));
    }

    /**
     * @param chatColor
     */
    public void onColorButtonPress(ChatColor chatColor) {
        boolean textWasFormatted = textIsFormatted;
        // change to original to add color code
        if (textIsFormatted) setTextIsFormatted(false);

        // add color code
        switch (editorTabbedPane.getSelectedIndex()) {
            case STACK_EDITOR_TAB:
                stackNameEditor.setText(stackNameEditor.getText().concat(chatColor.getColorCode()));
                break;
            case LORE_EDITOR_TAB:
                System.out.println("Operational");
                loreEditor.setText(loreEditor.getText().concat(chatColor.getColorCode()));
                break;
            case INV_EDITOR_TAB:
                inventoryNameEditor.setText(inventoryNameEditor.getText().concat(chatColor.getColorCode()));
                break;
            default:
                System.out.println("Unknown editor tab: Error");
        }

        // restore old view
        if(textWasFormatted) {
            setTextIsFormatted(true);
        }
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
