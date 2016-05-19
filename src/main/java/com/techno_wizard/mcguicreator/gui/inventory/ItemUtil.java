package com.techno_wizard.mcguicreator.gui.inventory;


import sun.awt.IconInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by Zombie_Striker on 4/4/2016.
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

    public static int getRowAt(int y,int rows){return (y/(300/rows));}
    public static int getColumnAt(int x,int cols){
        return (x/(600/cols));
    }
    public static int getMaxYAt(int y,int rows){return (y*(300/rows));}
    public static int getMaxXAt(int x,int cols){
        return (x*(600/cols));
    }


    public static Font getMCFont(Font oldFont){
        InputStream in = util.getClass().getResourceAsStream("/font/minecraft_font.ttf");
        Font font=null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, in);
        }catch(Exception e){
            e.printStackTrace();
        }
        if(font!=null){
            return font.deriveFont(oldFont.getStyle(), oldFont.getSize2D());
        }
        /*if(font!= null) {
            UIDefaults defs = UIManager.getLookAndFeelDefaults();
            for (Iterator i = defs.keySet().iterator(); i.hasNext(); ) {
                Object keyObject = i.next();
                if (keyObject instanceof StringBuffer) {
                    StringBuffer key = (StringBuffer) keyObject;
                    if (key.toString().endsWith(".font")) {
                        Font oldFont = defs.getFont(key.toString());
                        defs.put(key.toString(), font.deriveFont(oldFont.getStyle(), 1f * oldFont.getSize2D()));
                    }
                } else if (keyObject instanceof String) {
                    String key = (String) keyObject;
                    if (key.endsWith(".font")) {
                        Font oldFont = defs.getFont(key);
                        defs.put(key, font.deriveFont(oldFont.getStyle(), 1f * oldFont.getSize2D()));
                    }
                }
            }
        }*/
        return oldFont;
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
        Image newimg = img.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
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

    public static ImageIcon addEnchantments(ImageIcon icon){
        BufferedImage bi = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();
        g.drawImage(icon.getImage(),0,0,null);
        try {
            BufferedImage glow = ImageIO.read(ItemUtil.class.getResourceAsStream("/extraimg/enchantment.png"));
            for(int y = 0; y < bi.getHeight();y++){
                for(int x = 0; x < bi.getWidth();x++){
                    if(bi.getAlphaRaster().getPixel(x,y,new float[10])[0]>0)
                     g.drawImage(glow,x,y,null);
                }
                System.out.println("");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        g.dispose();
        return new ImageIcon(bi);
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
     * Returns the size of each character for the minecraft font
     * @param c
     * @return the size
     */
    public static int getCharSize(char c){
        switch(c){
            //TODO:Add more values
            case 'i':return 4;
            case 'j':return 4;
            case 'l':return 4;
            case '1':return 4;
            case '!':return 4;
            case '^':return 4;
            case '*':return 4;
            case '.':return 4;
            case ',':return 4;
            case '|':return 4;
            case '\'':return 4;
            case '"':return 4;
            default:return 8;
        }
    }

    /**
     * Returns an instance of the Util Class.
     * @return
     */
    public static ItemUtil getUtil(){
        return util;
    }

    public static ImageIcon tintImage(ImageIcon icon, Color tint){
        //Turn the icon into a BufferdImage
        BufferedImage bi = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics gr = bi.createGraphics();
        icon.paintIcon(null, gr, 0,0);
        gr.dispose();

        BufferedImage tintBi = new BufferedImage(bi.getWidth(),bi.getHeight(),BufferedImage.TYPE_INT_ARGB);
        for(int y = 0; y < bi.getHeight();y++){
            for(int x = 0; x <  bi.getWidth();x++){
                Color oldColor = new Color(bi.getRGB(x,y));
                if(oldColor.getRed()==0&&oldColor.getGreen()==0&&oldColor.getBlue()==0)
                    continue;
                double r = ((double)oldColor.getRed()/255)*tint.getRed();
                double g = ((double)oldColor.getGreen()/255)*tint.getGreen();
                double b = ((double)oldColor.getBlue()/255)*tint.getBlue();
                Color tintColor = new Color((int)(r),(int)(g),(int)(b));
                tintBi.setRGB(x,y,tintColor.getRGB());
            }
        }
        return new ImageIcon(tintBi);
    }

}
