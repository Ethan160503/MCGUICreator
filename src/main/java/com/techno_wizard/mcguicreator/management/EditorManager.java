package com.techno_wizard.mcguicreator.management;

import com.techno_wizard.mcguicreator.codecreator.CodeCreator;
import com.techno_wizard.mcguicreator.gui.ChatColor;
import com.techno_wizard.mcguicreator.gui.MainMenu;
import com.techno_wizard.mcguicreator.gui.codecreator.CodeExporter;
import com.techno_wizard.mcguicreator.gui.events.AutoGenerateType;
import com.techno_wizard.mcguicreator.gui.inventory.Enchantment;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.ItemUtil;
import com.techno_wizard.mcguicreator.gui.inventory.Material;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

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

    private JComboBox eventGeneratorBox;

    private JSpinner inventorySizeSpinner;

    private boolean textIsFormatted;

    private String stackNamePlain = "";
    private String inventoryNamePlain = "";
    private String lorePlain = "";

    public EditorManager(MainMenu mainMenu, JEditorPane stackNameEditor, JCheckBox showFormattedTxtDetails,
                         JCheckBox showFormattedTxtLore, JCheckBox showFormattedTxtInv, JSpinner stackItemCountSpinner, JComboBox materialBox,
                         JCheckBox enableEnchantCheckBox, JTextField notesBox, JEditorPane loreEditor,
                         JTabbedPane editorTabbedPane, JEditorPane inventoryNameEditor
                         ,JComboBox eventGeneratorBox,JSpinner inventorySizeSpinner) {
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

        this.eventGeneratorBox = eventGeneratorBox;
        this.inventorySizeSpinner = inventorySizeSpinner;
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

        //Creating the code for the clipboard.
        //todo delegate to managing class
        MouseListener copyToClipboardListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Update the active itemstack
                ItemStack is = mainMenu.getInvManager().getActiveItemStack();
                StringBuilder code = new StringBuilder();
                for (String s : CodeCreator.writecode(mainMenu)) {
                    code.append(s + "\n");
                }
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(code.toString()), null);
            }
        };
        mainMenu.copyToClipboardButton.addMouseListener(copyToClipboardListener);
        //Creating the code for the clipboard.

        MouseListener exportListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Update the active itemstack
                ItemStack is = mainMenu.getInvManager().getActiveItemStack();

                StringBuilder code = new StringBuilder();
                for (String s : CodeCreator.writecode(mainMenu)) {
                    code.append(s + "\n");
                }
                new CodeExporter(code.toString());
            }
        };
        mainMenu.exportButton.addMouseListener(exportListener);

        inventorySizeSpinner.setValue(mainMenu.getInventoryTableModel().getRowCount());
        inventorySizeSpinner.addChangeListener(e -> {
            this.mainMenu.getInventoryTableModel().setInventorySize((int)inventorySizeSpinner.getValue());
            this.mainMenu.getInventoryTable().setModel(this.mainMenu.getInventoryTableModel());
            this.mainMenu.getInventoryTable().setRowHeight((95*3)/mainMenu.getInventoryTableModel().getRowCount());
        }  );

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
        ((DefaultListModel)mainMenu.enchantmentList.getModel()).clear();

        for(int i =0;i<eventGeneratorBox.getItemCount(); i++) {
            if (AutoGenerateType.getTypeByName((String) eventGeneratorBox.getItemAt(i)) == stack.getAutoGenerateType()) {
                this.eventGeneratorBox.setSelectedIndex(i);
                break;
            }
        }

        for(Enchantment e : stack.getEnchantments()){
            ((DefaultListModel)mainMenu.enchantmentList.getModel()).addElement(e.getBukkitName()+" : "+ItemUtil.getRomanNumerals(e.getPowerLavel()));
        }
    }

    public void saveCurrentItemStack() {
        setTextIsFormatted(false);
        ItemStack oldItemstack = mainMenu.getInvManager().getActiveItemStack();
        this.saveItemStack(oldItemstack);
    }
    public void saveItemStack(ItemStack is) {
        ItemStack oldItemstack = is;

        //Make sure the itemstack exists;
        if(oldItemstack == null)
            return;

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
        oldItemstack.setAutoGenerateType(AutoGenerateType.getTypeByName((String)(eventGeneratorBox.getSelectedItem())));
        List<Enchantment> enchs = new ArrayList<>();
        for(int i = 0; i <((DefaultListModel)mainMenu.enchantmentList.getModel()).size();i++){
            String s = (String) ((DefaultListModel)mainMenu.enchantmentList.getModel()).getElementAt(i);
            Enchantment ench = new Enchantment(Enchantment.EnchantmentType.getEnchantmentByName(s.split(" : ")[0]), ItemUtil.getIntegers(s.split(" : ")[1]));
            enchs.add(ench);
        }
        oldItemstack.setEnchantments(enchs);
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
