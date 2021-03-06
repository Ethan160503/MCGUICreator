package com.techno_wizard.menuforge;

import com.techno_wizard.menuforge.codecreator.CodeGenerator;
import com.techno_wizard.menuforge.data_retention.StoredInventory;
import com.techno_wizard.menuforge.gui.ChatColor;
import com.techno_wizard.menuforge.gui.MainMenu;
import com.techno_wizard.menuforge.gui.codecreator.CodeExporter;
import com.techno_wizard.menuforge.gui.inventory.ItemStack;
import com.techno_wizard.menuforge.gui.inventory.ItemUtil;
import com.techno_wizard.menuforge.gui.inventory.Material;
import com.techno_wizard.menuforge.help.TutorialMenu;
import com.techno_wizard.menuforge.help.WarningPopUp;
import com.techno_wizard.menuforge.help.WarningResult;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ethan on 4/1/2016.
 */
public class MenuForge {

    private static MainMenu mainMenu;

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        mainMenu = new MainMenu();
        frame.setContentPane(mainMenu.getRootPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setSize(1250, 600);


        frame.setJMenuBar(getMenuBar(mainMenu));
        frame.setVisible(true);
    }

    private static JMenuBar getMenuBar(MainMenu mainMenu) {
        JMenuBar menuBar = new JMenuBar();

        menuBar.setFont(ItemUtil.getMCFont(menuBar.getFont()));

        JMenu fileMenu = new JMenu("File");
        JMenuItem fileOpen = new JMenuItem("Open");
        JMenuItem fileSave = new JMenuItem("Save");
        JMenu fileExport = new JMenu("Export code...");
        JMenuItem exportToClipboard = new JMenuItem("Export to clipboard");
        JMenuItem exportToPopup = new JMenuItem("Export to popup");
        addActionListener(mainMenu, exportToClipboard, exportToPopup, fileSave, fileOpen);


        menuBar.add(fileMenu);
        fileMenu.add(fileOpen);
        fileMenu.add(fileSave);
        fileMenu.add(fileExport);
        fileExport.add(exportToClipboard);
        fileExport.add(exportToPopup);


        //TODO: figure out why this does not update.
        JMenu chatColor = new JMenu("ChatColor");
        menuBar.add(chatColor);
        for (ChatColor c : ChatColor.values()) {
            JMenuItem color = new JMenuItem(c.getName());
            color.addActionListener(e -> mainMenu.getEditorManager().getTextEditorManager().editSelectedEditor(c.getColorCode()));
            chatColor.add(color);
        }


        JMenu helpMenu = new JMenu("[HELP]");
        JMenuItem helpItem = new JMenuItem("Open Tutorial Window");
        helpItem.addActionListener(e -> new TutorialMenu());
        helpMenu.add(helpItem);
        menuBar.add(helpMenu);


        return menuBar;
    }

    private static void addActionListener(MainMenu mainMenu, JMenuItem exportToClipboard, JMenuItem exportToPopup, JMenuItem fileSave, JMenuItem fileOpen) {
        exportToClipboard.addActionListener(e -> {
            printCode(false);
        });
        exportToPopup.addActionListener(e -> {
            printCode(true);
        });

        fileSave.addActionListener(e -> {

            //TODO: Do we want to use another file type?
            String fileType = ".txt";

            JFileChooser chooser = new JFileChooser();
            mainMenu.add(chooser);

            chooser.setSelectedFile(new File("Inventory"));
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Save to...");
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(mainMenu) == JFileChooser.APPROVE_OPTION) {
                try {
                    File out = new File(chooser.getSelectedFile() + fileType);
                    if (!out.exists())
                        //noinspection ResultOfMethodCallIgnored
                        out.createNewFile();
                    FileOutputStream fos = new FileOutputStream(out);
                    ObjectOutputStream ous = new ObjectOutputStream(fos);

                    // create storage class
                    StoredInventory toStore = new StoredInventory();
                    toStore.setInventoryModel(mainMenu.getInvManager().getInventoryTableModel());

                    // write
                    ous.writeObject(toStore);
                    ous.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mainMenu, "There was an error reading the file.\n" +
                                    "This may be a save file for an outdated MenuForge version or a corrupt file.",
                            "Oops...", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        fileOpen.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            mainMenu.add(chooser);

            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Open...");
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(mainMenu) == JFileChooser.APPROVE_OPTION) {
                try {
                    File open = chooser.getSelectedFile();
                    if (open.exists()) {
                        FileInputStream fis = new FileInputStream(open);
                        ObjectInputStream ois = new ObjectInputStream(fis);

                        StoredInventory storedInventory = (StoredInventory) ois.readObject();
                        storedInventory.loadData(mainMenu.getInvManager().getInventoryTableModel());
                        mainMenu.getInvManager().transferData(mainMenu.getInvManager().getInventoryTableModel());
                        ois.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mainMenu, "File could not be opened. Either it is for an outdated version of\n" +
                            "MenuForge or it is corrupt.\n" +
                            "We try to avoid making updates that break save files, but sometimes it is necessary to add features.");
                }
                mainMenu.remove(chooser);
            } else {
                mainMenu.remove(chooser);
            }
        });
    }

