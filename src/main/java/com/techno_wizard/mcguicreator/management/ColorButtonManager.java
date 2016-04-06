package com.techno_wizard.mcguicreator.management;

import com.techno_wizard.mcguicreator.gui.ChatColor;
import com.techno_wizard.mcguicreator.gui.MainMenu;

import javax.swing.*;

/**
 * Created by Ethan on 4/3/2016.
 */
public class ColorButtonManager {
    MainMenu menu;

    public ColorButtonManager(MainMenu mainMenu) {
        this.menu = mainMenu;
    }

    public void setButtonListener(ChatColor color, JButton button) {
        button.addActionListener(e -> onButtonPress(color, button));
    }

    private void onButtonPress(ChatColor color, JButton button) {
        System.out.println(color.toString() + " was fired");
    }
}
