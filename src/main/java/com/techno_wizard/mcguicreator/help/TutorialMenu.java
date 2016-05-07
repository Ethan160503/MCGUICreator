package com.techno_wizard.mcguicreator.help;


import com.techno_wizard.mcguicreator.gui.inventory.ItemUtil;

import javax.swing.*;

/**
 * Created by Zombie_Striker on 4/28/2016.
 */
public class TutorialMenu extends JFrame{
    private JButton basicsButton;
    private JButton advancedItemstackEditingButton;
    private JButton movingItemstacksButton;
    private JButton addingColorsButton;
    private JButton howToEditTheButton;

    private JLabel tutorialLabel;

    private JPanel contentFrame;
    private JImageAndTextPane tutorialText;
    private JLabel header;

    public TutorialMenu(){
        this.setContentPane(contentFrame);
        this.pack();
        this.setVisible(true);
        this.setName("Tutorial Content");

        tutorialText.setEditable(false);

        //TODO: Find way to add images to textfield. May be helpful when trying to explain what to do.

        this.setSize(500,500);

        tutorialText.setFont(ItemUtil.getMCFont(tutorialText.getFont()));
        tutorialLabel.setFont(ItemUtil.getMCFont(tutorialLabel.getFont()));
        header.setFont(ItemUtil.getMCFont(header.getFont()));

        init();
    }

    public void init() {
        basicsButton.addActionListener(e->getTutorialText(basicsButton));
        advancedItemstackEditingButton.addActionListener(e->getTutorialText(advancedItemstackEditingButton));
        addingColorsButton.addActionListener(e->getTutorialText(addingColorsButton));
        howToEditTheButton.addActionListener(e->getTutorialText(howToEditTheButton));
        movingItemstacksButton.addActionListener(e->getTutorialText(movingItemstacksButton));
    }

    public void getTutorialText(JButton b){
        //TODO: Move all the text to a .json file

        tutorialLabel.setText(b.getText());

        if(b==basicsButton){
            tutorialText.setText("TEST\n[image=0]\n\n**Basics**\n\n** Selecting Slots**\nTo select a slot to edit, click on the slot inside the inventory GUI. The slot will be highlighted (color we choose) if it has been selected.\n\n**Setting the itemstacks type*\nAfter selecting a slot, to set an itemstack's type you need to click the Material selector inside the Stack Details tab and scroll down the list till you find the itemstack you want. Keep in mind the names of each item are the same as those in-game, which are not the same as the Material types for Bukkit or Spigot \n\n**Setting the name of an itemstack** To change the name of an itemstack, click on the Name Editor inside the Stack Details tab. The name editor supports ChatColors and color codes.\n\n**Setting the lore of an itemstack**\nTo set the lore of an itemstack, select the Lore editor in the Lore tab. In here, each new line in the editor represents a new line in the lore. The lore editor supports chatcolors.\n\n**Adding Enchantments**\nTo add enchantments, you have to select the Enchantments tab. From there, select the enchantment you want to add and the power level. Once you set those values, click the \"Add Enchantment\" button to add the enchantment. To remove an enchantment, click the \"remove enchantment\" button while having the enchatnment selector set to thr enchantment you want to remove.\n\n Note that you can only add one of each type of enchantment to an itemstack. Adding a new enchentment that has the same enchantment type as another existing enchantment will override the previous enchantmet.\n\n==Editing the Inventory==\n\n**Chaning the name of the inventory**\nYou can edit the name of the inventory by editing the Iinventory name rditor inside the Inventory tab. The inventory name editor supports chatcolors.\n\n**Chaning the amount of rows for the inventory**\nTo change the amount of rows for the inventory, select the row count inside the Inventory tab. \n\nNote that any itemstack in the deleted row will also be delelted as well.");
            tutorialText.setImages(tutorialText.getImages(0,1));
        }else if(b == addingColorsButton){
            tutorialText.setText("TODO: Add stuff here. This is just here to show that other buttons work");
        }else{
            tutorialText.setText("Default: "+b.getText());
        }
    }

}
