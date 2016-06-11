package com.techno_wizard.mcguicreator.help;


import com.sun.java.swing.plaf.windows.DesktopProperty;
import com.techno_wizard.mcguicreator.gui.inventory.ItemUtil;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.swing.*;
import java.awt.*;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Zombie_Striker on 4/28/2016.
 */
public class TutorialMenu extends JFrame {
    private JButton basicsButton;
    private JButton advancedItemstackEditingButton;
    private JButton exportButton;
    private JButton howToEditTheButton;

    private JLabel tutorialLabel;

    private JPanel contentFrame;
    private JImageAndTextPane tutorialText;
    private JLabel header;

    private JSONObject tutorialJson;

    public TutorialMenu() {
        this.setContentPane(contentFrame);
        this.pack();
        this.setVisible(true);
        this.setName("Tutorial Content");

        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/text/tutorial_text.json"));
        tutorialJson = (JSONObject) JSONValue.parse(reader);

        tutorialText.setEditable(false);

        this.setSize(500, 500);

        tutorialText.setFont(ItemUtil.getMCFont(tutorialText.getFont()));
        tutorialLabel.setFont(ItemUtil.getMCFont(tutorialLabel.getFont()));
        header.setFont(ItemUtil.getMCFont(header.getFont()));

        init();
    }

    public void init() {
        basicsButton.addActionListener(e -> getTutorialText(basicsButton));
        advancedItemstackEditingButton.addActionListener(e -> getTutorialText(advancedItemstackEditingButton));
        howToEditTheButton.addActionListener(e -> getTutorialText(howToEditTheButton));
        exportButton.addActionListener(e -> getTutorialText(exportButton));
    }

    public void getTutorialText(JButton b) {
        tutorialLabel.setText(b.getText());
        JSONObject text = (JSONObject) tutorialJson.get("text");
        String template;
        int id = 0;

        if (b == basicsButton)
            id = 0;
        if (b == advancedItemstackEditingButton)
            id = 1;
        if (b == exportButton)
            id = 2;
        if (b == howToEditTheButton)
            id = 3;

        template = (String) text.get("tutorial-" + id);
        tutorialText.setText(template);
        tutorialText.setImages(tutorialText.loadImages(id));
    }

}
