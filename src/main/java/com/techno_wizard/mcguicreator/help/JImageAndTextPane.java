package com.techno_wizard.mcguicreator.help;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Zombie_Striker on 5/6/2016.
 */
public class JImageAndTextPane extends JEditorPane {

    private BufferedImage[] images;


    public JImageAndTextPane(){
    }

    @Override
    public void paint(Graphics g) {
        int fontSize = getFont().getSize();
        super.paint(g);

        int lineID = 0;

        for (String line : getText().split("\n")) {
            lineID++;
            if (line.contains("[image")) {
                //Default image code is  ' [image=#] '
                //Each image in the /tutorial/ folder should have the name [Tutorial ID]-[0-9]
                //Note that this will only accept up to 10 images, as I can't image using any more than that for a single menu.
                int imageID = Integer.parseInt(line.split("=")[1].charAt(0) + "");
                g.drawImage(images[imageID], 0, fontSize * lineID, null);
            }
        }
    }


    public BufferedImage[] loadImages(int tutorialID){
        BufferedImage[] bia = new BufferedImage[10];
        for(int i = 0;i<10;i++){
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/tutorial/" + tutorialID + "-" + i + ".png"));
                bia[i] = image;
            }catch(Exception e){
                break;
            }
        }
        return bia;
    }
    public void setImages(BufferedImage[] bi){
        this.images = bi;
    }
}
