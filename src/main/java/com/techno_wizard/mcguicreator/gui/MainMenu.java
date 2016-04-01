package com.techno_wizard.mcguicreator.gui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by Ethan on 4/1/2016.
 */
public class MainMenu extends JFrame {
    private JTable inventoryTable;
    private JPanel panel1;
    private JButton black0Button;
    private JButton darkRed4Button;
    private JButton darkPurple5Button;
    private JButton darkBlue1Button;
    private JButton darkAqua3Button;
    private JButton gold6Button;
    private JButton gray7Button;
    private JButton darkGray8Button;
    private JButton blue9Button;
    private JButton greenAButton;
    private JButton aquaBButton;
    private JButton redCButton;
    private JButton lightPurpleDButton;
    private JButton yellowEButton;
    private JButton whiteFButton;
    private JButton darkGreen2Button;
    private JButton strikethroughMButton;
    private JButton boldLButton;
    private JButton randomKButton;
    private JButton underlineUButton;
    private JButton italicOButton;
    private JButton resetRButton;
    private JEditorPane editorPane1;
    private JCheckBox showFormattedTextCheckBoxLore;
    private JCheckBox showFormattedTextCheckBoxDetails;

    public MainMenu() {
        super("MCGUICreator");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inventoryTable.getModel().setValueAt(new ImageIcon("itemimages/Poisonous_potato.png"), 0, 0);
        inventoryTable.setRowHeight(75);

        setVisible(true);
    }

    public JPanel getRootPanel() {
        return panel1;
    }

    private void createUIComponents() {
        DefaultTableModel model = new DefaultTableModel(6,6) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return ImageIcon.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        inventoryTable = new JTable(model);
    }
}
