package com.techno_wizard.mcguicreator.management;

import com.techno_wizard.mcguicreator.codecreator.CodeCreator;
import com.techno_wizard.mcguicreator.gui.*;
import com.techno_wizard.mcguicreator.gui.codecreator.CodeExporter;
import com.techno_wizard.mcguicreator.gui.events.AutoGenerateType;
import com.techno_wizard.mcguicreator.gui.inventory.*;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

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
    private JCheckBox showFormattedTxtInv;
    private JEditorPane inventoryNameEditor;
    private JTabbedPane editorTabbedPane;

    private JComboBox eventGeneratorBox;

    private JSpinner inventorySizeSpinner;

    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JList enchantmentList;

    private boolean textIsFormatted;

    private String stackNamePlain = "";
    private String inventoryNamePlain = "";
    private String lorePlain = "";

    private TextEditorManager textEditorManager;

    public EditorManager(MainMenu mainMenu, JEditorPane stackNameEditor, JCheckBox showFormattedTxtDetails,
                         JCheckBox showFormattedTxtLore, JCheckBox showFormattedTxtInv, JSpinner stackItemCountSpinner, JComboBox materialBox,
                         JCheckBox enableEnchantCheckBox, JTextField notesBox, JEditorPane loreEditor,
                         JTabbedPane editorTabbedPane, JEditorPane inventoryNameEditor,
                         JComboBox eventGeneratorBox, JSpinner inventorySizeSpinner,JButton copyToClipboardButton,JButton exportButton,
                         JList enchantmentList) {
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

        this.copyToClipboardButton = copyToClipboardButton;
        this.exportButton = exportButton;
        this.enchantmentList = enchantmentList;


        this.textEditorManager = new TextEditorManager(this);

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
        copyToClipboardButton.addMouseListener(copyToClipboardListener);

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
        exportButton.addMouseListener(exportListener);

        inventorySizeSpinner.setValue(mainMenu.getInventoryTableModel().getRowCount());
        inventorySizeSpinner.addChangeListener(e -> {
            this.mainMenu.getInventoryTableModel().setInventorySize((int) inventorySizeSpinner.getValue());
            this.mainMenu.getInventoryTable().setModel(this.mainMenu.getInventoryTableModel());
            this.mainMenu.getInventoryTable().setRowHeight(300 / mainMenu.getInventoryTableModel().getRowCount());
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
        ((DefaultListModel) enchantmentList.getModel()).clear();

        for (int i = 0; i < eventGeneratorBox.getItemCount(); i++) {
            if (AutoGenerateType.getTypeByName((String) eventGeneratorBox.getItemAt(i)) == stack.getAutoGenerateType()) {
                this.eventGeneratorBox.setSelectedIndex(i);
                break;
            }
        }
        for (Enchantment e : stack.getEnchantments())
            ((DefaultListModel) enchantmentList.getModel()).addElement(e.getDisplay());
    }

    public void saveCurrentItemStack() {
        setTextIsFormatted(false);
        this.saveItemStack(mainMenu.getInvManager().getActiveItemStack());
    }

    public void saveItemStack(ItemStack is) {
        ItemStack oldItemstack = is;
        //Make sure the itemstack exists;
        if (oldItemstack == null)
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
        oldItemstack.setAutoGenerateType(AutoGenerateType.getTypeByName((String) (eventGeneratorBox.getSelectedItem())));
        List<Enchantment> enchs = new ArrayList<>();
        for (int i = 0; i < ((DefaultListModel) enchantmentList.getModel()).size(); i++) {
            String s = (String) (enchantmentList.getModel()).getElementAt(i);
            Enchantment ench = new Enchantment(Enchantment.EnchantmentType.getEnchantmentByName(s.split(" : ")[0]), ItemUtil.getIntegers(s.split(" : ")[1]));
            enchs.add(ench);
        }
        oldItemstack.setEnchantments(enchs);
    }

    /**
     * sets the selected editor view
     *
     * @param textIsFormatted whether or not the text should be formatted
     */
    public void setTextIsFormatted(boolean textIsFormatted) {
        if ((this.textIsFormatted && textIsFormatted) || (!this.textIsFormatted && !textIsFormatted)) return;

        if (!textIsFormatted) {
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

    public void setButtonListener(ChatColor color, JButton button){
        button.addActionListener(e->onColorButtonPress(color));
    }

    /**
     * @param chatColor
     */
    public void onColorButtonPress(ChatColor chatColor) {
        boolean textWasFormatted = textIsFormatted;
        // change to original to add color code
        if (textIsFormatted) setTextIsFormatted(false);

        this.getTextEditorManager().editSelectedEditor(chatColor.getColorCode());

        // restore old view
        if(textWasFormatted)
            setTextIsFormatted(true);
    }

    /**
     * inits the materials
     */
    public void initMaterialList() {
        for (Material mat : Material.values())
            materialComboBox.addItem(mat);
    }
    public JEditorPane getInventoryNameEditor(){return inventoryNameEditor;}
    public JEditorPane getItemStackNameEditor(){return stackNameEditor;}
    public JEditorPane getItemStackLoreEditor(){return loreEditor;}
    public JTextField getNotes(){return notesBox;}

    public TextEditorManager getTextEditorManager(){return textEditorManager;}
}
