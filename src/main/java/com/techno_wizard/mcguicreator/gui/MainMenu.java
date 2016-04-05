package com.techno_wizard.mcguicreator.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import com.techno_wizard.mcguicreator.gui.inventory.*;

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
    private JLabel stackItemCountLabel;
    private JTextField stackNotes;
    private JCheckBox enableEnchantmentNotVisibleCheckBox;

    private ColorButtonManager colorButtonManager;

    private JComboBox stackType;

    private InventoryTableModel inventoryTableModel;

    public MainMenu() {
        setName("MC GUI Creator");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        //set the tabel model
        inventoryTableModel = new InventoryTableModel();
        inventoryTable.setModel(inventoryTableModel);

        //test val
        //inventoryTable.getModel().setValueAt(resizeIcon(new ImageIcon(getClass().getResource("/itemimages/Poisonous_potato.png"))), 0, 0);


        inventoryTable.setRowHeight(75);

        // initalize color buttons
        colorButtonManager = new ColorButtonManager(this);
        initButtons();

        // if one checkbox's state is changed, change the other one's state
        //TODO add text formatting switch
        showFormattedTextCheckBoxLore.addActionListener(e -> showFormattedTextCheckBoxDetails
                .setSelected(((JCheckBox)e.getSource()).isSelected()));
        showFormattedTextCheckBoxDetails.addActionListener(e -> showFormattedTextCheckBoxLore
                .setSelected(((JCheckBox)e.getSource()).isSelected()));


        //initialize the slots
        initSlots();
        initMaterials();
    }


    /**
     * inits the materials
     */
    public void initMaterials(){
        for(Material mat : Material.values()) {
            stackType.addItem(mat.getName());
        }
    }
    /**
     * Inits the slots for the inventory
     */
    public void initSlots(){

        //Getting the current slot
        inventoryTable.addMouseListener(new MouseListener() {

            int lastXClick=-1;
            int lastYClick=-1;

            @Override            public void mouseClicked(MouseEvent e) {action(e);            }
            @Override            public void mousePressed(MouseEvent e) {action(e);             }
            @Override            public void mouseReleased(MouseEvent e) {action(e);             }
            @Override            public void mouseEntered(MouseEvent e) {             }
            @Override            public void mouseExited(MouseEvent e) {             }

            public void action(MouseEvent e){
                //TODO: Find better way to get which slot was selected
                int column = e.getX() / (inventoryTable.getWidth()/9);
                int row = e.getY() / (inventoryTable.getHeight()/4);
                //Make sure the user does not click on the same slot twice
                if(lastXClick==column&&lastYClick==row)
                    return;

                //Make sure it's not out of bounds
                if(column >= 9 || row >=6)
                    return;

                //Save the previous Itemstack
                if(lastXClick >=0 && lastYClick>=0) {
                    ItemStack oldItemstack = inventoryTableModel.getItemStackAt(lastYClick, lastXClick);
                    oldItemstack.setName(stackNameEditor.getText());
                    for (Material m : Material.values()) {
                        if (m.getName().equals((String) stackType.getSelectedItem())) {
                            oldItemstack.setMaterial(m);
                            break;
                        }
                    }
                    oldItemstack.setLore(editorPane1.getText());
                }

                //Set lastSlot equal to the current slot
                lastYClick =row;
                lastXClick =column;

                //Load the new slot
                ItemStack nextItemStack = inventoryTableModel.getItemStackAt(row, column);
                stackNameEditor.setText(nextItemStack.getName());
                for(int i = 0 ; i<stackType.getItemCount();i++){
                    if((stackType.getItemAt(i)).equals(nextItemStack.getMaterial().getName())){
                        stackType.setSelectedIndex(i);
                        break;
                    }
                }
                editorPane1.setText(nextItemStack.getLore());
                inventoryTableModel.setActiveItemStack(row,column);
               // inventoryTableModel.setValueAt(inventoryTableModel.getValueAt(row,column),row,column);
            }

        });
        stackType.addMouseListener(new MouseListener() {
            @Override            public void mouseClicked(MouseEvent e) {action(e);            }
            @Override            public void mousePressed(MouseEvent e) {action(e);            }
            @Override            public void mouseReleased(MouseEvent e) {action(e);            }
            @Override            public void mouseEntered(MouseEvent e) {action(e);            }
            @Override            public void mouseExited(MouseEvent e) {action(e);            }
            public void action(MouseEvent e){
                //Load the new slot
                ItemStack is = inventoryTableModel.getActiveItemstack();
                for(Material mm :Material.values()){
                    if(mm.getName().equals(stackType.getSelectedItem())){
                        is.setMaterial(mm);
                        break;
                    }
                }
                int row = inventoryTableModel.getActiveItemStackRow();
                int column = inventoryTableModel.getActiveItemStackColumb();
                inventoryTableModel.setValueAt(inventoryTableModel.getValueAt(row,column),row,column);
                inventoryTableModel.fireTableCellUpdated(row,column);
            }
        });

    }

    /**
     * inits the button listeners
     */
    private void initButtons() {
        /* Unfortunately, due to restrictions of IDEA's GUI designer, i cannot set the buttons
           To compensate, the following madness is required. Yay......
        */
        colorButtonManager.setButtonListener(ChatColor.BLACK, blackButton);
        colorButtonManager.setButtonListener(ChatColor.DARK_BLUE, darkBlueButton);
        colorButtonManager.setButtonListener(ChatColor.DARK_GREEN, darkGreenButton);
        colorButtonManager.setButtonListener(ChatColor.DARK_AQUA, darkAquaButton);
        colorButtonManager.setButtonListener(ChatColor.DARK_RED, darkRedButton);
        colorButtonManager.setButtonListener(ChatColor.DARK_PURPLE, darkPurpleButton);
        colorButtonManager.setButtonListener(ChatColor.GOLD, goldButton);
        colorButtonManager.setButtonListener(ChatColor.GRAY, grayButton);
        colorButtonManager.setButtonListener(ChatColor.DARK_GRAY, darkGrayButton);
        colorButtonManager.setButtonListener(ChatColor.BLUE, blueButton);
        colorButtonManager.setButtonListener(ChatColor.GREEN, greenButton);
        colorButtonManager.setButtonListener(ChatColor.AQUA, aquaButton);
        colorButtonManager.setButtonListener(ChatColor.RED, redButton);
        colorButtonManager.setButtonListener(ChatColor.LIGHT_PURPLE, lightPurpleButton);
        colorButtonManager.setButtonListener(ChatColor.YELLOW, yellowButton);
        colorButtonManager.setButtonListener(ChatColor.WHITE, whiteButton);
        colorButtonManager.setButtonListener(ChatColor.MAGIC, magicButton);
        colorButtonManager.setButtonListener(ChatColor.BOLD, boldButton);
        colorButtonManager.setButtonListener(ChatColor.STRIKETHROUGH, strikethroughButton);
        colorButtonManager.setButtonListener(ChatColor.UNDERLINE, underlineButton);
        colorButtonManager.setButtonListener(ChatColor.ITALIC, italicButton);
        colorButtonManager.setButtonListener(ChatColor.RESET, resetButton);
    }

    /**
     * gets the root panel for use in the main() method to init the window
     * @return
     */
    public JPanel getRootPanel() {
        return panel1;
    }

    /**
     * Inits the variables that IDEA doesn't
     */
    private void createUIComponents() {
        DefaultTableModel model = new DefaultTableModel(6,9) {
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
    }


}
