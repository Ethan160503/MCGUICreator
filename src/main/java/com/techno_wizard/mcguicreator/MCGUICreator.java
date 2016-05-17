package com.techno_wizard.mcguicreator;

import com.techno_wizard.mcguicreator.codecreator.CodeCreator;
import com.techno_wizard.mcguicreator.codecreator.CodeGenerator;
import com.techno_wizard.mcguicreator.gui.ChatColor;
import com.techno_wizard.mcguicreator.gui.InventoryTableModel;
import com.techno_wizard.mcguicreator.gui.MainMenu;
import com.techno_wizard.mcguicreator.gui.codecreator.CodeExporter;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.ItemUtil;
import com.techno_wizard.mcguicreator.help.TutorialMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;


/**
 * Created by Ethan on 4/1/2016.
 */
public class MCGUICreator {

    private static MainMenu mainMenu;

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        mainMenu = new MainMenu();
        frame.setContentPane(mainMenu.getRootPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setSize(1250,600);


        frame.setJMenuBar(getMenuBar(mainMenu));
        frame.setVisible(true);
    }

    private static JMenuBar getMenuBar(MainMenu mainMenu){
        JMenuBar menuBar = new JMenuBar();

        menuBar.setFont(ItemUtil.getMCFont(menuBar.getFont()));

        JMenu fileMenu = new JMenu("File");
        JMenuItem fileOpen = new JMenuItem("Open");
        JMenuItem fileSave = new JMenuItem("Save");
        JMenu fileExport = new JMenu("Export code...");
        JMenuItem exportToClipboard = new JMenuItem("Export to clipboard");
        JMenuItem exportToPopup = new JMenuItem("Export to popup");
        addActionListener(mainMenu,exportToClipboard,exportToPopup,fileSave,fileOpen);


        menuBar.add(fileMenu);
        fileMenu.add(fileOpen);
        fileMenu.add(fileSave);
        fileMenu.add(fileExport);
        fileExport.add(exportToClipboard);
        fileExport.add(exportToPopup);


        //TODO: figure out why this does not update.
        JMenu chatColor = new JMenu("ChatColor");
        menuBar.add(chatColor);
        for(ChatColor c : ChatColor.values()) {
            JMenuItem color = new JMenuItem(c.getName());
            color.addActionListener(e -> mainMenu.getEditorManager().getTextEditorManager().editSelectedEditor(c.getColorCode()));
            chatColor.add(color);
        }


        JMenu helpMenu = new JMenu("[HELP]");
        JMenuItem helpItem = new JMenuItem("Open Tutorial Window");
        helpItem.addActionListener(e ->new TutorialMenu());
        helpMenu.add(helpItem);
        menuBar.add(helpMenu);


        return menuBar;
    }
    private static void addActionListener(MainMenu mainMenu,JMenuItem exportToClipboard,JMenuItem exportToPopup,JMenuItem fileSave,JMenuItem fileOpen){
        exportToClipboard.addActionListener(e -> { ItemStack is = mainMenu.getInvManager().getActiveItemStack();
            StringBuilder code = new StringBuilder();
            for (String s : CodeCreator.writecode(mainMenu)) {
                code.append(s + "\n");
            }
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(code.toString()), null);});
        exportToPopup.addActionListener(e -> {
            ItemStack is = mainMenu.getInvManager().getActiveItemStack();
            StringBuilder code = new StringBuilder();
            String output = CodeGenerator.getInstance().writeRepresentingJava(mainMenu.getInvManager().getInventoryTableModel());
            new CodeExporter(output);
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
                    File out = new File(chooser.getSelectedFile()+fileType);
                    if(!out.exists())
                        out.createNewFile();
                    FileOutputStream fos = new FileOutputStream(out);
                    ObjectOutputStream ous = new ObjectOutputStream(fos);
                    ous.writeObject(mainMenu.getInvManager().getInventoryTableModel());
                    ous.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                mainMenu.remove(chooser);
            }else {
                System.out.println("Save option not approved!");
                mainMenu.remove(chooser);
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
                    if(open.exists()) {
                        FileInputStream fis = new FileInputStream(open);
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        InventoryTableModel itm = (InventoryTableModel) ois.readObject();
                        mainMenu.getInvManager().transferData(itm);
                        ois.close();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                mainMenu.remove(chooser);
            }else {
                System.out.println("Option not approved!");
                mainMenu.remove(chooser);
            }
        });
    }

    public static MainMenu getMainMenu(){
        return mainMenu;
    }
}
