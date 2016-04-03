package com.techno_wizard.mcguicreator.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private ColorButtonManager colorButtonManager;

    public MainMenu() {
        setName("MC GUI Creator");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inventoryTable.getModel().setValueAt(resizeIcon(new ImageIcon(getClass().getResource("/itemimages/Poisonous_potato.png"))), 0, 0);
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


        setVisible(true);
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

    /**
     * resizes the icons to fit correctly inside of the jtable cells
     * @param original original image
     * @return shrunk image
     */
    private ImageIcon resizeIcon(ImageIcon original) {
        Image img = original.getImage();
        Image newimg = img.getScaledInstance(75, 75,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

}
