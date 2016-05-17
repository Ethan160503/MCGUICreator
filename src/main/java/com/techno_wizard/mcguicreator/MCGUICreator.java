package com.techno_wizard.mcguicreator;

import com.techno_wizard.mcguicreator.codecreator.CodeCreator;
import com.techno_wizard.mcguicreator.codecreator.CodeGenerator;
import com.techno_wizard.mcguicreator.gui.ChatColor;
import com.techno_wizard.mcguicreator.gui.MainMenu;
import com.techno_wizard.mcguicreator.gui.codecreator.CodeExporter;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.ItemUtil;
import com.techno_wizard.mcguicreator.help.TutorialMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;


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
        JMenu fileExport = new JMenu("Export code...");
        JMenuItem exportToClipboard = new JMenuItem("Export to clipboard");
        JMenuItem exportToPopup = new JMenuItem("Export to popup");
        addExportActionListener(mainMenu,exportToClipboard,exportToPopup);

        menuBar.add(fileMenu);
        fileMenu.add(fileOpen);
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


        return menuBar;
    }
    private static void addExportActionListener(MainMenu mainMenu,JMenuItem exportToClipboard,JMenuItem exportToPopup){
        exportToClipboard.addActionListener(e -> { ItemStack is = mainMenu.getInvManager().getActiveItemStack();
            StringBuilder code = new StringBuilder();
            for (String s : CodeCreator.writecode(mainMenu)) {
                code.append(s + "\n");
            }
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(code.toString()), null);});
        exportToPopup.addActionListener(e -> {
            //Update the active itemstack
            ItemStack is = mainMenu.getInvManager().getActiveItemStack();
            StringBuilder code = new StringBuilder();
            /*for (String s : CodeCreator.writecode(mainMenu)) {
                code.append(s + "\n");
            }*/
            String output = CodeGenerator.getInstance().writeRepresentingJava(mainMenu.getInventoryTableModel());
            new CodeExporter(output);
        });
    }

    public static MainMenu getMainMenu(){
        return mainMenu;
    }
}
