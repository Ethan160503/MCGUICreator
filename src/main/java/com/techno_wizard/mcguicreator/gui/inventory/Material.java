package com.techno_wizard.mcguicreator.gui.inventory;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Created by Ethan on 4/3/2016.
 */
public enum Material {
    AIR("Null","Air"),
    POISONOUS_POTATO("/itemimages/Poisonous_potato.png","Poisonous_Potato"),
    POTATO("/itemimages/Potato.png","Potato"),
    WOOD_SWORD("/itemimages/Wood_sword.png","Wood_sword");

    private ImageIcon image;
    private String name;

    Material(String imagePath,String name){
        this.name = name;
        if(!name.equals("Air")) {
            this.image = new ImageIcon(getClass().getResource(imagePath));
        }else{
            image = new ImageIcon(new BufferedImage(150,150,BufferedImage.TYPE_INT_ARGB));
        }
    }

    /**
     * This returns the name of each materials. Use this to turn the material into a string.
     * @return
     */
    public String getName() {
        return name;
    }

    public ImageIcon getImage(){
        return image;
    }

    public Material getMaterialByName(String name){
        for(Material m: Material.values()){
            if(m.getName().equals(name))
                return m;
        }
        return null;
    }

}
