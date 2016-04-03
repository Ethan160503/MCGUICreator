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
        frame.setVisible(true);
    }
}
