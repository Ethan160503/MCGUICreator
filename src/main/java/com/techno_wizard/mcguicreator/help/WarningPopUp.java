package com.techno_wizard.mcguicreator.help;

import com.techno_wizard.mcguicreator.gui.inventory.ItemUtil;

import javax.swing.*;

/**
 * Created by Zombie_Striker on 5/6/2016.
 */
public class WarningPopUp extends JFrame {
    private JButton decline;
    private JButton accept;
    private JTextPane warning;
    private JPanel panel;

    private WarningResult warningResult;

    public WarningPopUp(String warningText, WarningResult warningResult) {
        this.setContentPane(panel);
        this.setName("Warning");
        this.pack();
        this.warningResult = warningResult;
        this.setSize(400, 200);

        warning.setFont(ItemUtil.getMCFont(warning.getFont()));
        setFont(ItemUtil.getMCFont(warning.getFont()));
        warning.setText(warningText);
        this.setVisible(true);

        init();
    }

    private void init() {
        accept.addActionListener(e -> {
            warningResult.onActivate();
            this.dispose();
        });
        decline.addActionListener(e -> {
            warningResult.onCancel();
            this.dispose();
        });
    }
}
