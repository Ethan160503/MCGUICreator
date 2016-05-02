package com.techno_wizard.mcguicreator.gui;

import com.techno_wizard.mcguicreator.gui.events.AutoGenerateType;
import com.techno_wizard.mcguicreator.gui.inventory.*;
import com.techno_wizard.mcguicreator.management.*;
import com.techno_wizard.mcguicreator.tutorials.TutorialMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

/**
 * Created by Ethan on 4/1/2016.
 */
public class MainMenu extends JFrame {

    private JTable inventoryTable;
    private JPanel panel1;
    private JButton blackButton;
    private JButton darkRedButton;
    private JButton darkPurpleButton;
    private JButton darkBlueButton;
    private JButton darkAquaButton;
    private JButton goldButton;
    private JButton grayButton;
    private JButton darkGrayButton;
    private JButton blueButton;
    private JButton greenButton;
    private JButton aquaButton;
    private JButton redButton;
    private JButton lightPurpleButton;
    private JButton yellowButton;
    private JButton whiteButton;
    private JButton darkGreenButton;
    private JButton strikethroughButton;
    private JButton boldButton;
    private JButton magicButton;
    private JButton underlineButton;
    private JButton italicButton;
    private JButton resetButton;
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


    //Code buttons
    private JButton exportButton;
    private JButton copyToClipboardButton;
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

    private InventoryTableModel inventoryTableModel;

    public MainMenu() {
        setName("MC GUI Creator");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inventoryTable.setRowHeight(95);

        editorManager = new EditorManager(this,stackNameEditor,showFormattedTextCheckBoxDetails,
                showFormattedTextCheckBoxLore, showFormattedTextCheckBoxInv, stackItemCountSpinner,
                stackType,enableEnchantmentNotVisibleCheckBox,stackNotes,editorPane1, editorTabbedPane,
                inventoryNameEditor,eventGenerateType,inventorySizeSpinner,copyToClipboardButton,exportButton,
                enchantmentList);

        initButtons();
        // psst... this does nothing! Thanks IntelliJ! Lol. Got to fix that.
        initMenuBar();

        initMaterials();
        initAutoGenerateTypes();
        initEnchantments();
        pack();

        //TODO: Fix this
        new InventoryHoverOverGUI(inventoryTable,panel1,inventoryTableModel);

        //TODO: Add JButton or MenuButton to open menu.
        new TutorialMenu();
    }

    /**
     * inits the enchantments
     */
    public void initEnchantments(){
        //Create the list to store all the enchantments
        enchantmentList.setModel(new DefaultListModel());
        this.enchantmentType.addItem("");
        for(Enchantment.EnchantmentType e : Enchantment.EnchantmentType.values()){
            this.enchantmentType.addItem(e.getBukkitName());
        }
    }
    /**
     * inits the materials
     */
    public void initMaterials() {
        for (Material mat : Material.values())
            stackType.addItem(mat);
    }

    /**
     * inits the AutoGenerate Types
     */
    public void initAutoGenerateTypes(){
        for(AutoGenerateType agt : AutoGenerateType.values())
            eventGenerateType.addItem(agt.getName());
    }


    /**
     * inits the toolbar
     */
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu();
        JMenuItem fileOpen = new JMenuItem("Open");
        JMenu fileExport = new JMenu("Export code...");
        JMenuItem exportToClipboard = new JMenuItem("Export to clipboard");
        JMenuItem exportToPopup = new JMenuItem("Export to popup");

        //TODO: figure out why this does not update.
        JMenu chatColor = new JMenu("ChatColor");


        menuBar.add(fileMenu);
        fileMenu.add(fileOpen);
        fileMenu.add(fileExport);
        fileExport.add(exportToClipboard);
        fileExport.add(exportToPopup);

        menuBar.add(chatColor);
        for(ChatColor c : ChatColor.values()) {
            JMenuItem color = new JMenuItem(c.toString());
            color.addActionListener(e -> getEditorManager().getTextEditorManager().editSelectedEditor(c.getColorCode()));
            chatColor.add(color);
        }


