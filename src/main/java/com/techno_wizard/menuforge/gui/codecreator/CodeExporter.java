package com.techno_wizard.menuforge.gui.codecreator;

import com.techno_wizard.menuforge.gui.inventory.ItemUtil;

import javax.swing.*;

/**
 * Created by Zombie_Striker on 4/6/2016.
 */
public class CodeExporter extends JFrame {

    private JPanel panel1;
    private JTextPane printedCode;

    public CodeExporter(String code) {
        this.setContentPane(panel1);
        this.pack();
        this.setVisible(true);
        this.setName("Exported Code");
        printedCode.setFont(ItemUtil.getMCFont(printedCode.getFont()));
        printedCode.setText(code);
    }

}
