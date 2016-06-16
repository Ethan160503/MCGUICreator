package com.techno_wizard.mcguicreator.gui;

import com.techno_wizard.mcguicreator.gui.events.AutoGenerateType;
import com.techno_wizard.mcguicreator.gui.inventory.*;
import com.techno_wizard.mcguicreator.management.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

/**
 * Created by Ethan on 4/1/2016.
 */
public class MainMenu extends JFrame {

    private JTable inventoryTable;
    private JPanel panel1;
    private JEditorPane editorPane1;
    private JCheckBox showFormattedTextCheckBoxLore;
    private JCheckBox showFormattedTextCheckBoxDetails;
    private JEditorPane stackNameEditor;
    private JSpinner stackItemCountSpinner;
    private JTextField stackNotes;
    private JCheckBox enableEnchantmentNotVisibleCheckBox;
    private JComboBox stackType;

    private InventoryManager invManager;
    private EditorManager editorManager;


    private JEditorPane inventoryNameEditor;
    private JCheckBox showFormattedTextCheckBoxInv;
    private JSpinner inventorySizeSpinner;
    private JTabbedPane editorTabbedPane;

    private JSpinner enchantmentLevelSpinner;
    private JComboBox enchantmentType;
    private JList enchantmentList;
    private JButton enchantmentAdd;
    private JButton enchantmentRemove;
    private JScrollPane enchantmentScrollPane;

    private JComboBox eventGenerateType;
    private JRadioButton closeInvOnClickRButton;

    private InventoryTableModel inventoryTableModel;

    public MainMenu() {
        setName("MC GUI Creator");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        editorManager = new EditorManager(this, stackNameEditor, showFormattedTextCheckBoxDetails,
                showFormattedTextCheckBoxLore, showFormattedTextCheckBoxInv, stackItemCountSpinner,
                stackType, enableEnchantmentNotVisibleCheckBox, stackNotes, editorPane1, editorTabbedPane,
                inventoryNameEditor, eventGenerateType, inventorySizeSpinner,
                enchantmentList, closeInvOnClickRButton);

        editorManager.updateInventorySize(3);

        initButtons();

        initInventoryObject();
        pack();

        new InventoryHoverOverGUI(inventoryTable, panel1, inventoryTableModel);
    }

    /**
     * inits materials, enchantments, and autogenerate types
     */
    public void initInventoryObject(){
        //Create the list to store all the enchantments
        enchantmentList.setModel(new DefaultListModel());
        this.enchantmentType.addItem("");
        for (Enchantment.EnchantmentType e : Enchantment.EnchantmentType.values())
            this.enchantmentType.addItem(e.getBukkitName());
        for (Material mat : Material.values())
            stackType.addItem(mat);
        for (AutoGenerateType agt : AutoGenerateType.values())
            eventGenerateType.addItem(agt.getName());
    }

    /**
     * inits the button listeners
     */
    private void initButtons() {
        MouseListener addEnchantmentListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!(enchantmentType.getSelectedItem()).equals("") && ((int) enchantmentLevelSpinner.getValue()) > 0) {
                    DefaultListModel listModel = (DefaultListModel) enchantmentList.getModel();
                    Enchantment ench = new Enchantment(Enchantment.EnchantmentType.getEnchantmentByName((String) enchantmentType.getSelectedItem()), ((int) enchantmentLevelSpinner.getValue()));
                    getInvManager().getActiveItemStack().removeEnchantment(ench, enchantmentList);
                    getInvManager().getActiveItemStack().addEnchantment(ench);
                    listModel.addElement(ench.getDisplay());
                }
            }
        };
        enchantmentAdd.addMouseListener(addEnchantmentListener);

        MouseListener removeEnchantmentListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!(enchantmentType.getSelectedItem()).equals("")) {
                    Enchantment ench = new Enchantment(Enchantment.EnchantmentType.getEnchantmentByName((String) enchantmentType.getSelectedItem()), ((int) enchantmentLevelSpinner.getValue()));
                    getInvManager().getActiveItemStack().removeEnchantment(ench, enchantmentList);
                }
            }
        };
        enchantmentRemove.addMouseListener(removeEnchantmentListener);
    }

    /**
     * gets the root panel for use in the main() method to init the window
     *
     * @return
     */
    public JPanel getRootPanel() {
        return panel1;
    }

    /**
     * Inits the variables that IDEA doesn't
     */
    private void createUIComponents() {
        DefaultTableModel model = new DefaultTableModel(6, 9) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return ImageIcon.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        inventoryTable = new JTable(model);
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //set the table model
        inventoryTableModel = new InventoryTableModel();
        inventoryTable.setModel(inventoryTableModel);
        inventoryTable.setTableHeader(null);

        stackItemCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 64, 1));
        stackItemCountSpinner.setValue(1);
        inventorySizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 9, 1));
        inventorySizeSpinner.setValue(3);
        enchantmentLevelSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        invManager = new InventoryManager(this, inventoryTable);
    }

    public InventoryManager getInvManager() {
        return invManager;
    }

    public EditorManager getEditorManager() {return editorManager;}

    public JTable getInventoryTable() {
        return this.inventoryTable;
    }
}
