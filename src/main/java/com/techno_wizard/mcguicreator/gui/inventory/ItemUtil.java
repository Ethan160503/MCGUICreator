package com.techno_wizard.mcguicreator.gui.inventory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Frank_Jr on 4/4/2016.
 */
public class ItemUtil {

    /**
     * resizes the icons to fit correctly inside of the jtable cells
     * @param original original image
     * @return shrunk image
     */
    public static ImageIcon resizeIcon(ImageIcon original) {
        Image img = original.getImage();
        Image newimg = img.getScaledInstance(75, 75,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }
}