    public static void printCode(boolean shouldExport) {
        mainMenu.getEditorManager().saveCurrentItemStack();
        List<String> names = new ArrayList<>();
        List<Integer> slotsNoName = new ArrayList<>();
        List<Integer> slotsSameName = new ArrayList<>();
        boolean isEmpty = true;
        for (int index = 0; index < getMainMenu().getInvManager().getInventoryTableModel().getRowCount() * 9; index++) {
            ItemStack is = getMainMenu().getInvManager().getInventoryTableModel().getItemStackAt(index % 9, index / 9);
            if (is.getMaterial() != Material.AIR) {
                isEmpty = false;
                if (is.getName().length() == 0) {
                    slotsNoName.add(index);
                } else if (names.contains(is.getName())) {
                    slotsSameName.add(index);
                } else {
                    names.add(is.getName());
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        if (isEmpty) {
            sb.append("#-The Inventory is empty!");
        }
        if (slotsNoName.size() > 0) {
            sb.append("\n#-The following slots have no name.");
            for (int i : slotsNoName)
                sb.append("\n -" + i);
        }
        if (slotsSameName.size() > 0) {
            sb.append("\n#-The following slots have the same name as an already loaded slot.");
            for (int i : slotsSameName)
                sb.append("\n -" + i + " : \" " + getMainMenu().getInvManager().getInventoryTableModel().getItemStackAt(i % 9, i / 9).getName() + " \"");
        }
        if (slotsSameName.size() > 0 || slotsNoName.size() > 0 || isEmpty) {
            new WarningPopUp(sb.toString(), new WarningResult() {
                public void onActivate() {
                    if (shouldExport) {
                        ItemStack is = mainMenu.getInvManager().getActiveItemStack();
                        StringBuilder code = new StringBuilder();
                        String output = CodeGenerator.getInstance().writeRepresentingJava(mainMenu.getInvManager().getInventoryTableModel());
                        new CodeExporter(output);
                    } else {
                        ItemStack is = mainMenu.getInvManager().getActiveItemStack();
                        String output = CodeGenerator.getInstance().writeRepresentingJava(mainMenu.getInvManager().getInventoryTableModel());
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(new StringSelection(output), null);
                    }
                }

                public void onCancel() {
                }
            });
        } else {
            if (shouldExport) {
                mainMenu.getEditorManager().saveCurrentItemStack();
                String output = CodeGenerator.getInstance().writeRepresentingJava(mainMenu.getInvManager().getInventoryTableModel());
                new CodeExporter(output);
            } else {
                mainMenu.getEditorManager().saveCurrentItemStack();
                String output = CodeGenerator.getInstance().writeRepresentingJava(mainMenu.getInvManager().getInventoryTableModel());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(output), null);
            }
        }
        names.clear();
        slotsNoName.clear();
        slotsSameName.clear();
    }

    public static MainMenu getMainMenu() {
        return mainMenu;
    }
}
