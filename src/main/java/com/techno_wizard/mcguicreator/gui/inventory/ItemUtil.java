package com.techno_wizard.mcguicreator.gui.inventory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PackedColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Frank_Jr on 4/4/2016.
 */
public class ItemUtil {

    private static ItemUtil util;
    static{
        util = new ItemUtil();
    }

    /**
     * resizes the icons to fit correctly inside of the jtable cells
     *
     * @param original original image
     * @return shrunk image
     */
    public static ImageIcon resizeIcon(ImageIcon original) {
        Image img = original.getImage();
        Image newimg = img.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }
    public static ImageIcon addAmount(ImageIcon original,int amount){
        BufferedImage bi = new BufferedImage(original.getIconWidth(),original.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();
        int number1 = amount/10;
        int number2 = amount%10;
        g.drawImage(original.getImage(),0,0,null);
        if(number1 !=0)
        g.drawImage(getCharacter(number1,3),42,57,16,16,null);
        if(number2>1||number1>0)
        g.drawImage(getCharacter(number2,3),57,57,16,16,null);
        g.dispose();
        ImageIcon i = new ImageIcon(bi);
        return i;
    }
    public static BufferedImage getCharacter(int column,int row){
        //Starting with 1, the Numbers start at line 3
        //Size of each character (8 x 8)
        int textSize = 8;
        try {
            BufferedImage i = ImageIO.read(util.getClass().getResourceAsStream("/extraimg/ascii.png"));
            return i.getSubimage(column*textSize,row*textSize,textSize,textSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ItemUtil getUtil(){
        return util;
    }
}
