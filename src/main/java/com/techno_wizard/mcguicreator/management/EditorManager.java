package com.techno_wizard.mcguicreator.management;

import javax.swing.*;

/**
 * Created by Ethan on 4/6/2016.
 */
public class EditorManager {
    JEditorPane stackNameEditor;
    JCheckBox showFormattedTxtDetails;
    JCheckBox showFormattedTxtLore;
    JLabel stackItemCountLabel;
    JComboBox materialBox;
    JCheckBox enableEnchantCheckBox;
    JTextField notesBox;
    JEditorPane loreEditor;

    public EditorManager() {

    }


    public void initStack(JEditorPane stackNameEditor, JCheckBox showFormattedTxtDetails,
                          JCheckBox showFormattedTxtLore, JLabel stackItemCountLabel, JComboBox materialBox,
                          JCheckBox enableEnchantCheckBox, JTextField notesBox, JEditorPane loreEditor) {
        this.stackNameEditor = stackNameEditor;
        this.showFormattedTxtDetails = showFormattedTxtDetails;
        this.showFormattedTxtLore = showFormattedTxtLore;
        this.stackItemCountLabel = stackItemCountLabel;
        this.materialBox = materialBox;
        this.enableEnchantCheckBox = enableEnchantCheckBox;
        this.notesBox = notesBox;
        this.loreEditor = loreEditor;
    }
}
