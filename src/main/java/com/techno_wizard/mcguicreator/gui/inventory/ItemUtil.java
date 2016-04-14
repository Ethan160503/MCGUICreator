package com.techno_wizard.mcguicreator.gui.inventory;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
     * returns the Roman Numeral value equal to the integer I (if the values is less than 10).
     * @param i
     * @return
     */
    public static String getRomanNumerals(int i){
        if(i==1) return "I";
        if(i==2) return "II";
        if(i==3) return "III";
        if(i==4) return "IV";
        if(i==5) return "V";
        if(i==6) return "VI";
        if(i==7) return "VII";
        if(i==8) return "VII";
        if(i==9) return "IX";
        if(i==10) return "X";
        return i+"";
    }
    public static int getIntegers(String romanNum){
        if(romanNum.equals("I"))  return 1;
        if(romanNum.equals("II"))  return 2;
        if(romanNum.equals("III"))  return 3;
        if(romanNum.equals("IV"))  return 4;
        if(romanNum.equals("V"))  return 5;
        if(romanNum.equals("VI"))  return 6;
        if(romanNum.equals("VII"))  return 7;
        if(romanNum.equals("VIII"))  return 8;
        if(romanNum.equals("IX"))  return 9;
        if(romanNum.equals("X"))  return 10;
        return Integer.valueOf(romanNum);
    }

    /**
     * resizes the icons to fit correctly inside of the jtable cells
     *
     * @param original original image
     * @return shrunk image
     */
    public static ImageIcon resizeIcon(ImageIcon original) {
        Image img = original.getImage();
        /*
                TODO: Find way to get the size of both the width and height of each slot
                Currently, when there are more than 5 slots, the icons get cut off.
                We need to resize the image to either the width or the height, depending on which is smaller
        */
        Image newimg = img.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    /**
     * adds the numbers which represent the amount to the icon.
     * @param original
     * @param amount
     * @return
     */
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

    /**
     * Returns a character form the ASCII spritesheet
     * @param column
     * @param row
     * @return
     */
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

    /**
     * Returns an instance of the Util Class.
     * @return
     */
    public static ItemUtil getUtil(){
        return util;
    }
}
