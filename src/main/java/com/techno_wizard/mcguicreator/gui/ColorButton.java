package com.techno_wizard.mcguicreator.gui;

import javax.swing.*;

public class ColorButton extends JButton {
    ChatColor color;

    public ColorButton(String text, Icon icon, ChatColor color) {
        super(text, icon);
        this.color = color;
    }
}

