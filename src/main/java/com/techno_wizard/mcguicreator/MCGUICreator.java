package com.techno_wizard.mcguicreator;

import com.techno_wizard.mcguicreator.gui.MainMenu;

import javax.swing.*;


/**
 * Created by Ethan on 4/1/2016.
 */
public class MCGUICreator {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new MainMenu().getRootPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem fileOpen = new JMenuItem("Open");
        JMenu fileExport = new JMenu("Export code...");
        JMenuItem exportToClipboard = new JMenuItem("Export to clipboard");
        JMenuItem exportToPopup = new JMenuItem("Export to popup");

        menuBar.add(fileMenu);
        fileMenu.add(fileOpen);
        fileMenu.add(fileExport);
        fileExport.add(exportToClipboard);
        fileExport.add(exportToPopup);

        frame.setJMenuBar(menuBar);


        frame.setVisible(true);
    }
}