        this.setJMenuBar(menuBar);
    }

    /**
     * inits the button listeners
     */
    private void initButtons() {
        /* Unfortunately, due to restrictions of IDEA's GUI designer, i cannot set the buttons
           To compensate, the following madness is required. Yay......
        */
        editorManager.setButtonListener(ChatColor.BLACK, blackButton);
        editorManager.setButtonListener(ChatColor.DARK_BLUE, darkBlueButton);
        editorManager.setButtonListener(ChatColor.DARK_GREEN, darkGreenButton);
        editorManager.setButtonListener(ChatColor.DARK_AQUA, darkAquaButton);
        editorManager.setButtonListener(ChatColor.DARK_RED, darkRedButton);
        editorManager.setButtonListener(ChatColor.DARK_PURPLE, darkPurpleButton);
        editorManager.setButtonListener(ChatColor.GOLD, goldButton);
        editorManager.setButtonListener(ChatColor.GRAY, grayButton);
        editorManager.setButtonListener(ChatColor.DARK_GRAY, darkGrayButton);
        editorManager.setButtonListener(ChatColor.BLUE, blueButton);
        editorManager.setButtonListener(ChatColor.GREEN, greenButton);
        editorManager.setButtonListener(ChatColor.AQUA, aquaButton);
        editorManager.setButtonListener(ChatColor.RED, redButton);
        editorManager.setButtonListener(ChatColor.LIGHT_PURPLE, lightPurpleButton);
        editorManager.setButtonListener(ChatColor.YELLOW, yellowButton);
        editorManager.setButtonListener(ChatColor.WHITE, whiteButton);
        editorManager.setButtonListener(ChatColor.MAGIC, magicButton);
        editorManager.setButtonListener(ChatColor.BOLD, boldButton);
        editorManager.setButtonListener(ChatColor.STRIKETHROUGH, strikethroughButton);
        editorManager.setButtonListener(ChatColor.UNDERLINE, underlineButton);
        editorManager.setButtonListener(ChatColor.ITALIC, italicButton);
        editorManager.setButtonListener(ChatColor.RESET, resetButton);

        MouseListener addEnchantmentListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!(enchantmentType.getSelectedItem()).equals("")&&((int)enchantmentLevelSpinner.getValue())>0){
                    DefaultListModel listModel = (DefaultListModel) enchantmentList.getModel();
                    Enchantment ench = new Enchantment( Enchantment.EnchantmentType.getEnchantmentByName((String)enchantmentType.getSelectedItem()),((int)enchantmentLevelSpinner.getValue()));
                    getInvManager().getActiveItemStack().removeEnchantment(ench,enchantmentList);
                    getInvManager().getActiveItemStack().addEnchantment(ench);
                    listModel.addElement(ench.getDisplay());
                }
            }
        };
        enchantmentAdd.addMouseListener(addEnchantmentListener);

        MouseListener removeEnchantmentListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!(enchantmentType.getSelectedItem()).equals("")){
                    Enchantment ench = new Enchantment( Enchantment.EnchantmentType.getEnchantmentByName((String)enchantmentType.getSelectedItem()),((int)enchantmentLevelSpinner.getValue()));
                    getInvManager().getActiveItemStack().removeEnchantment(ench,enchantmentList);
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

        stackItemCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 64, 1));
        stackItemCountSpinner.setValue(3);
        inventorySizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1));
        inventorySizeSpinner.setValue(3);

        invManager = new InventoryManager(this,inventoryTable);
    }

    public InventoryManager getInvManager() {
        return invManager;
    }

    public EditorManager getEditorManager() {
        return editorManager;
    }

    /**
     * Use this to get the InventoryTableModelInstance.
     * @return
     */
    public InventoryTableModel getInventoryTableModel(){
        return inventoryTableModel;
    }
    public JTable getInventoryTable(){return this.inventoryTable;}
}
