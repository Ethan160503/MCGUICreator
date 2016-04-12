package com.techno_wizard.mcguicreator.gui;

import com.techno_wizard.mcguicreator.codecreator.CodeCreator;
import com.techno_wizard.mcguicreator.gui.codecreator.CodeExporter;
import com.techno_wizard.mcguicreator.gui.inventory.Enchantment;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.ItemUtil;
import com.techno_wizard.mcguicreator.gui.inventory.Material;
import com.techno_wizard.mcguicreator.management.EditorManager;
import com.techno_wizard.mcguicreator.management.InventoryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

    private JSpinner enchantmentLevel;
    private JComboBox enchantmentType;
    public JList enchantmentList;
    private JButton enchantmentAdd;
    private JButton enchantmentRemove;
    private JScrollPane enchantmentScrollPane;

    private InventoryTableModel inventoryTableModel;

    public MainMenu() {
        setName("MC GUI Creator");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        //test val
        //inventoryTable.getModel().setValueAt(resizeIcon(new ImageIcon(getClass().getResource("/itemimages/Poisonous_potato.png"))), 0, 0);


        inventoryTable.setRowHeight(75);

        editorManager = new EditorManager(this,stackNameEditor,showFormattedTextCheckBoxDetails,
                showFormattedTextCheckBoxLore, showFormattedTextCheckBoxInv, stackItemCountSpinner,
                stackType,enableEnchantmentNotVisibleCheckBox,stackNotes,editorPane1, editorTabbedPane, inventoryNameEditor);
        initButtons();

        // if one checkbox's state is changed, change the other one's state


        /*setJMenuBar(initMenuBar());
        //initialize the slots
        initSlots();*/


        //Create the list to store all the enchantments
        enchantmentList.setModel(new DefaultListModel());

        initMaterials();
        initEnchantments();
        pack();
    }

    /**
     * inits the enchantments
     */
    public void initEnchantments(){
        this.enchantmentType.addItem("");
        for(Enchantment.EnchantmentType e : Enchantment.EnchantmentType.values()){
            this.enchantmentType.addItem(e.getBukkitName());
        }
    }
    /**
     * inits the materials
     */
    public void initMaterials() {
        for (Material mat : Material.values()) {
            stackType.addItem(mat);
        }
    }


    /**
     * inits the toolbar
     */
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu();
        JMenuItem fileOpen = new JMenuItem("Open");
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
                if(!((String)enchantmentType.getSelectedItem()).equals("")&&((int)enchantmentLevel.getValue())!=0){
                    DefaultListModel listModel = (DefaultListModel) enchantmentList.getModel();
                    Enchantment ench = new Enchantment( Enchantment.EnchantmentType.getEnchantmentByName((String)enchantmentType.getSelectedItem()),((int)enchantmentLevel.getValue()));
                    String enchString = ench.getBukkitName()+" : "+ ItemUtil.getRomanNumerals(ench.getPowerLavel());
                    boolean containsEnchantment = false;
                    for(int i = 0 ;i<listModel.size();i++){
                        if(listModel.getElementAt(i).equals(enchString)){
                            containsEnchantment = true;
                            break;
                        }
                    }
                    if(!containsEnchantment) {
                        getInvManager().getActiveItemStack().addEnchantment(ench);
                        listModel.addElement(enchString);
                    }
                }
            }
        };
        enchantmentAdd.addMouseListener(addEnchantmentListener);

        MouseListener removeEnchantmentListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!((String)enchantmentType.getSelectedItem()).equals("")){
                    Enchantment ench = new Enchantment( Enchantment.EnchantmentType.getEnchantmentByName((String)enchantmentType.getSelectedItem()),((int)enchantmentLevel.getValue()));
                    getInvManager().getActiveItemStack().removeEnchantment(ench);
                    for(int i = 0; i < ((DefaultListModel)enchantmentList.getModel()).size();i++){
                        if(((DefaultListModel)enchantmentList.getModel()).getElementAt(i).equals(ench.getBukkitName()+" : "+ItemUtil.getRomanNumerals(ench.getPowerLavel())))
                            ((DefaultListModel)enchantmentList.getModel()).remove(i);
                    }
                }
            }
        };
        enchantmentRemove.addMouseListener(removeEnchantmentListener);

        //Creating the code for the clipboard.
        //todo delegate to managing class
        MouseListener copyToClipboardListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Upddate the active itemstack
                ItemStack is = invManager.getActiveItemStack();
                is.setName(stackNameEditor.getText());
                is.setLore(editorPane1.getText().replaceAll("\\<[^>]*>", ""));

                StringBuilder code = new StringBuilder();
                for (String s : CodeCreator.writecode(inventoryTableModel)) {
                    code.append(s + "\n");
                }
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(code.toString()), null);
            }
        };
        copyToClipboardButton.addMouseListener(copyToClipboardListener);
        //Creating the code for the clipboard.

        MouseListener exportListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Upddate the active itemstack
                ItemStack is = invManager.getActiveItemStack();
                is.setName(stackNameEditor.getText());
                is.setLore(editorPane1.getText().replaceAll("\\<[^>]*>", ""));

                StringBuilder code = new StringBuilder();
                for (String s : CodeCreator.writecode(inventoryTableModel)) {
                    code.append(s + "\n");
                }
                new CodeExporter(code.toString());
            }
        };
        exportButton.addMouseListener(exportListener);
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
        enchantmentLevel = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        inventorySizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1));

        invManager = new InventoryManager(this,inventoryTable);
    }

    public InventoryManager getInvManager() {
        return invManager;
    }

    public EditorManager getEditorManager() {
        return editorManager;
    }
}
