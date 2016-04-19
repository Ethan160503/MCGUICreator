package com.techno_wizard.mcguicreator.gui;

import com.techno_wizard.mcguicreator.gui.inventory.Enchantment;
import com.techno_wizard.mcguicreator.gui.inventory.ItemStack;
import com.techno_wizard.mcguicreator.gui.inventory.ItemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Created by Zombie_Striker on 4/18/2016.
 */
public class InventoryHoverOverGUI{

    private JTable inv;
    private JPanel panel;
    private InventoryTableModel model;

    //This represents the top left corner of the gui
    private int x;
    private int y;
    private int xP;
    private int yP;

    private boolean shouldDraw = true;
    private Thread thread;

    public InventoryHoverOverGUI(JTable j,JPanel panel,InventoryTableModel model){

        this.inv = j;
        this.panel = panel;
        this.model = model;


        MouseMotionListener mouseListener = getMouseListener();
        inv.addMouseMotionListener(mouseListener);

        MouseListener exitListener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                panel.updateUI();
                shouldDraw = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                shouldDraw = true;
            }
        };
        inv.addMouseListener(exitListener);

        createMenu();

    }

    public void createMenu(){
        Runnable r = ()-> {
            while(true) {
                try {
                    thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(shouldDraw) {
                    Graphics g = panel.getGraphics();
                    if (g != null) {
                        //Create the background
                        if (ItemUtil.getColumnAt(xP, 9) > 8 || ItemUtil.getRowAt(yP, 3) > 2)
                            continue;

                        //Get the itemstack
                        ItemStack is = model.getItemStackAt(ItemUtil.getColumnAt(xP, 9), ItemUtil.getRowAt(yP, 3));
                        int yOffset = 0;
                        if (is == null)
                            continue;
                        List<String> lore = is.getLoreAsList();
                        List<Enchantment> ench = is.getEnchantments();

                        //Set the dimentions
                        int height = 10 + 12 * (lore.size() + ench.size());
                        int width = 80;
                        if(is.getName().length()*6>80)
                            width =is.getName().length()*6;
                        for(String loreL :lore)
                            if(loreL.length()*6 > 80)
                                width = loreL.length()*6;

                        //Draw it
                        g.setColor(Color.gray);
                        g.fillRect(xP, yP, width, 15);
                        g.setColor(Color.lightGray);
                        g.fillRect(xP, yP+15, width, height);
                        g.setColor(Color.black);
                        g.drawString(is.getName() + "", xP+2, yP+13);
                        for (int loreLine = 0; loreLine < lore.size(); loreLine++) {
                            g.drawString(lore.get(loreLine), xP+2, yP + 30 + (yOffset * 12));
                            yOffset++;
                        }
                        for (int enchLine = 0; enchLine < ench.size(); enchLine++) {
                            g.drawString(ench.get(enchLine).getDisplay(), xP+2, yP + 30 + (yOffset * 12));
                            yOffset++;
                        }
                        g.drawRect(xP,yP,width, height+15);
                        g.dispose();
                        panel.update(g);
                    }
                }
            }
        };

        thread = new Thread(r);
        thread.start();
    }

    public MouseMotionListener getMouseListener() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                //Make sure the person at least moved the mouse. Don't want to create unnecessary lag.
                int tempx = ItemUtil.getColumnAt(e.getX(),model.getColumnCount());
                int tempy = ItemUtil.getRowAt(e.getY(),model.getRowCount());
                if (tempx != x || tempy != y) {
                    x = tempx;
                    y = tempy;
                    xP = ItemUtil.getMaxXAt(x,model.getColumnCount())+(600/model.getColumnCount())-1;
                    yP = ItemUtil.getMaxYAt(y,model.getRowCount());

                    panel.updateUI();
                }
            }
        };
    }
}
